---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 6</div>

# Coroutines

## Structured concurrency, Java interop and Flow

---

# `suspend` marks a function that can wait

<DrawnAnnotation text="suspend" label="May call other suspending functions"/>
<DrawnAnnotation text="delay(100.milliseconds)" label="Waits without blocking a thread"/>

```kotlin
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

suspend fun fetchSpeaker(): Speaker {
  delay(100.milliseconds)
  return Speaker(1, "Ada")
}
```

---
magic-move
---

# A `suspend fun` needs a coroutine to run in

<InlineCompilerError text="fetchSpeaker()" :line="7" message="Suspend function 'suspend fun fetchSpeaker(): Speaker' should be called only from a coroutine or another suspend function.">

```kotlin
suspend fun fetchSpeaker(): Speaker {
  delay(100.milliseconds)
  return Speaker(1, "Ada")
}

fun main() {
  fetchSpeaker()
}
```

</InlineCompilerError>

---
magic-move
---

# A coroutine builder constructs a coroutine

<DrawnAnnotation text="runBlocking" label="Blocks the thread until the coroutine completes: fine for `main`, nowhere else" color="red"/>

```kotlin
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val speaker = fetchSpeaker()
  println(speaker.name)
}
```
```console
Ada
```

---
magic-move
---

# `main` can suspend itself

<DrawnAnnotation text="suspend fun main" label="The runtime starts the coroutine, nothing blocks"/>

```kotlin
suspend fun main() {
  val speaker = fetchSpeaker()
  println(speaker.name)
}
```
```console
Ada
```

---
magic-move
---

# Suspending calls run in sequence

<DrawnAnnotation text="fetchTalks()" label="Only starts after `fetchSpeaker` returned: 100 ms, then 100 ms"/>

```kotlin
suspend fun main() {
  val speaker = fetchSpeaker()
  val talks = fetchTalks()
  println(Dashboard(speaker, talks))
}
```

---
class: job-slide
clicks: 0
---

# `coroutineScope` opens a scope for children

<DrawnAnnotation text="coroutineScope" label="Suspends until every child has completed"/>
<DrawnAnnotation text="{" :line="2" label="`this: CoroutineScope`"/>

```kotlin
import kotlinx.coroutines.coroutineScope

suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = fetchSpeaker()
    val talks = fetchTalks()
    Dashboard(speaker, talks)
  }
```

<JobTree :root="{ label: 'coroutineScope' }" />

---
magicMove: true
class: job-slide
clicks: 4
---

# `async` starts a child coroutine

<DrawnAnnotation text="async { fetchSpeaker() }" label="Starts now, returns a `Deferred<Speaker>`" :on="1"/>
<DrawnAnnotation text="async { fetchTalks() }" label="Both run at the same time" :on="2"/>
<DrawnAnnotation text="speaker.await()" label="Suspends until the value is there" :on="3"/>
<DrawnAnnotation text="talks.await()" label="Both done, the scope completes: 100 ms, not 200" :on="4"/>

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = async { fetchTalks() }
    Dashboard(speaker.await(), talks.await())
  }
```

<JobTree
  :root="{ label: 'coroutineScope', done: 4 }"
  :children="[
    { label: 'async fetchSpeaker()', at: 1, done: 3 },
    { label: 'async fetchTalks()', at: 2, done: 4 },
  ]"
/>

---

# `async` needs a scope, returns a `Deferred`

<DrawnAnnotation text="CoroutineScope.async" label="An extension on `CoroutineScope`: a child of that scope"/>
<DrawnAnnotation text="suspend CoroutineScope.() -> T" label="The body can start nested coroutines and produces a `T`"/>
<DrawnAnnotation text="Deferred<T>" label="A reference to the value, to be awaited"/>

```kotlin no-compile
fun <T> CoroutineScope.async(
  block: suspend CoroutineScope.() -> T,
): Deferred<T>
```

---

# `launch` is `async` without a value

<DrawnAnnotation text="-> Unit" label="Nothing to return"/>
<DrawnAnnotation text="Job" label="A reference to the coroutine: cancel it, or wait for it to complete"/>

```kotlin no-compile
fun CoroutineScope.launch(
  block: suspend CoroutineScope.() -> Unit,
): Job
```

---
class: job-slide
clicks: 2
---

# `launch` runs work the scope still waits for

<DrawnAnnotation text="launch" label="A child too: the scope waits for it" :on="1"/>
<DrawnAnnotation text="Dashboard(speaker.await(), talks)" label="Returned only once the notification went out" :on="2"/>

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = fetchTalks()
    launch { notifyAttendees(talks.first()) }
    Dashboard(speaker.await(), talks)
  }
```

<JobTree
  :root="{ label: 'coroutineScope', done: 2 }"
  :children="[
    { label: 'async fetchSpeaker()', done: 2 },
    { label: 'launch notifyAttendees()', at: 1, done: 2 },
  ]"
/>

---
class: job-slide
clicks: 3
---

# A failing child cancels its siblings

<DrawnAnnotation text="error(&quot;Schedule down&quot;)" label="Throws before `fetchSpeaker` finished" color="red" :on="1"/>
<DrawnAnnotation text="async { fetchSpeaker() }" label="The scope cancels its other child" color="red" :on="2"/>
<DrawnAnnotation text="coroutineScope" label="Then rethrows: the caller sees the `IllegalStateException`" color="red" :on="3"/>

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = async { error("Schedule down") }
    Dashboard(speaker.await(), talks.await())
  }
```

<JobTree
  :root="{ label: 'coroutineScope', failed: 3 }"
  :children="[
    { label: 'async fetchSpeaker()', cancelled: 2 },
    { label: 'async error(…)', failed: 1 },
  ]"
/>

---
clicks: 2
---

# A `SupervisorJob` lets children fail alone

<div class="jobtree-centre">
<JobTree
  :root="{ label: 'SupervisorJob' }"
  :children="[
    { label: 'launch' },
    { label: 'launch', failed: 1 },
    { label: 'launch' },
  ]"
/>
</div>

<!--
Click 1: one child fails. The supervisor observes the failure, for example to log
it, and the siblings keep running. Click 2 is the contrast on the next slide.
-->

---
clicks: 2
---

# Cancellation still flows down

<div class="jobtree-centre">
<JobTree
  :root="{ label: 'SupervisorJob', cancelled: 1 }"
  :children="[
    { label: 'launch', cancelled: 2 },
    { label: 'launch', cancelled: 2 },
    { label: 'launch', cancelled: 2 },
  ]"
  :cancel="1"
/>
</div>

---

# A service takes a scope and keeps it private

<DrawnAnnotation text="private val scope: CoroutineScope" label="Inject it, never extend `CoroutineScope`"/>
<DrawnAnnotation text="SupervisorJob()" label="One failed notification does not cancel the others" color="var(--fundamentals-blue)"/>

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class Notifier(private val scope: CoroutineScope) {
  fun notify(talk: Talk): Job =
    scope.launch { notifyAttendees(talk) }
}

val notifier = Notifier(
  CoroutineScope(SupervisorJob() + Dispatchers.Default),
)
```

---
class: job-slide
clicks: 2
---

# `cancel()` cancels the coroutine and its children

<DrawnAnnotation text="job.cancel()" label="Cancels `launch`, and `launch` cancels both `async`" :on="1"/>
<DrawnAnnotation text="fetchSpeaker()" label="`delay` inside throws `CancellationException`: no value, no print" :on="2"/>

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

val job = scope.launch {
  val speaker = async { fetchSpeaker() }
  val talks = async { fetchTalks() }
  println(speaker.await().name)
  println(talks.await().size)
}

job.cancel()
```

<JobTree
  :root="{ label: 'launch', cancelled: 1 }"
  :children="[
    { label: 'async fetchSpeaker()', cancelled: 2 },
    { label: 'async fetchTalks()', cancelled: 2 },
  ]"
  :cancel="1"
/>

---

# Cancellation is cooperative

<DrawnAnnotation text="compute()" label="No suspension point: `cancel()` is never observed, the loop runs forever" color="red"/>

```kotlin
import kotlinx.coroutines.launch

val job = scope.launch {
  while (true) {
    compute()
  }
}

job.cancel()
```

---
magic-move
---

# Cancellation is cooperative

<DrawnAnnotation text="isActive" label="`false` once the coroutine is cancelled"/>

```kotlin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

val job = scope.launch {
  while (isActive) {
    compute()
  }
}

job.cancel()
```

---
magic-move
---

# Cancellation is cooperative

<DrawnAnnotation text="ensureActive()" label="Throws `CancellationException` instead, like every suspension point"/>

```kotlin
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

val job = scope.launch {
  while (true) {
    ensureActive()
    compute()
  }
}

job.cancel()
```

---

# Structured concurrency in four rules

- `coroutineScope` → suspends until every child has completed
- `async`, `launch` → children of the scope they are started on
- a child failure → cancels the parent and its siblings, unless a `SupervisorJob` observes it
- cancellation → flows down to every child, and is cooperative

> **Every coroutine has a parent.**

---
layout: intro
class: section-slide
kodee: wave
---

<div class="lesson-number">Lesson 6</div>

# Wrapping Java SDKs

## Callbacks, futures and publishers become `suspend`

---

# A Java SDK has three asynchronous styles

<DrawnAnnotation text="Callback<Speaker> callback" label="Callback: the answer arrives on the SDK's thread"/>
<DrawnAnnotation text="CompletableFuture<List<Talk>>" label="Future: a value that is not there yet" color="var(--fundamentals-blue)"/>
<DrawnAnnotation text="Publisher<Integer>" label="Reactive Streams: many values, with backpressure" color="var(--fundamentals-pink)"/>

```java no-compile
public final class ConferenceSdk {
  public Call speaker(long id, Callback<Speaker> callback);
  public CompletableFuture<List<Talk>> talks(long speakerId);
  public Publisher<Integer> attendance(long talkId);
}
```

---

# A callback splits the result from the call

<DrawnAnnotation text="onSuccess" label="Exactly one of the two is called, later, on another thread"/>
<DrawnAnnotation text="Call" label="The only way to stop it" color="var(--fundamentals-blue)"/>

```java no-compile
public interface Callback<T> {
  void onSuccess(T value);
  void onFailure(Throwable error);
}

public interface Call {
  void cancel();
}
```

```kotlin
sdk.speaker(1, object : Callback<Speaker> {
  override fun onSuccess(value: Speaker) = println(value.name)
  override fun onFailure(error: Throwable) = error.printStackTrace()
})
```

---

# `suspendCoroutine` turns the callback into a return value

<DrawnAnnotation text="suspendCoroutine" label="Suspends until `continuation` is resumed"/>
<DrawnAnnotation text="continuation.resume(value)" label="The value becomes the return value"/>
<DrawnAnnotation text="continuation.resumeWithException(error)" label="The error becomes a thrown exception"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCoroutine { continuation ->
    speaker(id, object : Callback<Speaker> {
      override fun onSuccess(value: Speaker) =
        continuation.resume(value)
      override fun onFailure(error: Throwable) =
        continuation.resumeWithException(error)
    })
  }
```

---
magic-move
---

# `suspendCoroutine` turns the callback into a return value

<DrawnAnnotation text="sdk.speaker(1)" label="Looks like a plain call, waits like a coroutine"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCoroutine { continuation ->
    speaker(id, object : Callback<Speaker> {
      override fun onSuccess(value: Speaker) =
        continuation.resume(value)
      override fun onFailure(error: Throwable) =
        continuation.resumeWithException(error)
    })
  }

suspend fun main() {
  val speaker = sdk.speaker(1)
  println(speaker.name)
}
```
```console
Ada
```

---
magic-move
---

# Cancellation does not reach the SDK

<DrawnAnnotation text="job.cancel()" label="The coroutine is cancelled, the `Call` keeps running" color="red"/>
<DrawnAnnotation text="suspendCoroutine" label="Never learns about it: the callback resumes a dead coroutine" color="red"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.launch

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCoroutine { continuation ->
    speaker(id, object : Callback<Speaker> {
      override fun onSuccess(value: Speaker) =
        continuation.resume(value)
      override fun onFailure(error: Throwable) =
        continuation.resumeWithException(error)
    })
  }

val job = scope.launch { println(sdk.speaker(1).name) }
job.cancel()
```

---
magic-move
---

# `suspendCancellableCoroutine` forwards the cancellation

<DrawnAnnotation text="suspendCancellableCoroutine" label="Resumes with `CancellationException` when the coroutine is cancelled"/>
<DrawnAnnotation text="val call =" label="Keep the handle the SDK hands back"/>
<DrawnAnnotation text="invokeOnCancellation { call.cancel() }" label="and use it to stop the SDK's work"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCancellableCoroutine { continuation ->
    val call = speaker(id, object : Callback<Speaker> {
      override fun onSuccess(value: Speaker) =
        continuation.resume(value)
      override fun onFailure(error: Throwable) =
        continuation.resumeWithException(error)
    })
    continuation.invokeOnCancellation { call.cancel() }
  }

val job = scope.launch { println(sdk.speaker(1).name) }
job.cancel()
```

---

# A `CompletableFuture` can be awaited

<DrawnAnnotation text="await()" label="Suspends until the future completes, cancels it when the coroutine is cancelled"/>

```java no-compile
public CompletableFuture<List<Talk>> talks(long speakerId);
```

```kotlin
import kotlinx.coroutines.future.await

suspend fun main() {
  val talks = sdk.talks(1).await()
  println(talks.map { talk -> talk.title })
}
```
```console
[Structured concurrency, Flow]
```

---

# `future` goes the other way

<DrawnAnnotation text="scope.future" label="Starts a coroutine, returns its result as a `CompletableFuture`"/>
<DrawnAnnotation text="dashboardAsync()" label="Java never sees a `suspend`" color="var(--fundamentals-blue)"/>

```kotlin
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future

class DashboardService(private val scope: CoroutineScope) {
  fun dashboardAsync(): CompletableFuture<Dashboard> =
    scope.future { dashboard() }
}
```

```java
class JavaCaller {
  void example(DashboardService service) {
    service.dashboardAsync().thenAccept(System.out::println);
  }
}
```

---

# A `Publisher` becomes a `Flow`

> `kotlinx-coroutines-reactive`

<DrawnAnnotation text="asFlow()" label="Keeps the Reactive Streams guarantees, including backpressure"/>
<DrawnAnnotation text="take(3)" label="Cancels the subscription after the third value"/>

```java no-compile
public Publisher<Integer> attendance(long talkId);
```

```kotlin
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.reactive.asFlow

sdk.attendance(10).asFlow()
  .take(3)
  .collect { count -> println(count) }
```
```console
10
20
30
```

---

# A `Flow` becomes a `Publisher`

<DrawnAnnotation text="asPublisher()" label="Backpressure the other way: `emit` waits for the subscriber's `request`"/>

```kotlin
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asPublisher
import org.reactivestreams.Publisher

fun readings(): Publisher<Int> = flow {
  emit(10)
  emit(20)
}.asPublisher()
```

---

# Every asynchronous style has a bridge

| Java side | into a coroutine | out of a coroutine | module |
| --- | --- | --- | --- |
| callback | `suspendCancellableCoroutine` | implement the interface | `kotlinx-coroutines-core` |
| `CompletableFuture` | `.await()` | `future { }`, `.asCompletableFuture()` | `kotlinx-coroutines-core` |
| `Publisher` | `.asFlow()`, `.awaitFirst()` | `.asPublisher()`, `publish { }` | `kotlinx-coroutines-reactive` |
| `Mono`, `Flux` | `.awaitSingle()`, `.asFlow()` | `mono { }`, `flux { }` | `kotlinx-coroutines-reactor` |
| `Single`, `Observable` | `.await()`, `.asFlow()` | `rxSingle { }`, `.asObservable()` | `kotlinx-coroutines-rx3` |

<style>
.slidev-layout table { font-size: 1.25rem; }
</style>

---
class: job-slide
clicks: 2
---

# One scope composes all three

<DrawnAnnotation text="sdk.speaker(id)" label="Callback, wrapped" :on="0" color="var(--drawn-annotation-color)" :sequential="false"/>
<DrawnAnnotation text="sdk.talks(id).await()" label="`CompletableFuture`" :on="0" color="var(--drawn-annotation-color)" :sequential="false"/>
<DrawnAnnotation text="asFlow().first()" label="`Publisher`" :on="0" color="var(--drawn-annotation-color)" :sequential="false"/>
<DrawnAnnotation text="sdk.speaker(id)" label="→ `call.cancel()`" :on="2" color="red" :sequential="false"/>
<DrawnAnnotation text="sdk.talks(id).await()" label="→ `future.cancel()`" :on="2" color="red" :sequential="false"/>
<DrawnAnnotation text="asFlow().first()" label="→ `subscription.cancel()`" :on="2" color="red" :sequential="false"/>

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.asFlow

suspend fun dashboard(id: Long): Dashboard =
  coroutineScope {
    val speaker = async { sdk.speaker(id) }
    val talks = async { sdk.talks(id).await() }
    val live = async {
      sdk.attendance(id).asFlow().first()
    }
    Dashboard(
      speaker = speaker.await(),
      talks = talks.await(),
      attendance = live.await(),
    )
  }
```

<JobTree
  :root="{ label: 'coroutineScope', cancelled: 1 }"
  :children="[
    { label: 'async callback', cancelled: 2 },
    { label: 'async future', cancelled: 2 },
    { label: 'async publisher', cancelled: 2 },
  ]"
  :cancel="1"
/>

<!--
Click 1: the caller cancels the scope. Click 2: every bridge forwards it to its
Java side. One `cancel()` at the top stops the SDK's work in all three styles.
-->

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 5</div>

# Load the dashboard in parallel

> `fetchSpeaker()` and `fetchTalks()` take 100 ms each, so should `dashboard()`

```kotlin
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.measureTime
import kotlinx.coroutines.runBlocking

suspend fun dashboard(): Dashboard = TODO()

fun main() = runBlocking {
  val time = measureTime { dashboard() }
  check(time < 150.milliseconds)
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 5</div>

# Give up after a timeout

> `withTimeoutOrNull` returns `null` when its block takes too long

```kotlin
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.runBlocking

suspend fun dashboardOrNull(timeout: Duration): Dashboard? = TODO()

fun main() = runBlocking {
  check(dashboardOrNull(50.milliseconds) == null)
  check(dashboardOrNull(150.milliseconds) != null)
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 5</div>

# Wrap the callback

> Cancelling the coroutine must cancel the `Call`

```kotlin
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun ConferenceSdk.speaker(id: Long): Speaker = TODO()

fun main() = runBlocking {
  check(sdk.speaker(1).name == "Ada")

  val job = launch { sdk.speaker(1) }
  delay(10.milliseconds)
  job.cancelAndJoin()
  check(sdk.cancelledCalls == 1)
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 4 / 5</div>

# Bridge the futures

> `await()` one way, `future { }` the other

```kotlin
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking

suspend fun ConferenceSdk.allTalks(id: Long): List<Talk> = TODO()

fun CoroutineScope.dashboardAsync(): CompletableFuture<Dashboard> = TODO()

fun main() = runBlocking {
  check(sdk.allTalks(1).size == 2)
  check(dashboardAsync().await().talks.size == 2)
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 5 / 5</div>

# Read the stream

> `asFlow()` keeps the backpressure, `take` ends the subscription

```kotlin
import kotlinx.coroutines.runBlocking

suspend fun ConferenceSdk.readings(id: Long, count: Int): List<Int> = TODO()

fun main() = runBlocking {
  check(sdk.readings(10, count = 3) == [10, 20, 30])
}
```

---
layout: intro
class: section-slide
kodee: wave
---

<div class="lesson-number">Lesson 6</div>

# Flow

## A cold stream of values

---

# A `Flow` is a cold stream of values

<DrawnAnnotation text="flow {" label="Nothing runs until someone collects"/>
<DrawnAnnotation text="emit(count)" label="Suspends until the collector has taken the value"/>

```kotlin
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun attendance(): Flow<Int> = flow {
  var count = 0
  repeat(3) {
    delay(100.milliseconds)
    count += 10
    emit(count)
  }
}
```

---
magic-move
---

# `collect` runs the flow

<DrawnAnnotation text="collect" label="A suspending call: returns when the flow has completed"/>

```kotlin
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun attendance(): Flow<Int> = flow {
  var count = 0
  repeat(3) {
    delay(100.milliseconds)
    count += 10
    emit(count)
  }
}

attendance().collect { count -> println(count) }
```
```console
10
20
30
```

---
magic-move
---

# Operators are lazy too

<DrawnAnnotation text="filter" label="Each operator returns a new cold `Flow`"/>
<DrawnAnnotation text="collect" label="Still the only place where anything runs"/>

```kotlin
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

attendance()
  .filter { count -> count > 10 }
  .map { count -> "$count attendees" }
  .collect { line -> println(line) }
```
```console
20 attendees
30 attendees
```

---

# `callbackFlow` wraps a subscription

<DrawnAnnotation text="trySend(talk)" label="Called on the SDK's thread, hands the event to the collector"/>
<DrawnAnnotation text="awaitClose" label="Suspends until the collector stops, then unsubscribes"/>

```java no-compile
public Subscription onScheduleChange(Listener<Talk> listener);
```

```kotlin
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ConferenceSdk.scheduleChanges(): Flow<Talk> =
  callbackFlow {
    val subscription = onScheduleChange { talk -> trySend(talk) }
    awaitClose { subscription.unsubscribe() }
  }
```

---
magic-move
---

# `callbackFlow` wraps a subscription

<DrawnAnnotation text="take(2)" label="Stops collecting, so `awaitClose` runs and unsubscribes"/>

```kotlin
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.take

fun ConferenceSdk.scheduleChanges(): Flow<Talk> =
  callbackFlow {
    val subscription = onScheduleChange { talk -> trySend(talk) }
    awaitClose { subscription.unsubscribe() }
  }

sdk.scheduleChanges()
  .take(2)
  .collect { talk -> println(talk.title) }
```
```console
Room change
Room change
```

---

# `catch` sees upstream failures only

<DrawnAnnotation text="catch" label="Handles what `scheduleChanges` and `map` throw, not `collect`"/>
<DrawnAnnotation text="onCompletion" label="Runs once, with `null`, the failure or the `CancellationException`" color="var(--fundamentals-blue)"/>

```kotlin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take

sdk.scheduleChanges()
  .map { talk -> talk.title }
  .catch { cause -> emit("Schedule unavailable: ${cause.message}") }
  .onCompletion { cause -> println("Done: $cause") }
  .take(1)
  .collect { title -> println(title) }
```
```console
Room change
Done: null
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Emit on a schedule

> `flow { }` with a `delay` before every `emit`

```kotlin
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

fun ticks(count: Int, every: Duration): Flow<Int> = TODO()

fun main() = runBlocking {
  check(ticks(3, every = 100.milliseconds).toList() == [1, 2, 3])
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Transform the stream

> Keep the talks with a long enough title, map to the title

```kotlin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

fun longTitles(talks: Flow<Talk>, minLength: Int): Flow<String> = TODO()

fun main() = runBlocking {
  val talks = flowOf(Talk(1, "Flow"), Talk(2, "Structured concurrency"))
  check(longTitles(talks, minLength = 5).toList() == ["Structured concurrency"])
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Subscribe with `callbackFlow`

> `awaitClose` runs when the collector stops

```kotlin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun ConferenceSdk.scheduleChanges(): Flow<Talk> = TODO()

fun main() = runBlocking {
  check(sdk.scheduleChanges().first().title == "Room change")
  check(sdk.activeSubscriptions == 0)
}
```

---

# Coroutines in four rules

- `suspend` → waits without blocking, needs a coroutine to run in
- `coroutineScope` → every child completes, fails or is cancelled with its parent
- `suspendCancellableCoroutine`, `await()`, `asFlow()` → every Java style has a bridge, all of them cancellable
- `Flow` → cold, lazy, and cancelled with its collector

> **Every coroutine has a parent.**
