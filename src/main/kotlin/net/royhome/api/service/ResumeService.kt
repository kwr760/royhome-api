package net.royhome.api.service

import net.royhome.api.model.Resume
import net.royhome.api.repository.ResumeRepository
import org.springframework.stereotype.Service

@Service
class ResumeService(
  val repository: ResumeRepository
) {
  fun getResume(): List<Resume>{
    return repository.findAll()
  }
}
