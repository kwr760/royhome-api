package net.royhome.tictactoe.repository

import java.util.UUID
import net.royhome.tictactoe.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface PlayerRepository : JpaRepository<Player, Long> {
  fun findBySessionId(sessionId: UUID): Player?

  @Modifying
  @Transactional
  @Query(
    "DELETE FROM tictactoe.player WHERE player.game_id IN " +
      "(SELECT game.game_id FROM tictactoe.game WHERE game.modified < now() - interval '1 hour')",
    nativeQuery = true
  )
  fun deleteWhenAnHourOld(): Unit
}
