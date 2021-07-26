package net.royhome.api.service

import net.royhome.api.model.access.AccessLog
import net.royhome.api.repository.AccessRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccessService (
  val repository: AccessRepository
) {
  fun getAccessLog(sessionId: UUID) : AccessLog {
    return repository.findBySessionId(sessionId)
  }

  fun save(accessLog: AccessLog) : AccessLog {
    return repository.save(accessLog)
  }
}
