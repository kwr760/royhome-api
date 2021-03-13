package net.royhome.api.service

import net.royhome.api.model.resume.Resume
import net.royhome.api.repository.ResumeRepository
import org.springframework.stereotype.Service

@Service
class ResumeService(
  val repository: ResumeRepository
) {
  fun getResume(): Resume {
    return repository.findByEmail("kroy760@gmail.com")
  }
}
