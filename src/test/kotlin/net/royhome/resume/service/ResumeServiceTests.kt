package net.royhome.resume.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.resume.model.Resume
import net.royhome.resume.repository.ResumeRepository
import net.royhome.resume.service.ResumeService
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
  fun `resumeService getResume`() {
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
