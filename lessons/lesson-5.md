---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 5</div>

# DSLs and builders

## Design a small language using ordinary Kotlin

<!--
Allow 55 minutes for the walkthrough and 20 minutes for the exercise.
Prerequisites: lesson 2 extension functions; lesson 4 function values, lambdas,
and collection operations. Introduce receiver lambdas in this lesson.
Use presentation/support/Agenda.kt as the complete reference implementation.
-->

---

# An agenda in Kotlin

```kotlin
val program = agenda("Kotlin afternoon") {
  session("Expressive APIs") {
    speaker = "Simon"
    minutes = 30
  }
  session("Build your own DSL") {
    minutes = 20
  }
}
```

<!--
Ask learners to read this aloud before explaining implementation. The vocabulary
is agenda, session, speaker, and minutes. There is no parser or new language syntax.
Today we build this API, add scoped extensions, then extend a Ktor handler.
-->

---

# A DSL describes a domain

```kotlin
val program = Agenda(
  title = "Kotlin afternoon",
  sessions = listOf(
    Session("Expressive APIs", "Simon", 30),
    Session("Build your own DSL", null, 20)
  )
)
```

The constructor and the DSL can produce the same value.

<!--
DSL means domain-specific language. This is an internal DSL embedded in Kotlin.
Constructors are often enough. A builder earns its place when construction has
nested steps, reusable fragments, or configuration. Do not claim DSL syntax is
inherently better than these perfectly readable constructors.
-->

---

# The result is ordinary data

```kotlin
data class Session(
  val title: String,
  val speaker: String?,
  val minutes: Int
)

data class Agenda(
  val title: String,
  val sessions: List<Session>
)
```

<!--
The DSL produces these values. A mutable builder will be temporary; its returned
list is a snapshot. List is a read-only interface, not a general immutability guarantee.
-->

---

# The block has a receiver

```kotlin
val configure: SessionBuilder.() -> Unit = {
  speaker = "Simon"
  minutes = 30
}

val builder = SessionBuilder("Expressive APIs")
configure(builder)
val session = builder.build()
```

<!--
Build on lesson 4 function types. SessionBuilder is the lambda receiver; Unit is its result type.
These property assignments execute when configure is invoked, not when declared.
The builder is the object available as this inside the block.
-->

---

# `with` works with an existing value

```kotlin
val summary: String = with(program) {
  "$title: ${sessions.sumOf { it.minutes }} minutes"
}

println(summary)
// Kotlin afternoon: 50 minutes
```

`this` is the agenda. `it` is a session. The last expression is the result.

<!--
with receives its context object as a regular argument. The lambda has a receiver.
Source: https://kotlinlang.org/docs/scope-functions.html
-->

---

# `with` groups operations on an object

```kotlin
val text = StringBuilder()

with(text) {
  appendLine(program.title)
  append("Sessions: ")
  append(program.sessions.size)
}

println(text.toString())
```

<!--
Second with example: its result is ignored here. It still returns the final
expression, in this case append's StringBuilder result. with does not mean Unit.
-->

---

# Configure a builder explicitly

```kotlin
val builder = SessionBuilder("Expressive APIs")
builder.speaker = "Simon"
builder.minutes = 30

val session = builder.build()
```

<!--
The repeated builder name is the opportunity for apply. Identify the value we
want to keep: the configured builder, then the finished session.
-->

---
magic-move
---

# Configure a builder with `apply`

```kotlin
val builder = SessionBuilder("Expressive APIs").apply {
  speaker = "Simon"
  minutes = 30
}

val session = builder.build()
```

`apply` returns the same builder.

<!--
apply is an extension function accepting a receiver lambda. Its result does not
depend on the last expression of that lambda.
Source: https://kotlinlang.org/docs/scope-functions.html
-->

---

# A builder collects configuration

```kotlin
class SessionBuilder(private val title: String) {
  var speaker: String? = null
  var minutes: Int = 30

  fun build(): Session {
    require(minutes > 0) { "Duration must be positive" }
    return Session(title, speaker, minutes)
  }
}
```

<!--
Keep titles constructor-supplied, speaker optional, and a useful duration default.
Validation belongs at construction. The marker annotation is introduced later.
-->

---

# `run` returns the computed result

```kotlin
val session: Session = SessionBuilder("Expressive APIs").run {
  speaker = "Simon"
  minutes = 30
  build()
}
```

The receiver is a builder. The result is a session.

<!--
run uses the last expression as the result. Compare this with apply, which would
return the builder even if build() were the final expression.
-->

---

# `run` can also summarize a value

```kotlin
val total: Int = program.run {
  sessions.sumOf { it.minutes }
}

val sameTotal: Int = with(program) {
  sessions.sumOf { it.minutes }
}
```

Receiver `run` and `with` differ in how you supply the object.

<!--
These examples are alternatives, not a prescription to wrap single property
accesses in scope functions. Mention the non-extension run { ... } only if asked:
it has no receiver. Source: https://kotlinlang.org/docs/scope-functions.html
-->

---

# Predict the return types

```kotlin
val a = SessionBuilder("Builders").apply { minutes = 20 }
val b = SessionBuilder("Builders").run { build() }
val c = with(program) { sessions.size }
```

<v-click>

| Function | Object inside the block | Result here |
| --- | --- | --- |
| `apply` | `this` | `SessionBuilder` |
| `run` | `this` | `Session` |
| `with` | `this` | `Int` |

</v-click>

<!--
Pause before revealing. Choose based on the result needed and surrounding code.
Avoid teaching these as interchangeable stylistic tricks.
-->

---

# The entry point invokes the block

```kotlin
fun agenda(title: String, block: AgendaBuilder.() -> Unit): Agenda {
  val builder = AgendaBuilder(title)
  block(builder)
  return builder.build()
}
```

<!--
Three steps: allocate, configure, finish. Unit does not mean the entire agenda
function returns Unit; it describes only the configuration lambda's return type.
-->

---
magic-move
---

# The entry point invokes the block

```kotlin
fun agenda(title: String, block: AgendaBuilder.() -> Unit): Agenda =
  AgendaBuilder(title).apply(block).build()
```

`apply` packages the configuration step and returns the builder.

<!--
Do not teach inline or contracts here. Show that this is ordinary function
composition with the same lifecycle as the previous slide.
-->

---

# Nested blocks build child values

```kotlin
class AgendaBuilder(private val title: String) {
  private val sessions = mutableListOf<Session>()

  fun session(title: String, block: SessionBuilder.() -> Unit) {
    val child = SessionBuilder(title).apply(block).build()
    sessions.add(child)
  }

  fun build(): Agenda = Agenda(title, sessions.toList())
}
```

<!--
Follow a single session from the call site to this function and back. build takes
an independent list snapshot so later additions to this builder do not affect it.
-->

---

# Kotlin still supplies loops and collections

```kotlin
val topics = listOf("Extensions", "Receivers", "Builders")

val program = agenda("Lightning talks") {
  topics.forEach { topic ->
    session(topic) {
      minutes = 5
    }
  }
}
```

<!--
Reuse existing data instead of inventing looping syntax for the DSL.
Ask which names are DSL vocabulary and which are ordinary Kotlin.
-->

---

# Member extensions belong to a receiver scope

```kotlin
class AgendaBuilder(private val title: String) {
  private val sessions = mutableListOf<Session>()

  fun Session.insert() {
    require(minutes > 0) { "Duration must be positive" }
    sessions.add(this)
  }

  fun build(): Agenda = Agenda(title, sessions.toList())
}
```

<!--
There are two receivers: this is the Session; this@AgendaBuilder is the owning
builder. sessions is a member of the builder; minutes is a member of Session.
This is an extension declared as a member, not a new member added to Session.
Source: https://kotlinlang.org/docs/extensions.html#declaring-extensions-as-members
-->

---

# The builder makes `insert` available

```kotlin
val program = agenda("Kotlin afternoon") {
  introduction.insert()
  coffeeBreak.insert()
}
```

`Session` supplies the data. `AgendaBuilder` supplies where to insert it.

<!--
At an ordinary call site outside a suitable builder scope, introduction.insert()
is unavailable. with(builder) can supply that scope too: this is receiver-based
availability, not a special restriction to a function named agenda.
-->

---

# An operator is a function call

```kotlin
class AgendaBuilder(private val title: String) {
  private val sessions = mutableListOf<Session>()

  fun Session.insert() {
    require(minutes > 0) { "Duration must be positive" }
    sessions.add(this)
  }

  operator fun Session.unaryPlus() {
    insert()
  }
}
```

<!--
+value desugars to value.unaryPlus(). The operator delegates to the named
operation so validation stays in one place. Use operators only when meaning is
clear; insert remains an equally valid spelling.
-->

---

# Reuse a session inside the DSL

```kotlin
val program = agenda("Kotlin afternoon") {
  +introduction
  session("Builders") {
    minutes = 30
  }
  +coffeeBreak
}
```

<!--
Contrast with +coffeeBreak outside the DSL: Session has no general unaryPlus.
The complete implementation routes newly built sessions through insert as well.
-->

---

# Outer receivers can expose surprising operations

```kotlin
class SessionScope {
  var minutes: Int = 30
}
class AgendaScope {
  fun session(title: String, block: SessionScope.() -> Unit) {
    SessionScope().apply(block)
  }
}
// Example
with(AgendaScope()) {
  session("Builders") {
    session("Unexpected sibling") { minutes = 10 }
  }
}
```

<!--
The small unmarked scopes above are intentionally folded into the imports/setup
panel. The inner session call resolves to the outer AgendaScope. It is legal
Kotlin, but its nesting suggests a relationship the agenda does not support.
-->

---

# Mark the receivers of the same DSL

```kotlin
@DslMarker
annotation class AgendaDsl

@AgendaDsl
class AgendaScope

@AgendaDsl
class SessionScope
```

Only the nearest receiver of this DSL is implicitly available.

<!--
Apply the same annotation to AgendaBuilder and SessionBuilder in the real code.
This is a compile-time guardrail. Explicit outer receiver access remains possible;
it is not a security boundary. Keep the explanation to this example.
Source: https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker
-->

---

# Accidental nesting becomes a compiler error

<InlineCompilerError text="session" :occurrence="2" message="The outer DSL receiver cannot be called implicitly in this context.">

```kotlin
agenda("Kotlin afternoon") {
  session("Builders") {
    session("Unexpected sibling") { minutes = 10 }
  }
}
```

</InlineCompilerError>

<!--
Our support builders carry @AgendaDsl. This exact misuse must fail compilation.
Move the sibling session call out of the child block to fix it. No need for an
extended discussion of overload resolution or annotation targets.
-->

---

# Construction and runtime use are separate

```kotlin
import java.time.LocalTime

val morning = program.schedule(startAt = LocalTime.of(9, 0))
val afternoon = program.schedule(startAt = LocalTime.of(14, 0))

println(morning.first().startsAt)   // 09:00
println(afternoon.first().startsAt) // 14:00
```

Build one agenda. Supply the start time when scheduling it.

<!--
The agenda block runs immediately to construct data. Scheduling is another
operation on that data, with parameters independent of DSL construction.
This example uses LocalTime and is intended for a same-day workshop; a production
calendar would need dates and time zones. No execution engine is necessary here.
-->

---

# Interpret the agenda using a parameter

```kotlin
import java.time.LocalTime

fun Agenda.schedule(startAt: LocalTime): List<ScheduledSession> {
  var next = startAt
  return sessions.map { session ->
    val scheduled = ScheduledSession(session, next)
    next = next.plusMinutes(session.minutes.toLong())
    scheduled
  }
}
```

<!--
ScheduledSession contains a session and its start time. Local state exists only
for this scheduling call; the agenda is unchanged. Supply this function in the
exercise so learners can focus on the builder. Source for time API:
https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/LocalTime.html
-->

---

# Display configuration is also independent

```kotlin
import java.time.LocalTime

val schedule = program.schedule(LocalTime.of(14, 0))

val detailed = schedule.render(DisplayConfig(showSpeakers = true))
val compact = schedule.render(DisplayConfig(showSpeakers = false))
```

The same schedule can serve different audiences.

<!--
The renderer uses with(config) when choosing whether to append the speaker.
Show its implementation from the reference file if useful. This is the second
runtime variation: parameters control interpretation and configuration controls
presentation. Neither needs to be baked into the agenda-building syntax.
-->

---

# Ktor supplies a route-handler receiver

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.greetingRoutes() {
  get("/hello") {
    call.respondText("Hello, Ada")
  }
}
```

Inside `get`, the handler receiver is `RoutingContext`.

<!--
Route configures routes; RoutingContext is the per-call handler context. Keep
these distinct. Ktor owns both types. The handler runs for an incoming request,
which is another example of declaration and runtime behavior being separate.
Explain suspend only as the requirement for Ktor's response operations; coroutines
are covered later. Source: https://ktor.io/docs/server-routing.html
-->

---

# An ordinary extension can extend Ktor

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

suspend fun RoutingContext.respondGreeting(user: User) {
  call.respondText("Hello, ${user.name}")
}

fun Route.greetingRoutes() {
  get("/hello") {
    respondGreeting(User("Ada"))
  }
}
```

<!--
This is enough when RoutingContext is the object we want to extend. No context
parameter is necessary yet. We neither subclass nor modify Ktor.
-->

---

# Keep the user as the extension receiver

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

context(routing: RoutingContext)
suspend fun User.respondGreeting() {
  routing.call.respondText("Hello, $name")
}

fun Route.greetingRoutes() {
  get("/hello") {
    User("Ada").respondGreeting()
  }
}
```

<!--
User is this; routing is a named dependency. The route handler's receiver supplies
RoutingContext implicitly. Context parameters do not expose routing's members as
unqualified receiver members inside respondGreeting; use routing.call explicitly.
This code compiles against the real Ktor API. Sources:
https://kotlinlang.org/docs/context-parameters.html
https://api.ktor.io/ktor-server-core/io.ktor.server.routing/-routing-context/index.html
-->

---

# Context parameters declare required capabilities

```kotlin
import io.ktor.server.routing.*
import io.ktor.server.response.*

context(routing: RoutingContext)
suspend fun User.respondGreeting() {
  routing.call.respondText("Hello, $name")
}
// Example
suspend fun greet(user: User, routing: RoutingContext) {
  with(routing) {
    user.respondGreeting()
  }
}
```

`with` can supply the context explicitly when you already have it.

<!--
A plain local variable of type RoutingContext is not automatically an implicit
context. with makes it a receiver. Without a suitable context, the call fails to
compile. Member extensions need an owning class; context parameters let library
consumers write a top-level scoped extension without owning Ktor's class.
Use stable context parameters for the repository's Kotlin 2.4 toolchain. Explicit
named context arguments are a separate experimental feature and not needed here.
-->

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

<DrawnAnnotation text="@MustUseReturnValues" :connect="false"/>
<DrawnAnnotation text="calculator.calculate()" label="Ignoring the result is a compiler warning" color="red" :connect="false"/>

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

<DrawnAnnotation text="@IgnorableReturnValue" :connect="false"/>
<DrawnAnnotation text="calculator.add(1)" label="Opting out is explicit, ignoring this result is intentional" :connect="false"/>

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
<DrawnAnnotation text=" }" label="Any function type as _last_ parameter allows _trailing lambda syntax_" :connect="false"/>

```kotlin
fun <A> Iterable<A>.map(dummy: Unit, transform: (A) -> B): List<B>

[1, 2, 3].map(Unit) { input -> input * 2 }
```
