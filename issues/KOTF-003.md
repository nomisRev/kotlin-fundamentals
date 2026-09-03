# Compile-check the Kotlin fences of kotlin-fundamentals

**Status:** open
**Repository:** kotlin-fundamentals

## Problem

exposed-fundamentals and ktor-fundamentals generate one Kotlin file per fence
with `slidev-kotlin-snippets` and compile them with Gradle, so a stale slide
fails the build. kotlin-fundamentals has no `themeConfig.snippets`, no Gradle
project, and no Java step in `deploy.yml`, so its code is never checked.

## Work

- [ ] Add a minimal Gradle project (Kotlin JVM 21, matching the "Kotlin 2.4.10 ·
      JVM 21" cover line) with a `presentation.support` package for the types
      the slides use without defining them.
- [ ] Set `themeConfig.snippets` (`dir`, `package`, `imports`) and run
      `npx slidev-kotlin-snippets`; mark sketches `no-compile`.
- [ ] Register `slidev-kotlin-snippets --check` in Gradle and run
      `./gradlew build` in `deploy.yml`, as ktor-fundamentals does.

## Acceptance

- [ ] `./gradlew build` compiles every non-`no-compile` fence of the deck.
