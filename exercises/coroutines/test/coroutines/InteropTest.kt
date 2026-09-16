package coroutines

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

class InteropTest {
  @Test
  fun `speaker resumes with the callback's value`() = runTest {
    assertEquals(Speaker(1, "Ada"), ConferenceSdk().speaker(1))
  }

  @Test
  fun `cancelling the coroutine cancels the call`() = runTest {
    val sdk = ConferenceSdk()
    val job = launch { sdk.speaker(1) }
    runCurrent()
    job.cancelAndJoin()
    assertEquals(1, sdk.cancelledCalls, "the Call was not cancelled")
  }

  @Test
  fun `allTalks awaits the future`() = runTest {
    assertEquals(listOf("Structured concurrency", "Flow"), ConferenceSdk().allTalks(1).map { it.title })
  }

  @Test
  fun `dashboardAsync completes the future with the dashboard`() = runTest {
    assertEquals(Speaker(1, "Ada"), dashboardAsync().await().speaker)
  }

  @Test
  fun `readings takes the first readings of the publisher`() = runTest {
    assertEquals(listOf(10, 20, 30), ConferenceSdk().readings(10, count = 3))
  }
}
