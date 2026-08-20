<script setup lang="ts">
import type { ClicksInfo } from '@slidev/types'
import { computed, nextTick, onBeforeUnmount, onMounted, onUnmounted, ref, shallowRef, useId, watch } from 'vue'
import { useNav, useSlideContext } from '@slidev/client'
import { useMutationObserver, useResizeObserver } from '@vueuse/core'

const props = defineProps<{
  /** One-based source line number inside the single fenced code block this component wraps. */
  line: number
  message: string
  /** Slidev click at which to reveal the diagnostic. Supports relative values like "+1". Omit (or pass 0) to show it from the start. */
  at?: number | string
  /** First Slidev click at which to hide the diagnostic. */
  until?: number | string
  /**
   * `at` and `until` in one, for a diagnostic that belongs to a single click:
   * `:on="2"` is `:at="2" :until="3"`, and `on="0"` shows it before the first
   * click only. Give `at` and `until` separately for a diagnostic that stays
   * on screen for several clicks.
   */
  on?: number | string
}>()

const { $clicksContext, $clicks, $scale, $zoom } = useSlideContext()
const { isPrintMode } = useNav()
const id = useId()
const container = ref<HTMLElement>()
const position = ref({ left: '0px', top: '0px', maxWidth: 'none' })
// The wavy underline is one absolutely positioned element spanning the whole
// offending expression: per-token `text-decoration: underline wavy` restarts
// the wave's phase at every Shiki span boundary, breaking the squiggle.
const underline = ref({ left: '0px', top: '0px', width: '0px' })
// The requested line's token elements in the rendered code. A Magic Move step
// may not contain the line (yet), and an empty line has no tokens; without a
// target the message has no anchor and must stay hidden.
const targets = shallowRef<HTMLElement[]>([])
const hasTarget = computed(() => targets.value.length > 0)
// True while Shiki Magic Move is transitioning between steps.
const animating = ref(false)

// Slidev reserves click 0 for the pre-click state and warns when anything
// registers at 0, so `at` values <= 0 mean "visible from the start".
function isFromStart(at?: number | string) {
  if (at === undefined)
    return true
  if (typeof at === 'string' && '+-'.includes(at[0]))
    return false
  return +at <= 0
}
// `on` resolves into the separate values here, so everything below only ever
// reads `at` and `until`. Slidev ranges accept a relative end, so '+1' means
// "one click after the start" whether `on` itself is absolute or relative; an
// `on` that shows from the start is gone at the first click.
if (import.meta.env.DEV && props.on !== undefined && (props.at !== undefined || props.until !== undefined))
  console.warn('[InlineCompilerError] `on` is `at` and `until` in one, so the separate `at` / `until` given here are ignored. Use either `on` alone, or `at` and `until`.')
const at = props.on ?? props.at
const until = props.on === undefined ? props.until : isFromStart(props.on) ? 1 : '+1'
const fromStart = isFromStart(at)
// Click resolution waits for onMounted so relative values see the clicks
// registered by the elements mounted before this one, mirroring how Slidev's
// own components resolve their `at` props.
const clickInfo = shallowRef<ClicksInfo | null>(null)
onMounted(() => {
  if (!$clicksContext)
    return
  if (!fromStart) {
    clickInfo.value = until === undefined
      ? $clicksContext.calculateSince(at!)
      : $clicksContext.calculateRange([at!, until])
  }
  else if (until !== undefined) {
    // Visible from the start; the range still registers the hide click so the
    // slide waits for it even when nothing else on the slide is clickable.
    clickInfo.value = $clicksContext.calculateRange([1, until])
  }
  if (clickInfo.value)
    $clicksContext.register(id, clickInfo.value)
})
onUnmounted(() => $clicksContext?.unregister(id))
const visible = computed(() => {
  const info = clickInfo.value
  if (!info)
    return true
  return fromStart ? $clicks.value < info.end : info.isActive.value
})

// The diagnostic must not appear while Magic Move is still animating, and a
// click can reveal it a few frames before the step transition it also
// triggers begins. `revealed` therefore trails `canReveal` by a beat, so the
// message never flashes at a stale position.
const canReveal = computed(() => visible.value && hasTarget.value && !animating.value)
const revealed = ref(false)
// Classes Shiki Magic Move keeps on its elements only while a step transition
// is playing. They are private constants of MagicMoveRenderer (renderer.mjs
// in @shikijs/magic-move), so `sawAnimating` below asserts in DEV that they
// still exist after an upgrade.
const MAGIC_MOVE_ANIMATING = [
  '.shiki-magic-move-enter-active',
  '.shiki-magic-move-leave-active',
  '.shiki-magic-move-move',
  '.shiki-magic-move-container-resize',
  '.shiki-magic-move-container-restyle',
].join(', ')
let sawAnimating = false
let stepSwaps = 0
let warnedClassDrift = false

// The wrapped code block renders in one of two shapes, discovered lazily
// because Magic Move only renders after mount. The <pre>/<code> element
// itself survives step changes, so it is cached across syncs.
let codeHost: HTMLElement | null = null
let isMagicMove = false

function findLineTargets(host: HTMLElement): HTMLElement[] {
  if (!codeHost?.isConnected) {
    // Shiki Magic Move v2 renders tokens directly in a <pre>, separated by
    // <br> elements; a static block renders `.line` spans inside <code>.
    codeHost = host.querySelector<HTMLElement>('pre.shiki-magic-move-container')
    isMagicMove = !!codeHost
    if (!codeHost)
      codeHost = host.querySelector<HTMLElement>('.slidev-code code')
    if (!codeHost)
      return []
  }

  const tokens: HTMLElement[] = []
  if (isMagicMove) {
    let currentLine = 1
    for (const child of codeHost.children) {
      if (child.tagName === 'BR') {
        if (++currentLine > props.line)
          break
        continue
      }
      // Outgoing tokens of the previous step linger (absolutely positioned)
      // during a transition and must not count toward the new step's lines.
      if (currentLine === props.line && child instanceof HTMLElement
        && !child.className.includes('shiki-magic-move-leave'))
        tokens.push(child)
    }
  }
  else {
    const lineEl = codeHost.querySelectorAll<HTMLElement>('.line')[props.line - 1]
    if (lineEl) {
      const spans = Array.from(lineEl.children).filter((c): c is HTMLElement => c instanceof HTMLElement)
      // Prefer the token spans over the block-level line span, so indentation
      // is skipped like on the Magic Move path. A blank line has no anchor.
      if (spans.length)
        tokens.push(...spans)
      else if (lineEl.textContent?.trim())
        tokens.push(lineEl)
    }
  }
  // The renderer's anchor span, leading indentation, and trailing whitespace
  // are all whitespace-only tokens; trim them so the underline and the
  // message hug the actual code. Whitespace between tokens is kept so the
  // underline remains continuous across the offending expression.
  while (tokens.length && !tokens[0].textContent?.trim())
    tokens.shift()
  while (tokens.length && !tokens[tokens.length - 1].textContent?.trim())
    tokens.pop()
  return tokens
}

function sync() {
  const host = container.value
  if (!host)
    return

  const nextTargets = findLineTargets(host)
  const targetsChanged = targets.value.length !== nextTargets.length
    || targets.value.some((el, index) => el !== nextTargets[index])
  if (targetsChanged) {
    // Magic Move replaces its rendered code after this component has mounted.
    if (import.meta.env.DEV && isMagicMove && targets.value.length)
      stepSwaps++
    targets.value = nextTargets
  }

  animating.value = !!host.querySelector(MAGIC_MOVE_ANIMATING)
  if (animating.value)
    sawAnimating = true
  if (import.meta.env.DEV && !warnedClassDrift && !isPrintMode.value && stepSwaps >= 2 && !sawAnimating) {
    warnedClassDrift = true
    console.warn('[InlineCompilerError] Magic Move steps changed but its animation classes were never observed — the private class names in MAGIC_MOVE_ANIMATING may have been renamed by a @shikijs/magic-move upgrade, which would let the message reveal mid-animation.')
  }

  if (!visible.value || !targets.value.length)
    return

  // getBoundingClientRect() uses visual (scaled) pixels, while an absolutely
  // positioned child uses the host's unscaled CSS coordinate space.
  const scale = $scale.value * $zoom.value || 1
  const hostBox = host.getBoundingClientRect()
  // Token spans can be wider than their text; Ranges give the actual rendered
  // text edges. The union across the line's tokens also covers the whitespace
  // between them, keeping the underline continuous across the expression.
  const range = document.createRange()
  let left = Infinity
  let right = -Infinity
  let top = Infinity
  let bottom = -Infinity
  for (const el of targets.value) {
    range.selectNodeContents(el)
    const box = range.getBoundingClientRect()
    if (!box.width)
      continue
    left = Math.min(left, box.left)
    right = Math.max(right, box.right)
    top = Math.min(top, box.top)
    bottom = Math.max(bottom, box.bottom)
  }
  if (right <= left)
    return

  // The slide clips at its own edge; wrap the message within the remaining
  // room instead of letting it run off the canvas.
  const slideBox = (host.closest('.slidev-layout') ?? host).getBoundingClientRect()
  position.value = {
    left: `${(right - hostBox.left) / scale + 10}px`,
    top: `${(top - hostBox.top + (bottom - top) / 2) / scale}px`,
    maxWidth: `${Math.max(60, (slideBox.right - right) / scale - 34)}px`,
  }
  underline.value = {
    left: `${(left - hostBox.left) / scale}px`,
    top: `${(bottom - hostBox.top) / scale - 1}px`,
    width: `${(right - left) / scale}px`,
  }
}

// Coalesce observer bursts into one sync per frame: during a Magic Move
// transition every token flips classes.
let pendingSync: number | undefined
function scheduleSync() {
  pendingSync ??= requestAnimationFrame(() => {
    pendingSync = undefined
    sync()
  })
}

// The code lines do not exist until Shiki Magic Move renders its first step,
// and are replaced for every subsequent step. Class changes are observed too:
// Magic Move signals the end of a transition by removing its animation
// classes, without touching the DOM structure.
useMutationObserver(container, scheduleSync, { childList: true, subtree: true, attributes: true, attributeFilter: ['class'] })
// The getter re-observes automatically whenever the line is retargeted.
useResizeObserver(() => [container.value, ...targets.value], scheduleSync)

onMounted(async () => {
  await nextTick()
  sync()
  document.fonts?.ready.then(scheduleSync)
})

onBeforeUnmount(() => {
  if (pendingSync !== undefined)
    cancelAnimationFrame(pendingSync)
  clearTimeout(revealTimer)
  clearTimeout(missingLineWarning)
})
watch(() => props.line, sync)
watch(visible, sync)

let revealTimer: ReturnType<typeof setTimeout> | undefined
watch(canReveal, (can) => {
  clearTimeout(revealTimer)
  if (!can) {
    revealed.value = false
    return
  }
  const reveal = () => {
    revealed.value = true
    sync()
  }
  // Export captures the page as soon as it settles, so print mode reveals
  // synchronously instead of trailing by a beat.
  if (isPrintMode.value)
    reveal()
  else
    revealTimer = setTimeout(reveal, 100)
})

// A `:line` pointing at an empty (or nonexistent) line has no tokens to
// anchor to, so the diagnostic stays hidden. Silent in production, but tell
// the slide author. Delayed past the longest Magic Move transition, during
// which a not-yet-rendered line is expected. `immediate` matters: with
// `at`/`until` omitted neither source may ever change.
let missingLineWarning: ReturnType<typeof setTimeout> | undefined
watch([visible, hasTarget], ([isVisible, found]) => {
  clearTimeout(missingLineWarning)
  if (import.meta.env.DEV && isVisible && !found) {
    missingLineWarning = setTimeout(() => {
      console.warn(`[InlineCompilerError] No code tokens on line ${props.line} for "${props.message}" — the diagnostic is not shown. Empty lines cannot be annotated; check the :line prop.`)
    }, 1500)
  }
}, { immediate: true })
</script>

<template>
  <div ref="container" class="inline-compiler-error-host">
    <slot />
    <!-- Screen readers only announce a live region reliably when its content
         changes while it is already mounted, so this one exists permanently
         and only its text swaps on reveal. -->
    <span class="inline-compiler-error-status" role="status" aria-live="polite" aria-atomic="true">{{ revealed ? props.message : '' }}</span>
    <Transition name="compiler-error" :css="!isPrintMode">
      <span
        v-if="revealed"
        class="inline-compiler-error-underline"
        :style="underline"
        aria-hidden="true"
      />
    </Transition>
    <Transition name="compiler-error" :css="!isPrintMode">
      <span
        v-if="revealed"
        class="inline-compiler-error-message"
        :style="position"
        aria-hidden="true"
      ><i>●</i>{{ props.message }}</span>
    </Transition>
  </div>
</template>

<style scoped>
.inline-compiler-error-host { position: relative; }
.inline-compiler-error-status { position: absolute; width: 1px; height: 1px; overflow: hidden; clip-path: inset(50%); white-space: nowrap; }
.inline-compiler-error-message { position: absolute; z-index: 2; display: inline-flex; align-items: center; gap: .38rem; color: #e95757; font-family: var(--slidev-font-sans); font-size: 1.53rem; font-weight: 700; line-height: 1.15; pointer-events: none; transform: translateY(-50%); }
.compiler-error-enter-active,
.compiler-error-leave-active { transition: opacity .18s ease; }
.compiler-error-enter-from,
.compiler-error-leave-to { opacity: 0; }
.inline-compiler-error-message i { color: #f05b5b; font-size: .62rem; font-style: normal; }
.inline-compiler-error-underline {
  position: absolute;
  z-index: 2;
  height: 6px;
  pointer-events: none;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='6'%3E%3Cpath d='M0 3 Q3 1 6 3 T12 3' fill='none' stroke='%23f05b5b' stroke-width='1.5'/%3E%3C/svg%3E") repeat-x left top / 12px 6px;
}
</style>
