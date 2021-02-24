package net.royhome.api.model

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class ResumeEducation(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resume_education_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    val resume: Resume,

    val school: String = "",
    val degree: String = "",
    val graduation: String = "",
)
