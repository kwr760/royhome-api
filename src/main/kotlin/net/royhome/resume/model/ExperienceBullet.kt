package net.royhome.resume.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(schema = "resume")
class ExperienceBullet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bullet_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experience_id")
    @JsonBackReference
    val experience: Experience,

    val name: String = "",
    @Column(name = "position")
    val position: Int? = null,
)
