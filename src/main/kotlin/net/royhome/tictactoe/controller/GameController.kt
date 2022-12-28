package net.royhome.tictactoe.controller

import net.royhome.tictactoe.constant.GameActionEnum
import net.royhome.tictactoe.constant.PieceEnum
import net.royhome.tictactoe.model.Message
import net.royhome.tictactoe.model.action.EndAction
import net.royhome.tictactoe.model.action.PlayAction
import net.royhome.tictactoe.model.action.StartAction
import net.royhome.tictactoe.service.GameService
import net.royhome.tictactoe.service.MessagingService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class GameController(
  val gameService: GameService,
  val msgService: MessagingService,
) {
  @MessageMapping("/start")
  fun startGame(action: StartAction) {
    val game = gameService.joinGame(action.sessionId, action.name)

    when (game.players.count()) {
      1 -> {
        msgService.send(
          action.sessionId,
          Message(
            GameActionEnum.Wait,
          )
        )
      }

      2 -> {
        val players = game.players
        val first = players.find { it.piece == PieceEnum.X.name }
        val opponent = players.find { it.piece == PieceEnum.O.name }
        first?.let {
          msgService.send(
            it.sessionId,
            Message(
              action = GameActionEnum.TakeTurn,
              game
            )
          )
        }
        opponent?.let {
          msgService.send(
            it.sessionId,
            Message(
              action = GameActionEnum.Wait,
              game
            )
          )
        }
      }

      else -> {
        throw IndexOutOfBoundsException("Received an unexpected number of players")
      }
    }
  }

  @MessageMapping("/end")
  fun endGame(action: EndAction) {
    val game = gameService.endGame(action.sessionId)
    game?.let {
      game.players.forEach {
        val message = Message(
          GameActionEnum.EndGame,
          game,
          action.reason
        )
        msgService.send(it.sessionId, message)
      }
    }
  }

  @MessageMapping("/turn")
  fun takeTurn(action: PlayAction) {
    val game = gameService.updateGame(action.sessionId, action.board)
    game?.let {
      val opponent = game.players.find { it.sessionId != action.sessionId }
      opponent?.let {
        msgService.send(
          opponent.sessionId,
          Message(
            action = GameActionEnum.TakeTurn,
            game
          )
        )
      }
    }
  }
}
