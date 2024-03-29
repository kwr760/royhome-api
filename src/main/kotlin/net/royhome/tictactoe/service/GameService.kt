package net.royhome.tictactoe.service

import java.util.UUID
import net.royhome.tictactoe.constant.GameStateEnum
import net.royhome.tictactoe.model.Game
import net.royhome.tictactoe.model.Player
import net.royhome.tictactoe.repository.GameRepository
import net.royhome.tictactoe.repository.PlayerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameService(
  val gameRepository: GameRepository,
  val playerRepository: PlayerRepository,
  val toolkit: ToolkitService,
) {
  fun getGame(sessionId: UUID): Game? {
    val player = playerRepository.findBySessionId(sessionId)
    player?.let {
      return player.game
    }
    return null
  }

  @Transactional
  fun joinGame(sessionId: UUID, name: String): Game {
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

    val player = game.players.find { it.sessionId == sessionId }
    val opponent = game.players.find { it.sessionId !== sessionId }
    if (player == null) {
      if (opponent == null) {
        game.players.add(
          Player(
            sessionId = sessionId,
            name = name,
            game = game,
            piece = toolkit.getRandomPiece().name,
          )
        )
      } else {
        game.players.add(
          Player(
            sessionId = sessionId,
            name = name,
            game = game,
            piece = toolkit.getOtherPiece(opponent.piece).name
          )
        )
        game.state = GameStateEnum.Closed.value
      }
    }

    gameRepository.save(game)
    return game
  }

  fun updateGame(sessionId: UUID, board: String): Game? {
    val player = playerRepository.findBySessionId(sessionId)
    player?.let {
      val game = player.game
      game.board = board
      gameRepository.save(game)
      return game
    }
    return null
  }

  @Transactional
  fun endGame(sessionId: UUID): Game? {
    val player = playerRepository.findBySessionId(sessionId)
    player?.let {
      val game = player.game
      gameRepository.deleteByGameId(game.gameId)
      return game
    }
    return null
  }
}
