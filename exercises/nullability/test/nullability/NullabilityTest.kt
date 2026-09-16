package nullability

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NullabilityTest {
  @Test
  fun `shout passes null through`() {
    assertEquals("HI", shout("hi"))
    assertNull(shout(null))
  }

  @Test
  fun `lengthOrZero falls back to 0`() {
    assertEquals(6, lengthOrZero("Kotlin"))
    assertEquals(0, lengthOrZero(null))
  }

  @Test
  fun `firstInitial answers with a question mark when there is none`() {
    assertEquals('S', firstInitial("simon"))
    assertEquals('?', firstInitial(null))
    assertEquals('?', firstInitial(""))
  }
}
