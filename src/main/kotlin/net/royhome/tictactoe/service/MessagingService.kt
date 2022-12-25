package net.royhome.tictactoe.service

import net.royhome.tictactoe.model.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MessagingService {
  @Autowired
  lateinit var template: SimpMessagingTemplate

  fun send(sessionId: UUID, response: Message) {
    template.convertAndSend(
      "/session/$sessionId",
      response
    )
  }
}
