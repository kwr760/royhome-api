package net.royhome.api.exception

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException

class RequestExceptionHandlerTests {
  private lateinit var underTest: RequestExceptionHandler

  @BeforeEach
  fun setUp() {
    underTest = RequestExceptionHandler()
  }

  @Test
  fun `requestExceptionHandler handles exceptions`() {
    // Arrange - input
    val errorMessage: String = "Error Message"
    val exception = HttpMessageConversionException(errorMessage)
    // Arrange - response
    val expectedResponse: ResponseEntity<Any> = ResponseEntity<Any>(
      errorMessage, HttpHeaders(), HttpStatus.BAD_REQUEST
    )

    // Act
    val response = underTest.handleRequestException(exception)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
  }
}
