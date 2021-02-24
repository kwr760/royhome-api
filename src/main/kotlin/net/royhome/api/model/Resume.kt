package net.royhome.api.model

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
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
        val skillGroups: Set<ResumeSkills>,
        @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val education: Set<ResumeEducation>,
        @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val experience: Set<ResumeExperience>,
)
