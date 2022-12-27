package net.royhome.tictactoe.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID
import net.royhome.tictactoe.constant.Constants
import net.royhome.tictactoe.constant.GameStateEnum
import net.royhome.tictactoe.constant.PieceEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Player
import net.royhome.tictactoe.repository.GameRepository
import net.royhome.tictactoe.repository.PlayerRepository
import net.royhome.tool.service.ToolkitService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameServiceTests {
  private lateinit var gameRepositoryMock: GameRepository
  private lateinit var playerRepositoryMock: PlayerRepository
  private lateinit var toolkitServiceMock: ToolkitService
  private lateinit var underTest: GameService

  @BeforeEach
  fun setUpBeforeEach() {
    gameRepositoryMock = mockk(relaxed = true)
    playerRepositoryMock = mockk(relaxed = true)
    toolkitServiceMock = mockk(relaxed = true)
    underTest = GameService(gameRepositoryMock, playerRepositoryMock, toolkitServiceMock)
  }

  @Test
  fun `getGame`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val expectedGame = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
    val player = Player(sessionId, name, PieceEnum.X.name, expectedGame)
    every { playerRepositoryMock.findBySessionId(any()) } returns player

    // Act
    val response = underTest.getGame(sessionId)

    // Assert
    Assertions.assertEquals(response, expectedGame)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
  }

  @Test
  fun `getGame - no player`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    every { playerRepositoryMock.findBySessionId(any()) } returns null

    // Act
    val response = underTest.getGame(sessionId)

    // Assert
    Assertions.assertNull(response)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 0) { gameRepositoryMock.findByGameId(any()) }
  }

  @Test
  fun `endGame`() {
    // Arrange
    val playerId: UUID = UUID.randomUUID()
    val opponentId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Closed.value, Constants.InitialBoard, players)
    val player = Player(playerId, "Player Name", PieceEnum.X.name, game)
    val opponent = Player(opponentId, "Opponent Name", PieceEnum.O.name, game)
    game.players.addAll(listOf(player, opponent))
    val expectedResult = Game(gameId, GameStateEnum.Closed.value, Constants.InitialBoard, mutableSetOf(player, opponent))
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.findByGameId(any()) } returns game
    every { gameRepositoryMock.deleteByGameId(any()) } returns Unit

    // Act
    val response = underTest.endGame(playerId)

    // Assert
    Assertions.assertEquals(expectedResult.toString(), response.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(playerId) }
    verify(exactly = 1) { gameRepositoryMock.deleteByGameId(gameId) }
  }

  @Test
  fun `endGame - no player`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    every { playerRepositoryMock.findBySessionId(any()) } returns null

    // Act
    val result = underTest.endGame(sessionId)

    // Assert
    Assertions.assertNull(result)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 0) { gameRepositoryMock.findByGameId(any()) }
  }

  @Test
  fun `joinGame - existing game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "Player Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
    val player = Player(sessionId, name, PieceEnum.X.name, game)
    players.add(player)
    val expectedResult = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, mutableSetOf(player))
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.findByEarliestState(any()) } returns null
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.joinGame(sessionId, name)

    // Assert
    Assertions.assertEquals(expectedResult.state, response.state)
    Assertions.assertEquals(expectedResult.board, response.board)
    Assertions.assertEquals(expectedResult.players.toString(), response.players.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 0) { gameRepositoryMock.findByEarliestState(GameStateEnum.Open.value) }
    verify(exactly = 1) { gameRepositoryMock.save(any()) }
  }

  @Test
  fun `joinGame - new game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "Player Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
    val player = Player(sessionId, name, PieceEnum.X.name, game)
    players.add(player)
    val expectedResult = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, mutableSetOf(player))
    every { playerRepositoryMock.findBySessionId(any()) } returns null
    every { gameRepositoryMock.findByEarliestState(any()) } returns null
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.joinGame(sessionId, name)

    // Assert
    Assertions.assertEquals(expectedResult.state, response.state)
    Assertions.assertEquals(expectedResult.board, response.board)
    Assertions.assertEquals(expectedResult.players.toString(), response.players.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 1) { gameRepositoryMock.findByEarliestState(GameStateEnum.Open.value) }
    verify(exactly = 1) { gameRepositoryMock.save(any()) }
  }

  @Test
  fun `joinGame - earliest game - no opponent`() {
    // Arrange
    val playerId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
    val player = Player(playerId, name, PieceEnum.X.name, game)
    val expectedResult = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, mutableSetOf(player))
    every { playerRepositoryMock.findBySessionId(any()) } returns null
    every { gameRepositoryMock.findByEarliestState(any()) } returns game
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.joinGame(playerId, name)

    // Assert
    Assertions.assertEquals(expectedResult.toString(), response.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(playerId) }
    verify(exactly = 1) { gameRepositoryMock.findByEarliestState(GameStateEnum.Open.value) }
    verify(exactly = 1) { gameRepositoryMock.save(game) }
  }

  @Test
  fun `joinGame - existing game - opponent already existed`() {
    // Arrange
    val playerId: UUID = UUID.randomUUID()
    val opponentId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val playerName = "Player Name"
    val opponentName = "Opponent Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Closed.value, Constants.InitialBoard, players)
    val player = Player(playerId, playerName, PieceEnum.X.name, game)
    val opponent = Player(opponentId, opponentName, PieceEnum.O.name, game)
    players.add(opponent)
    val expectedResult = Game(gameId, GameStateEnum.Closed.value, Constants.InitialBoard, mutableSetOf(opponent, player))
    every { playerRepositoryMock.findBySessionId(any()) } returns null
    every { gameRepositoryMock.findByEarliestState(any()) } returns game
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.joinGame(playerId, playerName)

    // Assert
    Assertions.assertEquals(expectedResult.toString(), response.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(playerId) }
    verify(exactly = 1) { gameRepositoryMock.findByEarliestState(GameStateEnum.Open.value) }
    verify(exactly = 1) { gameRepositoryMock.save(game) }
  }

  @Test
  fun `updateGame - found game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "Player Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
    val player = Player(sessionId, name, PieceEnum.X.name, game)
    players.add(player)
    val expectedResult = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, mutableSetOf(player))
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.updateGame(sessionId, "XO---X---")

    // Assert
    Assertions.assertEquals(expectedResult.toString(), response.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 1) { gameRepositoryMock.save(any()) }
  }

  @Test
  fun `updateGame - no game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "Player Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, Constants.InitialBoard, players)
    val player = Player(sessionId, name, PieceEnum.X.name, game)
    players.add(player)
    every { playerRepositoryMock.findBySessionId(any()) } returns null
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val result = underTest.updateGame(sessionId, "XO---X---")

    // Assert
    Assertions.assertNull(result)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 0) { gameRepositoryMock.save(any()) }
  }
}
