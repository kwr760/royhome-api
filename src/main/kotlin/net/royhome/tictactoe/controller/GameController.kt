package net.royhome.tictactoe.controller

import net.royhome.tictactoe.constant.Constants
import net.royhome.tictactoe.constant.GameActionEnum
import net.royhome.tictactoe.constant.ResultEnum
import net.royhome.tictactoe.model.EndAction
import net.royhome.tictactoe.model.Response
import net.royhome.tictactoe.model.StartAction
import net.royhome.tictactoe.model.TurnAction
import net.royhome.tictactoe.service.GameService
import net.royhome.tictactoe.service.MessagingService
import net.royhome.tool.service.ToolkitService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class GameController(
  val gameService: GameService,
  val msgService: MessagingService,
  val toolkit: ToolkitService,
) {

  @MessageMapping("/start")
  fun startGame(action: StartAction) {
    val result = gameService.joinGame(action.sessionId, action.name)

    if (result.opponent !== null) {
      var player = result.player
      var opponent = result.opponent
      if (toolkit.getRandomBool()) {
        player = result.opponent
        opponent = result.player
      }
      msgService.send(
        player!!.sessionId,
        Response(
          result = result.result,
          action = GameActionEnum.TakeTurn,
          board = Constants.InitialBoard,
          opponent = opponent
        )
      )
    }
    msgService.send(action.sessionId, Response(result.result))
  }

  @MessageMapping("/end")
  fun endGame(action: EndAction) {
    val result = gameService.endGame(action.sessionId)
    msgService.send(
      result.opponent!!.sessionId,
      Response(
        result.result,
        action = GameActionEnum.Closed
      )
    )
    msgService.send(action.sessionId, Response(result.result))
  }

  @MessageMapping("/turn")
  fun takeTurn(action: TurnAction) {
    val response = Response(ResultEnum.FAILURE)
    val game = gameService.getGame(action.sessionId)
    if (game !== null) {
      val opponent = game.players.find { it.sessionId != action.sessionId }
      if (opponent !== null) {
        msgService.send(
          opponent.sessionId,
          Response(
            result = ResultEnum.SUCCESS,
            action = GameActionEnum.TakeTurn,
            opponent = opponent,
            board = action.board
          )
        )
        response.result = ResultEnum.SUCCESS
      }
    }
    msgService.send(action.sessionId, response)
  }
}
