package net.royhome.tictactoe.service

import net.royhome.tictactoe.model.Message
import net.royhome.tictactoe.model.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MessagingService {
    @Autowired
    lateinit var template: SimpMessagingTemplate

    fun send(message: Message, players: List<Player>) {
        for (player in players) {
            send(message, player.sessionId)
        }
    }

    fun send(message: Message, sessionId: UUID) {
        template.convertAndSend(
            "/session/$sessionId",
            message
        )
    }
}
