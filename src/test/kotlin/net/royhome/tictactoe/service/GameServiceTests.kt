package net.royhome.tictactoe.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tictactoe.constant.GameStateEnum
import net.royhome.tictactoe.constant.ResultEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Player
import net.royhome.tictactoe.model.Result
import net.royhome.tictactoe.repository.GameRepository
import net.royhome.tictactoe.repository.PlayerRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class GameServiceTests {
  private lateinit var gameRepositoryMock: GameRepository
  private lateinit var playerRepositoryMock: PlayerRepository
  private lateinit var underTest: GameService

  @BeforeEach
  fun setUpBeforeEach() {
    gameRepositoryMock = mockk(relaxed = true)
    playerRepositoryMock = mockk(relaxed = true)
    underTest = GameService(gameRepositoryMock, playerRepositoryMock)
  }

  @Test
  fun `get a game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val expectedGame = Game(gameId, GameStateEnum.Open.value, players)
    val player = Player(sessionId, name, expectedGame)
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.findByGameId(any()) } returns expectedGame

    // Act
    val response = underTest.getGame(sessionId)

    // Assert
    Assertions.assertEquals(response, expectedGame)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 1) { gameRepositoryMock.findByGameId(gameId) }
  }

  @Test
  fun `get a game - no player`() {
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
  fun `end a game`() {
    // Arrange
    val playerId: UUID = UUID.randomUUID()
    val opponentId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Closed.value, players)
    val player = Player(playerId, "Player Name", game)
    val opponent = Player(opponentId, "Opponent Name", game)
    game.players.addAll(listOf(player, opponent))
    val expectedResult = Result(ResultEnum.SUCCESS, player = player, opponent = opponent)
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.findByGameId(any()) } returns game

    // Act
    val response = underTest.endGame(playerId)

    // Assert
    Assertions.assertEquals(expectedResult, response)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(playerId) }
    verify(exactly = 1) { gameRepositoryMock.findByGameId(gameId) }
  }

  @Test
  fun `end a game - opponent`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Closed.value, players)
    val player = Player(sessionId, name, game)
    val expectedResult = Result(ResultEnum.SUCCESS, player = player)
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.findByGameId(any()) } returns game

    // Act
    val response = underTest.endGame(sessionId)

    // Assert
    Assertions.assertEquals(expectedResult, response)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 1) { gameRepositoryMock.findByGameId(gameId) }
  }

  @Test
  fun `end a game - no player`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val expectedResult = Result(ResultEnum.FAILURE)
    every { playerRepositoryMock.findBySessionId(any()) } returns null

    // Act
    val response = underTest.endGame(sessionId)

    // Assert
    Assertions.assertEquals(expectedResult, response)
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 0) { gameRepositoryMock.findByGameId(any()) }
  }

  @Test
  fun `join a game - existing game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val opponentId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, players)
    val player = Player(sessionId, name, game)
    val opponent = Player(opponentId, "Opponent Name", game)
    game.players.addAll(listOf(opponent, player))
    val expectedResult = Result(ResultEnum.SUCCESS, opponent = opponent, player = player)
    every { playerRepositoryMock.findBySessionId(any()) } returns player
    every { gameRepositoryMock.findByGameId(any()) } returns game
    every { gameRepositoryMock.findByEarliestState(any()) } returns game
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.joinGame(sessionId, name)

    // Assert
    Assertions.assertEquals(expectedResult.toString(), response.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 1) { gameRepositoryMock.findByGameId(gameId) }
    verify(exactly = 1) { gameRepositoryMock.save(game) }
  }

  @Test
  fun `join a game - open game`() {
    // Arrange
    val playerId: UUID = UUID.randomUUID()
    val opponentId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, players)
    val player = Player(playerId, name, game)
    val opponent = Player(opponentId, "Opponent Name", game)
    game.players.add(opponent)
    val expectedResult = Result(ResultEnum.SUCCESS, opponent = opponent, player = player)
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
  fun `join a game - open game - player - no opponent`() {
    // Arrange
    val playerId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, players)
    val player = Player(playerId, name, game)
    game.players.add(player)
    val expectedResult = Result(ResultEnum.SUCCESS, player = player)
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
  fun `join a game - new game`() {
    // Arrange
    val sessionId: UUID = UUID.randomUUID()
    val gameId: UUID = UUID.randomUUID()
    val name = "PLayer Name"
    val players = mutableSetOf<Player>()
    val game = Game(gameId, GameStateEnum.Open.value, players)
    val player = Player(sessionId, name, game)
    val expectedResult = Result(ResultEnum.SUCCESS, player = player)
    every { playerRepositoryMock.findBySessionId(any()) } returns null
    every { gameRepositoryMock.findByEarliestState(any()) } returns null
    every { gameRepositoryMock.save(any()) } returns game

    // Act
    val response = underTest.joinGame(sessionId, name)

    // Assert
    Assertions.assertEquals(expectedResult.toString(), response.toString())
    verify(exactly = 1) { playerRepositoryMock.findBySessionId(sessionId) }
    verify(exactly = 1) { gameRepositoryMock.findByEarliestState(GameStateEnum.Open.value) }
    verify(exactly = 1) { gameRepositoryMock.save(any()) }
  }
}
