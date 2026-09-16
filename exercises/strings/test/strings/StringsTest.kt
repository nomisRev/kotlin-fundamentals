package strings

import kotlin.test.Test
import kotlin.test.assertEquals

class StringsTest {
  @Test
  fun `birthday interpolates the name and the expression`() {
    assertEquals("Ada turns 37 next year", birthday("Ada", 36))
  }

  @Test
  fun `receipt is three trimmed lines`() {
    assertEquals("Coffee costs 3.5\n----------\nThank you!", receipt("Coffee", 3.5))
  }

  @Test
  fun `payload keeps the dollar sign`() {
    assertEquals("{ \"${'$'}id\": \"kodee\" }", payload("kodee"))
  }
}
