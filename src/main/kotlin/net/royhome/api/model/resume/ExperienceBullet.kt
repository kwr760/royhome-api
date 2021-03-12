package net.royhome.api.model.resume

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class ExperienceBullet(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bullet_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experience_id")
    val experience: Experience,

    val name: String = "",
)
