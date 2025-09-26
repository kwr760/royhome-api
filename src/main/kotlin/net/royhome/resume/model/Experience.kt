package net.royhome.resume.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.OrderBy
import java.util.UUID

@Entity
@Table(schema = "resume")
@Suppress("LongParameterList")
class Experience(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "experience_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    @JsonBackReference
    val resume: Resume,

    val title: String? = null,
    val company: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderBy(clause = "position")
    val description: Set<ExperienceDescription>,
    @OneToMany(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderBy(clause = "position")
    val bullets: Set<ExperienceBullet>,
    @OneToOne(mappedBy = "experience", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @OrderBy(clause = "position")
    val tech: SkillGroup,
)
