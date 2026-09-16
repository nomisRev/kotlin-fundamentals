package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

/**
 * Exercise 1 / 3: produce the talks.
 *
 * Start a `produce` coroutine on the scope that sends every talk from
 * [fetchTalks]. `produce` closes the channel when its block ends, so the
 * caller's `for` loop ends too.
 */
fun CoroutineScope.talks(): ReceiveChannel<Talk> = TODO()

/**
 * Exercise 2 / 3: sum a channel.
 *
 * Iterate the channel with `for` and add up every value. The loop suspends
 * before every element and ends once the sender closes the channel.
 */
suspend fun ReceiveChannel<Int>.total(): Int = TODO()

/**
 * Exercise 3 / 3: collect two flows at once.
 *
 * Build a `channelFlow` that collects [first] and [second] in two child
 * coroutines and sends every value to the collector. A `flow { }` would not
 * do: it may only `emit` from its own coroutine.
 */
fun <T> both(first: Flow<T>, second: Flow<T>): Flow<T> = TODO()
