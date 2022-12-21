package net.royhome.tictactoe.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tictactoe.constant.Constants
import net.royhome.tictactoe.constant.GameActionEnum
import net.royhome.tictactoe.constant.GameStateEnum
import net.royhome.tictactoe.constant.ResultEnum
import net.royhome.tictactoe.model.EndAction
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Player
import net.royhome.tictactoe.model.Response
import net.royhome.tictactoe.model.Result
import net.royhome.tictactoe.model.StartAction
import net.royhome.tictactoe.model.TurnAction
import net.royhome.tictactoe.service.GameService
import net.royhome.tictactoe.service.MessagingService
import net.royhome.tool.service.ToolkitService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
  fun `gameController joins a game - first`() {
    // Arrange
    val playerSessionId = UUID.randomUUID()
    val playerName = "Player Name"
    val opponentSessionId = UUID.randomUUID()
    val action = StartAction(playerSessionId, playerName)
    val player = Player(
      playerSessionId,
      name = playerName,
      game = mockk()
    )
    val opponent = Player(
      opponentSessionId,
      name = "opponent",
      game = mockk()
    )
    val result = Result(
      ResultEnum.SUCCESS,
      player,
      opponent
    )
    every { gameServiceMock.joinGame(any(), any()) } returns result
    every { toolkitMock.getRandomBool() } returns true
    val expectedResponse = Response(ResultEnum.SUCCESS)
    val expectedStartPlayer = Response(
      ResultEnum.SUCCESS,
      GameActionEnum.TakeTurn,
      player,
      Constants.InitialBoard
    )

    // Act
    val response = underTest.startGame(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.joinGame(playerSessionId, playerName) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
    verify(exactly = 1) {
      messagingServiceMock.send(
        opponentSessionId,
        expectedStartPlayer
      )
    }
  }

  @Test
  fun `gameController joins a game - second`() {
    // Arrange
    val playerSessionId = UUID.randomUUID()
    val playerName = "Player Name"
    val opponentSessionId = UUID.randomUUID()
    val action = StartAction(playerSessionId, playerName)
    val player = Player(
      playerSessionId,
      name = playerName,
      game = mockk()
    )
    val opponent = Player(
      opponentSessionId,
      name = "opponent",
      game = mockk()
    )
    val result = Result(
      ResultEnum.SUCCESS,
      player,
      opponent
    )
    every { gameServiceMock.joinGame(any(), any()) } returns result
    every { toolkitMock.getRandomBool() } returns false
    val expectedResponse = Response(ResultEnum.SUCCESS)
    val expectedStartOpponent = Response(
      ResultEnum.SUCCESS,
      GameActionEnum.TakeTurn,
      opponent,
      Constants.InitialBoard
    )

    // Act
    val response = underTest.startGame(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.joinGame(playerSessionId, playerName) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
    verify(exactly = 1) {
      messagingServiceMock.send(
        playerSessionId,
        expectedStartOpponent
      )
    }
  }

  @Test
  fun `gameController joins a game - no opponent`() {
    // Arrange
    val playerSessionId = UUID.randomUUID()
    val playerName = "Player Name"
    val action = StartAction(playerSessionId, playerName)
    val player = Player(
      playerSessionId,
      name = playerName,
      game = mockk()
    )
    val result = Result(
      ResultEnum.SUCCESS,
      player,
    )
    every { gameServiceMock.joinGame(any(), any()) } returns result
    val expectedResponse = Response(ResultEnum.SUCCESS)

    // Act
    val response = underTest.startGame(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.joinGame(playerSessionId, playerName) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
  }

  @Test
  fun `gameController ends a game`() {
    // Arrange
    val playerSessionId = UUID.randomUUID()
    val action = EndAction(playerSessionId)
    val opponentSessionId = UUID.randomUUID()
    val player = Player(
      playerSessionId,
      name = "player",
      game = mockk()
    )
    val opponent = Player(
      opponentSessionId,
      name = "opponent",
      game = mockk()
    )
    val result = Result(
      ResultEnum.SUCCESS,
      player,
      opponent
    )
    every { gameServiceMock.endGame(any()) } returns result
    val expectedResponse = Response(ResultEnum.SUCCESS)
    val expectedEnd = Response(
      ResultEnum.SUCCESS,
      GameActionEnum.Closed,
    )

    // Act
    val response = underTest.endGame(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.endGame(playerSessionId) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
    verify(exactly = 1) { messagingServiceMock.send(opponentSessionId, expectedEnd) }
  }

  @Test
  fun `gameController takes a turn`() {
    // Arrange
    val gameId = UUID.randomUUID()
    val playerSessionId = UUID.randomUUID()
    val playerName = "Player Name"
    val board = Constants.InitialBoard
    val opponentSessionId = UUID.randomUUID()
    val action = TurnAction(playerSessionId, board)
    val player = Player(
      playerSessionId,
      name = playerName,
      game = mockk()
    )
    val opponent = Player(
      opponentSessionId,
      name = "opponent",
      game = mockk()
    )
    val game = Game(
      gameId,
      GameStateEnum.Closed.value,
      players = mutableSetOf(player, opponent)
    )
    every { gameServiceMock.getGame(any()) } returns game
    val expectedResponse = Response(ResultEnum.SUCCESS)
    val expectedTurn = Response(
      ResultEnum.SUCCESS,
      GameActionEnum.TakeTurn,
      opponent,
      Constants.InitialBoard
    )

    // Act
    val response = underTest.takeTurn(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.getGame(playerSessionId) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
    verify(exactly = 1) { messagingServiceMock.send(opponentSessionId, expectedTurn) }
  }

  @Test
  fun `gameController takes a turn opponent not found`() {
    // Arrange
    val gameId = UUID.randomUUID()
    val playerSessionId = UUID.randomUUID()
    val playerName = "Player Name"
    val board = Constants.InitialBoard
    val action = TurnAction(playerSessionId, board)
    val player = Player(
      playerSessionId,
      name = playerName,
      game = mockk()
    )
    val game = Game(
      gameId,
      GameStateEnum.Closed.value,
      players = mutableSetOf(player)
    )
    every { gameServiceMock.getGame(any()) } returns game
    val expectedResponse = Response(ResultEnum.FAILURE)

    // Act
    val response = underTest.takeTurn(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.getGame(playerSessionId) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
  }

  @Test
  fun `gameController takes a turn but game does not exist`() {
    // Arrange
    val playerSessionId = UUID.randomUUID()
    val board = Constants.InitialBoard
    val action = TurnAction(playerSessionId, board)
    every { gameServiceMock.getGame(any()) } returns null
    val expectedResponse = Response(ResultEnum.FAILURE)

    // Act
    val response = underTest.takeTurn(action)

    // Assert
    Assertions.assertEquals(Unit, response)
    verify(exactly = 1) { gameServiceMock.getGame(playerSessionId) }
    verify(exactly = 1) { messagingServiceMock.send(playerSessionId, expectedResponse) }
  }
}
