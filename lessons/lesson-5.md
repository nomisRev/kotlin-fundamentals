---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 5</div>

# DSLs and builders

## Design a small language using ordinary Kotlin

---

# HTML

```kotlin
html {
    body {
        div {
            a("https://kotlinlang.org") {
                target = ATarget.blank
                +"Main site"
            }
        }
    }
}
```

---

# DSL entrypoint

```kotlin
class HtmlBuilder {
    fun body(block: BodyBuilder.() -> Unit) = TODO()       
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()
```

---
magic-move
---

# DSL entrypoint

```kotlin
class HtmlBuilder {
    fun body(block: BodyBuilder.() -> Unit) = TODO()       
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()

val index = html {
    body { }
}
```

---
magic-move
---

# Building a DSL

```kotlin
class HtmlBuilder {
    private val buffer = StringBuilder()
    
    fun body(block: BodyBuilder.() -> Unit) = TODO()
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()
```

---
magic-move
---

# Building a DSL

```kotlin
class HtmlBuilder {
    private val buffer = StringBuilder()
    
    fun body(block: BodyBuilder.() -> Unit) {
        buffer.append("<body>")
        BodyBuilder(buffer).block()
        buffer.append("</body>")
    }
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()
```

---
magic-move
---

# Building a DSL

```kotlin
class HtmlBuilder {
    private val buffer = StringBuilder()
    
    fun body(block: BodyBuilder.() -> Unit) {
        buffer.append("<body>")
        BodyBuilder(buffer).block()
        buffer.append("</body>")
    }
    
    fun toString(): String = buffer.toString()
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()
```

---
magic-move
---

# Building a DSL

```kotlin
class HtmlBuilder {
    private val buffer = StringBuilder()
        
    fun body(block: BodyBuilder.() -> Unit) {
        buffer.append("<body>")
        BodyBuilder(buffer).block()
        buffer.append("</body>")
    }
    
    fun toString(): String = buffer.toString()
}

fun html(block: HtmlBuilder.() -> Unit): String = 
    HtmlBuilder().apply(block).toString()
```

---

# Special syntax

```kotlin
a("https://kotlinlang.org") {
    target = ATarget.blank
    +"Main site"
}
```

---

# Special syntax

```kotlin
class A {
    var target: ATarget
    operator fun String.unaryPlus(): Unit
}

a("https://kotlinlang.org") {
    target = ATarget.blank
    +"Main site"
}
```

---

# Incorrect nesting

<InlineCompilerError text="body" :line="3" on="1" message="'fun body(block: BodyBuilder.() -> Unit): Unit' cannot be called in this context" >

```kotlin
html {
    body {
        body {
            
        }
    }
}
```

</InlineCompilerError>

---

# Mark the receivers of the same DSL

> Only the nearest receiver of this DSL is implicitly available.

```kotlin
@DslMarker
annotation class HtmlDsl

@HtmlDsl
class HtmlBuilder

@HtmlDsl
class BodyBuilder
```


---

# Mark the receivers of the same DSL

> Only the nearest receiver of this DSL is implicitly available.

<InlineCompilerError text="body" :line="3" message="'fun body(block: BodyBuilder.() -> Unit): Unit' cannot be called in this context" >

```kotlin
html { // HtmlBuilder.() -> Unit
    body { // BodyBuilder.() -> Unit
        body {
              // ↑ Only BodyBuilder visible
        } 
    }
}
```

</InlineCompilerError>

---

# Ktor supplies a route-handler receiver

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.greetingRoutes() {
  get("/hello") { // RoutingContext.() -> Unit
    call.respondText("Hello, Ada")
  }
}
```


---
magic-move
---

# An ordinary extension can extend Ktor

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.greetingRoutes() {
  get("/hello") { // RoutingContext.() -> Unit
    respondGreeting(User("Ada"))
  }
}

suspend fun RoutingContext.respondGreeting(user: User) {
    call.respondText("Hello, ${user.name}")
}
```

---

# Keep the user as the extension receiver

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.greetingRoutes() {
  get("/hello") {
    User("Ada").respondGreeting()
  }
}

context(routing: RoutingContext)
suspend fun User.respondGreeting() {
    routing.call.respondText("Hello, $name")
}
```

---
layout: default
kodee: sitting
---

<div class="lesson-number">Exercise · 20 minutes</div>

# Complete the agenda builder

Implement the TODOs in `AgendaExercise.kt`.

- Build and validate sessions; configure children with `apply`.
- Add `Session.insert()` and delegate `unaryPlus()` to it.
- Return an agenda with a snapshot of its session list.
- Implement `Agenda.totalMinutes()` with the collection API.

<!--
The file lives under src/main/kotlin. Models, markers, scheduling, and rendering
are supplied. README instructions give the Gradle command for running checks.
Allow 12 minutes for construction, 5 for member extensions, and 3 for validation.
Reference code is separate so students can work without immediately seeing it.
-->

---

# Make this program work

```kotlin
val program = agenda("Kotlin afternoon") {
  session("Expressive APIs") {
    speaker = "Simon"
    minutes = 30
  }
  session("Build your own DSL") { minutes = 20 }
  +coffeeBreak
}

check(program.totalMinutes() == 65)
check(program.sessions.last() == coffeeBreak)
```

<!--
The slide uses the reference implementation so the deck itself compiles. Students
run the same behavior against their exercise package with lesson5ExerciseCheck.
A supplied Session with a non-positive duration must be rejected by insert too.
-->

---

# Check the boundaries

- An omitted duration becomes 30 minutes; speaker defaults to `null`.
- Zero and negative durations are rejected, including inserted sessions.
- An empty agenda is valid and totals zero.
- Reusing a builder cannot change an earlier agenda's list.
- Scheduling at 09:00 and 14:00 leaves the agenda unchanged.

<!--
Ask learners to explain why toList matters. Check the DSL-marker failure in the
IDE by moving a session inside another session; do not leave broken code in source.
-->

---

# Extend the DSL with a reusable helper

```kotlin
fun AgendaBuilder.lightningTalk(title: String, speaker: String) {
  session(title) {
    this.speaker = speaker
    minutes = 5
  }
}

val program = agenda("Lightning talks") {
  lightningTalk("Receivers", "Ada")
}
```

<!--
Optional stretch. this.speaker disambiguates the builder property from the outer
function parameter. This is an extension of the DSL receiver itself, whereas
Session.insert is a member extension on another value inside its scope.
-->

---

# Read the syntax as function calls

| Syntax | Meaning |
| --- | --- |
| `agenda { ... }` | A function invokes a receiver lambda |
| `apply { ... }` | Configure and return the same receiver |
| `run { ... }`, `with(value) { ... }` | Return the block's result |
| `session.insert()` | A member extension uses the builder's scope |
| `+session` | Call `session.unaryPlus()` |
| `context(routing: RoutingContext)` | Require a context for the operation |

<!--
Closing check: ask who owns the state and what each expression returns.
@DslMarker controls implicit access; the runtime consumes the completed data.
Optional scope-function reference: let uses an argument and returns the lambda
result; also uses an argument and returns the original value. Both are ordinary
functions too. Do not add them to the required exercise.
-->

---

# Return values are meant to be used

> Experimental in Kotlin 2.3.0 with `-Xreturn-value-checker=full`

<DrawnAnnotation text="@MustUseReturnValues"/>
<DrawnAnnotation text="calculator.calculate()" label="Ignoring the result is a compiler warning" color="red"/>

```kotlin
@MustUseReturnValues
class Calculator {
  fun calculate(): Int = 42
}

fun example(calculator: Calculator) {
  calculator.calculate()
  val result = calculator.calculate()
}
```

---
magic-move
---

# Return values are meant to be used

> Experimental in Kotlin 2.3.0 with `-Xreturn-value-checker=full`

<DrawnAnnotation text="@IgnorableReturnValue"/>
<DrawnAnnotation text="calculator.add(1)" label="Opting out is explicit, ignoring this result is intentional"/>

```kotlin
@MustUseReturnValues
class Calculator {
  private var total = 0
  fun calculate(): Int = total

  @IgnorableReturnValue
  fun add(value: Int): Calculator = apply { total += value }
}

fun example(calculator: Calculator) {
  calculator.add(1)
  val result = calculator.calculate()
}
```

---

# Functions that take lambdas

<DrawnAnnotation text="transform: (A) -> B)" />
<DrawnAnnotation text=" { " />
<DrawnAnnotation text=" }" label="Any function type as _last_ parameter allows _trailing lambda syntax_"/>

```kotlin
fun <A> Iterable<A>.map(dummy: Unit, transform: (A) -> B): List<B>

[1, 2, 3].map(Unit) { input -> input * 2 }
```
