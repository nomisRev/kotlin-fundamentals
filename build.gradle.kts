plugins {
    id("org.jetbrains.kotlin.jvm") version "2.4.10"
}

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(21)
    // Experimental language features the slides teach and demonstrate compiling:
    // - collection literals (`[1, 2, 3]`), lesson 2
    // - the unused return value checker (`@MustUseReturnValues`/`@IgnorableReturnValue`), lesson 2
    // - context-sensitive resolution (bare enum entries in a `when`), lesson 3
    compilerOptions.freeCompilerArgs.addAll(
        "-Xcollection-literals",
        "-Xreturn-value-checker=full",
        "-Xcontext-sensitive-resolution",
    )
}

val snippetsCheck by tasks.registering(Exec::class) {
    commandLine("npx", "slidev-kotlin-snippets", "--check")
}

tasks.check { dependsOn(snippetsCheck) }
