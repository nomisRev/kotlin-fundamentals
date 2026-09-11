package presentation.support

import java.time.LocalTime
import presentation.support.Agenda
import presentation.support.Session
import presentation.support.DisplayConfig
import presentation.support.schedule
import presentation.support.render

fun main() {
  val coffee = Session("Coffee", null, 15)
  val result = agenda("Workshop") {
    session("APIs") { speaker = "Simon"; minutes = 30 }
    session("Exercise") { minutes = 20 }
    +coffee
  }
  check(result.sessions.map { it.title } == listOf("APIs", "Exercise", "Coffee"))
  check(result.totalMinutes() == 65)
  check(result.sessions.first().speaker == "Simon")
  val defaults = agenda("Defaults") { session("Talk") {} }.sessions.single()
  check(defaults.minutes == 30 && defaults.speaker == null)
  check(agenda("Empty") {}.totalMinutes() == 0)
  for (invalid in listOf(0, -1)) {
    check(runCatching { agenda("Invalid") { session("Talk") { minutes = invalid } } }
      .exceptionOrNull() is IllegalArgumentException)
    check(runCatching { agenda("Invalid") { Session("Talk", null, invalid).insert() } }
      .exceptionOrNull() is IllegalArgumentException)
    check(runCatching { agenda("Invalid") { +Session("Talk", null, invalid) } }
      .exceptionOrNull() is IllegalArgumentException)
  }
  val builder = AgendaBuilder("Snapshot")
  with(builder) { coffee.insert() }
  val before = builder.build()
  with(builder) { +coffee }
  check(before.sessions.size == 1 && builder.build().sessions.size == 2)
  val morning = result.schedule(LocalTime.of(9, 0))
  val afternoon = result.schedule(LocalTime.of(14, 0))
  check(morning.map { it.startsAt } == listOf(LocalTime.of(9, 0), LocalTime.of(9, 30), LocalTime.of(9, 50)))
  check(afternoon.first().startsAt == LocalTime.of(14, 0))
  check(result.totalMinutes() == 65)
  check("Simon" in morning.render(DisplayConfig(true)))
  check("Simon" !in morning.render(DisplayConfig(false)))
  check(agenda("Empty") {}.schedule(LocalTime.NOON).isEmpty())
  check(builder.apply { build() } === builder)
  check(builder.run { build() } == builder.build())
  println("Lesson 5 acceptance checks passed")
}
