---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 1</div>

# Introduction to Kotlin

## From compiler to first program

---

# Write Kotlin in any editor

- **Kotlin LSP** brings Kotlin to every editor and the CLI
- **IntelliJ IDEA** is the most used and most powerful Kotlin IDE
- **Coding agents** (Junie, Claude, Codex, OpenCode, ...) navigate and refactor through the LSP

---

# The compiler is all you need

<DrawnAnnotation text="fun main()" label="Entrypoint into your Kotlin program" :geometry="{ label: { x: 0.2911, y: 0.3722 } }"/>

```kotlin
fun main() {
  println("Hello, World!")
}
```

---
magic-move
---

# The compiler is all you need

> Kotlin compiles to Java compatible bytecode

<DrawnAnnotation text="-include-runtime" label="Make the jar self-contained by including the Kotlin stdlib" :geometry="{ label: { x: 0.4837, y: 0.5696 } }"/>

```kotlin
fun main() {
  println("Hello, World!")
}
```

```bash
$ kotlinc Hello.kt -include-runtime -d hello.jar
```

---
magic-move
---

# The compiler is all you need

> Kotlin compiles to Java compatible bytecode

<DrawnAnnotation text="java -jar hello.jar" label="Runs on any JVM" :geometry="{ label: { x: 0.3051, y: 0.6520 } }"/>

```kotlin
fun main() {
  println("Hello, World!")
}
```

```bash
$ kotlinc Hello.kt -include-runtime -d hello.jar
$ java -jar hello.jar
# Hello, World!
```

---

# The Kotlin Toolchain installs in one line

> Build JVM, Android, iOS, multiplatform, and server-side applications

```bash
sdk install kotlintoolchain

curl -fsSL https://kotl.in/install.sh | sh

powershell -ExecutionPolicy ByPass -c "irm 'https://kotl.in/install.ps1' | iex"
```

---
class: wide-code-slide
---

# `kotlin init` scaffolds a project

```bash
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

# A module is one YAML file

<DrawnAnnotation text="jvm/app" label="The product type says what the module builds" :geometry="{ label: { x: 0.3484, y: 0.2844 } }"/>

```yaml toolchain
product: jvm/app
```

---
magic-move
---

# A module is one YAML file

<DrawnAnnotation text="- ./shared" label="Depend on another module of this project" :geometry="{ label: { x: 0.3061, y: 0.4681 } }"/>

```yaml toolchain
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared
```

---
magic-move
---

# A module is one YAML file

<DrawnAnnotation text="test-dependencies" label="Only on the test classpath" :geometry="{ label: { x: 0.2601, y: 0.6110 } }"/>

```yaml toolchain
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared

test-dependencies:
  - org.testcontainers:testcontainers:2.0.5
```

---
magic-move
---

# A module is one YAML file

<DrawnAnnotation text="settings:" label="Toolchain settings replace plugin blocks" :geometry="{ label: { x: 0.3079, y: 0.7912 }, connector: { type: 'quadratic', start: { x: 0.1597, y: 0.6595 }, control: { x: 0.1992, y: 0.6599 }, end: { x: 0.2285, y: 0.6207 } } }"/>

```yaml toolchain
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared

test-dependencies:
  - org.testcontainers:testcontainers:2.0.5

settings:
  kotlin:
    version: 2.4.10
```

---
magic-move
---

# A module is one YAML file

<DrawnAnnotation text="mainClass" label="Entry point of a `jvm/app`" :geometry="{ label: { x: 0.3928, y: 0.8680 }, connector: { type: 'quadratic', start: { x: 0.2071, y: 0.8368 }, control: { x: 0.2423, y: 0.8295 }, end: { x: 0.2661, y: 0.8609 } } }"/>

```yaml toolchain
product: jvm/app

dependencies:
  - org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0
  - ./shared

test-dependencies:
  - org.testcontainers:testcontainers:2.0.5

settings:
  kotlin:
    version: 2.4.10
  jvm:
    mainClass: org.jetbrains.example.MainKt
    release: 17
```

---
magic-move
---

# A module is one YAML file

<DrawnAnnotation text="apply:"/>

```yaml toolchain
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
    version: 2.4.10
  jvm:
    release: 17
```

<!--
A module template shares settings across modules: the Kotlin version and JVM release
move out of module.yaml into shared.module-template.yaml, and every module applies it.
-->

---

# The KMP web wizard

<div class="kmp-web-wizard-images">
  <img src="/kmp-wizard-new-project.png" alt="Kotlin Multiplatform Wizard New Project form with Android and iOS targets selected" />
  <img src="/kmp-wizard-template-gallery.png" alt="Kotlin Multiplatform Wizard template gallery showing shared-UI and native-UI app templates" />
</div>

---

# IntelliJ IDEA

> Community (free) and Ultimate (paid) editions at jetbrains.com/idea

<FillImage src="/intellij.png" alt="IntelliJ IDEA displaying a Kotlin project tree and ProfileRoutes.kt in the editor" />

---

# IntelliJ's new project wizard

<FillImage src="/intellij_create_project.png" alt="IntelliJ IDEA New Project dialog with Kotlin selected as the build system and Ktor server application selected as the template" />

---

# Build system: Gradle

<DrawnAnnotation text="kotlin(&quot;jvm&quot;) version &quot;2.4.10&quot;" label="The Kotlin Gradle plugin is the only requirement" :geometry="{ label: { x: 0.3258, y: 0.3778 } }"/>

```kotlin gradle
plugins {
  kotlin("jvm") version "2.4.10"
}
```

---
magic-move
---

# Build system: Gradle

<DrawnAnnotation text="org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0" label="Maven coordinates, resolved from Maven Central"/>

```kotlin gradle
plugins {
  kotlin("jvm") version "2.4.10"
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
}
```

---
magic-move
---

# Build system: Gradle

<DrawnAnnotation text="project(&quot;:shared&quot;)" label="Depend on another module of this build"/>

```kotlin gradle
plugins {
  kotlin("jvm") version "2.4.10"
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
  implementation(project(":shared"))
}
```

---
magic-move
---

# Build system: Gradle

<DrawnAnnotation text="application"/>
<DrawnAnnotation text="mainClass"/>

```kotlin gradle
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

---
magic-move
---

# Build system: Gradle

<DrawnAnnotation text="jvmToolchain(21)"/>

```kotlin gradle
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

---
magic-move
---

# Build system: Gradle

<DrawnAnnotation text="mavenCentral" label="Need to explicitly referenve mavenCentral"/>

```kotlin gradle
plugins {
  kotlin("jvm") version "2.4.10"
  application
}

application {
  mainClass = "org.jetbrains.example.MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines:1.11.0")
  implementation(project(":shared"))
}

kotlin {
  jvmToolchain(21)
}
```

---
class: maven-slide
---

# Build system: Maven

<DrawnAnnotation text="kotlin-maven-plugin"/>

```xml maven
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

---
magicMove: true
class: maven-slide
---

# Build system: Maven

<DrawnAnnotation text="kotlin-stdlib" label="Maven needs the stdlib declared explicitly" :geometry="{ label: { x: 0.5352, y: 0.8730 } }"/>

```xml maven
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

---

# Start writing Kotlin

> Add a Kotlin file in the `src` directory of any **Kotlin Toolchain** module

<DrawnAnnotation text="package org.jetbrains.example" label="Packages organize Kotlin files logically" :geometry="{ label: { x: 0.4814, y: 0.4314 } }"/>

```kotlin
package org.jetbrains.example

fun main() {
  println("My first Kotlin program!")
}
```

---

# Running our first Kotlin program

> With the main class configured in the build

<DrawnAnnotation text="-Dexec.mainClass" label="Maven has no `run` task, so name the class"/>

```bash
kotlin run
./gradlew run
mvn exec:java -Dexec.mainClass="org.jetbrains.example.MainKt"
```

---

# Running our first Kotlin program

> Any Kotlin file with a `main` function will do

<FillImage src="/intellij-hello-kotlin.png" alt="IntelliJ IDEA showing a Kotlin main function and the successful program output in the Run tool window" />

---

# Running our first Kotlin program

<DrawnAnnotation source-type="circle" selector="[data-run-configuration]" target="[data-screenshot]" target-type="circle" :target-x="92.8" :target-y="5.5" :target-radius="1.5" at="1" passive>
<DrawnAnnotation source-type="circle" selector="[data-gutter-run]" target="[data-screenshot]" target-type="circle" :target-x="4.3" :target-y="43.94" :target-radius="2.15">

> Click the gutter run icon <span class="run-icon" data-gutter-run role="img" aria-label="Run"></span> next to the `main` function
> 
> ⌘ + ⇧ + R / Ctrl + ⇧ + R

<img src="/intellij-hello-kotlin-run.png" data-screenshot class="course-image" alt="Kotlin main function in IntelliJ IDEA with the gutter Run icon and toolbar run configuration visible" />

</DrawnAnnotation>

<v-click at="1">

> Afterwards the run configuration <span class="run-icon" data-run-configuration role="img" aria-label="Run"></span> icon does the same
>
> ⌘ + R / Ctrl + R 

</v-click>

</DrawnAnnotation>

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 5</div>

# Create a project

- Use the CLI (`kotlin init`), the IntelliJ wizard, or the KMP web wizard
- Pick the **JVM console application** template

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 5</div>

# Add an entry point

- Create a package
- Add a `main` function (if missing)

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 5</div>

# Say hello

- Make `main` print `"My first Kotlin Program!"`

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 5</div>

# Run it from the CLI

<DrawnAnnotation text="./kotlin" label="Will boostrap Toolchain on first run" />

- `./kotlin run` or `kotlin run` if Kotlin Toolchain is installed

---
layout: intro
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 5 / 5</div>

# Run it from IntelliJ

- Click the gutter run icon next to `main`
- Or ⌘ + ⇧ + R / Ctrl + ⇧ + R
