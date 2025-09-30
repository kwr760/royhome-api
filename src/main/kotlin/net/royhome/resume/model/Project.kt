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
@Suppress("LongParameterList")
class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resume_id")
    @JsonBackReference
    val resume: Resume,

    val name: String = "",
    val url: String = "",
    val description: String = "",
    val startDate: String? = null,
    val endDate: String? = null,
    @Column(name = "position")
    val position: Int? = null,
)
