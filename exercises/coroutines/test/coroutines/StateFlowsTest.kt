package coroutines

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

class StateFlowsTest {
  @Test
  fun `count starts at zero and follows the check-ins`() = runTest {
    val attendance = Attendance()
    assertEquals(0, attendance.count.value)
    repeat(3) { attendance.checkIn() }
    assertEquals(3, attendance.count.value)
  }

  @Test
  fun `concurrent check-ins are never lost`() = runTest {
    val attendance = Attendance()
    withContext(Dispatchers.Default) {
      repeat(100) {
        launch { repeat(100) { attendance.checkIn() } }
      }
    }
    assertEquals(10_000, attendance.count.value, "expected an atomic `update`")
  }

  @Test
  fun `latest keeps the last reading`() = runTest {
    val count = latest(flowOf(10, 20, 30))
    assertEquals(0, count.value, "expected the initial value before anything was collected")
    testScheduler.runCurrent()
    assertEquals(30, count.value)
  }
}
