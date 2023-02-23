package net.royhome.tracker.model

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
@Table(schema = "tracker")
class Activity(
  @Id
  @Column(name = "activity_id")
  var activityId: UUID? = null,

  var platform: String = "",
  var activity: String = "",
  var progress: String = "",

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  @JsonBackReference
  val group: Group,
)
