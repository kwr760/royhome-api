package net.royhome.api.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RequestExceptionHandler {
  @ExceptionHandler
  fun handleRequestException(e : HttpMessageConversionException): ResponseEntity<Any> {
    return ResponseEntity<Any>(
      e.message.toString(), HttpHeaders(), HttpStatus.BAD_REQUEST
    )
  }
}
