package net.royhome.tictactoe.model

import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(schema = "tictactoe")
class Game(
  @Id
  @Type(type = "pg-uuid")
  @Column(name = "game_id")
  var gameId: UUID,

  var state: Int,

  @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  val players: MutableSet<Player>,
) {
  override fun toString(): String {
    return "Game(gameId=$gameId, state=$state, players=$players)"
  }
}
