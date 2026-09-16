package coroutines

import kotlin.time.Duration
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeoutOrNull

suspend fun dashboard(): Dashboard = coroutineScope {
  val speaker = async { fetchSpeaker() }
  val talks = async { fetchTalks() }
  Dashboard(speaker.await(), talks.await())
}

suspend fun dashboardOrNull(timeout: Duration): Dashboard? =
  withTimeoutOrNull(timeout) { dashboard() }
