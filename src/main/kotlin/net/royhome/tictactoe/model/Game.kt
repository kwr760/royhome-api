package net.royhome.tictactoe.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID
import net.royhome.tictactoe.constant.Constants

@Entity
@Table(schema = "tictactoe")
class Game(
  @Id
  @Column(name = "game_id")
  var gameId: UUID,

  var state: Int,

  var board: String = Constants.InitialBoard,

  @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  val players: MutableSet<Player>,
) {
  override fun toString(): String {
    return "Game(gameId=$gameId, state=$state, players=$players)"
  }
}
