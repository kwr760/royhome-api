package net.royhome.api.model

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class ResumeData(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resume_data_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_skills_id")
    val skills: ResumeSkills,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_experience_id")
    val experience: ResumeExperience,

    val name: String = "",
    val type: String,
)
