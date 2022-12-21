package net.royhome.tictactoe.repository

import net.royhome.tictactoe.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlayerRepository : JpaRepository<Player, Long> {
  fun findBySessionId(sessionId: UUID): Player?
}
