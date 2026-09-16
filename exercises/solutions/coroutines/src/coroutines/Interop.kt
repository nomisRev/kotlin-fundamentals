package coroutines

import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun ConferenceSdk.speaker(id: Long): Speaker =
  suspendCancellableCoroutine { continuation ->
    val call = speaker(id, object : Callback<Speaker> {
      override fun onSuccess(value: Speaker) = continuation.resume(value)
      override fun onFailure(error: Throwable) = continuation.resumeWithException(error)
    })
    continuation.invokeOnCancellation { call.cancel() }
  }

suspend fun ConferenceSdk.allTalks(id: Long): List<Talk> = talks(id).await()

fun CoroutineScope.dashboardAsync(): CompletableFuture<Dashboard> = future { dashboard() }

suspend fun ConferenceSdk.readings(id: Long, count: Int): List<Int> =
  attendance(id).asFlow().take(count).toList()
