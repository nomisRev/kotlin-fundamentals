<script setup>
const props = defineProps({
  /** Label above each upstream/downstream pair, e.g. ['0', '1', '2']. */
  steps: { type: Array, default: () => [] },
  /** How many pairs to draw when `steps` is empty. */
  count: { type: Number, default: 2 },
  /** Legend for the purple (upstream) and blue (downstream) arrows. */
  up: { type: String, default: 'emit' },
  down: { type: String, default: 'collect' },
  /** Text of the inner dashed box around the first pair, e.g. `while (true)`. */
  loop: { type: String, default: '' },
  /** Trail off with an ellipsis after the last pair. */
  ellipsis: { type: Boolean, default: false },
  title: { type: String, default: 'Coroutine' },
})
const pairs = () => (props.steps.length ? props.steps : Array.from({ length: props.count }, () => ''))
</script>

<template>
  <div class="flow-timeline" role="img" :aria-label="`${title}: ${up} and ${down} alternate${loop ? ` inside ${loop}` : ''}`">
    <div class="ft-legend" aria-hidden="true">
      <span class="ft-legend-up">{{ up }}</span>
      <span class="ft-legend-down">{{ down }}</span>
    </div>
    <div class="ft-box">
      <span class="ft-title">{{ title }}</span>
      <div v-for="(step, i) in pairs()" :key="i" class="ft-pair" :class="{ 'ft-pair--loop': loop && i === 0 }">
        <span v-if="loop && i === 0" class="ft-loop">{{ loop }}</span>
        <span class="ft-step">{{ step }}</span>
        <span class="ft-arrows">
          <svg viewBox="0 0 120 20" class="ft-arrow ft-arrow--up"><path d="M0 10 H100 M88 2 L104 10 L88 18" /></svg>
          <svg viewBox="0 0 120 20" class="ft-arrow ft-arrow--down"><path d="M0 10 H100 M88 2 L104 10 L88 18" /></svg>
        </span>
      </div>
      <span v-if="ellipsis" class="ft-ellipsis">…</span>
    </div>
  </div>
</template>

<style scoped>
.flow-timeline { --ft-up: var(--fundamentals-purple, #7954f6); --ft-down: var(--fundamentals-blue, #06b6d4); --ft-ink: var(--fundamentals-ink, #1f2023); width: 100%; margin-top: 1rem; color: var(--ft-ink); font-family: var(--slidev-font-sans); }
.ft-legend { display: flex; gap: 2.4rem; margin: 0 0 0.6rem 0.4rem; font-size: 1.4rem; font-weight: 700; }
.ft-legend span::before { display: inline-block; width: 1.6rem; height: 0.3rem; margin-right: 0.6rem; border-radius: 0.2rem; vertical-align: middle; content: ''; }
.ft-legend-up::before { background: var(--ft-up); }
.ft-legend-down::before { background: var(--ft-down); }
.ft-box { position: relative; display: flex; align-items: center; gap: 1.6rem; padding: 3rem 2rem 1.8rem; border: 3px dashed var(--ft-ink); border-radius: 1.4rem; }
.ft-title { position: absolute; top: 0.7rem; right: 1.4rem; font-size: 1.35rem; font-weight: 700; letter-spacing: -0.01em; opacity: 0.75; }
.ft-pair { position: relative; display: grid; justify-items: center; gap: 0.3rem; padding: 0.8rem 1rem 0.6rem; }
.ft-pair--loop { border: 3px dashed var(--ft-up); border-radius: 1.1rem; }
.ft-loop { position: absolute; top: -1.9rem; left: 0.6rem; color: var(--ft-up); font-family: var(--slidev-font-mono); font-size: 1.3rem; font-weight: 700; }
.ft-step { min-height: 1.8rem; font-size: 1.6rem; font-weight: 700; }
.ft-arrows { display: flex; gap: 0.8rem; }
.ft-arrow { width: 9rem; height: 1.5rem; fill: none; stroke-width: 5; stroke-linecap: round; stroke-linejoin: round; }
.ft-arrow--up { stroke: var(--ft-up); }
.ft-arrow--down { stroke: var(--ft-down); }
.ft-ellipsis { font-size: 2.4rem; font-weight: 800; letter-spacing: 0.1em; }
</style>
