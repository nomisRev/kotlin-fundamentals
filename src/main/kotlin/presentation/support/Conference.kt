// Hand-written context for lesson 6 (Coroutines).
//
// The slides load a speaker and their talks from three flavours of "slow" API:
// plain suspending functions here, and the callback, CompletableFuture and
// Reactive Streams flavours of the Java `ConferenceSdk` next to this file.
package presentation.support

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine

data class Speaker(val id: Long, val name: String)
data class Talk(val id: Long, val title: String)
data class Dashboard(val speaker: Speaker, val talks: List<Talk>, val attendance: Int = 0)

/** A slow remote call: takes 100 ms and returns Ada. */
suspend fun fetchSpeaker(): Speaker {
  delay(100.milliseconds)
  return Speaker(1, "Ada")
}

/** A slow remote call: takes 100 ms and returns Ada's talks. */
suspend fun fetchTalks(): List<Talk> {
  delay(100.milliseconds)
  return listOf(Talk(10, "Structured concurrency"), Talk(11, "Flow"))
}

/** The parallel dashboard the structured-concurrency slides build up to. */
suspend fun dashboard(): Dashboard = coroutineScope {
  val speaker = async { fetchSpeaker() }
  val talks = async { fetchTalks() }
  Dashboard(speaker.await(), talks.await())
}

/** Something a child coroutine does without producing a value. */
suspend fun notifyAttendees(talk: Talk) {
  delay(100.milliseconds)
}

/** A CPU-bound step with no suspension point, for the cooperative-cancellation slides. */
fun compute() {}

/** The Java SDK every interop slide wraps. */
val sdk = ConferenceSdk()

/** The suspending wrapper the interop slides write; later slides call it. */
suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCancellableCoroutine { continuation ->
    val call = speaker(id, object : Callback<Speaker> {
      override fun onSuccess(value: Speaker) = continuation.resume(value)
      override fun onFailure(error: Throwable) = continuation.resumeWithException(error)
    })
    continuation.invokeOnCancellation { call.cancel() }
  }

/** A long-lived application scope, as a service would receive it. */
val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

/** The cold flow the Flow slides build; later steps of the chain use it without redeclaring it. */
fun attendance(): Flow<Int> = flow {
  var count = 0
  repeat(3) {
    delay(100.milliseconds)
    count += 10
    emit(count)
  }
}

/** The `callbackFlow` wrapper the Flow slides write; later slides call it. */
fun ConferenceSdk.scheduleChanges(): Flow<Talk> =
  callbackFlow {
    val subscription = onScheduleChange { talk -> trySend(talk) }
    awaitClose { subscription.unsubscribe() }
  }
