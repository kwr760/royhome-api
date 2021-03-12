package net.royhome.api.model.resume

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class SkillGroup(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skill_group_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id", nullable = true)
    val resume: Resume,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experience_id", nullable = true)
    val experience: Experience,

    val name: String = "",
    @OneToMany(mappedBy = "skillGroup", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val skills: List<Skill>,
)
