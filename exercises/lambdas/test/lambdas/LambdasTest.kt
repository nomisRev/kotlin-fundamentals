package lambdas

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LambdasTest {
  @Test
  fun `isEven is a lambda`() {
    assertTrue(isEven(4))
    assertFalse(isEven(7))
  }

  @Test
  fun `add names both parameters`() {
    assertEquals(5, add(2, 3))
  }

  @Test
  fun `shout has a String receiver`() {
    assertEquals("HI!", "hi".shout())
  }

  @Test
  fun `len is a reference`() {
    assertEquals(6, len("Kotlin"))
  }

  @Test
  fun `startsWith returns a predicate`() {
    val isK = startsWith("K")
    assertTrue(isK("Kotlin"))
    assertFalse(isK("Java"))
    assertEquals(listOf("Kotlin"), listOf("Kotlin", "Java").filter(startsWith("K")))
  }
}
