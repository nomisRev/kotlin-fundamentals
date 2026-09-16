package coroutines

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ChannelsTest {
  @Test
  fun `talks sends every talk and closes the channel`() = runTest {
    val titles = buildList { for (talk in talks()) add(talk.title) }
    assertEquals(listOf("Structured concurrency", "Flow"), titles)
  }

  @Test
  fun `total adds up the channel until it is closed`() = runTest {
    val readings = produce {
      send(10)
      send(20)
      send(30)
    }
    assertEquals(60, readings.total())
  }

  @Test
  fun `both sends the values of both flows`() = runTest {
    val titles = both(flowOf("Flow"), flowOf("Channels")).toList()
    assertEquals(listOf("Channels", "Flow"), titles.sorted())
  }

  @Test
  fun `both collects the flows at the same time`() = runTest {
    val slow = flow {
      delay(100.milliseconds)
      emit(1)
    }
    both(slow, slow).toList()
    assertEquals(100, currentTime, "expected both flows to be collected at once")
  }
}
