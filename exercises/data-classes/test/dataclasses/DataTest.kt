package dataclasses

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class DataTest {
  @Test
  fun `members with the same values are equal`() {
    assertEquals(Member("Ada", 36), Member("Ada", 36))
    assertNotEquals(Member("Ada", 36), Member("Ada", 37))
  }

  @Test
  fun `toString names the properties`() {
    assertEquals("Member(name=Ada, age=36)", Member("Ada", 36).toString())
  }

  @Test
  fun `copy changes one property`() {
    assertEquals(Member("Ada", 37), Member("Ada", 36).copy(age = 37))
  }

  @Test
  fun `Age wraps an Int`() {
    assertEquals(36, Age(36).value)
    assertEquals(Age(36), Age(36))
  }

  @Test
  fun `Age rejects a negative value`() {
    assertFailsWith<IllegalArgumentException> { Age(-1) }
  }

  @Test
  fun `ages compare and sort`() {
    assertTrue(Age(17) < Age(36))
    assertEquals(listOf(Age(17), Age(25), Age(36)), listOf(Age(36), Age(17), Age(25)).sorted())
  }
}
