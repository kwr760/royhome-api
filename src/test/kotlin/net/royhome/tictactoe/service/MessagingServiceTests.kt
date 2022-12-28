package net.royhome.tictactoe.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID
import net.royhome.tictactoe.constant.GameActionEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Message
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.messaging.simp.SimpMessagingTemplate

class MessagingServiceTests {
  private lateinit var underTest: MessagingService
  private var template: SimpMessagingTemplate = mockk()

  @BeforeEach
  fun setUpBeforeEach() {
    underTest = MessagingService()
    underTest.template = template
  }

  @Test
  fun `messagingService sends a message`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    every { template.convertAndSend(any(), any<Game>()) } returns Unit
    val dest = "/session/$sessionId"
    val serviceResponse = Message(GameActionEnum.TakeTurn)

    // Act
    val response = underTest.send(sessionId, serviceResponse)

    // Assert
    Assertions.assertEquals(response, Unit)
    verify(exactly = 1) { template.convertAndSend(dest, serviceResponse) }
  }
}
