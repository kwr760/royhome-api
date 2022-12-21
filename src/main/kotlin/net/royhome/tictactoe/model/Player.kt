package net.royhome.tictactoe.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "tictactoe")
class Player(
  @Id
  @Type(type = "pg-uuid")
  @Column(name = "session_id")
  var sessionId: UUID,

  var name: String,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @JsonBackReference
  val game: Game,
) {
  override fun toString(): String {
    return "Player(sessionId=$sessionId, name=$name)"
  }
}
