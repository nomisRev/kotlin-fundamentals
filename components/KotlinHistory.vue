<script setup lang="ts">
import { computed } from 'vue'
import { useSlideContext } from '@slidev/client'
import kodeeSitting from 'slidev-theme-kotlin/assets/kodee-sitting.svg?url'

const { $clicks } = useSlideContext()
const stage = computed(() => Math.min($clicks.value, 5))
</script>

<template>
  <div
    class="kotlin-history"
    role="img"
    aria-label="Animated timeline of Kotlin's history from 2010 through Kotlin 2.0 in 2024."
  >
    <!-- Hidden click targets let Slidev own navigation and presenter mode. -->
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />

    <svg viewBox="0 0 1920 1080" preserveAspectRatio="xMidYMid meet" aria-hidden="true">
      <defs>
        <linearGradient id="kotlin-history-gradient" x1="0" y1="24" x2="0" y2="1064" gradientUnits="userSpaceOnUse">
          <stop offset="0" stop-color="#7954f6" />
          <stop offset="42%" stop-color="#8053f4" />
          <stop offset="67%" stop-color="#9a4def" />
          <stop offset="100%" stop-color="#d74ae5" />
        </linearGradient>
      </defs>

      <g>
        <rect class="year" x="720" y="22" width="180" height="78" rx="39" />
        <text class="year-text" x="810" y="61">2010</text>
        <text class="event" x="324" y="158">First commit at <tspan class="accent">JetBrains</tspan></text>
      </g>

      <path class="timeline build" :class="{ shown: stage >= 1 }" d="M914 24 V301" />
      <g class="build year-build" :class="{ shown: stage >= 1 }">
        <rect class="year" x="943" y="170" width="182" height="76" rx="38" />
        <text class="year-text" x="1034" y="208">2011</text>
      </g>
      <text class="build event event-build event-1" :class="{ shown: stage >= 1 }" x="947" y="301"><tspan class="accent">Kotlin</tspan><tspan> publicly announced</tspan></text>

      <path class="timeline build" :class="{ shown: stage >= 2 }" d="M914 301 V526" />
      <g class="build year-build" :class="{ shown: stage >= 2 }">
        <rect class="year" x="715" y="318" width="182" height="76" rx="38" />
        <text class="year-text" x="806" y="356">2016</text>
      </g>
      <text class="build event event-build event-1" :class="{ shown: stage >= 2 }" x="466" y="451"><tspan class="accent">Kotlin 1.0</tspan><tspan> released</tspan></text>
      <text class="build event event-build event-2" :class="{ shown: stage >= 2 }" x="131" y="526">Kotlin added into <tspan class="accent">SpringInitializer</tspan></text>

      <path class="timeline build" :class="{ shown: stage >= 3 }" d="M914 526 V747" />
      <g class="build year-build" :class="{ shown: stage >= 3 }">
        <rect class="year later" x="937" y="539" width="182" height="76" rx="38" />
        <text class="year-text" x="1028" y="577">2017</text>
      </g>
      <text class="build event event-build event-1" :class="{ shown: stage >= 3 }" x="947" y="672"><tspan class="accent later">Google</tspan><tspan> announces official Kotlin support</tspan></text>
      <text class="build event event-build event-2" :class="{ shown: stage >= 3 }" x="947" y="747"><tspan class="accent later">Spring</tspan><tspan> 5.0 includes Kotlin into core</tspan></text>

      <path class="timeline build" :class="{ shown: stage >= 4 }" d="M914 747 V903" />
      <g class="build year-build" :class="{ shown: stage >= 4 }">
        <rect class="year later" x="720" y="768" width="180" height="76" rx="38" />
        <text class="year-text" x="810" y="806">2018</text>
      </g>
      <text class="build event event-build event-1" :class="{ shown: stage >= 4 }" x="220" y="903"><tspan class="accent later">Kotlin Foundation</tspan><tspan> announced</tspan></text>

      <path class="timeline build" :class="{ shown: stage >= 5 }" d="M914 903 V1042" />
      <g class="build year-build" :class="{ shown: stage >= 5 }">
        <rect class="year later" x="937" y="908" width="182" height="76" rx="38" />
        <text class="year-text" x="1028" y="946">2024</text>
      </g>
      <text class="build event event-build event-1" :class="{ shown: stage >= 5 }" x="947" y="1042"><tspan class="accent later">Kotlin 2.0</tspan><tspan> released</tspan></text>

    </svg>

    <img class="kodee" :src="kodeeSitting" alt="" aria-hidden="true" />
    <div class="help">Click / → to advance · ← to go back</div>
  </div>
</template>

<style scoped>
.kotlin-history {
  --purple: #7954f6;
  --violet: #9c4df0;
  --history-event: #1f2023;
  position: absolute;
  inset: 0;
  overflow: hidden;
  font-family: 'JetBrains Sans', Arial, sans-serif;
}

/* The SVG is self-contained, so it does not inherit the slide's text colour
   automatically when Slidev switches to night mode. The background is left
   transparent on purpose so the slide's own surface shows through. */
html.dark .kotlin-history {
  --history-event: #f5f3ff;
}

svg { display: block; width: 100%; height: 100%; }
.timeline {
  fill: none;
  stroke: url(#kotlin-history-gradient);
  stroke-width: 13;
  stroke-linecap: round;
}
.year { fill: var(--purple); }
.year.later { fill: var(--violet); }
.year-text {
  fill: #fff;
  font: 48px 'JetBrains Sans', Arial, sans-serif;
  text-anchor: middle;
  dominant-baseline: central;
}
.event { fill: var(--history-event); font: 48px 'JetBrains Sans', Arial, sans-serif; dominant-baseline: middle; }
.accent { fill: var(--purple); font-weight: 700; }
.accent.later { fill: var(--violet); }

.build {
  opacity: 0;
  transform-box: fill-box;
  transform-origin: center;
  transform: translateY(18px);
  transition: opacity 520ms ease, transform 620ms cubic-bezier(.22,.61,.36,1);
}
.build.shown { opacity: 1; transform: none; transition-delay: 0ms; }
.year-build.shown { transition-delay: 200ms; }
.event-1.shown { transition-delay: 500ms; }
.event-2.shown { transition-delay: 800ms; }
.timeline.build {
  transform: scaleY(0);
  transform-origin: top;
  transition: opacity 250ms ease, transform 1200ms cubic-bezier(.22,.61,.36,1);
}
.timeline.build.shown { transform: scaleY(1); transition-delay: 0ms; }

.click-marker { position: absolute; width: 0; height: 0; overflow: hidden; }
.kodee {
  position: absolute;
  right: 0;
  bottom: -42px;
  z-index: 1;
  width: 200px;
  height: 200px;
  object-fit: contain;
  pointer-events: none;
}
.help {
  position: absolute;
  right: 22px;
  top: 18px;
  z-index: 2;
  padding: 8px 11px;
  color: #fff;
  background: #201b2dba;
  border-radius: 5px;
  font: 14px 'JetBrains Sans', Arial, sans-serif;
  opacity: .75;
}

@media (prefers-reduced-motion: reduce) {
  .build, .timeline.build { transition-duration: 1ms !important; }
}
</style>
