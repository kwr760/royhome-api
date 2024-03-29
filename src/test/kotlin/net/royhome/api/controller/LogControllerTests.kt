package net.royhome.api.controller

import io.mockk.mockk
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogControllerTests {
  private lateinit var stdout: PrintStream
  private lateinit var mockOut: PrintStream
  private lateinit var underTest: LogController
  private val outContent: ByteArrayOutputStream = ByteArrayOutputStream()

  @BeforeEach
  fun setUp() {
    underTest = LogController()

    stdout = System.out
    mockOut = mockk(relaxed = true)
    System.setOut(PrintStream(outContent, true, "UTF-8"))
  }

  fun tearDown() {
    System.setOut(stdout)
  }

  @Test
  fun `logController accepts request and prints log`() {
    // Arrange
    val msg = "Message"
    val expected = "Message\n"

    // Act
    underTest.log(msg)

    // Assert
    Assertions.assertEquals(expected, outContent.toString())
  }
}
