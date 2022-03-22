package net.royhome.api.service

import net.royhome.api.model.JwtClaim
import net.royhome.api.model.Session
import net.royhome.api.model.User
import net.royhome.api.repository.SessionRepository
import net.royhome.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.UUID

@Service
class SessionService(
  val sessionRepository: SessionRepository,
  val userRepository: UserRepository,
) {
  fun getSession(sessionId: UUID): Session {
    return sessionRepository.findBySessionId(sessionId)
  }

  fun saveSession(sessionId: UUID, browserId: UUID, claim: JwtClaim): Session {
    val session = Session(
      sessionId = sessionId,
      browserId = browserId,
    )

    claim.expiration?.let {
      if (it != 0L) {
        session.expiration = Timestamp(it)
      }
    }
    claim.darkMode?.let { session.darkMode = it }
    claim.email?.let {
      val user = userRepository.findByEmail(claim.email) ?: User(
        email = claim.email,
        context = claim.context.toString()
      )
      userRepository.save(user)
      session.user = user
    }

    return sessionRepository.save(session)
  }
}
