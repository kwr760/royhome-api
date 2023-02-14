package net.royhome.api.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.sql.Timestamp
import java.util.UUID

@Entity
@Table(schema = "session")
class Session(
  @Id
  val sessionId: UUID,
  val browserId: UUID,
  var expiration: Timestamp? = null,
  var darkMode: String? = null,
  @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = true)
  var user: User? = null,
)
