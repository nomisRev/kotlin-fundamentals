<script setup>
defineProps({
  left: { type: String, default: 'emit' },
  right: { type: String, default: 'collect' },
  /** Channel capacity: the number of slots drawn between the two sides. */
  capacity: { type: Number, default: 3 },
  /** How many coroutines send into the channel, one arrow each. */
  producers: { type: Number, default: 1 },
  /** How many coroutines receive from the channel, one arrow each. */
  consumers: { type: Number, default: 1 },
})
</script>

<template>
  <div class="channel-diagram" role="img" :aria-label="`${producers} ${left} coroutine(s) send into a channel of ${capacity}; ${consumers} ${right} coroutine(s) take from it`">
    <div class="cd-side">
      <span class="cd-title">{{ left }} {{ producers === 1 ? 'coroutine' : 'coroutines' }}</span>
      <div class="cd-box">
        <svg v-for="i in producers" :key="i" viewBox="0 0 120 20" class="cd-arrow cd-arrow--up"><path d="M0 10 H100 M88 2 L104 10 L88 18" /></svg>
      </div>
    </div>
    <div class="cd-channel">
      <div class="cd-slots">
        <span v-for="i in capacity" :key="i" class="cd-slot"></span>
      </div>
      <span class="cd-channel-label">Channel({{ capacity }})</span>
    </div>
    <div class="cd-side">
      <span class="cd-title">{{ right }} {{ consumers === 1 ? 'coroutine' : 'coroutines' }}</span>
      <div class="cd-box">
        <svg v-for="i in consumers" :key="i" viewBox="0 0 120 20" class="cd-arrow cd-arrow--down"><path d="M0 10 H100 M88 2 L104 10 L88 18" /></svg>
      </div>
    </div>
  </div>
</template>

<style scoped>
.channel-diagram { --cd-up: var(--fundamentals-purple, #7954f6); --cd-down: var(--fundamentals-blue, #06b6d4); --cd-ink: var(--fundamentals-ink, #1f2023); display: flex; align-items: flex-end; justify-content: center; gap: 1.2rem; width: 100%; margin-top: 1rem; color: var(--cd-ink); font-family: var(--slidev-font-sans); }
.cd-side { display: grid; gap: 0.6rem; }
.cd-title { font-size: 1.4rem; font-weight: 700; }
.cd-box { display: grid; gap: 0.7rem; padding: 1.2rem 2.4rem; border: 3px dashed var(--cd-ink); border-radius: 1.4rem; }
.cd-arrow { width: 13rem; height: 1.6rem; fill: none; stroke-width: 5; stroke-linecap: round; stroke-linejoin: round; }
.cd-arrow--up { stroke: var(--cd-up); }
.cd-arrow--down { stroke: var(--cd-down); }
.cd-channel { display: grid; justify-items: center; gap: 0.5rem; }
.cd-slots { display: grid; width: 5.4rem; border: 3px dashed var(--cd-ink); border-radius: 1rem; overflow: hidden; }
.cd-slot { height: 2.3rem; }
.cd-slot + .cd-slot { border-top: 3px solid var(--cd-ink); }
.cd-channel-label { font-family: var(--slidev-font-mono); font-size: 1.3rem; font-weight: 700; }
</style>
