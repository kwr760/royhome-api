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
class Education(
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "education_id")
  val id: UUID = UUID.randomUUID(),

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resume_id")
  @JsonBackReference
  val resume: Resume,

  val school: String = "",
  val degree: String = "",
  val graduation: String = "",
)
