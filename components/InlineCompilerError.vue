<script setup lang="ts">
import { computed, nextTick, onBeforeUnmount, onMounted, ref, useId, watch } from 'vue'
import { useSlideContext } from '@slidev/client'

const props = defineProps<{
  /** One-based source line number inside the fenced code block. */
  line: number
  message: string
  /** Slidev click at which to reveal the diagnostic. Omit to show it immediately. */
  at?: number | string
  /** First Slidev click at which to hide the diagnostic. */
  until?: number | string
}>()

const { $clicks } = useSlideContext()
const container = ref<HTMLElement>()
const position = ref({ left: '0px', top: '0px' })
// Whether the requested line currently exists in the rendered code. A Magic
// Move step may not contain the line (yet), and an empty line has no tokens;
// without a target the message has no anchor and must stay hidden.
const hasTarget = ref(false)
// True while Shiki Magic Move is transitioning between steps.
const animating = ref(false)
const visible = computed(() => (
  (props.at === undefined || $clicks.value >= Number(props.at))
  && (props.until === undefined || $clicks.value < Number(props.until))
))
// The diagnostic must not appear while Magic Move is still animating, and a
// click can reveal it a few frames before the step transition it also
// triggers begins. `revealed` therefore trails `canReveal` by a beat, so the
// message never flashes at a stale position.
const canReveal = computed(() => visible.value && hasTarget.value && !animating.value)
const revealed = ref(false)
// Classes Shiki Magic Move keeps on its elements only while a step
// transition is playing (see MagicMoveRenderer in @shikijs/magic-move).
const MAGIC_MOVE_ANIMATING = [
  '.shiki-magic-move-enter-active',
  '.shiki-magic-move-leave-active',
  '.shiki-magic-move-move',
  '.shiki-magic-move-container-resize',
  '.shiki-magic-move-container-restyle',
].join(', ')
// Nested diagnostics can point to the same Magic Move token. Each instance
// needs its own class so hiding one does not remove another's underline.
const decorationClass = `inline-compiler-error-line-${useId()}`
let resizeObserver: ResizeObserver | undefined
let mutationObserver: MutationObserver | undefined
let target: HTMLElement | undefined
let targets: HTMLElement[] = []

function findLineTargets(host: HTMLElement): HTMLElement[] {
  const regularLine = host.querySelectorAll<HTMLElement>('.slidev-code code .line')[props.line - 1]
  if (regularLine)
    return [regularLine]

  // Shiki Magic Move v2 renders tokens directly in a <pre>, separated by
  // <br> elements, rather than as `.line` spans.
  const magicMove = host.querySelector<HTMLElement>('pre.shiki-magic-move-container')
  if (!magicMove)
    return []

  let currentLine = 1
  const lineTokens: HTMLElement[] = []
  for (const child of magicMove.children) {
    if (child.tagName === 'BR') {
      currentLine++
      continue
    }
    // Skip leading indentation, but retain whitespace between tokens so the
    // underline remains continuous across the offending expression.
    if (currentLine === props.line && child instanceof HTMLElement
      && (child.textContent?.trim() || lineTokens.length > 0))
      lineTokens.push(child)
  }
  return lineTokens
}

function updateDecoration() {
  for (const lineTarget of targets)
    lineTarget.classList.toggle(decorationClass, revealed.value)
}

function updatePosition() {
  const host = container.value
  const nextTargets = host ? findLineTargets(host) : []
  const nextTarget = nextTargets.at(-1)

  // Magic Move replaces its rendered code after this component has mounted.
  // Remove the decoration from the outgoing line before targeting the new one.
  const targetsChanged = targets.length !== nextTargets.length
    || targets.some((lineTarget, index) => lineTarget !== nextTargets[index])
  if (targetsChanged) {
    for (const lineTarget of targets)
      lineTarget.classList.remove(decorationClass)
    targets = nextTargets
    target = nextTarget
  }

  hasTarget.value = targets.length > 0
  animating.value = !!host?.querySelector(MAGIC_MOVE_ANIMATING)

  if (!host || !target)
    return

  const hostBox = host.getBoundingClientRect()
  const lineBox = target.getBoundingClientRect()
  // getBoundingClientRect() uses visual (scaled) pixels, while an absolutely
  // positioned child uses the host's unscaled CSS coordinate space.
  const scaleX = hostBox.width / host.offsetWidth || 1
  const scaleY = hostBox.height / host.offsetHeight || 1
  // Shiki line spans are block-level and therefore as wide as the entire
  // editor. A Range gives us the actual rendered text edge of `count++`.
  const range = document.createRange()
  range.selectNodeContents(target)
  const textBox = range.getBoundingClientRect()
  position.value = {
    left: `${(textBox.right - hostBox.left) / scaleX + 10}px`,
    top: `${(lineBox.top - hostBox.top + lineBox.height / 2) / scaleY}px`,
  }
}

onMounted(async () => {
  await nextTick()
  updatePosition()
  updateDecoration()
  resizeObserver = new ResizeObserver(updatePosition)
  if (container.value) {
    resizeObserver.observe(container.value)
    // The code lines do not exist until Shiki Magic Move renders its first
    // step, and are replaced for every subsequent step. Class changes are
    // observed too: Magic Move signals the end of a transition by removing
    // its animation classes, without touching the DOM structure.
    mutationObserver = new MutationObserver(() => {
      updatePosition()
      updateDecoration()
    })
    mutationObserver.observe(container.value, { childList: true, characterData: true, subtree: true, attributes: true, attributeFilter: ['class'] })
  }
  if (target)
    resizeObserver.observe(target)
  document.fonts?.ready.then(() => {
    updatePosition()
    updateDecoration()
  })
})

onBeforeUnmount(() => {
  resizeObserver?.disconnect()
  mutationObserver?.disconnect()
  clearTimeout(revealTimer)
  clearTimeout(missingLineWarning)
  for (const lineTarget of targets)
    lineTarget.classList.remove(decorationClass)
})
watch(() => props.line, () => {
  updatePosition()
  updateDecoration()
})
watch(visible, () => {
  updatePosition()
})

let revealTimer: ReturnType<typeof setTimeout> | undefined
watch(canReveal, (can) => {
  clearTimeout(revealTimer)
  if (can) {
    revealTimer = setTimeout(() => {
      revealed.value = true
      updatePosition()
      updateDecoration()
    }, 100)
  }
  else {
    revealed.value = false
    updateDecoration()
  }
})

// A `:line` pointing at an empty (or nonexistent) line has no tokens to
// anchor to, so the diagnostic stays hidden. Silent in production, but tell
// the slide author. Delayed past the longest Magic Move transition, during
// which a not-yet-rendered line is expected.
let missingLineWarning: ReturnType<typeof setTimeout> | undefined
watch([visible, hasTarget], ([isVisible, found]) => {
  clearTimeout(missingLineWarning)
  if (import.meta.env.DEV && isVisible && !found) {
    missingLineWarning = setTimeout(() => {
      console.warn(`[InlineCompilerError] No code tokens on line ${props.line} for "${props.message}" — the diagnostic is not shown. Empty lines cannot be annotated; check the :line prop.`)
    }, 1500)
  }
})
</script>

<template>
  <div ref="container" class="inline-compiler-error-host">
    <slot />
    <span v-if="props.at !== undefined" v-click="props.at" class="click-marker" />
    <Transition name="compiler-error">
      <span
        v-if="revealed"
        class="inline-compiler-error-message"
        :style="position"
        role="status"
        aria-live="polite"
        aria-atomic="true"
      ><i aria-hidden="true">●</i>{{ props.message }}</span>
    </Transition>
  </div>
</template>

<style scoped>
.inline-compiler-error-host { position: relative; }
.click-marker { position: absolute; width: 0; height: 0; overflow: hidden; }
.inline-compiler-error-message { position: absolute; z-index: 2; display: inline-flex; align-items: center; gap: .38rem; color: #e95757; font-family: var(--slidev-font-sans); font-size: 1.53rem; font-weight: 700; line-height: 1; pointer-events: none; transform: translateY(-50%); white-space: nowrap; }
.compiler-error-enter-active,
.compiler-error-leave-active { transition: opacity .18s ease; }
.compiler-error-enter-from,
.compiler-error-leave-to { opacity: 0; }
.inline-compiler-error-message i { color: #f05b5b; font-size: .62rem; font-style: normal; }
:deep([class*="inline-compiler-error-line-"]), :deep([class*="inline-compiler-error-line-"] span) { text-decoration: underline wavy #f05b5b 1.5px; text-underline-offset: .23rem; }
</style>
