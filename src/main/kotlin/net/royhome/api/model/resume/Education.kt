package net.royhome.api.model.resume

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class Education(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "education_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    val resume: Resume,

    val school: String = "",
    val degree: String = "",
    val graduation: String = "",
)
