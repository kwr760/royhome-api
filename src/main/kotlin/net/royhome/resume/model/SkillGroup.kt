package net.royhome.resume.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.OrderBy
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(schema = "resume")
class SkillGroup(
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "skill_group_id")
  val id: UUID = UUID.randomUUID(),

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resume_id", nullable = true)
  @JsonBackReference
  val resume: Resume,
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "experience_id", nullable = true)
  @JsonBackReference
  val experience: Experience,

  val name: String = "",
  @OneToMany(mappedBy = "skillGroup", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val skills: List<Skill>,
)
