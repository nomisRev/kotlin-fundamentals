plugins {
  id("org.jetbrains.kotlin.jvm") version "2.4.20"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.11.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.11.0")
  implementation("io.projectreactor:reactor-core:3.7.11")
}

kotlin {
  jvmToolchain(21)
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
  )
}

val snippetsCheck by tasks.register<Exec>("snippetsCheck") {
  commandLine("npx", "slidev-kotlin-snippets", "--check")
}

val appendixSnippetsCheck = tasks.register<Exec>("appendixSnippetsCheck") {
  commandLine("npx", "slidev-kotlin-snippets", "appendix.md", "--check")
}

tasks.check { dependsOn(snippetsCheck, appendixSnippetsCheck) }
