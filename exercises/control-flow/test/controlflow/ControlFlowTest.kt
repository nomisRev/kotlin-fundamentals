package controlflow

import kotlin.test.Test
import kotlin.test.assertEquals

class ControlFlowTest {
  @Test
  fun `abs drops the sign`() {
    assertEquals(3, abs(-3))
    assertEquals(4, abs(4))
  }

  @Test
  fun `grade maps a score to a letter`() {
    assertEquals("A", grade(95))
    assertEquals("B", grade(85))
    assertEquals("F", grade(40))
  }

  @Test
  fun `students between 12 and 64 pay 8`() {
    assertEquals(5, ticketPrice(8, student = false))
    assertEquals(8, ticketPrice(20, student = true))
    assertEquals(12, ticketPrice(20, student = false))
    assertEquals(6, ticketPrice(70, student = true))
  }

  @Test
  fun `next covers every entry`() {
    assertEquals(Light.GREEN, next(Light.RED))
    assertEquals(Light.AMBER, next(Light.GREEN))
    assertEquals(Light.RED, next(Light.AMBER))
  }

  @Test
  fun `digitCount runs the body at least once`() {
    assertEquals(1, digitCount(0))
    assertEquals(1, digitCount(7))
    assertEquals(4, digitCount(2024))
  }

  @Test
  fun `sumUntil stops at the value`() {
    assertEquals(6, sumUntil(listOf(1, 2, 3, 4, 5), stopAt = 4))
    assertEquals(0, sumUntil(listOf(4, 6, 8), stopAt = 4))
  }
}
