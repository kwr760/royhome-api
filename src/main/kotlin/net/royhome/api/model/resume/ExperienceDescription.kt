package net.royhome.api.model.resume

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class ExperienceDescription(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "description_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experience_id", insertable = false, updatable = false)
    val experience: Experience,

    val name: String = "",
)
