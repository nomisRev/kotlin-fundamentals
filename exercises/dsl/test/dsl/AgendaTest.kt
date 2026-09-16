package dsl

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class AgendaTest {
  private fun workshop(): Agenda = agenda("Workshop") {
    session("APIs") {
      speaker = "Simon"
      minutes = 30
    }
    session("Exercise") { minutes = 20 }
    +coffeeBreak
  }

  @Test
  fun `a session defaults to 30 minutes and no speaker`() {
    assertEquals(Session("Talk", null, 30), SessionBuilder("Talk").build())
  }

  @Test
  fun `a session rejects a non-positive duration`() {
    assertFailsWith<IllegalArgumentException> { SessionBuilder("Talk").apply { minutes = 0 }.build() }
  }

  @Test
  fun `sessions are inserted in order`() {
    val result = workshop()
    assertEquals(listOf("APIs", "Exercise", "Coffee"), result.sessions.map { it.title })
    assertEquals("Simon", result.sessions.first().speaker)
    assertEquals(coffeeBreak, result.sessions.last())
  }

  @Test
  fun `insert and unaryPlus reject a non-positive duration`() {
    assertFailsWith<IllegalArgumentException> { agenda("Invalid") { Session("Talk", null, 0).insert() } }
    assertFailsWith<IllegalArgumentException> { agenda("Invalid") { +Session("Talk", null, -1) } }
  }

  @Test
  fun `an agenda snapshots its sessions`() {
    val builder = AgendaBuilder("Snapshot")
    with(builder) { coffeeBreak.insert() }
    val before = builder.build()
    with(builder) { +coffeeBreak }
    assertEquals(1, before.sessions.size)
    assertEquals(2, builder.build().sessions.size)
  }

  @Test
  fun `an empty agenda is valid`() {
    val empty = agenda("Empty") {}
    assertEquals("Empty", empty.title)
    assertEquals(0, empty.totalMinutes())
  }

  @Test
  fun `totalMinutes sums the sessions`() {
    assertEquals(65, workshop().totalMinutes())
  }

  @Test
  fun `lightningTalk adds a five-minute session`() {
    val session = agenda("Lightning") { lightningTalk("Receivers", "Ada") }.sessions.single()
    assertEquals(Session("Receivers", "Ada", 5), session)
    assertNull(agenda("Defaults") { session("Talk") {} }.sessions.single().speaker)
  }
}
