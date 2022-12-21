package net.royhome.tictactoe.service

import net.royhome.tictactoe.model.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MessagingService {
  @Autowired
  lateinit var template: SimpMessagingTemplate

  fun send(sessionId: UUID, response: Response) {
    template.convertAndSend(
      "/session/$sessionId",
      response
    )
  }
}
