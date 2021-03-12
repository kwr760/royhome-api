package net.royhome.api.model.resume

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
        val skillGroups: Set<SkillGroup>,
        @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val education: Set<Education>,
        @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val experience: Set<Experience>,
)
