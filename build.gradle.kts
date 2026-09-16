plugins {
  id("org.jetbrains.kotlin.jvm") version "2.4.20"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
  // Lesson 6: coroutines, plus the interop modules the slides wrap Java SDKs with.
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.11.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.11.0")
  implementation("io.projectreactor:reactor-core:3.7.11")
}

kotlin {
  jvmToolchain(21)
  // Every experimental (-X) flag `kotlinc -X` still reports as active for language
  // version 2.4 (2.4.20) rather than redundant. Flags `kotlinc` reports as redundant
  // for 2.4 (their feature already shipped/stabilized) are omitted, as is
  // `-Xcontext-receivers`, which 2.4.20 rejects outright as superseded by
  // `-Xcontext-parameters`. Flags the slides teach and demonstrate compiling:
  // - collection literals (`[1, 2, 3]`), lesson 2
  // - the unused return value checker (`@MustUseReturnValues`/`@IgnorableReturnValue`), lesson 2
  // - context-sensitive resolution (bare enum entries in a `when`), lesson 3
  // - explicit backing fields (`val count: StateFlow<Int>` with `field = MutableStateFlow(0)`), lesson 6
  compilerOptions.freeCompilerArgs.addAll(
    "-Xcollection-literals",
    "-Xreturn-value-checker=full",
    "-Xcontext-sensitive-resolution",
    "-Xintrinsic-const-evaluation",
    "-Xcompanion-blocks-and-extensions",
    "-Xdirect-java-actualization",
    "-Xexplicit-context-arguments",
    "-Xlocal-type-aliases",
    "-Xname-based-destructuring=complete",
    "-Xallow-returns-result-of",
    "-Xexplicit-backing-fields",
  )
}

val snippetsCheck by tasks.registering(Exec::class) {
  commandLine("npx", "slidev-kotlin-snippets", "--check")
}

// The appendix (lessons/appendix-compiler-flags.md) is a standalone deck, not
// imported into slides.md, so it needs its own entry point and its own
// `themeConfig.snippets.dir`/`package` (appendix.md): each `slidev-kotlin-snippets`
// run deletes generated files its own entry no longer produces, so two entries
// sharing one output directory would delete each other's files.
val appendixSnippetsCheck by tasks.registering(Exec::class) {
  commandLine("npx", "slidev-kotlin-snippets", "appendix.md", "--check")
}

tasks.check { dependsOn(snippetsCheck, appendixSnippetsCheck) }

// Keep this lesson runnable while other lessons are being edited.
val lesson5 = sourceSets.create("lesson5")
kotlin.sourceSets.named("lesson5") {
  kotlin.srcDir("src/main/kotlin")
  kotlin.include("presentation/support/Agenda*.kt")
  kotlin.include("presentation/snippets/lesson-5/**")
}
configurations[lesson5.implementationConfigurationName].extendsFrom(configurations.implementation.get())

// Lesson 6 compiles on its own too, Java SDK included, so its fences are
// checked while earlier lessons still have fences that do not compile.
val lesson6 = sourceSets.create("lesson6")
kotlin.sourceSets.named("lesson6") {
  kotlin.srcDir("src/main/kotlin")
  kotlin.include("presentation/support/Conference.kt")
  kotlin.include("presentation/snippets/lesson-6/**")
}
lesson6.java.srcDirs("src/main/java")
lesson6.java.include("presentation/support/**", "snippets/lesson-6/**")
configurations[lesson6.implementationConfigurationName].extendsFrom(configurations.implementation.get())
