plugins {
  id("org.jetbrains.kotlin.jvm") version "2.4.20"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.ktor:ktor-server-core-jvm:3.4.0")
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
    "-Xallow-returns-result-of"
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
  kotlin.include("exercises/lesson5/**")
}
configurations[lesson5.implementationConfigurationName].extendsFrom(configurations.implementation.get())

// The incomplete student starter is checked only when explicitly requested.
val lesson5ReferenceCheck = tasks.register<JavaExec>("lesson5ReferenceCheck") {
  classpath = lesson5.runtimeClasspath
  mainClass.set("presentation.support.AgendaChecksKt")
}
tasks.register<JavaExec>("lesson5ExerciseCheck") {
  classpath = lesson5.runtimeClasspath
  mainClass.set("exercises.lesson5.AgendaChecksKt")
}
tasks.check { dependsOn(lesson5ReferenceCheck) }
