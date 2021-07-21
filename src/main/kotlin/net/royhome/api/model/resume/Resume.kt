package net.royhome.api.model.resume

import org.hibernate.annotations.OrderBy
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "resume")
@Suppress("LongParameterList")
class Resume(
  @Id
  @Type(type = "pg-uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "resume_id")
  val id: UUID = UUID.randomUUID(),

  val name: String = "",
  val address: String = "",
  val phone: String = "",
  val displayPhone: Boolean = false,
  val email: String = "",
  val summary: String = "",
  @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val skillGroups: Set<SkillGroup>,
  @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val education: Set<Education>,
  @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val experience: Set<Experience>,
  @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @OrderBy(clause = "position")
  val project: Set<Project>,
)
