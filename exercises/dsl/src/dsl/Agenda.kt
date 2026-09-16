package dsl

@DslMarker
annotation class AgendaDsl

data class Session(val title: String, val speaker: String?, val minutes: Int)

data class Agenda(val title: String, val sessions: List<Session>)

val coffeeBreak = Session("Coffee", null, 15)
