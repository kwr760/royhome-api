package net.royhome.api.service

import net.royhome.api.model.db.resume.Resume
import net.royhome.api.repository.ResumeRepository
import org.springframework.stereotype.Service

@Service
class ResumeService(
  val repository: ResumeRepository
) {
  fun getResume(email: String): Resume {
    return repository.findByEmail(email)
  }
}
