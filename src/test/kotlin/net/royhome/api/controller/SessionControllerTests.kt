package net.royhome.api.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.api.model.api.Response
import net.royhome.api.model.api.Result
import net.royhome.api.model.api.session.JwtClaim
import net.royhome.api.model.db.session.Session
import net.royhome.api.service.SessionService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.UUID

class SessionControllerTests {
  private lateinit var underTest: SessionController
  private lateinit var sessionServiceMock: SessionService

  @BeforeEach
  fun setUp() {
    sessionServiceMock = mockk(relaxed = true)
    underTest = SessionController(sessionServiceMock)
  }

  @Test
  fun `getSession accepts request and returns response`() {
    // Arrange - input
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val sessionMock: Session = mockk()
    every { sessionServiceMock.getSession(any()) } answers { sessionMock }
    // Arrange - response
    val expectedResponse = ResponseEntity.ok(Response(sessionMock, Result(true, "Success")))

    // Act
    val response = underTest.getSession(sessionId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { sessionServiceMock.getSession(sessionId) }
  }

  @Test
  fun `getSession handles DataAccessException`() {
    // Arrange - input
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { sessionServiceMock.getSession(any()) } throws exception
    // Arrange - response
    val expectedResponse = ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Response(null, Result(false, errorMessage)))

    // Act
    val response = underTest.getSession(sessionId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { sessionServiceMock.getSession(sessionId) }
  }

  @Test
  fun `getSession handles EmptyResultDataAccessException`() {
    // Arrange - input
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val errorMessage = "Not Found"
    val exception: DataAccessException = EmptyResultDataAccessException(errorMessage, 1)
    every { sessionServiceMock.getSession(any()) } throws exception
    // Arrange - response
    val expectedResponse = ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(Response(null, Result(true, "Success")))

    // Act
    val response = underTest.getSession(sessionId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { sessionServiceMock.getSession(sessionId) }
  }

  @Test
  fun `saveSession accepts request and returns response`() {
    // Arrange - input
    val expiration = 1000L
    val userId = UUID.randomUUID()
    val email = "person@email.com"
    val darkMode = "dark-mode"
    val context = "context"
    val claim = JwtClaim(expiration, userId, email, darkMode, context)
    val browserId = UUID.randomUUID()
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val sessionMock: Session = mockk()
    every { sessionServiceMock.saveSession(any(), any(), any()) } answers { sessionMock }
    // Arrange - response
    val expectedResponse = ResponseEntity.ok(Response(sessionMock, Result(true, "Success")))

    // Act
    val response = underTest.saveSession(claim, sessionId, browserId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { sessionServiceMock.saveSession(sessionId, browserId, claim) }
  }

  @Test
  fun `saveSession handles DataAccessException`() {
    // Arrange - input
    val expiration = 1000L
    val userId = UUID.randomUUID()
    val email = "person@email.com"
    val darkMode = "dark-mode"
    val context = "context"
    val claim = JwtClaim(expiration, userId, email, darkMode, context)
    val browserId = UUID.randomUUID()
    val sessionId = UUID.randomUUID()
    // Arrange - mocks
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { sessionServiceMock.saveSession(any(), any(), any()) } throws exception
    // Arrange - response
    val expectedResponse = ResponseEntity.status(
      HttpStatus.INTERNAL_SERVER_ERROR
    ).body(
      Response(null, Result(false, errorMessage))
    )

    // Act
    val response = underTest.saveSession(claim, sessionId, browserId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { sessionServiceMock.saveSession(sessionId, browserId, claim) }
  }
}
