package net.royhome.resume.repository

import net.royhome.resume.model.Resume
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ResumeRepository : JpaRepository<Resume, UUID> {
    fun findByEmail(email: String): Resume
}
