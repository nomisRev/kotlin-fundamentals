<script setup lang="ts">
import { computed } from 'vue'

defineOptions({ inheritAttrs: false })

const props = withDefaults(defineProps<{
  /** Image URL. */
  src: string
  /** Accessible description of the image. Use an empty string for decorative images. */
  alt: string
  /** How the image is resized inside the available space. */
  fit?: 'contain' | 'cover' | 'fill' | 'none' | 'scale-down'
  /** CSS object-position value. */
  position?: string
  /** Add the same visual depth used by course screenshots. */
  shadow?: boolean
}>(), {
  fit: 'contain',
  position: 'center',
  shadow: true,
})

const imageStyle = computed(() => ({
  objectFit: props.fit,
  objectPosition: props.position,
}))
</script>

<template>
  <div class="fill-image">
    <img
      v-bind="$attrs"
      :src="src"
      :alt="alt"
      class="fill-image__image"
      :class="{ 'fill-image__image--shadow': shadow }"
      :style="imageStyle"
    >
  </div>
</template>

<style>
/* Opt the containing Slidev layout into the column needed for a child to
   claim all space left by the content above it. */
.slidev-layout:has(.fill-image) {
  display: flex;
  flex-direction: column;
}

.fill-image {
  display: flex;
  flex: 1 1 0%;
  align-items: center;
  justify-content: center;
  min-width: 0;
  min-height: 0;
}

.fill-image__image {
  display: block;
  width: 100%;
  height: 100%;
  min-width: 0;
  min-height: 0;
}

.fill-image__image--shadow {
  filter: drop-shadow(0 0.4rem 0.55rem rgb(31 32 35 / 18%));
}
</style>
