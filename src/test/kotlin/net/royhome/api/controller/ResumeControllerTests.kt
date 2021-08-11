package net.royhome.api.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.api.model.api.Response
import net.royhome.api.model.api.Result
import net.royhome.api.model.db.resume.Resume
import net.royhome.api.service.ResumeService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.http.HttpResponse

class ResumeControllerTests {
  private lateinit var underTest: ResumeController
  private lateinit var resumeServiceMock: ResumeService

  @BeforeEach
  fun setUp() {
    resumeServiceMock = mockk(relaxed = true)
    underTest = ResumeController(resumeServiceMock)
  }

  @Test
  fun `resumeController accepts request and returns response`() {
    // Arrange - input
    val email = "person@email.com"
    // Arrange - mocks
    val resumeMock: Resume = mockk()
    every { resumeServiceMock.getResume(any()) } answers { resumeMock }
    // Arrange - response
    val expectedResponse = ResponseEntity.ok(Response(resumeMock, Result(true, "Success")))

    // Act
    val response = underTest.getResume(email)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { resumeServiceMock.getResume(any()) }
  }

  @Test
  fun `resumeController handles exception well`() {
    // Arrange - input
    val email = "person@email.com"
    // Arrange - mocks
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { resumeServiceMock.getResume(any()) } throws exception
    // Arrange - response
    val expectedResponse = ResponseEntity.status(
      HttpStatus.INTERNAL_SERVER_ERROR).body(Response(null, Result(false, errorMessage))
    )

    // Act
    val response = underTest.getResume(email)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { resumeServiceMock.getResume(any()) }
  }
}
