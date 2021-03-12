package net.royhome.api.model.resume

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class Skill(
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skill_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    val skillGroup: SkillGroup,

    val name: String = "",
)
