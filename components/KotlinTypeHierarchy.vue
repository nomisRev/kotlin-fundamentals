<script setup lang="ts">
import { computed } from 'vue'
import { useSlideContext } from '@slidev/client'

const { $clicks } = useSlideContext()
const stage = computed(() => Math.min($clicks.value, 5))
</script>

<template>
  <div
    class="type-hierarchy"
    role="img"
    aria-label="Animated Kotlin type hierarchy. Any branches to String, Boolean, Number, Unit, and Fruit; all concrete types connect to Nothing. A pink question-mark badge appears last on every type to mark its nullable counterpart."
  >
    <!-- These markers let Slidev own navigation, presenter mode, and backwards clicks. -->
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />

    <svg viewBox="0 0 1920 1080" preserveAspectRatio="xMidYMid meet" aria-hidden="true">
      <image :href="'/kotlin-type-logo.svg'" x="95" y="105" width="72" height="72" />
      <text class="heading" x="185" y="143">Type hierarchy</text>

      <!-- Connectors are drawn before nodes so their endpoints disappear under the pills. -->
      <g class="edges">
        <path class="edge reveal" :class="{ visible: stage >= 1 }" style="--delay: 0ms" pathLength="1" d="M566.5 285.1 L120.6 501.7" />
        <path class="edge reveal" :class="{ visible: stage >= 1 }" style="--delay: 250ms" pathLength="1" d="M563.5 285.1 L342.3 501.7" />
        <path class="edge reveal" :class="{ visible: stage >= 1 }" style="--delay: 500ms" pathLength="1" d="M568.1 285.3 L568.1 501.5" />
        <path class="edge reveal" :class="{ visible: stage >= 2 }" pathLength="1" d="M572.6 285 L821.4 495.8" />
        <path class="edge reveal" :class="{ visible: stage >= 3 }" pathLength="1" d="M567.4 285.3 L1057.1 495.5" />

        <path class="edge reveal" :class="{ visible: stage >= 1 }" style="--delay: 1450ms" pathLength="1" d="M560.7 556.8 L477.1 677.3" />
        <path class="edge reveal" :class="{ visible: stage >= 1 }" style="--delay: 1750ms" pathLength="1" d="M594.3 560.4 L678.9 685.7" />
        <path class="edge reveal" :class="{ visible: stage >= 3 }" style="--delay: 950ms" pathLength="1" d="M1029.1 546.8 L923.9 687.3" />
        <path class="edge reveal" :class="{ visible: stage >= 3 }" style="--delay: 1270ms" pathLength="1" d="M1045.4 556.4 L1166.7 677.7" />

        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 0ms" pathLength="1" d="M96.4 558.9 L509.8 972.3" />
        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 150ms" pathLength="1" d="M271.2 554.9 L535.7 976.3" />
        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 300ms" pathLength="1" d="M494.3 737.6 L543.5 952.1" />
        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 450ms" pathLength="1" d="M667.1 746.4 L598.6 963.2" />
        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 600ms" pathLength="1" d="M875.4 552.2 L655.9 982.7" />
        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 750ms" pathLength="1" d="M924.4 736.5 L662.4 973.1" />
        <path class="edge reveal" :class="{ visible: stage >= 4 }" style="--delay: 900ms" pathLength="1" d="M1173.4 732 L648.5 977.6" />

      </g>

      <g class="nodes">
        <g class="node"><rect x="497.876" y="215.707" width="140.513" height="70.256" rx="999" /><text x="568.13" y="250.84">Any</text></g>

        <g class="node fade" :class="{ visible: stage >= 1 }" style="--delay: 600ms"><rect x="15.415" y="480.130" width="195.373" height="83.528" rx="999" /><text x="113.10" y="521.89">String</text></g>
        <g class="node fade" :class="{ visible: stage >= 1 }" style="--delay: 850ms"><rect x="230.632" y="480.130" width="225.841" height="83.528" rx="999" /><text x="343.55" y="521.89">Boolean</text></g>
        <g class="node fade" :class="{ visible: stage >= 1 }" style="--delay: 1100ms"><rect x="476.438" y="480.130" width="195.373" height="83.528" rx="999" /><text x="574.12" y="521.89">Number</text></g>
        <g class="node fade" :class="{ visible: stage >= 2 }" style="--delay: 500ms"><rect x="714.566" y="480.130" width="195.373" height="83.528" rx="999" /><text x="812.25" y="521.89">Unit</text></g>
        <g class="node fade" :class="{ visible: stage >= 3 }" style="--delay: 500ms"><rect x="937.461" y="480.130" width="195.373" height="83.528" rx="999" /><text x="1035.15" y="521.89">Fruit</text></g>

        <g class="node fade" :class="{ visible: stage >= 1 }" style="--delay: 2100ms"><rect x="412.597" y="666.612" width="140.513" height="83.528" rx="999" /><text x="482.85" y="708.38">Char</text></g>
        <text class="ellipsis fade" :class="{ visible: stage >= 1 }" style="--delay: 1320ms" x="579.59" y="705.25">…</text>
        <g class="node fade" :class="{ visible: stage >= 1 }" style="--delay: 2400ms"><rect x="595.139" y="666.612" width="178.585" height="83.528" rx="999" /><text x="684.43" y="708.38">Double</text></g>
        <g class="node fade" :class="{ visible: stage >= 3 }" style="--delay: 1480ms"><rect x="838.314" y="665.489" width="195.373" height="83.528" rx="999" /><text x="936" y="707.25">Banana</text></g>
        <g class="node fade" :class="{ visible: stage >= 3 }" style="--delay: 1800ms"><rect x="1074.888" y="666.612" width="195.373" height="83.528" rx="999" /><text x="1172.57" y="708.38">Apple</text></g>
        <g class="node fade" :class="{ visible: stage >= 4 }" style="--delay: 1100ms"><rect x="461.204" y="948.142" width="225.841" height="83.528" rx="999" /><text x="574.12" y="989.91">Nothing</text></g>
      </g>

      <!-- A pink "?" badge marks each type's nullable counterpart, e.g. String?, without
           drawing eleven more branches: the badge sits on the pill it belongs to. -->
      <g class="nullable-badges">
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 0ms"><circle cx="630.39" cy="223.71" r="22" /><text x="630.39" y="224">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 130ms"><circle cx="202.79" cy="488.13" r="22" /><text x="202.79" y="489">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 260ms"><circle cx="448.47" cy="488.13" r="22" /><text x="448.47" y="489">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 390ms"><circle cx="663.81" cy="488.13" r="22" /><text x="663.81" y="489">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 520ms"><circle cx="901.94" cy="488.13" r="22" /><text x="901.94" y="489">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 650ms"><circle cx="1124.83" cy="488.13" r="22" /><text x="1124.83" y="489">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 780ms"><circle cx="545.11" cy="674.61" r="22" /><text x="545.11" y="675">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 910ms"><circle cx="765.72" cy="674.61" r="22" /><text x="765.72" y="675">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 1040ms"><circle cx="1025.69" cy="673.49" r="22" /><text x="1025.69" y="674">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 1170ms"><circle cx="1262.26" cy="674.61" r="22" /><text x="1262.26" y="675">?</text></g>
        <g class="nullable-badge fade" :class="{ visible: stage >= 5 }" style="--delay: 1300ms"><circle cx="679.05" cy="956.14" r="22" /><text x="679.05" y="957">?</text></g>
      </g>

      <g class="annotation" :class="{ visible: stage === 0 }">
        <path class="edge" d="M655.4 256.1 L833.2 187.7" />
        <text x="844.205" y="187">Root of every non-null type</text>
        <text x="844.205" y="242" class="annotation-detail">≈ Java's Object</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 1 }" style="--delay: 2750ms">
        <path class="edge" d="M596.5 663.6 L772.6 573.9" /><text x="796.356" y="575">Includes unsigned variants</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 2 }" style="--delay: 950ms">
        <path class="edge" d="M847.1 466.7 L1023.2 377" />
        <text x="1046.914" y="378">Similar to Java’s void</text>
        <text x="1046.914" y="442">Normal return, but no data</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 4 }" style="--delay: 1550ms">
        <path class="edge" d="M710.4 989.9 L813.9 989.9" />
        <text x="837.205" y="985">Breaks control flow</text>
        <text x="837.205" y="1044" class="annotation-detail">exception or while (true) { }</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 5 }" style="--delay: 1500ms">
        <path class="edge" d="M655.4 256.1 L833.2 187.7" />
        <text x="844.205" y="187">Every type has a T? sibling</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 5 }" style="--delay: 1800ms">
        <path class="edge" d="M703 956 L810 956" />
        <text x="833.205" y="951">Nothing? = Nothing | null,</text>
        <text x="833.205" y="1010" class="annotation-detail">never returns, or null</text>
      </g>

    </svg>
  </div>
</template>

<style scoped>
.type-hierarchy {
  --purple: #7954f6;
  --pink: var(--fundamentals-pink, #eb55e6);
  position: absolute;
  inset: 0;
  overflow: hidden;
  background: #fff;
}

svg { display: block; width: 100%; height: 100%; }
.click-marker { position: absolute; width: 0; height: 0; overflow: hidden; }
.heading { fill: #1f2023; font: 700 72px 'JetBrains Sans', Arial, sans-serif; dominant-baseline: middle; }
.edge { fill: none; stroke: var(--purple); stroke-width: 6; stroke-linecap: round; stroke-linejoin: round; }
.node rect { fill: var(--purple); }
.nullable-badge circle { fill: var(--pink); stroke: #fff; stroke-width: 4; }
.nullable-badge text {
  fill: #fff;
  font: 700 28px "JetBrains Mono", ui-monospace, monospace;
  text-anchor: middle;
  dominant-baseline: middle;
}
.node text {
  fill: #fff;
  font: 32px "JetBrains Mono", ui-monospace, monospace;
  text-anchor: middle;
  dominant-baseline: middle;
}
.ellipsis {
  fill: var(--purple);
  font: 48px "JetBrains Mono", ui-monospace, monospace;
  text-anchor: middle;
  dominant-baseline: middle;
}
.annotation {
  opacity: 0;
  transition: opacity 640ms cubic-bezier(.22, .61, .36, 1);
  transition-delay: 0ms;
}
.annotation text {
  fill: var(--purple);
  font: 48px "JetBrains Mono", ui-monospace, monospace;
}
.annotation .annotation-detail { font-size: 39px; }
.fade {
  opacity: 0;
  transition: opacity 640ms cubic-bezier(.22, .61, .36, 1);
  transition-delay: 0ms;
}
.reveal {
  opacity: 0;
  stroke-dasharray: 1;
  stroke-dashoffset: 1;
  transition:
    stroke-dashoffset 900ms cubic-bezier(.22, .61, .36, 1),
    opacity 200ms linear;
  transition-delay: 0ms, 0ms;
}

/* Delays only apply while entering. Keeping them off the base state is
   important: otherwise an annotation waits several seconds before leaving
   when the presenter advances to the next build. */
.fade.visible,
.annotation.visible {
  transition-delay: var(--delay, 0ms);
}
.reveal.visible {
  transition-delay: var(--delay, 0ms), var(--delay, 0ms);
}
.visible { opacity: 1; stroke-dashoffset: 0; }

@media (prefers-reduced-motion: reduce) {
  .fade, .reveal, .annotation { transition-duration: 1ms !important; transition-delay: 0ms !important; }
}
</style>
