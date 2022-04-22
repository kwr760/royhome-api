package net.royhome.tictactoe.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tictactoe.model.Game
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.messaging.simp.SimpMessagingTemplate

class GameControllerTests {
  private lateinit var underTest: GameController
  private var template: SimpMessagingTemplate = mockk()

  @BeforeEach
  fun setUpBeforeEach() {
    underTest = GameController()
    underTest.template = this.template
  }

  @Test
  fun `gameController accepts request and returns response`() {
    // Arrange - input
    val sessionId = "uuid"
    // Arrange - mocks
    every { template.convertAndSend(any<String>(), any<Game>()) } returns Unit
    // Arrange - response
    val dest = "/game/$sessionId"

    // Act
    val response = underTest.connect(sessionId)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { template.convertAndSend(dest, Game(sessionId)) }
  }
}
