---
theme: kotlin
transition: fade
layout: cover
class: fundamentals-cover
canvasWidth: 1440
aspectRatio: 16/9
colorSchema: both
favicon: /favicon.svg
fonts:
  sans: JetBrains Sans
  mono: JetBrains Mono
  provider: none
  local:
    - JetBrains Sans
    - JetBrains Mono
highlighter: shiki
themeConfig:
  kodee: greeting
  drawnAnnotation:
    connect: false
  snippets:
    dir: src/main/kotlin/presentation/snippets
    package: presentation.snippets
    imports:
      - presentation.support.*
kodee: welcome
---

<!-- @formatter:off -->

# Kotlin Fundamentals

## From first expression to expressive Kotlin APIs

Kotlin 2.4.10 · JVM 21 · IntelliJ IDEA

---
layout: intro
class: section-slide agenda-slide
kodee: wave
---

# Course topics

1. Intro to the Kotlin language
2. Kotlin syntax and extension functions
3. Classes and types
4. Lambdas and collections
5. DSLs and builders
6. Coroutines

---
class: kotlin-history-slide
---

<KotlinHistory />

---
src: ./lessons/lesson-1.md
---
---
src: ./lessons/lesson-2.md
---
---
src: ./lessons/lesson-4.md
---
---
src: ./lessons/lesson-3.md
---
---
src: ./lessons/lesson-5.md
---
---
src: ./lessons/appendix-compiler-flags.md
---
