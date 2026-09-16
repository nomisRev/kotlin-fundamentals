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
import kotlinx.html.*
import kotlinx.html.stream.createHTML

createHTML().html {
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
    fun body(block: BodyBuilder.() -> Unit): Unit = TODO()       
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()
```

---
magic-move
---

# DSL entrypoint

<DrawnAnnotation text="  b" label="Called on `this.` `HtmlBuilder` only available within `html { }`"  :geometry="{ label: { x: 0.3777, y: 0.5848 } }"/>

<TypeHint :line="7" receiver="HtmlBuilder">

```kotlin
class HtmlBuilder {
    fun body(block: BodyBuilder.() -> Unit): Unit = TODO()       
}

fun html(block: HtmlBuilder.() -> Unit): String = TODO()

val index = html {
    body { }
}
```

</TypeHint>

---
magic-move
---

# Building a DSL

```kotlin
class HtmlBuilder {
    private val buffer = StringBuilder()
    
    fun body(block: BodyBuilder.() -> Unit): Unit = TODO()
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
    
    override fun toString(): String = buffer.toString()
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
    
    override fun toString(): String = buffer.toString()
}

fun html(block: HtmlBuilder.() -> Unit): String = 
    HtmlBuilder().apply(block).toString()
```

---

# Special syntax

```kotlin no-compile
a("https://kotlinlang.org") {
    target = ATarget.blank
    +"Main site"
}
```

---

# Special syntax

```kotlin no-compile
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

<TypeHint :line="1" receiver="HtmlBuilder" >
<TypeHint :line="2" receiver="BodyBuilder" >
<InlineCompilerError text="body" :line="3" message="'fun body(block: BodyBuilder.() -> Unit): Unit' cannot be called in this context" >

```kotlin
html {
    body {
        body {

        } 
    }
}
```

</InlineCompilerError>
</TypeHint>
</TypeHint>

---

# Extending library DSLs

<TypeHint :line="1" receiver="BODY">

```kotlin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun Agenda.render(): String = createHTML().body {
  h1 { +title }
  sessions.forEach { session ->
    val label = "${session.title} · ${session.minutes} min"
    div("session") { +label }
  }
}
```

</TypeHint>

---
magic-move
---

# An ordinary extension can extend the DSL

<TypeHint :line="1" receiver="BODY">
<DrawnAnnotation text="FlowContent." label="Available inside every tag that accepts flow content" :geometry="{ label: { x: 0.3276, y: 0.6573 } }"/>

```kotlin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun Agenda.render(): String = createHTML().body {
  h1 { +title }
  sessions.forEach { sessionCard(it) }
}

fun FlowContent.sessionCard(session: Session) {
  val label = "${session.title} · ${session.minutes} min"
  div("session") { +label }
}
```

</TypeHint>

---

# Keep the session as the extension receiver

<TypeHint :line="1" receiver="BODY">
<DrawnAnnotation text="context(html: FlowContent)" label="Satisfied by the `body { }` receiver, nothing is passed" :geometry="{ label: { x: 0.3490, y: 0.7019 } }"/>
<DrawnAnnotation text="it.card()"/>

```kotlin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun Agenda.render(): String = createHTML().body {
  h1 { +title }
  sessions.forEach { it.card() }
}

context(html: FlowContent)
fun Session.card() {
  val label = "$title · $minutes min"
  html.div("session") { +label }
}
```

</TypeHint>

---

# Keep the session as the extension receiver

<TypeHint :line="1" receiver="BODY">
<TypeHint :line="8" receiver="Session">
<DrawnAnnotation text=".cards()" on="0" />
<DrawnAnnotation text="Iterable" label="Iterable is more flexible and allows `List`, `Set`, ... and custom `Iterable` types" on="0" :geometry="{ label: { x: 0.5161, y: 0.6961 } }"/>
<DrawnAnnotation text="with(it)" label="Leverage `with` `Session.` to avoid refactoring the implementation" on="1"  :geometry="{ label: { x: 0.4801, y: 0.7039 } }"/>

```kotlin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun Agenda.render(): String = createHTML().body {
  h1 { +title }
  sessions.cards()
}

context(html: FlowContent)
fun Iterable<Session>.cards() = forEach {
    with(it) {
        val label = "$title · $minutes min"
        html.div("session") { +label }
    }
}
```

</TypeHint>
</TypeHint>

---

# Keep the session as the extension receiver

<TypeHint :line="1" receiver="BODY">
<TypeHint :line="8" receiver="Session">
<DrawnAnnotation text="+" occurrence="2" />
<DrawnAnnotation text="operator" />
<DrawnAnnotation text="unaryPlus" label="Create 1st party syntax for your own domain"  :geometry="{ label: { x: 0.3637, y: 0.7038 } }"/>

```kotlin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun Agenda.render(): String = createHTML().body {
  h1 { +title }
  +sessions
}

context(html: FlowContent)
operator fun Iterable<Session>.unaryPlus() = forEach {
    with(it) {
        val label = "$title · $minutes min"
        html.div("session") { +label }
    }
}
```

</TypeHint>
</TypeHint>

---

# A receiver satisfies a context parameter

<TypeHint :line="7" receiver="Clock">
<DrawnAnnotation text="with(Clock.System)" label="`Clock` is `this` and satisfies `context(clock: Clock)`" :geometry="{ label: { x: 0.3340, y: 0.6559 } }"/>

```kotlin
import kotlin.time.Clock
import kotlin.time.Instant

data class Talk(val title: String, val startsAt: Instant, val endsAt: Instant)

context(clock: Clock)
fun Talk.isLive(): Boolean = clock.now() in startsAt..<endsAt

fun liveTalks(talks: List<Talk>): List<Talk> =
  with(Clock.System) {
    talks.filter { it.isLive() }
  }
```

</TypeHint>

---
magic-move
---

# `context` supplies a context without a receiver

<TypeHint :line="7" context="Clock">
<DrawnAnnotation text="context(Clock.System)" label="Only a context, `this` stays what it was" :geometry="{ label: { x: 0.2759, y: 0.6531 } }"/>

```kotlin
import kotlin.time.Clock
import kotlin.time.Instant

data class Talk(val title: String, val startsAt: Instant, val endsAt: Instant)

context(clock: Clock)
fun Talk.isLive(): Boolean = clock.now() in startsAt..<endsAt

fun liveTalks(talks: List<Talk>): List<Talk> =
  context(Clock.System) {
    talks.filter { it.isLive() }
  }
```

</TypeHint>

---

# A context is not a receiver

> A receiver resolves context parameters, a context never resolves members

<TypeHint :line="1" receiver="Clock">
<TypeHint :line="5" context="Clock">
<InlineCompilerError text="now()" :line="6" message="Unresolved reference 'now'.">

```kotlin
with(Clock.System) {
  now()
}

context(Clock.System) {
  now()
}
```

</InlineCompilerError>
</TypeHint>
</TypeHint>

---

# Pass a context argument by name

> `-Xexplicit-context-arguments` · experimental in Kotlin 2.4

<DrawnAnnotation text="logger = Logger(&quot;APP&quot;)" label="One call, nothing to wrap in `with` or `context`"/>

```kotlin
class Logger(val tag: String) {
  fun log(message: String) = println("[$tag] $message")
}

context(logger: Logger)
fun greet(name: String) = logger.log("hello, $name")

greet(logger = Logger("APP"), name = "Grace")
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 5</div>

# Build the session

> `build()` `require`s a positive duration, the defaults are `null` and `30`

```kotlin
check(SessionBuilder("Talk").build() == Session("Talk", null, 30))
check(runCatching { SessionBuilder("Talk").apply { minutes = 0 }.build() }.isFailure)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 5</div>

# Insert with `+`

> `insert()` is a member extension, `unaryPlus()` delegates to it

```kotlin
val program = agenda("Kotlin afternoon") {
  session("Expressive APIs") {
    speaker = "Simon"
    minutes = 30
  }
  session("Build your own DSL") { minutes = 20 }
  +coffeeBreak
}

check(program.sessions.last() == coffeeBreak)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 5</div>

# Build the agenda

> `build()` snapshots the list with `toList()`, `agenda` is `apply` then `build()`

```kotlin
val builder = AgendaBuilder("Snapshot")
with(builder) { +coffeeBreak }
val before = builder.build()
with(builder) { +coffeeBreak }

check(before.sessions.size == 1)
check(builder.build().sessions.size == 2)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 5</div>

# Total the minutes

> `sumOf` over the sessions

```kotlin no-compile
check(program.totalMinutes() == 65)
check(agenda("Empty") {}.totalMinutes() == 0)
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 5 / 5</div>

# Add `lightningTalk`

> An ordinary extension on `AgendaBuilder`, `this.speaker` picks the property

```kotlin no-compile
val talks = agenda("Lightning talks") {
  lightningTalk("Receivers", "Ada")
}

check(talks.sessions.single() == Session("Receivers", "Ada", 5))
```

---

# Read the syntax as function calls

| Syntax | Meaning |
| --- | --- |
| `agenda { ... }` | A function invokes a receiver lambda |
| `apply { ... }` | Configure and return the same receiver |
| `run { ... }`, `with(value) { ... }` | Return the block's result |
| `session.insert()` | A member extension uses the builder's scope |
| `+session` | Call `session.unaryPlus()` |
| `context(html: FlowContent)` | Require a context for the operation |
| `context(value) { ... }` | Supply a context, keep the receiver |
| `greet(logger = log)` | Pass a context argument by name |

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
fun <A, B> Iterable<A>.map(dummy: Unit, transform: (A) -> B): List<B> = TODO()

[1, 2, 3].map(Unit) { input -> input * 2 }
```
