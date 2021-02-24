package net.royhome.api.model

import org.hibernate.annotations.Type
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.*

@Entity
class ResumeSkills(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resume_skills_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    val resume: Resume,

    val name: String = "",
    @OneToMany(mappedBy = "skills", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @Where(clause = "TYPE = 'SKILL'")
    val skills: List<ResumeData>,
)
