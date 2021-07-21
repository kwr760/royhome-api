package net.royhome.api.model.resume

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.OrderBy
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.*

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
