package net.royhome.api.model.resume

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.*

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
