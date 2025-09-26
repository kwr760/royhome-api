package net.royhome.tictactoe.controller

import net.royhome.tictactoe.constant.MessageActionEnum
import net.royhome.tictactoe.model.Message
import net.royhome.tictactoe.model.action.EndAction
import net.royhome.tictactoe.model.action.PlayAction
import net.royhome.tictactoe.model.action.StartAction
import net.royhome.tictactoe.service.GameService
import net.royhome.tictactoe.service.MessagingService
import net.royhome.tictactoe.service.ToolkitService
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
        val game = gameService.joinGame(action.sessionId, action.name)
        val nextPlayer = toolkit.getNextPlayer(game.board)

        when (game.players.count()) {
            1 -> { return }

            2 -> {
                msgService.send(
                    Message(
                        action = MessageActionEnum.SetPlayers,
                        game
                    ),
                    game.players.toList()
                )
                msgService.send(
                    Message(
                        action = MessageActionEnum.TakeTurn,
                        game
                    ),
                    game.players.filter { it.piece == nextPlayer.name }
                )
            }

            else -> {
                throw IndexOutOfBoundsException("Received an unexpected number of players")
            }
        }
    }

    @MessageMapping("/end")
    fun endGame(action: EndAction) {
        val game = gameService.endGame(action.sessionId)
        val players = if (game !== null) game.players else mutableSetOf()

        msgService.send(
            Message(
                action = MessageActionEnum.EndGame,
                game,
                action.reason
            ),
            players.toList()
        )
    }

    @MessageMapping("/turn")
    fun takeTurn(action: PlayAction) {
        val game = gameService.updateGame(action.sessionId, action.board)
        val players = if (game !== null) game.players else mutableSetOf()
        msgService.send(
            Message(
                action = MessageActionEnum.TakeTurn,
                game
            ),
            players.filter { it.sessionId != action.sessionId },
        )
    }
}
