package net.royhome.api.model.access

import org.hibernate.annotations.Type
import java.sql.Timestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "access")
class AccessLog (
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "access_id")
  val id: UUID = UUID.randomUUID(),

  @Type(type = "pg-uuid")
  val browserId: UUID = UUID.randomUUID(),
  @Type(type = "pg-uuid")
  val sessionId: UUID = UUID.randomUUID(),
  val browserInfo: String = "",
  @Type(type = "pg-uuid")
  val userId: UUID?,
  val token: String?,
  @Type(type = "timestamp")
  val createTs: Timestamp? = null,
  @Type(type = "timestamp")
  val lastTs: Timestamp? = null,
  val reloadTimes: Int = 1,
)

