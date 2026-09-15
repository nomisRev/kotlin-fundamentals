<script setup lang="ts">
import { computed } from 'vue'
import { useSlideContext } from '@slidev/client'

const { $clicks } = useSlideContext()
const stage = computed(() => Math.min($clicks.value, 3))

type Kind = 'readonly' | 'mutable'

interface Card {
  id: string
  label: string
  /** Centre x of the card on the 1920x1080 canvas. */
  cx: number
  /** Top y of the card. */
  top: number
  kind: Kind
  stage: number
  delay: number
  /** Members this interface adds. A `//` line is a comment, not a member. */
  members: string[]
}

// Same rows and colours as KotlinCollectionHierarchy, but every pill grows a
// body listing what that interface adds. Widths derive from the longest line
// so the cards stay hugging their code.
const HEADER = 60
const LINE = 32
const PAD_TOP = 6
const PAD_BOTTOM = 14
const PAD_X = 24
const CHAR = 15.6 // 26px JetBrains Mono advance
const HEADER_CHAR = 18 // 30px JetBrains Mono advance
const RADIUS = 30

const cards: Card[] = [
  { id: 'iterable', label: 'Iterable', cx: 560, top: 190, kind: 'readonly', stage: 0, delay: 0,
    members: ['operator fun iterator(): Iterator<T>'] },
  { id: 'iterator', label: 'Iterator', cx: 235, top: 380, kind: 'readonly', stage: 1, delay: 400,
    members: ['fun hasNext(): Boolean', 'fun next(): T'] },

  { id: 'collection', label: 'Collection', cx: 720, top: 515, kind: 'readonly', stage: 2, delay: 500,
    members: ['val size: Int', 'fun contains(element: T): Boolean'] },
  { id: 'list', label: 'List', cx: 470, top: 760, kind: 'readonly', stage: 2, delay: 1000,
    members: ['operator fun get(index: Int): T'] },
  { id: 'set', label: 'Set', cx: 920, top: 760, kind: 'readonly', stage: 2, delay: 1250,
    members: ['// no duplicates'] },

  { id: 'mutableIterable', label: 'MutableIterable', cx: 1400, top: 190, kind: 'mutable', stage: 3, delay: 500,
    members: ['override fun iterator(): MutableIterator<T>'] },
  { id: 'mutableIterator', label: 'MutableIterator', cx: 1700, top: 380, kind: 'mutable', stage: 3, delay: 1000,
    members: ['fun remove(): Unit'] },
  { id: 'mutableCollection', label: 'MutableCollection', cx: 1480, top: 515, kind: 'mutable', stage: 3, delay: 1300,
    members: [
      'fun add(element: T): Boolean',
      'fun remove(element: T): Boolean',
      'fun addAll(elements: Collection<T>): Boolean',
      'fun removeAll(elements: Collection<T>): Boolean',
    ] },
  { id: 'mutableList', label: 'MutableList', cx: 960, top: 925, kind: 'mutable', stage: 3, delay: 2000,
    members: ['operator fun set(index: Int, element: T): T'] },
  { id: 'mutableSet', label: 'MutableSet', cx: 1660, top: 925, kind: 'mutable', stage: 3, delay: 2250,
    members: ['// no new members'] },
]

interface Box extends Card {
  x: number
  y: number
  w: number
  h: number
  cy: number
}

const boxes: Box[] = cards.map((c) => {
  const longest = Math.max(...c.members.map((m) => m.length)) * CHAR
  const w = Math.ceil(Math.max(longest, c.label.length * HEADER_CHAR) + PAD_X * 2)
  const h = HEADER + (c.members.length ? PAD_TOP + LINE * c.members.length + PAD_BOTTOM : 0)
  return { ...c, x: c.cx - w / 2, y: c.top, w, h, cy: c.top + h / 2 }
})

const byId = Object.fromEntries(boxes.map((b) => [b.id, b])) as Record<string, Box>

interface Edge {
  from: string
  to: string
  stage: number
  delay: number
  /** `extends` is a solid subtype edge; `returns` is dashed: `iterator()` hands out the target. */
  relation: 'extends' | 'returns'
}

// Connectors run centre to centre underneath the opaque cards, so only the
// stretch between two cards shows.
const edges: Edge[] = [
  { from: 'iterable', to: 'iterator', stage: 1, delay: 0, relation: 'returns' },

  { from: 'iterable', to: 'collection', stage: 2, delay: 0, relation: 'extends' },
  { from: 'collection', to: 'list', stage: 2, delay: 500, relation: 'extends' },
  { from: 'collection', to: 'set', stage: 2, delay: 750, relation: 'extends' },

  { from: 'iterable', to: 'mutableIterable', stage: 3, delay: 0, relation: 'extends' },
  { from: 'mutableIterable', to: 'mutableIterator', stage: 3, delay: 700, relation: 'returns' },
  { from: 'mutableIterable', to: 'mutableCollection', stage: 3, delay: 800, relation: 'extends' },
  { from: 'collection', to: 'mutableCollection', stage: 3, delay: 1000, relation: 'extends' },
  { from: 'mutableCollection', to: 'mutableList', stage: 3, delay: 1500, relation: 'extends' },
  { from: 'list', to: 'mutableList', stage: 3, delay: 1700, relation: 'extends' },
  { from: 'mutableCollection', to: 'mutableSet', stage: 3, delay: 1750, relation: 'extends' },
  { from: 'set', to: 'mutableSet', stage: 3, delay: 1950, relation: 'extends' },
]

const edgePath = (e: Edge) => {
  const a = byId[e.from]
  const b = byId[e.to]
  return `M${a.cx} ${a.cy} L${b.cx} ${b.cy}`
}

interface Span {
  text: string
  cls: string
}

// Split a signature so the member name can be set in bold: keyword, name, rest.
const spans = (member: string): Span[] => {
  if (member.startsWith('//')) return [{ text: member, cls: 'comment' }]
  const m = member.match(/^((?:operator |override )?(?:fun|val) )(\w+)(.*)$/)
  if (!m) return [{ text: member, cls: '' }]
  return [
    { text: m[1], cls: 'keyword' },
    { text: m[2], cls: 'name' },
    { text: m[3], cls: '' },
  ]
}
</script>

<template>
  <div
    class="collection-contracts"
    role="img"
    aria-label="Animated Kotlin collection hierarchy showing what each interface adds. Iterable declares operator fun iterator() returning an Iterator, which has hasNext() and next(). Collection extends Iterable and adds size and contains. List adds get by index; Set adds nothing but forbids duplicates. MutableIterable overrides iterator() to return a MutableIterator, which adds remove(). MutableCollection extends both Collection and MutableIterable and adds add, remove, addAll and removeAll. MutableList adds set by index; MutableSet adds no new members."
  >
    <!-- These markers let Slidev own navigation, presenter mode, and backwards clicks. -->
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />
    <span v-click class="click-marker" />

    <svg viewBox="0 0 1920 1080" preserveAspectRatio="xMidYMid meet" aria-hidden="true">
      <defs>
        <clipPath v-for="b in boxes" :id="`contract-${b.id}`" :key="b.id">
          <rect :x="b.x" :y="b.y" :width="b.w" :height="b.h" :rx="RADIUS" />
        </clipPath>
      </defs>

      <image :href="'/kotlin-type-logo.svg'" x="95" y="105" width="72" height="72" />
      <text class="heading" x="185" y="143">Collection hierarchy</text>

      <g class="edges">
        <path
          v-for="e in edges"
          :key="`${e.from}-${e.to}`"
          class="edge"
          :class="[byId[e.to].kind, e.relation === 'returns' ? 'returns fade' : 'reveal', { visible: stage >= e.stage }]"
          :style="{ '--delay': `${e.delay}ms` }"
          :pathLength="e.relation === 'extends' ? 1 : undefined"
          :d="edgePath(e)"
        />
      </g>

      <g class="cards">
        <g
          v-for="b in boxes"
          :key="b.id"
          class="card"
          :class="[b.kind, { fade: b.stage > 0, visible: stage >= b.stage }]"
          :style="{ '--delay': `${b.delay}ms` }"
        >
          <rect class="body" :x="b.x" :y="b.y" :width="b.w" :height="b.h" :rx="RADIUS" />
          <rect
            class="header"
            :x="b.x" :y="b.y" :width="b.w" :height="HEADER"
            :clip-path="`url(#contract-${b.id})`"
          />
          <text class="label" :x="b.cx" :y="b.y + HEADER / 2">{{ b.label }}</text>
          <text
            v-for="(member, i) in b.members"
            :key="member"
            class="member"
            :x="b.x + PAD_X"
            :y="b.y + HEADER + PAD_TOP + LINE * i + LINE / 2"
          ><tspan v-for="(s, j) in spans(member)" :key="j" :class="s.cls">{{ s.text }}</tspan></text>
        </g>
      </g>
    </svg>
  </div>
</template>

<style scoped>
.collection-contracts {
  --purple: #7954f6;
  --pink: var(--fundamentals-pink, #eb55e6);
  --purple-tint: #efeafe;
  --pink-tint: #fde8fb;
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
.edge.returns { stroke-dasharray: 14 18; }

.card .body { fill: var(--purple-tint); }
.card .header { fill: var(--purple); }
.card.mutable .body { fill: var(--pink-tint); }
.card.mutable .header { fill: var(--pink); }
.card .label {
  fill: #fff;
  font: 30px "JetBrains Mono", ui-monospace, monospace;
  text-anchor: middle;
  dominant-baseline: middle;
}
.card .member {
  fill: var(--purple);
  font: 26px "JetBrains Mono", ui-monospace, monospace;
  dominant-baseline: middle;
  white-space: pre;
}
.card.mutable .member { fill: var(--pink); }
.member .keyword { opacity: 0.65; }
.member .name { font-weight: 700; }
.member .comment { font-style: italic; opacity: 0.6; }

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

/* Delays only apply while entering, so a build leaves promptly on the next click. */
.fade.visible {
  transition-delay: var(--delay, 0ms);
}
.reveal.visible {
  transition-delay: var(--delay, 0ms), var(--delay, 0ms);
}
.visible { opacity: 1; stroke-dashoffset: 0; }

@media (prefers-reduced-motion: reduce) {
  .fade, .reveal { transition-duration: 1ms !important; transition-delay: 0ms !important; }
}
</style>
