<script setup lang="ts">
import { computed } from 'vue'
import { useSlideContext } from '@slidev/client'

/**
 * The Job hierarchy beside a snippet, staged by the slide's clicks.
 *
 * Every node names the click at which it starts, completes, fails or is
 * cancelled. A node without `at` is running from the initial state; a node
 * without an end keeps running. The slide declares the click count itself
 * (`clicks: N` in its frontmatter), so annotations and the tree share one
 * timeline.
 */
export interface JobNode {
  label: string
  /** Click at which the coroutine starts; absent means from click 0. */
  at?: number
  /** Click at which it completes. */
  done?: number
  /** Click at which it throws. */
  failed?: number
  /** Click at which its parent cancels it. */
  cancelled?: number
}

const props = withDefaults(defineProps<{
  root: JobNode
  children?: JobNode[]
  /** Click at which `cancel()` arrives at the root from outside. */
  cancel?: number
}>(), { children: () => [] })

const { $clicks } = useSlideContext()

type State = 'pending' | 'running' | 'done' | 'failed' | 'cancelled'

function stateOf(node: JobNode): State {
  const click = $clicks.value
  if (node.failed !== undefined && click >= node.failed) return 'failed'
  if (node.cancelled !== undefined && click >= node.cancelled) return 'cancelled'
  if (node.done !== undefined && click >= node.done) return 'done'
  if (click >= (node.at ?? 0)) return 'running'
  return 'pending'
}

const rootState = computed(() => stateOf(props.root))
const childStates = computed(() => props.children.map(stateOf))
const cancelling = computed(() => props.cancel !== undefined && $clicks.value >= props.cancel)

const broken = (state: State) => state === 'failed' || state === 'cancelled'

/** One edge per child, from the root's bottom centre to the child's column centre. */
const edges = computed(() => props.children.map((_, index) => {
  const count = props.children.length
  const x = ((index + 0.5) / count) * 100
  const state = childStates.value[index]
  return {
    d: `M50 0 L${x} 20`,
    visible: state !== 'pending',
    broken: broken(state) && broken(rootState.value),
  }
}))

const description = computed(() =>
  `${props.root.label} owns ${props.children.map(child => child.label).join(', ') || 'no children'}`)
</script>

<template>
  <div class="jobtree" role="img" :aria-label="description">
    <div class="jobtree-cancel" :class="{ 'is-visible': cancelling }" aria-hidden="true">
      <span>cancel()</span>
      <svg viewBox="0 0 12 20"><path d="M6 0 V15 M1 11 L6 17 L11 11" /></svg>
    </div>
    <div class="jobtree-node jobtree-node--root" :class="`is-${rootState}`">{{ root.label }}</div>
    <svg
      v-if="children.length"
      class="jobtree-fan"
      viewBox="0 0 100 20"
      preserveAspectRatio="none"
      aria-hidden="true"
    >
      <path
        v-for="(edge, index) in edges"
        :key="index"
        :d="edge.d"
        :class="{ 'is-visible': edge.visible, 'is-broken': edge.broken }"
      />
    </svg>
    <div v-if="children.length" class="jobtree-children" :style="{ '--jobtree-columns': children.length }">
      <div
        v-for="(child, index) in children"
        :key="index"
        class="jobtree-node"
        :class="`is-${childStates[index]}`"
      >
        {{ child.label }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.jobtree {
  --jobtree-ink: var(--fundamentals-ink, #1f2023);
  --jobtree-running: var(--fundamentals-purple, #7954f6);
  --jobtree-done: #2fa37f;
  --jobtree-broken: #d3325a;
  position: relative;
  display: grid;
  justify-items: center;
  width: 100%;
  font-family: var(--slidev-font-mono);
  font-size: 1.1rem;
  font-weight: 600;
  letter-spacing: -0.02em;
}

.jobtree-node {
  position: relative;
  padding: 0.55rem 1.2rem;
  border: 2.5px solid var(--jobtree-running);
  border-radius: 0.8rem;
  background: rgb(255 255 255 / 92%);
  color: var(--jobtree-ink);
  white-space: nowrap;
  transition: opacity 300ms ease, transform 300ms ease, border-color 300ms ease, color 300ms ease;
}
.jobtree-node--root { padding-inline: 1.6rem; font-size: 1.25rem; }

.jobtree-node.is-pending {
  opacity: 0;
  transform: translateY(-0.5rem);
}
.jobtree-node.is-running {
  animation: jobtree-pulse 1.6s ease-in-out infinite;
}
.jobtree-node.is-done {
  border-color: var(--jobtree-done);
}
.jobtree-node.is-done::after {
  position: absolute;
  top: -0.7rem;
  right: -0.7rem;
  display: grid;
  place-items: center;
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 999px;
  background: var(--jobtree-done);
  color: #fff;
  font-size: 0.95rem;
  line-height: 1;
  content: '✓';
}
.jobtree-node.is-failed {
  border-color: var(--jobtree-broken);
  background: rgb(211 50 90 / 12%);
  color: #a11d45;
}
.jobtree-node.is-failed::after {
  position: absolute;
  top: -0.7rem;
  right: -0.7rem;
  display: grid;
  place-items: center;
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 999px;
  background: var(--jobtree-broken);
  color: #fff;
  font-size: 0.95rem;
  line-height: 1;
  content: '!';
}
.jobtree-node.is-cancelled {
  border-style: dashed;
  border-color: var(--jobtree-broken);
  color: #767982;
  text-decoration: line-through;
  text-decoration-color: var(--jobtree-broken);
  text-decoration-thickness: 0.12em;
}

.jobtree-fan {
  width: 100%;
  height: 3rem;
  fill: none;
  stroke: var(--jobtree-running);
  stroke-width: 2.5;
}
.jobtree-fan path {
  vector-effect: non-scaling-stroke;
  opacity: 0;
  transition: opacity 300ms ease, stroke 300ms ease;
}
.jobtree-fan path.is-visible { opacity: 1; }
.jobtree-fan path.is-broken { stroke: var(--jobtree-broken); }

.jobtree-children {
  display: grid;
  grid-template-columns: repeat(var(--jobtree-columns), 1fr);
  justify-items: center;
  width: 100%;
  gap: 0.8rem;
}

.jobtree-cancel {
  position: absolute;
  top: -2.9rem;
  left: 50%;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transform: translateX(3rem);
  color: var(--jobtree-broken);
  opacity: 0;
  transition: opacity 300ms ease;
}
.jobtree-cancel.is-visible { opacity: 1; }
.jobtree-cancel svg {
  width: 1rem;
  height: 2.4rem;
  fill: none;
  stroke: currentColor;
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
}

@keyframes jobtree-pulse {
  0%, 100% { box-shadow: 0 0 0 0 rgb(121 84 246 / 35%); }
  50% { box-shadow: 0 0 0 0.45rem rgb(121 84 246 / 0%); }
}

html.dark .jobtree-node { background: rgb(31 32 35 / 92%); }
html.dark .jobtree-node.is-failed { color: #ff8fb0; }
html.dark .jobtree-node.is-cancelled { color: #b9bbc2; }

@media (prefers-reduced-motion: reduce) {
  .jobtree-node.is-running { animation: none; }
}
</style>
