package coroutines

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest

class FlowsTest {
  @Test
  fun `ticks emits every number after a delay`() = runTest {
    assertEquals(listOf(1, 2, 3), ticks(3, every = 100.milliseconds).toList())
    assertEquals(300, currentTime, "expected one delay before every emit")
  }

  @Test
  fun `longTitles keeps the long titles`() = runTest {
    val talks = flowOf(Talk(1, "Flow"), Talk(2, "Structured concurrency"))
    assertEquals(listOf("Structured concurrency"), longTitles(talks, minLength = 5).toList())
  }

  @Test
  fun `scheduleChanges unsubscribes when the collector stops`() = runTest {
    val sdk = ConferenceSdk()
    assertEquals("Room change", sdk.scheduleChanges().first().title)
    assertEquals(0, sdk.activeSubscriptions, "the Subscription was not unsubscribed")
  }
}
