package net.royhome.resume.service

import net.royhome.resume.model.Resume
import net.royhome.resume.repository.ResumeRepository
import org.springframework.stereotype.Service

@Service
class ResumeService(
    val repository: ResumeRepository
) {
    fun getResume(email: String): Resume {
        return repository.findByEmail(email)
    }
}
