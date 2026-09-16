package coroutines

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest

class StructuredConcurrencyTest {
  @Test
  fun `dashboard loads the speaker and the talks`() = runTest {
    val dashboard = dashboard()
    assertEquals(Speaker(1, "Ada"), dashboard.speaker)
    assertEquals(listOf("Structured concurrency", "Flow"), dashboard.talks.map { it.title })
  }

  @Test
  fun `dashboard loads both at the same time`() = runTest {
    dashboard()
    assertEquals(100, currentTime, "expected 100 ms of virtual time, not one call after the other")
  }

  @Test
  fun `dashboardOrNull gives up after the timeout`() = runTest {
    assertNull(dashboardOrNull(50.milliseconds))
  }

  @Test
  fun `dashboardOrNull returns the dashboard in time`() = runTest {
    assertNotNull(dashboardOrNull(150.milliseconds))
  }
}
