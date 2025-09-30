package net.royhome.resume.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderColumn
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(schema = "resume")
@Suppress("LongParameterList")
class Resume(
    @Id
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
    @OrderColumn(name = "position")
    val skillGroups: List<SkillGroup>,
    @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderColumn(name = "position")
    val education: List<Education>,
    @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderColumn(name = "position")
    val experience: List<Experience>,
    @OneToMany(mappedBy = "resume", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderColumn(name = "position")
    val project: List<Project>,
)
