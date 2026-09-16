package iteration

class Song(val title: String, val minutes: Int)

class Playlist(private val songs: List<Song>) : Iterable<Song> {
  override operator fun iterator(): Iterator<Song> = songs.iterator()

  companion object {
    operator fun of(vararg songs: Song): Playlist = Playlist(songs.toList())
  }
}
