package net.royhome.resume.repository

import net.royhome.resume.model.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResumeRepository : JpaRepository<Resume, Long> {
    fun findByEmail(email: String): Resume
}
