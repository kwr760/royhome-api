package net.royhome.api.repository

import net.royhome.api.model.access.AccessLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccessRepository : JpaRepository<AccessLog, Long> {
  fun findBySessionId(sessionId: UUID): AccessLog
}
