<script setup lang="ts">
import { computed } from 'vue'
import { useSlideContext } from '@slidev/client'

const { $clicks } = useSlideContext()
const stage = computed(() => Math.min($clicks.value, 4))

type Kind = 'readonly' | 'mutable'

interface Node {
  id: string
  label: string
  x: number
  y: number
  w: number
  kind: Kind
  stage: number
  delay: number
}

const H = 84

// Pills sit on a 1920x1080 canvas. Rows: 255 interfaces, 475 collections,
// 695 lists and sets, 915 their mutable counterparts. The mutable side is
// pink so the read-only/mutable split reads at a glance.
const nodes: Node[] = [
  { id: 'iterable', label: 'Iterable', x: 430, y: 255, w: 220, kind: 'readonly', stage: 0, delay: 0 },
  { id: 'collection', label: 'Collection', x: 430, y: 475, w: 260, kind: 'readonly', stage: 1, delay: 500 },
  { id: 'list', label: 'List', x: 270, y: 695, w: 150, kind: 'readonly', stage: 1, delay: 1000 },
  { id: 'set', label: 'Set', x: 590, y: 695, w: 150, kind: 'readonly', stage: 1, delay: 1250 },

  { id: 'mutableIterable', label: 'MutableIterable', x: 1010, y: 255, w: 360, kind: 'mutable', stage: 2, delay: 500 },
  { id: 'mutableCollection', label: 'MutableCollection', x: 1010, y: 475, w: 400, kind: 'mutable', stage: 2, delay: 1000 },
  { id: 'mutableList', label: 'MutableList', x: 860, y: 915, w: 280, kind: 'mutable', stage: 2, delay: 1700 },
  { id: 'mutableSet', label: 'MutableSet', x: 1180, y: 915, w: 260, kind: 'mutable', stage: 2, delay: 1950 },

  { id: 'map', label: 'Map', x: 1480, y: 475, w: 150, kind: 'readonly', stage: 3, delay: 0 },
  { id: 'mutableMap', label: 'MutableMap', x: 1480, y: 695, w: 260, kind: 'mutable', stage: 3, delay: 700 },
]

const byId = Object.fromEntries(nodes.map((n) => [n.id, n])) as Record<string, Node>

interface Edge {
  from: string
  to: string
  stage: number
  delay: number
}

// Connectors run centre to centre and are drawn before the pills, so their
// endpoints disappear underneath. Order within a stage is the reveal order.
const edges: Edge[] = [
  { from: 'iterable', to: 'collection', stage: 1, delay: 0 },
  { from: 'collection', to: 'list', stage: 1, delay: 500 },
  { from: 'collection', to: 'set', stage: 1, delay: 750 },

  { from: 'iterable', to: 'mutableIterable', stage: 2, delay: 0 },
  { from: 'mutableIterable', to: 'mutableCollection', stage: 2, delay: 500 },
  { from: 'collection', to: 'mutableCollection', stage: 2, delay: 700 },
  { from: 'mutableCollection', to: 'mutableList', stage: 2, delay: 1200 },
  { from: 'list', to: 'mutableList', stage: 2, delay: 1400 },
  { from: 'mutableCollection', to: 'mutableSet', stage: 2, delay: 1450 },
  { from: 'set', to: 'mutableSet', stage: 2, delay: 1650 },

  { from: 'map', to: 'mutableMap', stage: 3, delay: 300 },
]

const edgePath = (e: Edge) => {
  const a = byId[e.from]
  const b = byId[e.to]
  return `M${a.x} ${a.y} L${b.x} ${b.y}`
}

interface Factory {
  label: string
  x: number
  y: number
  anchor: 'start' | 'end'
  kind: Kind
  delay: number
}

// The factory function sits beside the pill it produces, on whichever side
// is free of connectors.
const factories: Factory[] = [
  { label: 'listOf()', x: 175, y: 695, anchor: 'end', kind: 'readonly', delay: 0 },
  { label: 'setOf()', x: 495, y: 695, anchor: 'end', kind: 'readonly', delay: 150 },
  { label: 'mutableListOf()', x: 700, y: 915, anchor: 'end', kind: 'mutable', delay: 300 },
  { label: 'mutableSetOf()', x: 1330, y: 915, anchor: 'start', kind: 'mutable', delay: 450 },
  { label: 'mapOf()', x: 1575, y: 475, anchor: 'start', kind: 'readonly', delay: 600 },
  { label: 'mutableMapOf()', x: 1630, y: 695, anchor: 'start', kind: 'mutable', delay: 750 },
]
</script>

<template>
  <div
    class="collection-hierarchy"
    role="img"
    aria-label="Animated Kotlin collection hierarchy. Iterable branches to Collection, which branches to List and Set. Each read-only interface has a pink mutable counterpart: MutableIterable, MutableCollection, MutableList, and MutableSet, each extending both its read-only type and the mutable type above it. Map and MutableMap stand apart because Map is not an Iterable. The final build names the factory function next to every type: listOf, setOf, mutableListOf, mutableSetOf, mapOf, and mutableMapOf."
  >
    <!-- These markers let Slidev own navigation, presenter mode, and backwards clicks. -->
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />

    <svg viewBox="0 0 1920 1080" preserveAspectRatio="xMidYMid meet" aria-hidden="true">
      <image :href="'/kotlin-type-logo.svg'" x="95" y="105" width="72" height="72" />
      <text class="heading" x="185" y="143">Collection hierarchy</text>

      <g class="edges">
        <path
          v-for="e in edges"
          :key="`${e.from}-${e.to}`"
          class="edge reveal"
          :class="[byId[e.to].kind, { visible: stage >= e.stage }]"
          :style="{ '--delay': `${e.delay}ms` }"
          pathLength="1"
          :d="edgePath(e)"
        />
      </g>

      <g class="nodes">
        <g
          v-for="n in nodes"
          :key="n.id"
          class="node"
          :class="[n.kind, { fade: n.stage > 0, visible: stage >= n.stage }]"
          :style="{ '--delay': `${n.delay}ms` }"
        >
          <rect :x="n.x - n.w / 2" :y="n.y - H / 2" :width="n.w" :height="H" rx="999" />
          <text :x="n.x" :y="n.y">{{ n.label }}</text>
        </g>
      </g>

      <g class="factories">
        <text
          v-for="f in factories"
          :key="f.label"
          class="factory fade"
          :class="[f.kind, { visible: stage >= 4 }]"
          :style="{ '--delay': `${f.delay}ms`, textAnchor: f.anchor }"
          :x="f.x"
          :y="f.y"
        >{{ f.label }}</text>
      </g>

      <g class="annotation" :class="{ visible: stage === 0 }">
        <path class="edge" d="M545 265 L625 320" />
        <text x="640" y="340">Anything a for loop can walk</text>
        <text x="640" y="395" class="annotation-detail">map, filter and fold are defined here</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 1 }" style="--delay: 1700ms">
        <path class="edge" d="M565 475 L700 475" />
        <text x="720" y="470">Read-only: size, contains, iterator</text>
        <text x="720" y="525" class="annotation-detail">List keeps order, Set forbids duplicates</text>
      </g>
      <g class="annotation mutable" :class="{ visible: stage === 2 }" style="--delay: 2400ms">
        <path class="edge" d="M1215 475 L1265 475" />
        <text x="1285" y="470">Adds add and remove</text>
        <text x="1285" y="525" class="annotation-detail">MutableList is also a List</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 3 }" style="--delay: 1200ms">
        <path class="edge" d="M1480 428 L1480 380" />
        <text x="1210" y="300">Map is not an Iterable</text>
        <text x="1210" y="352" class="annotation-detail">use entries, keys or values</text>
      </g>
      <g class="annotation" :class="{ visible: stage === 4 }" style="--delay: 1000ms">
        <path class="edge" d="M1640 360 L1640 445" />
        <text x="1210" y="330">Factories name the type</text>
      </g>
    </svg>
  </div>
</template>

<style scoped>
.collection-hierarchy {
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
.edge.mutable { stroke: var(--pink); }
.node rect { fill: var(--purple); }
.node.mutable rect { fill: var(--pink); }
.node text {
  fill: #fff;
  font: 32px "JetBrains Mono", ui-monospace, monospace;
  text-anchor: middle;
  dominant-baseline: middle;
}
.factory {
  fill: var(--purple);
  font: 30px "JetBrains Mono", ui-monospace, monospace;
  dominant-baseline: middle;
}
.factory.mutable { fill: var(--pink); }
.annotation {
  opacity: 0;
  transition: opacity 640ms cubic-bezier(.22, .61, .36, 1);
  transition-delay: 0ms;
}
.annotation text {
  fill: var(--purple);
  font: 48px "JetBrains Mono", ui-monospace, monospace;
}
.annotation.mutable text { fill: var(--pink); }
.annotation.mutable .edge { stroke: var(--pink); }
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
