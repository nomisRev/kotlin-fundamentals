---
layout: intro
class: section-slide
kodee: wave
---

<!-- @formatter:off -->

<div class="lesson-number">Lesson 6</div>

# Coroutines

## Structured concurrency, interop, Flow and state

---

# `suspend` marks a function that can wait

<DrawnAnnotation text="suspend" label="May call other suspending functions" on="0" :geometry="{ label: { x: 0.2604, y: 0.4023 } }"/>
<DrawnAnnotation text="delay(100.milliseconds)" label="Waits without blocking a thread" on="1" :geometry="{ label: { x: 0.2655, y: 0.4017 } }"/>

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

<DrawnAnnotation text="runBlocking" label="Blocks the thread until the coroutine completes: fine for `main`, nowhere else" color="red" :geometry="{ label: { x: 0.5179, y: 0.5200 } }"/>

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

# Suspending calls run in sequence

<DrawnAnnotation text="fetchTalks()" label="Only starts after `fetchSpeaker` returned: 100 ms, then 100 ms" :geometry="{ label: { x: 0.3721, y: 0.4615 } }"/>

```kotlin
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val speaker = fetchSpeaker()
  val talks = fetchTalks()
  println(Dashboard(speaker, talks))
}
```

---
class: job-slide
---

# `coroutineScope` opens a scope for children

<DrawnAnnotation text="coroutineScope" label="Suspends until every child has completed"/>
<TypeHint :line="2" receiver="CoroutineScope">

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
      
  }
```
</TypeHint>
<JobTree :root="{ label: 'coroutineScope' }" />

---
magicMove: true
class: job-slide
---

# `async` starts a child coroutine

<DrawnAnnotation text="async { fetchSpeaker() }" label="Scheduled now, returns a `Deferred<Speaker>`"/>

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# `async` starts a child coroutine

<DrawnAnnotation text="async { fetchTalks() }" label="Both run at the same time"/>

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = async { fetchTalks() }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
    { label: 'async fetchTalks()' },
  ]"
/>

---
magicMove: true
class: job-slide
clicks: 3
---

# `async` starts a child coroutine

<DrawnAnnotation text="speaker.await()" label="Suspends until the value is there" :on="1"/>
<DrawnAnnotation text="talks.await()" label="Both values are there" :on="2"/>
<DrawnAnnotation text="coroutineScope" label="Both children done, the scope completes: 100 ms, not 200" :on="3"/>

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
  :root="{ label: 'coroutineScope', done: 3 }"
  :children="[
    { label: 'async fetchSpeaker()', done: 1 },
    { label: 'async fetchTalks()', done: 2 },
  ]"
/>

---

# `async` needs a scope, returns a `Deferred`

<DrawnAnnotation text="CoroutineScope.async" label="An extension on `CoroutineScope`: a child of that scope" on="1" :geometry="{ label: { x: 0.3435, y: 0.3745 } }"/>
<DrawnAnnotation text="suspend CoroutineScope.() -> T" label="The body can start nested coroutines and produces a `T`" on="2" :geometry="{ label: { x: 0.3734, y: 0.3905 } }"/>
<DrawnAnnotation text="Deferred<T>" label="A reference to the value, to be awaited or cancelled" on="3"  :geometry="{ label: { x: 0.3965, y: 0.3742 } }"/>

```kotlin no-compile
fun <T> CoroutineScope.async(
  block: suspend CoroutineScope.() -> T,
): Deferred<T>
```

---

# `launch` is `async` without a value

<DrawnAnnotation text="-> Unit" on="0" label="Nothing to return"/>
<DrawnAnnotation text="Job" on="1" label="A reference to the coroutine: cancel it, or wait for it to complete" :geometry="{ label: { x: 0.3662, y: 0.3912 } }"/>

```kotlin no-compile
fun CoroutineScope.launch(
  block: suspend CoroutineScope.() -> Unit,
): Job
```

---
class: job-slide
---

# `launch` runs work the scope still waits for

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
  }
```

<JobTree :root="{ label: 'coroutineScope' }" />

---
magicMove: true
class: job-slide
---

# `launch` runs work the scope still waits for

<DrawnAnnotation text="async { fetchSpeaker() }" label="A child, as before"/>

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# `launch` runs work the scope still waits for

<DrawnAnnotation text="fetchTalks()" label="A plain suspending call: runs in the scope itself, no child"/>

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = fetchTalks()
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# `launch` runs work the scope still waits for

<DrawnAnnotation text="launch" label="A child too: the scope waits for it"/>

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = fetchTalks()
    launch { notifyAttendees(talks.first()) }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
    { label: 'launch notifyAttendees()' },
  ]"
/>

---
magicMove: true
class: job-slide
clicks: 3
---

# `launch` runs work the scope still waits for

<DrawnAnnotation text="speaker.await()" label="The value is there, the scope is still open" :on="1"/>
<DrawnAnnotation text="launch" label="The notification went out" :on="2"/>
<DrawnAnnotation text="coroutineScope" label="Only now does it complete and return the `Dashboard`" :on="3" :geometry="{ label: { x: 0.4168, y: 0.5622 } }"/>

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
  :root="{ label: 'coroutineScope', done: 3 }"
  :children="[
    { label: 'async fetchSpeaker()', done: 1 },
    { label: 'launch notifyAttendees()', done: 2 },
  ]"
/>

---
class: job-slide
---

# A failing child cancels its siblings

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
  }
```

<JobTree :root="{ label: 'coroutineScope' }" />

---
magicMove: true
class: job-slide
---

# A failing child cancels its siblings

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# A failing child cancels its siblings

<DrawnAnnotation text="error(&quot;Schedule down&quot;)" label="A child that will throw" color="red"/>

```kotlin no-compile
suspend fun dashboard(): Dashboard =
  coroutineScope {
    val speaker = async { fetchSpeaker() }
    val talks = async { error("Schedule down") }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async fetchSpeaker()' },
    { label: 'async error(…)' },
  ]"
/>

---
magicMove: true
class: job-slide
clicks: 4
---

# A failing child cancels its siblings

<DrawnAnnotation text="error(&quot;Schedule down&quot;)" label="Throws before `fetchSpeaker` finished" color="red" :on="1"/>
<DrawnAnnotation text="async { fetchSpeaker() }" label="The scope cancels its other child" color="var(--fundamentals-cancel)" :on="2"/>
<DrawnAnnotation text="coroutineScope" label="Once the sibling is gone it rethrows: the caller sees the `IllegalStateException`" color="red" :on="4"/>

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
  :root="{ label: 'coroutineScope', failed: 4 }"
  :children="[
    { label: 'async fetchSpeaker()', cancelling: 2, cancelled: 3 },
    { label: 'async error(…)', failed: 1 },
  ]"
/>

<!--
Click 1: the second child throws. Click 2: the scope cancels its sibling. Click 3:
the sibling has finished cancelling. Click 4: only now does the scope fail and
rethrow to the caller.
-->

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
clicks: 4
---

# Cancellation still flows down

<div class="jobtree-centre">
<JobTree
  :root="{ label: 'SupervisorJob', cancelling: 1, cancelled: 4 }"
  :children="[
    { label: 'launch', cancelling: 2, cancelled: 3 },
    { label: 'launch', cancelling: 2, cancelled: 3 },
    { label: 'launch', cancelling: 2, cancelled: 3 },
  ]"
  :cancel="1"
/>
</div>

<v-click at="4">

> `cancelAndJoin()` returns only once every child has finished

</v-click>

<!--
Click 1: `cancel()` reaches the supervisor, which starts cancelling. Click 2: the
cancellation flows down, every child is cancelling: running `finally` blocks,
`NonCancellable` cleanup. Click 3: the children have finished. Click 4: only now is
the parent completely cancelled, which is what `cancelAndJoin()` waits for.
-->

---

# A service takes a scope and keeps it private

<DrawnAnnotation text="private val scope: CoroutineScope" label="Inject it, never extend `CoroutineScope`" :geometry="{ label: { x: 0.7196, y: 0.2643 } }"/>
<DrawnAnnotation text="SupervisorJob()" label="One failed notification does not cancel the others" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5228, y: 0.5999 } }"/>

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
---

# `cancel()` cancels the coroutine and its children

<DrawnAnnotation text="scope.launch" label="A child of the application scope"/>

```kotlin
import kotlinx.coroutines.launch

val job = scope.launch {
}
```

<JobTree :root="{ label: 'launch' }" />

---
magicMove: true
class: job-slide
---

# `cancel()` cancels the coroutine and its children

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

val job = scope.launch {
  val speaker = async { fetchSpeaker() }
}
```

<JobTree
  :root="{ label: 'launch' }"
  :children="[
    { label: 'async fetchSpeaker()' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# `cancel()` cancels the coroutine and its children

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

val job = scope.launch {
  val speaker = async { fetchSpeaker() }
  val talks = async { fetchTalks() }
}
```

<JobTree
  :root="{ label: 'launch' }"
  :children="[
    { label: 'async fetchSpeaker()' },
    { label: 'async fetchTalks()' },
  ]"
/>

---
magicMove: true
class: job-slide
clicks: 4
---

# `cancel()` cancels the coroutine and its children

<DrawnAnnotation text="job.cancel()" label="Cancels `launch`, and `launch` cancels both `async`" color="var(--fundamentals-cancel)" :on="1"/>
<DrawnAnnotation text="fetchSpeaker()" label="`delay` inside throws `CancellationException`: no value, no print" color="var(--fundamentals-cancel)" :on="2" :geometry="{ label: { x: 0.5992, y: 0.6162 } }"/>
<DrawnAnnotation text="scope.launch" label="Cancelled once both children are" color="var(--fundamentals-cancel)" :on="4" :geometry="{ label: { x: 0.4019, y: 0.5055 } }"/>

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
  :root="{ label: 'launch', cancelling: 1, cancelled: 4 }"
  :children="[
    { label: 'async fetchSpeaker()', cancelling: 2, cancelled: 3 },
    { label: 'async fetchTalks()', cancelling: 2, cancelled: 3 },
  ]"
  :cancel="1"
/>

<!--
Click 1: `cancel()` reaches `launch`. Click 2: it cancels both `async` children,
whose `delay` throws. Click 3: the children have finished cancelling. Click 4: the
`launch` is completely cancelled.
-->

---

# Cancellation is cooperative

<DrawnAnnotation text="compute()" label="No suspension point: `cancel()` is never observed, the loop runs forever" color="red" :geometry="{ label: { x: 0.5634, y: 0.6636 } }"/>

```kotlin
import kotlin.random.Random
import kotlinx.coroutines.launch

fun compute() = println(Random.nextInt())

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

<DrawnAnnotation text="isActive" label="`false` once the coroutine is cancelled" :geometry="{ label: { x: 0.4427, y: 0.4236 } }"/>

```kotlin
import kotlin.random.Random
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun compute() = println(Random.nextInt())

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

<DrawnAnnotation text="ensureActive()" label="Throws `CancellationException` instead, like every suspension point" :geometry="{ label: { x: 0.5526, y: 0.4811 } }"/>

```kotlin
import kotlin.random.Random
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

fun compute() = println(Random.nextInt())

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

<DrawnAnnotation text="Callback<Speaker> callback" on="0" label="Callback: the answer arrives on the SDK's thread"/>
<DrawnAnnotation text="CompletableFuture<List<Talk>>" on="1" label="Future: a value that is not there yet" color="var(--fundamentals-blue)"/>
<DrawnAnnotation text="Publisher<Integer>" on="2" label="Reactive Streams: many values, with backpressure" color="var(--fundamentals-pink)" :geometry="{ label: { x: 0.3343, y: 0.4742 } }"/>

```java no-compile
public final class ConferenceSdk {
  public Call speaker(long id, Callback<Speaker> callback);
  public CompletableFuture<List<Talk>> talks(long speakerId);
  public Publisher<Integer> attendance(long talkId);
}
```

---

# A callback splits the result from the call

<DrawnAnnotation text="onSuccess" label="Exactly one of the two is called, later, on another thread" :geometry="{ label: { x: 0.5922, y: 0.3549 } }"/>
<DrawnAnnotation text="Call" occurrence="2" label="The only way to stop it" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.3617, y: 0.4956 } }"/>

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

<DrawnAnnotation text="suspendCoroutine" label="Suspends until `continuation` is resumed" on="0" :geometry="{ label: { x: 0.3747, y: 0.7276 } }"/>
<DrawnAnnotation text="cont.resume(value)" label="The value becomes the return value" on="1" :geometry="{ label: { x: 0.3837, y: 0.7290 } }"/>
<DrawnAnnotation text="cont.resumeWithException(error)" label="The error becomes a thrown exception" on="2" :geometry="{ label: { x: 0.3894, y: 0.7292 } }"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCoroutine { cont ->
      speaker(id, object : Callback<Speaker> {
          override fun onSuccess(value: Speaker) = cont.resume(value)
          
          override fun onFailure(error: Throwable) =
              cont.resumeWithException(error)
      })
  }
```

---
magic-move
---

# `suspendCoroutine` turns the callback into a return value

<DrawnAnnotation text="sdk.speaker(1)" label="Looks like a plain call, waits like a coroutine" :geometry="{ label: { x: 0.5541, y: 0.8704 } }"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
    suspendCoroutine { cont ->
        speaker(id, object : Callback<Speaker> {
            override fun onSuccess(value: Speaker) = cont.resume(value)

            override fun onFailure(error: Throwable) =
                cont.resumeWithException(error)
        })
    }

suspend fun main() {
  val speaker = sdk.speaker(1)
  println(speaker.name)
}
```

---
magic-move
---

# Cancellation does not reach the SDK

<DrawnAnnotation text="job.cancel()" on="0" label="The coroutine is cancelled, the `Call` keeps running" color="var(--fundamentals-cancel)"/>
<DrawnAnnotation text="suspendCoroutine" on="1" label="Never learns about it: the callback resumes a dead coroutine" color="red" :geometry="{ label: { x: 0.5185, y: 0.5548 } }"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.launch

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
    suspendCoroutine { cont ->
        speaker(id, object : Callback<Speaker> {
            override fun onSuccess(value: Speaker) = cont.resume(value)

            override fun onFailure(error: Throwable) =
                cont.resumeWithException(error)
        })
    }

val job = scope.launch { println(sdk.speaker(1).name) }
job.cancel()
```

---
magic-move
---

# `suspendCancellableCoroutine` forwards cancel

<DrawnAnnotation text="suspendCancellableCoroutine" on="0" label="Resumes with `CancellationException` when the coroutine is cancelled" :geometry="{ label: { x: 0.4884, y: 0.8456 } }"/>
<DrawnAnnotation text="val call =" on="1" label="Keep the handle the SDK hands back" :geometry="{ label: { x: 0.4126, y: 0.8435 } }"/>
<DrawnAnnotation text="invokeOnCancellation { call.cancel() }" on="3" label="and use it to stop the SDK's work"/>

```kotlin
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCancellableCoroutine { cont ->
    val call = speaker(id, object : Callback<Speaker> {
        override fun onSuccess(value: Speaker) = cont.resume(value)

        override fun onFailure(error: Throwable) =
            cont.resumeWithException(error)
    })
      cont.invokeOnCancellation { call.cancel() }
  }

val job = scope.launch { println(sdk.speaker(1).name) }
job.cancel()
```

---

# A `CompletableFuture` can be awaited

<DrawnAnnotation text="await()" label="Suspends until the future completes, cancels it when the coroutine is cancelled" :geometry="{ label: { x: 0.4800, y: 0.5872 } }"/>

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

<DrawnAnnotation text="scope.future" on="0" label="Starts a coroutine, returns its result as a `CompletableFuture`" :geometry="{ label: { x: 0.5905, y: 0.3487 } }"/>
<DrawnAnnotation text="dashboardAsync()" on="1" label="Java never sees a `suspend`" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.4054, y: 0.5866 } }"/>

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

<DrawnAnnotation text="asFlow()" on="0" label="Keeps the Reactive Streams guarantees, including backpressure" :geometry="{ label: { x: 0.5432, y: 0.5698 } }"/>
<DrawnAnnotation text="take(3)" on="1" label="Cancels the subscription after the third value" :geometry="{ label: { x: 0.5293, y: 0.6262 } }"/>

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

<DrawnAnnotation text="asPublisher()" label="Backpressure the other way: `emit` waits for the subscriber's `request`" :geometry="{ label: { x: 0.3986, y: 0.4246 } }"/>

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
---

# One scope composes all three

```kotlin no-compile
suspend fun dashboard(id: Long): Dashboard =
  coroutineScope {
  }
```

<JobTree :root="{ label: 'coroutineScope' }" />

---
magicMove: true
class: job-slide
---

# One scope composes all three

<DrawnAnnotation text="sdk.speaker(id)" label="Callback, wrapped"/>

```kotlin no-compile
suspend fun dashboard(id: Long): Dashboard =
  coroutineScope {
    val speaker = async { sdk.speaker(id) }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async callback' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# One scope composes all three

<DrawnAnnotation text="sdk.talks(id).await()" label="`CompletableFuture`"/>

```kotlin no-compile
suspend fun dashboard(id: Long): Dashboard =
  coroutineScope {
    val speaker = async { sdk.speaker(id) }
    val talks = async { sdk.talks(id).await() }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async callback' },
    { label: 'async future' },
  ]"
/>

---
magicMove: true
class: job-slide
---

# One scope composes all three

<DrawnAnnotation text="asFlow().first()" label="`Publisher`"/>

```kotlin no-compile
suspend fun dashboard(id: Long): Dashboard =
  coroutineScope {
    val speaker = async { sdk.speaker(id) }
    val talks = async { sdk.talks(id).await() }
    val live = async {
      sdk.attendance(id).asFlow().first()
    }
  }
```

<JobTree
  :root="{ label: 'coroutineScope' }"
  :children="[
    { label: 'async callback' },
    { label: 'async future' },
    { label: 'async publisher' },
  ]"
/>

---
magicMove: true
class: job-slide
clicks: 4
---

# One scope composes all three

<DrawnAnnotation text="sdk.speaker(id)" label="→ `call.cancel()`" :on="2" color="var(--fundamentals-cancel)" :sequential="false"/>
<DrawnAnnotation text="sdk.talks(id).await()" label="→ `future.cancel()`" :on="2" color="var(--fundamentals-cancel)" :sequential="false"/>
<DrawnAnnotation text="asFlow().first()" label="→ `subscription.cancel()`" :on="2" color="var(--fundamentals-cancel)" :sequential="false"/>
<DrawnAnnotation text="coroutineScope" label="Cancelled once every bridge has stopped its Java side" :on="4" color="var(--fundamentals-cancel)" :geometry="{ label: { x: 0.5165, y: 0.8467 } }"/>

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
  :root="{ label: 'coroutineScope', cancelling: 1, cancelled: 4 }"
  :children="[
    { label: 'async callback', cancelling: 2, cancelled: 3 },
    { label: 'async future', cancelling: 2, cancelled: 3 },
    { label: 'async publisher', cancelling: 2, cancelled: 3 },
  ]"
  :cancel="1"
/>

<!--
Click 1: the caller cancels the scope. Click 2: every bridge forwards it to its
Java side. Click 3: the three children have finished cancelling. Click 4: the scope
is completely cancelled. One `cancel()` at the top stops the SDK's work in all
three styles.
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

<DrawnAnnotation text="flow {" on="0" label="Nothing runs until someone collects" :geometry="{ label: { x: 0.5763, y: 0.2696 } }"/>
<DrawnAnnotation text="emit(count)" on="1" label="Suspends until the collector has taken the value" :geometry="{ label: { x: 0.3779, y: 0.4956 } }"/>

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

<DrawnAnnotation text="collect" label="A suspending call: returns when the flow has completed" :geometry="{ label: { x: 0.3512, y: 0.8933 } }"/>

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

<DrawnAnnotation text="filter" />
<DrawnAnnotation text="map"  label="Each operator returns a new cold `Flow`" :geometry="{ label: { x: 0.2987, y: 0.5743 } }" />

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

# Emitting is a `suspend` call

<DrawnAnnotation text="suspend fun emit" label="Upstream waits inside `emit` until downstream is done with `value`" :geometry="{ label: { x: 0.4430, y: 0.3773 } }"/>

```kotlin no-compile
fun interface FlowCollector<in T> {
  suspend fun emit(value: T)
}
```

---
class: timeline-slide
---

# Upstream and downstream take turns

<DrawnAnnotation text="attendance()" on="0" label="Upstream: `emit` suspends until `println` returned" :geometry="{ label: { x: 0.3266, y: 0.5544 } }"/>
<DrawnAnnotation text="println(count)" on="1" label="Downstream: runs while `emit` is suspended" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5383, y: 0.6225 } }"/>

```kotlin
attendance().collect { count -> println(count) }
```

<FlowTimeline :steps="['10', '20', '30']" loop="repeat(3)" up="emit" down="println" />

---

# `callbackFlow` wraps a subscription

<DrawnAnnotation text="trySend(talk)" on="0" label="Called on the SDK's thread, hands the event to the collector" :geometry="{ label: { x: 0.3938, y: 0.5396 } }"/>
<DrawnAnnotation text="awaitClose" on="1" label="Suspends until the collector stops, then unsubscribes" :geometry="{ label: { x: 0.4078, y: 0.5419 } }"/>

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

<DrawnAnnotation text="take(2)" label="Stops collecting, so `awaitClose` runs and unsubscribes" :geometry="{ label: { x: 0.3384, y: 0.7903 } }"/>

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

<DrawnAnnotation text="catch" on="0" label="Called when upstream (`scheduleChanges` or `map`) throw\nAllowed to emit new values" :geometry="{ label: { x: 0.4001, y: 0.6433 } }"/>
<DrawnAnnotation text="onCompletion" on="1" label="Runs once, with `null`, the failure or the `CancellationException`" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.4205, y: 0.6398 } }"/>

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
layout: intro
class: section-slide
kodee: wave
---

<div class="lesson-number">Lesson 6</div>

# Channels

## Values between coroutines

---

# A `Channel` hands values between coroutines

<DrawnAnnotation text="Channel<Talk>()" label="A `Channel` both sides suspend on by default (size=0)" :geometry="{ label: { x: 0.4634, y: 0.2604 } }"/>
<DrawnAnnotation text="send" on="0" label="Suspends until someone receives" :geometry="{ label: { x: 0.4794, y: 0.5893 } }"/>
<DrawnAnnotation text="receive()" on="1" label="Suspends until someone sends" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.4880, y: 0.5904 } }"/>

```kotlin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val channel = Channel<Talk>()

coroutineScope {
  launch { channel.send(Talk(10, "Structured concurrency")) }
  println(channel.receive().title)
}
```
```console
Structured concurrency
```

---
magic-move
---

# `close` ends the channel

<DrawnAnnotation text="channel.close()" label="No more values: the receiving loop ends" :geometry="{ label: { x: 0.4710, y: 0.4469 } }"/>
<DrawnAnnotation text="for (talk in channel)" label="Suspends before every element, like `receive`" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5517, y: 0.6434 } }"/>

```kotlin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val channel = Channel<Talk>()

coroutineScope {
  launch {
    for (talk in fetchTalks()) channel.send(talk)
    channel.close()
  }
    
  for (talk in channel) {
      println(talk.title)
  }
}
```
```console
Structured concurrency
Flow
```

---

# `for` over a channel suspends in `hasNext()`

<DrawnAnnotation text="suspend operator fun hasNext()" label="The `iterator()` contract from lesson 4, now suspending" :geometry="{ label: { x: 0.3364, y: 0.4157 } }"/>
<DrawnAnnotation text="next()" label="Never suspends: `hasNext` already fetched the value" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.4506, y: 0.4854 } }"/>

```kotlin no-compile
interface ChannelIterator<out E> {
  suspend operator fun hasNext(): Boolean
  operator fun next(): E
}
```

---

# `produce` ties the channel to a scope

<DrawnAnnotation text="produce" on="0" label="A child coroutine that owns the sending side: closes it when the block ends" :geometry="{ label: { x: 0.5810, y: 0.3161 } }"/>
<DrawnAnnotation text="ReceiveChannel<Talk>" on="1" label="The caller only sees the receiving side" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5284, y: 0.3223 } }"/>

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope

fun CoroutineScope.talks(): ReceiveChannel<Talk> = produce {
  for (talk in fetchTalks()) send(talk)
}

coroutineScope {
  for (talk in talks()) println(talk.title)
}
```
```console
Structured concurrency
Flow
```

---
class: timeline-slide
---

# `buffer` puts a channel in between

<DrawnAnnotation text="buffer(capacity = 3)" label="Upstream gets its own coroutine\n`emit` only waits once all 3 slots are full" :geometry="{ label: { x: 0.3445, y: 0.6630 } }"/>

```kotlin
import kotlinx.coroutines.flow.buffer

attendance()
  .buffer(capacity = 3)
  .collect { count -> println(count) }
```

<ChannelDiagram left="emit" right="collect" :capacity="3" />

---

# `flow { }` emits from one coroutine only

<DrawnAnnotation text="launch { emit(" :line="3" label="`emit` from a child coroutine: the collector is not thread-safe" color="red" :geometry="{ label: { x: 0.4861, y: 0.4097 } }"/>

```kotlin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun titles(): Flow<String> = flow {
  coroutineScope {
    launch { emit(fetchSpeaker().name) }
    launch { emit(fetchTalks().first().title) }
  }
}

titles().collect { title -> println(title) }
```

<v-clicks>

<div class="stacktrace" aria-label="Application stack trace">
  <div>Exception in thread "main" <span class="stacktrace-link">java.lang.IllegalStateException</span>: Flow invariant is violated:</div>
  <div>&nbsp;&nbsp;&nbsp;&nbsp;Emission from another coroutine is detected.</div>
  <div>&nbsp;&nbsp;&nbsp;&nbsp;FlowCollector is not thread-safe and concurrent emissions are prohibited.</div>
  <div>&nbsp;&nbsp;&nbsp;&nbsp;To mitigate this restriction please use 'channelFlow' builder instead of 'flow'</div>
</div>

</v-clicks>

---
magic-move
---

# `channelFlow` emits from any coroutine

<DrawnAnnotation text="channelFlow" label="A `ProducerScope`: a `CoroutineScope` with a `SendChannel`" :geometry="{ label: { x: 0.5474, y: 0.6366 } }"/>
<DrawnAnnotation text="send" :line="2" label="Through a channel to the collector, from any child" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5174, y: 0.6925 } }"/>

```kotlin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun titles(): Flow<String> = channelFlow {
  launch { send(fetchSpeaker().name) }
  launch { send(fetchTalks().first().title) }
}

titles().collect { title -> println(title) }
```
```console
Ada
Structured concurrency
```

---

# `callbackFlow` is `channelFlow` plus `awaitClose`

<DrawnAnnotation text="ProducerScope<T>" :line="1" label="`trySend` from the SDK's thread was a channel all along"/>
<DrawnAnnotation text="callbackFlow" label="Same builder, but the block must end in `awaitClose`" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5881, y: 0.4406 } }"/>

```kotlin no-compile
fun <T> channelFlow(block: suspend ProducerScope<T>.() -> Unit): Flow<T>

fun <T> callbackFlow(block: suspend ProducerScope<T>.() -> Unit): Flow<T>
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 3</div>

# Produce the talks

> `produce` closes the channel when its block ends

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.runBlocking

fun CoroutineScope.talks(): ReceiveChannel<Talk> = TODO()

fun main() = runBlocking {
  val titles = buildList { for (talk in talks()) add(talk.title) }
  check(titles == ["Structured concurrency", "Flow"])
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 3</div>

# Sum a channel

> `for` over the channel suspends until `close`

```kotlin
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

suspend fun ReceiveChannel<Int>.total(): Int = TODO()

fun main() = runBlocking {
  val readings = produce {
    send(10)
    send(20)
    send(30)
  }
  check(readings.total() == 60)
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 3 / 3</div>

# Collect two flows at once

> `channelFlow` may `send` from a child coroutine

```kotlin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

fun <T> both(first: Flow<T>, second: Flow<T>): Flow<T> = TODO()

fun main() = runBlocking {
  val titles = both(flowOf("Flow"), flowOf("Channels")).toList()
  check(titles.sorted() == ["Channels", "Flow"])
}
```

---
layout: intro
class: section-slide
kodee: wave
---

<div class="lesson-number">Lesson 6</div>

# StateFlow

## A value that changes over time

---

# A `StateFlow` always has a value

<DrawnAnnotation text="MutableStateFlow(0)" label="Starts with a value, is never empty" :geometry="{ label: { x: 0.5634, y: 0.2676 } }"/>
<DrawnAnnotation text="count.value = 10" label="Read or written right away, no coroutine needed" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5078, y: 0.3859 } }"/>

```kotlin
import kotlinx.coroutines.flow.MutableStateFlow

val count = MutableStateFlow(0)

println(count.value)
count.value = 10
println(count.value)
```
```console
0
10
```

---
magic-move
---

# Collectors see the latest value

<DrawnAnnotation text="count.collect" label="Starts with the current value, never completes" :geometry="{ label: { x: 0.6069, y: 0.3575 } }"/>
<DrawnAnnotation text="delay(100.milliseconds)" />
<DrawnAnnotation text="count.value = 20" label="Overwritten before the collector was ready: never seen" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5161, y: 0.7599 } }"/>

```kotlin
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val count = MutableStateFlow(0)

scope.launch {
  count.collect { value ->
    delay(100.milliseconds)
    println(value)
  }
}

delay(10.milliseconds)
count.value = 10
count.value = 20
count.value = 30
```
```console
0
30
```

---

# `update` changes the value atomically

<DrawnAnnotation text="count.value = count.value + 1" label="Two threads read `0`, both write `1` unsafe atomic operation" color="red" :geometry="{ label: { x: 0.6676, y: 0.3005 } }"/>
<DrawnAnnotation text="update" label="Retries when another thread wrote in between" :geometry="{ label: { x: 0.3779, y: 0.4759 } }"/>

```kotlin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

val count = MutableStateFlow(0)

count.value = count.value + 1

count.update { value -> value + 1 }
```

---

# Expose the read-only `StateFlow`

<DrawnAnnotation text="_count" on="0" label="The underscore convention: two properties for one value" :geometry="{ label: { x: 0.4483, y: 0.4952 } }"/>
<DrawnAnnotation text="asStateFlow()" on="1" label="Callers read and collect, only the class writes" color="var(--fundamentals-blue)" :geometry="{ label: { x: 0.5204, y: 0.5401 } }"/>

```kotlin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Attendance {
  private val _count = MutableStateFlow(0)
  val count: StateFlow<Int> = _count.asStateFlow()

  fun checkIn() {
    _count.update { value -> value + 1 }
  }
}
```

---
magic-move
---

# An explicit backing field replaces the underscore

> Enabled by default since Kotlin 2.4

<DrawnAnnotation text="StateFlow<Int>" on="0" label="Outside, it is what the declaration says" :geometry="{ label: { x: 0.6245, y: 0.3783 } }"/>
<DrawnAnnotation text="field = MutableStateFlow(0)" on="1" label="One property: the backing field keeps the wider type" :geometry="{ label: { x: 0.6516, y: 0.4644 } }"/>
<DrawnAnnotation text="count.update" on="1" />

```kotlin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class Attendance {
  val count: StateFlow<Int>
    field = MutableStateFlow(0)

  fun checkIn() {
    count.update { value -> value + 1 }
  }
}
```

---
magic-move
---

# Outside the class, `count` is read-only

<InlineCompilerError text="value" :line="11" message="'val' cannot be reassigned.">

```kotlin
class Attendance {
  val count: StateFlow<Int>
    field = MutableStateFlow(0)

  fun checkIn() {
    count.update { value -> value + 1 }
  }
}

val attendance = Attendance()
attendance.count.value = 5
```

</InlineCompilerError>

---

# `stateIn` turns a cold flow into state

<DrawnAnnotation text="stateIn" on="0" label="Collects upstream in `scope`, shares the latest value" :geometry="{ label: { x: 0.6049, y: 0.4800 } }"/>
<DrawnAnnotation text="SharingStarted.WhileSubscribed()" on="1" label="Upstream runs only while someone collects" :geometry="{ label: { x: 0.5694, y: 0.4748 } }"/>
<DrawnAnnotation text="initialValue = 0" on="2" label="Until the first reading arrives"  :geometry="{ label: { x: 0.5668, y: 0.4788 } }"/>

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.reactive.asFlow

class LiveAttendance(scope: CoroutineScope) {
  val count: StateFlow<Int> =
    sdk.attendance(10).asFlow().stateIn(
      scope,
      started = SharingStarted.WhileSubscribed(),
      initialValue = 0,
    )
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 1 / 2</div>

# Count the check-ins

> `update` keeps concurrent check-ins from being lost

```kotlin
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

class Attendance {
  val count: StateFlow<Int> = TODO()
  fun checkIn(): Unit = TODO()
}

fun main() = runBlocking {
  val attendance = Attendance()
  repeat(3) { attendance.checkIn() }
  check(attendance.count.value == 3)
}
```

---
class: exercise-slide
kodee: heart
---

<div class="lesson-number">Exercise 2 / 2</div>

# Keep the latest reading

> `stateIn` needs a scope, a `SharingStarted` and an initial value

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun CoroutineScope.latest(readings: Flow<Int>): StateFlow<Int> = TODO()

fun main() = runBlocking {
  val count = latest(flowOf(10, 20, 30))
  yield()
  check(count.value == 30)
}
```

---

# Coroutines in six rules

- `suspend` → waits without blocking, needs a coroutine to run in
- `coroutineScope` → every child completes, fails or is cancelled with its parent
- `suspendCancellableCoroutine`, `await()`, `asFlow()` → every Java style has a bridge, all of them cancellable
- `Flow` → cold, lazy, and cancelled with its collector
- `Channel` → hot, `send` and `receive` suspend, `for` waits for `close`
- `StateFlow` → always has a value, collectors see the latest one

> **Every coroutine has a parent.**
