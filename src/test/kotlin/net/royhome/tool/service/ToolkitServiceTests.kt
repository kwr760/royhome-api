package net.royhome.tool.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToolkitServiceTests {
  private lateinit var underTest: ToolkitService

  @BeforeEach
  fun setUpBeforeEach() {
    underTest = ToolkitService()
  }

  @Test
  fun `get a toolkit boolean`() {
    // Arrange // Act
    val response = underTest.getRandomBool()

    // Assert
    Assertions.assertTrue(listOf(true, false).contains(response))
  }
}
