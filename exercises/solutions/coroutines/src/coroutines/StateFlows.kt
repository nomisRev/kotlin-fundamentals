package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class Attendance {
  val count: StateFlow<Int>
    field = MutableStateFlow(0)

  fun checkIn() {
    count.update { value -> value + 1 }
  }
}

fun CoroutineScope.latest(readings: Flow<Int>): StateFlow<Int> =
  readings.stateIn(this, SharingStarted.Eagerly, 0)
