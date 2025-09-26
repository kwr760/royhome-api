package net.royhome.tictactoe.controller

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
import net.royhome.tictactoe.model.action.EndAction
import net.royhome.tictactoe.model.action.PlayAction
import net.royhome.tictactoe.model.action.StartAction
import net.royhome.tictactoe.service.GameService
import net.royhome.tictactoe.service.MessagingService
import net.royhome.tictactoe.service.ToolkitService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GameControllerTests {
    private lateinit var underTest: GameController
    private lateinit var gameServiceMock: GameService
    private lateinit var messagingServiceMock: MessagingService
    private lateinit var toolkitMock: ToolkitService

    @BeforeEach
    fun setUpBeforeEach() {
        gameServiceMock = mockk(relaxed = true)
        messagingServiceMock = mockk(relaxed = true)
        toolkitMock = mockk(relaxed = true)
        underTest = GameController(gameServiceMock, messagingServiceMock, toolkitMock)
    }

    @Test
    fun `gameController start a game - exception`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerName = "Player Name"
        val action = StartAction(UUID.randomUUID(), playerName)
        val game = Game(
            gameId,
            GameStateEnum.Open.value,
            Constants.InitialBoard,
            mutableSetOf()
        )

        every { gameServiceMock.joinGame(any(), any()) } returns game

        // Act // Assert
        assertThrows<RuntimeException> {
            underTest.startGame(action)
        }
    }

    @Test
    fun `gameController start a game - wrong pieces`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerSessionId = UUID.randomUUID()
        val playerName = "Player Name"
        val opponentSessionId = UUID.randomUUID()
        val action = StartAction(playerSessionId, playerName)
        val game = Game(
            gameId,
            GameStateEnum.Open.value,
            Constants.InitialBoard,
            mutableSetOf()
        )
        val player = Player(
            playerSessionId,
            name = playerName,
            piece = "A",
            game = game
        )
        val opponent = Player(
            opponentSessionId,
            name = "opponent",
            piece = "B",
            game = game
        )
        game.players.add(player)
        game.players.add(opponent)

        every { gameServiceMock.joinGame(any(), any()) } returns game
        val expectedResponse = Message(MessageActionEnum.TakeTurn, game)
        val expectedStartOpponent = Message(
            MessageActionEnum.TakeTurn,
            game
        )

        // Act
        val response = underTest.startGame(action)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { gameServiceMock.joinGame(playerSessionId, playerName) }
        verify(exactly = 0) { messagingServiceMock.send(expectedResponse, playerSessionId) }
        verify(exactly = 0) {
            messagingServiceMock.send(
                expectedStartOpponent,
                playerSessionId
            )
        }
    }

    @Test
    fun `gameController start a game - ready`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerSessionId = UUID.randomUUID()
        val playerName = "Player Name"
        val opponentSessionId = UUID.randomUUID()
        val action = StartAction(playerSessionId, playerName)
        val game = Game(
            gameId,
            GameStateEnum.Open.value,
            Constants.InitialBoard,
            mutableSetOf()
        )
        val player = Player(
            playerSessionId,
            name = playerName,
            piece = PieceEnum.X.name,
            game = game
        )
        val opponent = Player(
            opponentSessionId,
            name = "opponent",
            piece = PieceEnum.O.name,
            game = game
        )
        game.players.add(player)
        game.players.add(opponent)

        every { toolkitMock.getNextPlayer(any()) } returns PieceEnum.X
        every { gameServiceMock.joinGame(any(), any()) } returns game

        // Act
        val response = underTest.startGame(action)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { gameServiceMock.joinGame(playerSessionId, playerName) }
        verify(exactly = 1) {
            messagingServiceMock.send(
                Message(
                    MessageActionEnum.SetPlayers,
                    game
                ),
                game.players.toList(),
            )
        }
        verify(exactly = 1) {
            messagingServiceMock.send(
                Message(
                    MessageActionEnum.TakeTurn,
                    game
                ),
                listOf(player)
            )
        }
    }

    @Test
    fun `gameController start a game - wait`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerSessionId = UUID.randomUUID()
        val playerName = "Player Name"
        val action = StartAction(playerSessionId, playerName)
        val player = Player(
            playerSessionId,
            name = playerName,
            piece = PieceEnum.X.name,
            game = mockk()
        )
        val game = Game(
            gameId,
            GameStateEnum.Open.value,
            Constants.InitialBoard,
            mutableSetOf(player)
        )
        every { gameServiceMock.joinGame(any(), any()) } returns game

        // Act
        val response = underTest.startGame(action)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { gameServiceMock.joinGame(playerSessionId, playerName) }
        verify(exactly = 0) { messagingServiceMock.send(any(), any<List<Player>>()) }
    }

    @Test
    fun `gameController ends a game`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerSessionId = UUID.randomUUID()
        val action = EndAction(playerSessionId, "Win")
        val opponentSessionId = UUID.randomUUID()
        val player = Player(
            playerSessionId,
            name = "player",
            piece = PieceEnum.X.name,
            game = mockk()
        )
        val opponent = Player(
            opponentSessionId,
            name = "opponent",
            piece = PieceEnum.X.name,
            game = mockk()
        )
        val game = Game(
            gameId,
            GameStateEnum.Open.value,
            Constants.InitialBoard,
            mutableSetOf(player, opponent)
        )
        val expectedMessage = Message(
            MessageActionEnum.EndGame,
            game,
            action.reason
        )
        every { gameServiceMock.endGame(any()) } returns game

        // Act
        val response = underTest.endGame(action)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { gameServiceMock.endGame(playerSessionId) }
        verify(exactly = 1) { messagingServiceMock.send(expectedMessage, game.players.toList()) }
    }

    @Test
    fun `gameController takes a turn`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerSessionId = UUID.randomUUID()
        val playerName = "Player Name"
        val board = Constants.InitialBoard
        val opponentSessionId = UUID.randomUUID()
        val action = PlayAction(playerSessionId, board)
        val player = Player(
            playerSessionId,
            name = playerName,
            piece = PieceEnum.X.name,
            game = mockk()
        )
        val opponent = Player(
            opponentSessionId,
            name = "opponent",
            piece = PieceEnum.X.name,
            game = mockk()
        )
        val game = Game(
            gameId,
            GameStateEnum.Closed.value,
            Constants.InitialBoard,
            players = mutableSetOf(player, opponent)
        )
        every { gameServiceMock.updateGame(any(), any()) } returns game
        val expectedTurn = Message(
            MessageActionEnum.TakeTurn,
            game
        )

        // Act
        val response = underTest.takeTurn(action)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { gameServiceMock.updateGame(playerSessionId, board) }
        verify(exactly = 1) { messagingServiceMock.send(expectedTurn, any<List<Player>>()) }
    }

    @Test
    fun `gameController takes a turn opponent not found`() {
        // Arrange
        val gameId = UUID.randomUUID()
        val playerSessionId = UUID.randomUUID()
        val playerName = "Player Name"
        val board = Constants.InitialBoard
        val action = PlayAction(playerSessionId, board)
        val player = Player(
            playerSessionId,
            name = playerName,
            piece = PieceEnum.X.name,
            game = mockk()
        )
        val game = Game(
            gameId,
            GameStateEnum.Closed.value,
            players = mutableSetOf(player)
        )
        every { gameServiceMock.updateGame(any(), any()) } returns game

        // Act
        val response = underTest.takeTurn(action)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { gameServiceMock.updateGame(playerSessionId, board) }
        verify(exactly = 1) { messagingServiceMock.send(any(), listOf()) }
    }

    @Test
    fun `gameController takes a turn but game does not exist`() {
        // Arrange
        val playerSessionId = UUID.randomUUID()
        val board = Constants.InitialBoard
        val action = PlayAction(playerSessionId, board)
        every { gameServiceMock.updateGame(any(), any()) } returns null

        // Act
        underTest.takeTurn(action)

        // Assert
        verify(exactly = 1) { gameServiceMock.updateGame(playerSessionId, board) }
        verify(exactly = 1) { messagingServiceMock.send(any(), listOf()) }
    }

    @Test
    fun `gameController ends a game but game does not exist`() {
        // Arrange
        val playerSessionId = UUID.randomUUID()
        val action = EndAction(playerSessionId, "win")
        every { gameServiceMock.endGame(any()) } returns null

        // Act
        underTest.endGame(action)

        // Assert
        verify(exactly = 1) { gameServiceMock.endGame(playerSessionId) }
        verify(exactly = 1) {
            messagingServiceMock.send(
                Message(
                    MessageActionEnum.EndGame,
                    null,
                    "win"
                ),
                any<List<Player>>()
            )
        }
    }
}
