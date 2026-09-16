package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.talks(): ReceiveChannel<Talk> = produce {
  for (talk in fetchTalks()) send(talk)
}

suspend fun ReceiveChannel<Int>.total(): Int {
  var total = 0
  for (value in this) total += value
  return total
}

fun <T> both(first: Flow<T>, second: Flow<T>): Flow<T> = channelFlow {
  launch { first.collect { send(it) } }
  launch { second.collect { send(it) } }
}
