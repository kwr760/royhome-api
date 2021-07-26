package net.royhome.api.controller

import net.royhome.api.model.access.AccessLog
import net.royhome.api.model.api.Response
import net.royhome.api.model.api.Result
import net.royhome.api.service.AccessService
import org.springframework.dao.DataAccessException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AccessController(
  val service: AccessService
) {
  @GetMapping("access/{session_id}")
  fun getAccessLog(@PathVariable("session_id") sessionId: UUID): Response<AccessLog> {
    return try {
      val accessLog = service.getAccessLog(sessionId)
      Response(accessLog, Result(true, 0, "Success"))
    } catch (e: DataAccessException) {
      val error = e.message.toString()
      Response(null, Result(false, 1, error))
    }
  }

  @PostMapping("access/log")
  fun postAccessLog(@RequestBody input: AccessLog): Response<AccessLog> {
    return try {
      val accessLog = service.save(input)
      Response(accessLog, Result(true, 0, "Success"))
    } catch (e: Exception) {
      val error = e.message.toString()
      Response(null, Result(false, 1, error))
    }
  }
}
