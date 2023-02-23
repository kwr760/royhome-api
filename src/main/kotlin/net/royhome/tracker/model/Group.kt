package net.royhome.tracker.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(schema = "tracker")
class Group(
  @Id
  @Column(name = "group_id")
  var groupId: UUID? = null,

  var userId: UUID? = null,

  var name: String? = null,

  @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  val activities: MutableSet<Activity>? = null,
)
