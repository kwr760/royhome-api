package net.royhome.tictactoe.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tictactoe.constant.ResultEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Response
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.UUID

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
    every { template.convertAndSend(any<String>(), any<Game>()) } returns Unit
    val dest = "/session/$sessionId"
    val serviceResponse = Response(ResultEnum.SUCCESS)

    // Act
    val response = underTest.send(sessionId, serviceResponse)

    // Assert
    Assertions.assertEquals(response, Unit)
    verify(exactly = 1) { template.convertAndSend(dest, serviceResponse) }
  }
}
