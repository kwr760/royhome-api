package net.royhome.api.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.api.model.resume.Resume
import net.royhome.api.repository.ResumeRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ResumeServiceTests {
  private lateinit var underTest: ResumeService
  private lateinit var resumeRepositoryMock: ResumeRepository

  @BeforeEach
  fun setUp() {
    resumeRepositoryMock = mockk(relaxed = true)
    underTest = ResumeService(resumeRepositoryMock)
  }

  @Test
  fun `resumeController accepts request and returns response`() {
    // Arrange - input
    val email = "person@email.com"
    // Arrange - mocks
    val resumeMock: Resume = mockk()
    every { resumeRepositoryMock.findByEmail(any()) } answers { resumeMock }

    // Act
    val response = underTest.getResume(email)

    // Assert
    Assertions.assertEquals(resumeMock, response)
    verify(exactly = 1) { resumeRepositoryMock.findByEmail(any()) }
  }
}
