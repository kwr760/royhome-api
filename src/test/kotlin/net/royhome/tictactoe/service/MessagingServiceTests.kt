package net.royhome.tictactoe.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tictactoe.constant.Constants
import net.royhome.tictactoe.constant.GameStateEnum
import net.royhome.tictactoe.constant.MessageActionEnum
import net.royhome.tictactoe.constant.PieceEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Message
import net.royhome.tictactoe.model.Player
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.UUID

class MessagingServiceTests {
    private lateinit var underTest: MessagingService
    private var template: SimpMessagingTemplate = mockk()

    @BeforeEach
    fun setUpBeforeEach() {
        underTest = MessagingService()
    }

    @Test
    fun `messagingService sends a message`() {
        // Arrange
        val sessionId: UUID = UUID.randomUUID()
        every { template.convertAndSend(any(), any<Game>()) } returns Unit
        val dest = "/session/$sessionId"
        val serviceResponse = Message(MessageActionEnum.TakeTurn)

        // Act
        underTest.template = template
        val response = underTest.send(serviceResponse, sessionId)

        // Assert
        Assertions.assertEquals(response, Unit)
        verify(exactly = 1) { template.convertAndSend(dest, serviceResponse) }
    }

    @Test
    fun `messagingService sends a message to both players`() {
        // Arrange
        val oneSessionId: UUID = UUID.randomUUID()
        val twoSessionId: UUID = UUID.randomUUID()
        val gameId: UUID = UUID.randomUUID()
        every { template.convertAndSend(any(), any<Game>()) } returns Unit
        val oneDest = "/session/$oneSessionId"
        val twoDest = "/session/$twoSessionId"
        val serviceResponse = Message(MessageActionEnum.TakeTurn)
        val players = mutableSetOf<Player>()
        val game = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
        val one = Player(oneSessionId, "Player #1", PieceEnum.X.name, game)
        val two = Player(twoSessionId, "Player #2", PieceEnum.O.name, game)
        players.add(one)
        players.add(two)

        // Act
        underTest.template = template
        val response = underTest.send(serviceResponse, players.toList())

        // Assert
        Assertions.assertEquals(response, Unit)
        verify(exactly = 1) { template.convertAndSend(oneDest, serviceResponse) }
        verify(exactly = 1) { template.convertAndSend(twoDest, serviceResponse) }
    }

    @Test
    fun `messagingService no template`() {
        // Arrange
        val sessionId: UUID = UUID.randomUUID()
        every { template.convertAndSend(any(), any<Game>()) } returns Unit
        val serviceResponse = Message(MessageActionEnum.TakeTurn)

        // Act
        assertThrows<UninitializedPropertyAccessException> {
            underTest.send(serviceResponse, sessionId)
        }
    }
}
