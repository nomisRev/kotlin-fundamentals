package coroutines

import kotlin.time.Duration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

fun ticks(count: Int, every: Duration): Flow<Int> = flow {
  for (tick in 1..count) {
    delay(every)
    emit(tick)
  }
}

fun longTitles(talks: Flow<Talk>, minLength: Int): Flow<String> =
  talks.filter { it.title.length >= minLength }.map { it.title }

fun ConferenceSdk.scheduleChanges(): Flow<Talk> = callbackFlow {
  val subscription = onScheduleChange { talk -> trySend(talk) }
  awaitClose { subscription.unsubscribe() }
}
