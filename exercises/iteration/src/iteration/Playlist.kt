package iteration

class Song(val title: String, val minutes: Int)

/**
 * Exercise 1 / 2: make `Playlist` iterable.
 *
 * Implement `Iterable<Song>` and `override` its `iterator()`, so that a
 * `for` loop and the whole collections API work on a `Playlist`.
 *
 * Exercise 2 / 2: make `Playlist` a literal target.
 *
 * Add a companion `operator fun of(vararg songs: Song): Playlist`, so that
 * `val playlist: Playlist = [intro, outro]` compiles.
 */
class Playlist(private val songs: List<Song>)
