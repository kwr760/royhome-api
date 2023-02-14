package net.royhome.tictactoe.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(schema = "tictactoe")
class Player(
  @Id
  @Column(name = "session_id")
  var sessionId: UUID,

  var name: String,

  var piece: String,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @JsonBackReference
  val game: Game,
) {
  override fun toString(): String {
    return "Player(sessionId=$sessionId, name=$name)"
  }
}
