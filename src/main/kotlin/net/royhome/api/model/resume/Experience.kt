package net.royhome.api.model.resume

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.OrderBy
import org.hibernate.annotations.Type
import java.util.*
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

@Entity
@Suppress("LongParameterList")
class Experience(
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "experience_id")
  val id: UUID = UUID.randomUUID(),

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "resume_id")
  @JsonBackReference
  val resume: Resume,

  val title: String? = null,
  val company: String? = null,
  val startDate: String? = null,
  val endDate: String? = null,
  @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val description: Set<ExperienceDescription>,
  @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val bullets: Set<ExperienceBullet>,
  @OneToOne(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val skills: SkillGroup,
)
