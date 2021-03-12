package net.royhome.api.model.resume

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class Experience(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "experience_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    val resume: Resume,

    val title: String? = null,
    val company: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val description: Set<ExperienceDescription>,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val bullets: Set<ExperienceBullet>,
    @OneToOne(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val skills: SkillGroup,
)
