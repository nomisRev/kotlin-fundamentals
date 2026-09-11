package presentation.support

import java.time.LocalTime

@DslMarker
annotation class AgendaDsl

data class Session(val title: String, val speaker: String?, val minutes: Int)
data class Agenda(val title: String, val sessions: List<Session>)
data class ScheduledSession(val session: Session, val startsAt: LocalTime)
data class DisplayConfig(val showSpeakers: Boolean = true)
data class User(val name: String)

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

fun Agenda.schedule(startAt: LocalTime): List<ScheduledSession> {
  var next = startAt
  return sessions.map { session ->
    val scheduled = ScheduledSession(session, next)
    next = next.plusMinutes(session.minutes.toLong())
    scheduled
  }
}

fun List<ScheduledSession>.render(config: DisplayConfig): String =
  joinToString("\n") { entry ->
    with(config) {
      val speaker = if (showSpeakers) entry.session.speaker else null
      val suffix = if (speaker == null) "" else " — $speaker"
      "${entry.startsAt} ${entry.session.title}$suffix"
    }
  }

val introduction = Session("Welcome", "Simon", 10)
val coffeeBreak = Session("Coffee", null, 15)
val program = agenda("Kotlin afternoon") {
  session("Expressive APIs") {
    speaker = "Simon"
    minutes = 30
  }
  session("Build your own DSL") {
    minutes = 20
  }
}
