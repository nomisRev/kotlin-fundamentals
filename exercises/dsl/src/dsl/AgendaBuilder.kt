package dsl

/**
 * Exercise 1 / 5: build the session.
 *
 * `build()` rejects a non-positive [minutes] with `require` and constructs
 * the [Session]. An omitted speaker stays `null`, an omitted duration is 30
 * minutes.
 */
@AgendaDsl
class SessionBuilder(private val title: String) {
  var speaker: String? = null
  var minutes: Int = 30

  fun build(): Session = TODO()
}

@AgendaDsl
class AgendaBuilder(private val title: String) {
  private val sessions = mutableListOf<Session>()

  /**
   * Exercise 2 / 5: insert with `+`.
   *
   * `session` configures a child [SessionBuilder] with `apply`, builds it and
   * inserts the result. `insert()` is a member extension: it rejects a
   * non-positive duration and appends the session. `unaryPlus()` delegates
   * to `insert()`, so that `+coffeeBreak` works inside `agenda { }`.
   */
  fun session(title: String, block: SessionBuilder.() -> Unit): Unit = TODO()

  fun Session.insert(): Unit = TODO()

  operator fun Session.unaryPlus(): Unit = TODO()

  /**
   * Exercise 3 / 5: build the agenda.
   *
   * Return an [Agenda] with a copy of the list, so that inserting more
   * sessions into this builder afterwards cannot change an agenda already
   * built.
   */
  fun build(): Agenda = TODO()
}

/**
 * Exercise 3 / 5: build the agenda.
 *
 * The entry point: configure an [AgendaBuilder] with `apply`, then `build()`.
 */
fun agenda(title: String, block: AgendaBuilder.() -> Unit): Agenda = TODO()

/**
 * Exercise 4 / 5: total the minutes.
 *
 * Use the collection API: `sumOf`.
 */
fun Agenda.totalMinutes(): Int = TODO()

/**
 * Exercise 5 / 5: add `lightningTalk`.
 *
 * An ordinary extension on [AgendaBuilder] that adds a five-minute session
 * with the given title and speaker. `this.speaker` disambiguates the builder
 * property from the parameter.
 */
fun AgendaBuilder.lightningTalk(title: String, speaker: String): Unit = TODO()
