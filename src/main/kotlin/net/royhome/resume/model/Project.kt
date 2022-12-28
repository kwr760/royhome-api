package net.royhome.resume.model

import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import org.hibernate.annotations.Type

@Entity
@Table(schema = "resume")
@Suppress("LongParameterList")
class Project(
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "project_id")
  val id: UUID = UUID.randomUUID(),

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resume_id")
  @JsonBackReference
  val resume: Resume,

  val name: String = "",
  val url: String = "",
  val description: String = "",
  val startDate: String? = null,
  val endDate: String? = null,
)
