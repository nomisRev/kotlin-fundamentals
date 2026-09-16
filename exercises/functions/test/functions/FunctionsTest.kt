package functions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FunctionsTest {
  @Test
  fun `average of 4 and 8 is 6`() {
    assertEquals(6, average(4, 8))
  }

  @Test
  fun `increment falls back to 1`() {
    assertEquals(2, increment(1))
    assertEquals(6, increment(1, by = 5))
  }

  @Test
  fun `twoTerms skips b`() {
    assertEquals(2, twoTerms(1, 1))
    assertEquals(3, twoTerms(1, 2))
  }

  @Test
  fun `largest takes any number of arguments`() {
    assertEquals(9, largest(3, 9, 4))
    assertEquals(7, largest(7))

    val scores = intArrayOf(12, 40, 8)
    assertEquals(40, largest(*scores))
  }

  @Test
  fun `isEven extends Int`() {
    assertTrue(4.isEven())
    assertFalse(7.isEven())
  }

  @Test
  fun `percentOf reads like a sentence`() {
    assertEquals(20, 10 percentOf 200)
    assertEquals(4, 50 percentOf 8)
  }

  @Test
  fun `times repeats the word`() {
    assertEquals("HoHoHo", 3 * "Ho")
    assertEquals("", 0 * "Hi")
  }
}
