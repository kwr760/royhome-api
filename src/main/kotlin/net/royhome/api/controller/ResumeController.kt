package net.royhome.api.controller

import net.royhome.api.constant.Constant
import net.royhome.api.model.api.Response
import net.royhome.api.model.api.Result
import net.royhome.api.model.db.resume.Resume
import net.royhome.api.service.ResumeService
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
