package net.royhome.resume.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID
import org.hibernate.annotations.OrderBy

@Entity
@Table(schema = "resume")
class SkillGroup(
  @Id
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
  val skills: Set<Skill>,
)
