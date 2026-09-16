package coroutines

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow

/**
 * Exercise 1 / 3: emit on a schedule.
 *
 * Build a `flow { }` that waits [every] and then emits the next number,
 * from `1` up to and including [count].
 */
fun ticks(count: Int, every: Duration): Flow<Int> = TODO()

/**
 * Exercise 2 / 3: transform the stream.
 *
 * Keep the talks whose title has at least [minLength] characters and map each
 * of them to its title.
 */
fun longTitles(talks: Flow<Talk>, minLength: Int): Flow<String> = TODO()

/**
 * Exercise 3 / 3: subscribe with `callbackFlow`.
 *
 * `ConferenceSdk.onScheduleChange(listener)` pushes a [Talk] for every change
 * and returns a [Subscription]. Send every event into the flow and unsubscribe
 * in `awaitClose` once the collector stops.
 */
fun ConferenceSdk.scheduleChanges(): Flow<Talk> = TODO()
