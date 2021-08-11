package net.royhome.api.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.api.model.api.session.JwtClaim
import net.royhome.api.model.db.resume.Resume
import net.royhome.api.model.db.session.Session
import net.royhome.api.model.db.session.User
import net.royhome.api.repository.ResumeRepository
import net.royhome.api.repository.SessionRepository
import net.royhome.api.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Timestamp
import java.util.UUID

class SessionServiceTests {
  private lateinit var underTest: SessionService
  private lateinit var sessionRepositoryMock: SessionRepository
  private lateinit var userRepositoryMock: UserRepository

  @BeforeEach
  fun setUp() {
    sessionRepositoryMock = mockk(relaxed = true)
    userRepositoryMock = mockk(relaxed = true)
    underTest = SessionService(sessionRepositoryMock, userRepositoryMock)
  }

  @Test
  fun `sessionService getSession gets session from repository`() {
    // Arrange - input
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val sessionMock: Session = mockk()
    every { sessionRepositoryMock.findBySessionId(any()) } answers { sessionMock }

    // Act
    val response = underTest.getSession(sessionId)

    // Assert
    Assertions.assertEquals(sessionMock, response)
    verify(exactly = 1) { sessionRepositoryMock.findBySessionId(sessionId) }
  }

  @Test
  fun `sessionService saveSession save session in repository`() {
    // Arrange - input
    val expiration = 1000L
    val userId = UUID.randomUUID()
    val email = "person@email.com"
    val darkMode = "dark-mode"
    val context = "context"
    val claim  = JwtClaim(expiration, userId, email, darkMode, context)
    val browserId = UUID.randomUUID()
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val session = Session(sessionId, browserId, Timestamp(expiration), darkMode)
    val user = User(userId, email, context)
    session.user = user
    every { sessionRepositoryMock.save(any()) } answers { session }
    every { userRepositoryMock.save(any()) } answers { user }

    // Act
    val response = underTest.saveSession(sessionId, browserId, claim)

    // Assert
    Assertions.assertEquals(session, response)
    verify(exactly = 1) { sessionRepositoryMock.save(any()) }
  }

  @Test
  fun `saveSession save session without claim in repository`() {
    // Arrange - input
    val expiration = 1000L
    val claim  = JwtClaim()
    val browserId = UUID.randomUUID()
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val session = Session(sessionId, browserId, Timestamp(expiration))
    every { sessionRepositoryMock.save(any()) } answers { session }

    // Act
    val response = underTest.saveSession(sessionId, browserId, claim)

    // Assert
    Assertions.assertEquals(session, response)
    verify(exactly = 1) { sessionRepositoryMock.save(any()) }
  }

  @Test
  fun `sessionService saveSession save session new user - no expiration in repository`() {
    // Arrange - input
    val expiration = 0L
    val email = "person@email.com"
    val claim  = JwtClaim(expiration = expiration, email = email)
    val browserId = UUID.randomUUID()
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val session = Session(sessionId, browserId, Timestamp(expiration) )
    val user = User(email = email)
    session.user = user
    every { sessionRepositoryMock.save(any()) } answers { session }
    every { userRepositoryMock.save(any()) } answers { user }

    // Act
    val response = underTest.saveSession(sessionId, browserId, claim)

    // Assert
    Assertions.assertEquals(session, response)
    verify(exactly = 1) { sessionRepositoryMock.save(any()) }
  }
}
