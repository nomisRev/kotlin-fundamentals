---
layout: intro
class: section-slide
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 1</div>

# Introduction to Kotlin

---
class: fundamentals-slide
---

# How to start writing Kotlin

* Kotlin LSP makes Kotlin available everywhere including CLI
    * IntelliJ IDEA is most used, and most powerful IDE for Kotlin

<v-clicks at="1">

* Coding agents: Junie, Claude, Codex, OpenCode, ...
    * Can use Kotlin LSP to navigate, and refactor code

</v-clicks>

---
class: fundamentals-slide
---

# Using Kotlin compiler manually

<DrawnAnnotation type="underline" text="fun main()" label="Entrypoint into your Kotlin program" :on="0">

```kotlin [Hello.kt]
fun main() {
    println("Hello, World!")
}
```

</DrawnAnnotation>

<v-click at="1">

<div class="h8" />

<DrawnAnnotation type="underline" text="-include-runtime" label="Make jar self-contained by including Kotlin Std jar" :on="1">

> Kotlin compiles to java compatible bytecode

```bash [Terminal]
$ kotlinc Hello.kt -include-runtime -d hello.jar
```

</DrawnAnnotation>

</v-click>

<div class="h8" />

<v-click at="2">

```bash [Terminal]
$ java -jar hello.jar
# Hello, World!
```

</v-click>

---
class: fundamentals-slide
---

# The Kotlin Toolchain

> Build JVM, Android, iOS, multiplatform, and server-side applications

<div class="h-8"></div>

```bash [Terminal]
sdk install kotlintoolchain

curl -fsSL https://kotl.in/install.sh | sh

powershell -ExecutionPolicy ByPass -c "irm 'https://kotl.in/install.ps1' | iex"
```

---
class: fundamentals-slide
---

# The Kotlin Toolchain

```bash [Terminal]
simonvergauwen@Simons-MacBook-Pro ~/D/tmp> kotlin init
Select a project template:
  Android application (Jetpack Compose)
    An Android application using Jetpack Compose for its UI
  Compose Multiplatform application
    A KMP project with Android, iOS, and JVM desktop applications sharing UI with Compose Multiplatform
  iOS application (Compose Multiplatform)
    An iOS application using Compose Multiplatform for iOS for its UI
  JVM console application
    A plain JVM console application without any framework
  JVM GUI application (Compose Multiplatform)
    A JVM application using Compose Multiplatform for Desktop for its UI
  Kotlin Multiplatform library
    A multiplatform library targeting Android, iOS, and the JVM
❯ Ktor server application
    A Ktor server application with the Netty engine
  Multiplatform CLI application
    A multiplatform CLI application targeting the JVM, as well as Linux, macOS, and Windows native targets
  Spring Boot application (Java)
    A Spring Boot application written in Java
  Spring Boot application (Kotlin)
    A Spring Boot application written in Kotlin
↑ up • ↓ down • enter select
```

---
class: fundamentals-slide amper-slide
---

<DrawnAnnotation type="underline" text="- ./shared" label="Depend on other Kotlin Toolchain module" :on="2" insert>

````md magic-move [module.yaml]
```yaml [module.yaml]
product: jvm/app
```

```yaml [module.yaml]
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared
```

```yaml [module.yaml]
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared
  
test-dependencies:
  - org.testcontainers:testcontainers:2.0.5
```

```yaml [module.yaml]
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared
  
test-dependencies:
  - org.testcontainers:testcontainers:2.0.5
  
settings:
  kotlin:
    version: 2.2.21
```

```yaml [module.yaml]
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared
  
test-dependencies:
  - org.testcontainers:testcontainers:2.0.5
  
settings:
  kotlin:
    version: 2.2.21
  jvm:
    mainClass: org.jetbrains.example.MainKt
    release: 17
```

```yaml [module.yaml]
product: jvm/app

apply:
  - ../shared.module-template.yaml

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared
  
test-dependencies:
  - org.testcontainers:testcontainers:2.0.5

settings:
  kotlin:
    version: 2.2.21
  jvm:
    release: 17
```
````

</DrawnAnnotation>

---
class: fundamentals-slide
---

# Web Wizard

<div class="kmp-web-wizard-images">
  <img src="/kmp-wizard-new-project.png" class="kmp-web-wizard-new-project" alt="Kotlin Multiplatform Wizard New Project form with Android and iOS targets selected" />
  <img src="/kmp-wizard-template-gallery.png" class="kmp-web-wizard-template-gallery" alt="Kotlin Multiplatform Wizard template gallery showing shared-UI and native-UI app templates" />
</div>

---
class: fundamentals-slide
---

# IntelliJ @ https://www.jetbrains.com/idea/

<div class="card"> Community (FREE) & Ultimate (paid) version available </div>

<FillImage src="/intellij.png" alt="IntelliJ IDEA displaying a Kotlin project tree and ProfileRoutes.kt in the editor" />

---
class: fundamentals-slide
---

# IntelliJ New Project Wizard

<FillImage src="/intellij_create_project.png" alt="IntelliJ IDEA New Project dialog with Kotlin selected as the build system and Ktor server application selected as the template" />

---
class: fundamentals-slide gradle-slide
---

# Build system: Gradle

````md magic-move [build.gradle.kts]
```kotlin [build.gradle.kts]
plugins {
    kotlin("jvm") version "2.4.10"
}
```

```kotlin [build.gradle.kts]
plugins {
    kotlin("jvm") version "2.4.10"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
}
```

```kotlin [build.gradle.kts]
plugins {
    kotlin("jvm") version "2.4.10"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
    implementation(project(":shared"))
}
```

```kotlin [build.gradle.kts]
plugins {
    kotlin("jvm") version "2.4.10"
    application
}

application {
  mainClass = "org.jetbrains.example.MainKt"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
    implementation(project(":shared"))
}
```

```kotlin [build.gradle.kts]
plugins {
    kotlin("jvm") version "2.4.10"
    application
}

application {
  mainClass = "org.jetbrains.example.MainKt"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
    implementation(project(":shared"))
}

kotlin {
    jvmToolchain(21)
}
```
````

---
class: fundamentals-slide maven-slide
---

# Build system: Maven

````md magic-move [pom.xml]
```xml [pom.xml]
<build>
    <plugins>
      <plugin>
        <groupId>org.jetbrains.kotlin</groupId>
        <artifactId>kotlin-maven-plugin</artifactId>
        <version>2.4.10</version>
        <extensions>true</extensions>
        <executions>...</executions>
      </plugin>
    </plugins>
</build>
```

```xml [pom.xml]
<build>
    <plugins>
      <plugin>
        <groupId>org.jetbrains.kotlin</groupId>
        <artifactId>kotlin-maven-plugin</artifactId>
        <version>2.4.10</version>
        <extensions>true</extensions>
        <executions>...</executions>
      </plugin>
    </plugins>
</build>

<dependencies>
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-stdlib</artifactId>
      <version>2.4.10</version>
    </dependency>
</dependencies>
```
````

---
class: fundamentals-slide
---

# Start writing Kotlin

> Add a Kotlin file in the `src` directory of any **Kotlin Toolchain** module

<div class="h-8"></div>

<DrawnAnnotation text="package org.jetbrains.example" label="Add packages to organize Kotlin files logically" :at="1">

```kotlin [src/Main.kt]
package org.jetbrains.example

fun main() {
    println("My first Kotlin program!")
}
```

</DrawnAnnotation>

---
class: fundamentals-slide
---

# Running our first Kotlin program

<div class="card">With our main class configured in our build</div>

```bash [Terminal]
kotlin run
./gradlew run
mvn exec:java -Dexec.mainClass="org.jetbrains.example.MainKt"
```

---
class: fundamentals-slide
---

# Running our first Kotlin program

<div class="card">We just need to write a <b>main</b> function in any Kotlin file.</div>

<FillImage src="/intellij-hello-kotlin.png" alt="IntelliJ IDEA showing a Kotlin main function and the successful program output in the Run tool window" />

---
class: fundamentals-slide
---

# Running our first Kotlin program

<DrawnAnnotation source-type="circle"
selector="[data-run-configuration]"
target="[data-screenshot]"
target-type="circle"
:target-x="92.8"
:target-y="5.5"
:target-radius="1.5"
:at="2"
>
<DrawnAnnotation source-type="circle"
selector="[data-gutter-run]"
target="[data-screenshot]"
target-type="circle"
:target-x="4.3"
:target-y="43.94"
:target-radius="2.15"
:at="1"
>

  <div class="card">Click on the gutter run icon <span class="run-icon" data-gutter-run role="img" aria-label="Run"></span> next to the main function.</div>
  <img src="/intellij-hello-kotlin-run.png" data-screenshot class="course-image" alt="Kotlin main function in IntelliJ IDEA with the gutter Run icon and toolbar run configuration visible" />

</DrawnAnnotation>

  <v-click at="2">

  > Afterwards you can click on the Run Configuration <span class="run-icon" data-run-configuration role="img" aria-label="Run"></span> icon.

  </v-click>

</DrawnAnnotation>

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise</div>

# Create a Kotlin project

- Create a Kotlin Toolchain Kotlin/JVM project
    - Use CLI, IntelliJ Wizard or KMP Web Wizard.
- Add a package and a `main` function (if missing)
    - Make it `println("My first Kotlin Program!")`
- Run it from CLI, and/or IntelliJ

