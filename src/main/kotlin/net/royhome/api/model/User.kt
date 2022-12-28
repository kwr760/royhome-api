package net.royhome.api.model

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import org.hibernate.annotations.Type

@Entity
@Table(schema = "session")
class User(
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  var userId: UUID? = null,

  val email: String,
  val context: String? = null,
)
