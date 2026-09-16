package coroutines

import kotlin.time.Duration

/**
 * Exercise 1 / 5: load the dashboard in parallel.
 *
 * `fetchSpeaker()` and `fetchTalks()` take 100 ms each. Run them at the same
 * time inside a `coroutineScope` so that `dashboard()` takes 100 ms as well.
 */
suspend fun dashboard(): Dashboard = TODO()

/**
 * Exercise 2 / 5: give up after a timeout.
 *
 * Return the dashboard, or `null` when loading it takes longer than [timeout].
 * `withTimeoutOrNull` cancels the block, and with it every child, when the
 * time is up.
 */
suspend fun dashboardOrNull(timeout: Duration): Dashboard? = TODO()
