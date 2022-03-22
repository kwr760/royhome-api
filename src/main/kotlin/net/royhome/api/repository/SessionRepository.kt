package net.royhome.api.repository

import net.royhome.api.model.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SessionRepository : JpaRepository<Session, Long> {
  fun findBySessionId(sessionId: UUID): Session
}
