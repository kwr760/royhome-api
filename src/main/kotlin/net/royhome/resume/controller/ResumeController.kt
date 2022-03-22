package net.royhome.resume.controller

import net.royhome.api.constant.Constant
import net.royhome.api.model.Response
import net.royhome.api.model.Result
import net.royhome.resume.model.Resume
import net.royhome.resume.service.ResumeService
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ResumeController(
  val service: ResumeService
) {
  @GetMapping("resume/{email}")
  fun getResume(@PathVariable("email") email: String): ResponseEntity<Response<Resume>> {
    return try {
      val resume = service.getResume(email)
      ResponseEntity.ok(Response(resume, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
}
