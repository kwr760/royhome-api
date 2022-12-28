package net.royhome.api.model

import java.sql.Timestamp
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import org.hibernate.annotations.Type

@Entity
@Table(schema = "session")
class Session(
  @Id
  @Type(type = "pg-uuid")
  val sessionId: UUID,
  @Type(type = "pg-uuid")
  val browserId: UUID,
  @Type(type = "timestamp")
  var expiration: Timestamp? = null,
  var darkMode: String? = null,
  @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = true)
  var user: User? = null,
)
