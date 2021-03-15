package net.royhome.api.controller

import net.royhome.api.model.api.Request
import net.royhome.api.model.api.Response
import net.royhome.api.model.api.Result
import net.royhome.api.model.api.request.GetResumeRequest
import net.royhome.api.model.resume.Resume
import net.royhome.api.service.ResumeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ResumeController(
  val service: ResumeService
) {
  @GetMapping("resume")
  fun getResume(@RequestBody request: Request<GetResumeRequest>): Response<Resume> {
    return try {
      val email = request.input!!.email
      val resume = service.getResume(email)
      Response(resume, Result(true, 0, "Success"))
    } catch (e: Exception) {
      Response(null, Result(false, 1, "Failure"))
    }
  }
}
