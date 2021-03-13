package net.royhome.api.repository

import net.royhome.api.model.resume.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResumeRepository : JpaRepository<Resume, Long> {
    fun findByEmail(email: String): Resume
}