package net.royhome.tictactoe.repository

import net.royhome.tictactoe.model.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GameRepository : JpaRepository<Game, Long> {
  fun deleteByGameId(gameId: UUID)
  fun findByGameId(gameId: UUID): Game

  @Query(
    "SELECT game.* FROM tictactoe.game GROUP BY game.game_id HAVING MIN(game.created) = game.created",
    nativeQuery = true
  )
  fun findByEarliestState(state: Int): Game?
}
