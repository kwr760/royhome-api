package net.royhome.tictactoe.controller

import net.royhome.tictactoe.model.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class GameController {
  @Autowired
  lateinit var template: SimpMessagingTemplate

  @MessageMapping("/connect")
  fun connect(sessionId: String) {
    template.convertAndSend(
      "/game/$sessionId",
      Game(sessionId)
    )
  }
}
