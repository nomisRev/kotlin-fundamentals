package coroutines

import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.CoroutineScope

/**
 * Exercise 3 / 5: wrap the callback.
 *
 * `ConferenceSdk.speaker(id, callback)` answers through a [Callback] and hands
 * back a [Call]. Turn it into a suspending function with
 * `suspendCancellableCoroutine`: `resume` on success, `resumeWithException` on
 * failure, and cancel the [Call] when the coroutine is cancelled.
 */
suspend fun ConferenceSdk.speaker(id: Long): Speaker = TODO()

/**
 * Exercise 4 / 5: bridge the futures, one way.
 *
 * `ConferenceSdk.talks(id)` returns a `CompletableFuture`. Await it.
 */
suspend fun ConferenceSdk.allTalks(id: Long): List<Talk> = TODO()

/**
 * Exercise 4 / 5: bridge the futures, the other way.
 *
 * Expose [dashboard] to Java callers as a `CompletableFuture` with the
 * `future { }` builder on this scope.
 */
fun CoroutineScope.dashboardAsync(): CompletableFuture<Dashboard> = TODO()

/**
 * Exercise 5 / 5: read the stream.
 *
 * `ConferenceSdk.attendance(id)` is a Reactive Streams `Publisher` that never
 * completes. Turn it into a `Flow`, take the first [count] readings and
 * collect them into a list.
 */
suspend fun ConferenceSdk.readings(id: Long, count: Int): List<Int> = TODO()
