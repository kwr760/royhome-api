package net.royhome.api.repository

import java.util.UUID
import net.royhome.api.model.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Long> {
  fun findBySessionId(sessionId: UUID): Session
}
