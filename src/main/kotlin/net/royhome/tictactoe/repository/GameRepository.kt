package net.royhome.tictactoe.repository

import java.util.UUID
import net.royhome.tictactoe.model.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface GameRepository : JpaRepository<Game, Long> {
  fun deleteByGameId(gameId: UUID)
  fun findByGameId(gameId: UUID): Game

  @Query(
    "SELECT game.* FROM tictactoe.game WHERE game.state = 0" +
      " GROUP BY game.game_id HAVING MIN(game.created) = game.created",
    nativeQuery = true
  )
  fun findByEarliestState(state: Int): Game?

  @Modifying
  @Transactional
  @Query(
    "DELETE FROM tictactoe.game WHERE game.modified < now() - interval '1 hour'",
    nativeQuery = true
  )
  fun deleteWhenAnHourOld(): Unit
}
