package net.royhome.tictactoe.service

import net.royhome.tictactoe.constant.GameStateEnum
import net.royhome.tictactoe.constant.ResultEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Player
import net.royhome.tictactoe.model.Result
import net.royhome.tictactoe.repository.GameRepository
import net.royhome.tictactoe.repository.PlayerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GameService(
  val gameRepository: GameRepository,
  val playerRepository: PlayerRepository
) {
  fun getGame(sessionId: UUID): Game? {
    val player = playerRepository.findBySessionId(sessionId)
    if (player !== null) {
      return gameRepository.findByGameId(player.game.gameId)
    }
    return null
  }

  @Transactional
  fun joinGame(sessionId: UUID, name: String): Result {
    val result = Result(result = ResultEnum.FAILURE)
    var game = getGame(sessionId)
    if (game === null) {
      game = gameRepository.findByEarliestState(GameStateEnum.Open.value)
    }
    if (game === null) {
      game = Game(
        gameId = UUID.randomUUID(),
        state = GameStateEnum.Open.value,
        players = mutableSetOf()
      )
    }

    result.opponent = game.players.find {
      it.sessionId != sessionId
    }
    result.player = game.players.find {
      it.sessionId == sessionId
    }
    if (result.player == null) {
      result.player = Player(
        sessionId = sessionId,
        name = name,
        game = game,
      )
      game.players.add(result.player!!)
    }

    if (game.players.count() == 2) {
      game.state = GameStateEnum.Closed.value
    }
    gameRepository.save(game)
    result.result = ResultEnum.SUCCESS
    return result
  }

  @Transactional
  fun endGame(sessionId: UUID): Result {
    val result = Result(ResultEnum.FAILURE)
    val player = playerRepository.findBySessionId(sessionId)
    result.player = player
    if (player !== null) {
      val game = gameRepository.findByGameId(player.game.gameId)
      val opponent = game.players.find { player != it }
      result.opponent = opponent
      gameRepository.deleteByGameId(game.gameId)
      result.result = ResultEnum.SUCCESS
    }
    return result
  }
}
