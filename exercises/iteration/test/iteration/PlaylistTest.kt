package iteration

import kotlin.test.Test
import kotlin.test.assertEquals

class PlaylistTest {
  @Test
  fun `a Playlist can be iterated`() {
    val playlist = Playlist(listOf(Song("Intro", 3), Song("Outro", 4)))
    assertEquals(listOf("Intro", "Outro"), playlist.map { it.title })
  }

  @Test
  fun `a Playlist is a literal target`() {
    val playlist: Playlist = [Song("Intro", 3), Song("Outro", 4)]
    assertEquals(7, playlist.sumOf { it.minutes })
  }
}
