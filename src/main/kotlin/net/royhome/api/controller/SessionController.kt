package net.royhome.api.controller

import net.royhome.api.constant.Constant
import net.royhome.api.model.Response
import net.royhome.api.model.Result
import net.royhome.api.model.JwtClaim
import net.royhome.api.model.Session
import net.royhome.api.service.SessionService
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class SessionController(
  val service: SessionService
) {
  @GetMapping("session/{session_id}")
  fun getSession(@PathVariable("session_id") sessionId: UUID): ResponseEntity<Response<Session>> {
    return try {
      val session = service.getSession(sessionId)
      ResponseEntity.ok(Response(session, Result(true, Constant.SUCCESS)))
    } catch (e: EmptyResultDataAccessException) {
      ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Response(null, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }

  @PostMapping("session")
  fun saveSession(
    @RequestBody claim: JwtClaim,
    @CookieValue("session-id") sessionId: UUID,
    @CookieValue("browser-id") browserId: UUID,
  ): ResponseEntity<Response<Session>> {
    return try {
      val session = service.saveSession(sessionId, browserId, claim)
      ResponseEntity.ok(Response(session, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
}
