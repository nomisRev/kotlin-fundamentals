package dsl

@AgendaDsl
class SessionBuilder(private val title: String) {
  var speaker: String? = null
  var minutes: Int = 30

  fun build(): Session {
    require(minutes > 0) { "Session duration must be positive" }
    return Session(title, speaker, minutes)
  }
}

@AgendaDsl
class AgendaBuilder(private val title: String) {
  private val sessions = mutableListOf<Session>()

  fun session(title: String, block: SessionBuilder.() -> Unit) {
    SessionBuilder(title).apply(block).build().insert()
  }

  fun Session.insert() {
    require(minutes > 0) { "Session duration must be positive" }
    sessions.add(this)
  }

  operator fun Session.unaryPlus() {
    insert()
  }

  fun build(): Agenda = Agenda(title, sessions.toList())
}

fun agenda(title: String, block: AgendaBuilder.() -> Unit): Agenda =
  AgendaBuilder(title).apply(block).build()

fun Agenda.totalMinutes(): Int = sessions.sumOf { it.minutes }

fun AgendaBuilder.lightningTalk(title: String, speaker: String) {
  session(title) {
    this.speaker = speaker
    minutes = 5
  }
}
