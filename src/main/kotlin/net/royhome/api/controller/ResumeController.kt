package net.royhome.api.controller

import net.royhome.api.model.api.Response
import net.royhome.api.model.api.Result
import net.royhome.api.model.resume.Resume
import net.royhome.api.service.ResumeService
import org.springframework.dao.DataAccessException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ResumeController(
  val service: ResumeService
) {
  @GetMapping("resume/{email}")
  fun getResume(@PathVariable("email") email: String): Response<Resume> {
    return try {
      val resume = service.getResume(email)
      Response(resume, Result(true, 0, "Success"))
    } catch (e: DataAccessException) {
      val error = e.message.toString()
      Response(null, Result(false, 1, error))
    }
  }
}
