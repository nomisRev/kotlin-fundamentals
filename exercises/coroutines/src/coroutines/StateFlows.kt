package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Exercise 1 / 2: count the check-ins.
 *
 * Keep the number of check-ins in a `MutableStateFlow` and expose it as a
 * read-only [StateFlow]: either with an explicit backing field
 * (`val count: StateFlow<Int>` followed by `field = MutableStateFlow(0)`,
 * `-Xexplicit-backing-fields` is enabled in `module.yaml`) or with the
 * `_count` / `asStateFlow()` pair. [checkIn] adds one with `update`, so that
 * concurrent check-ins are never lost.
 */
class Attendance {
  val count: StateFlow<Int> = TODO()

  fun checkIn(): Unit = TODO()
}

/**
 * Exercise 2 / 2: keep the latest reading.
 *
 * Turn the cold [readings] flow into a [StateFlow] with `stateIn`: collect it
 * on this scope right away (`SharingStarted.Eagerly`), starting from `0`.
 */
fun CoroutineScope.latest(readings: Flow<Int>): StateFlow<Int> = TODO()
