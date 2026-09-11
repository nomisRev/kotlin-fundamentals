package exercises.lesson5

import presentation.support.Agenda
import presentation.support.AgendaDsl
import presentation.support.Session

// Implement the TODOs here. The reference implementation is presentation/support/Agenda.kt.
@AgendaDsl
class SessionBuilder(private val title: String) {
  var speaker: String? = null
  var minutes: Int = 30

  fun build(): Session = TODO("Reject non-positive durations, then construct a Session")
}

@AgendaDsl
class AgendaBuilder(private val title: String) {
  private val sessions = mutableListOf<Session>()

  fun session(title: String, block: SessionBuilder.() -> Unit) {
    TODO("Configure a child builder and insert the finished session")
  }

  fun Session.insert() {
    TODO("Reject non-positive durations and append this session")
  }

  operator fun Session.unaryPlus() {
    TODO("Delegate to insert")
  }

  fun build(): Agenda = TODO("Return an Agenda with a snapshot of sessions")
}

fun agenda(title: String, block: AgendaBuilder.() -> Unit): Agenda =
  TODO("Configure a builder with apply, then build")

fun Agenda.totalMinutes(): Int = TODO("Use the collection API")
