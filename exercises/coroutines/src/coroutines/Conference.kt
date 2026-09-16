package coroutines

import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

data class Speaker(val id: Long, val name: String)
data class Talk(val id: Long, val title: String)
data class Dashboard(val speaker: Speaker, val talks: List<Talk>)

/** A slow remote call: takes 100 ms and returns Ada. */
suspend fun fetchSpeaker(): Speaker {
  delay(100.milliseconds)
  return Speaker(1, "Ada")
}

/** A slow remote call: takes 100 ms and returns Ada's talks. */
suspend fun fetchTalks(): List<Talk> {
  delay(100.milliseconds)
  return listOf(Talk(10, "Structured concurrency"), Talk(11, "Flow"))
}

/** The Java SDK the interop exercises wrap. */
val sdk = ConferenceSdk()
