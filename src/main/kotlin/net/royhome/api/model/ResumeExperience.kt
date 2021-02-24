package net.royhome.api.model

import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.*

@Entity
class ResumeExperience(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resume_experience_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    val resume: Resume,

    val title: String? = null,
    val company: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @Where(clause = "TYPE = 'DESCRIPTION'")
    val description: Set<ResumeData>,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @Where(clause = "TYPE = 'BULLET'")
    val bullets: Set<ResumeData>,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @Where(clause = "TYPE = 'TECH'")
    val techs: Set<ResumeData>,
)
