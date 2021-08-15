package net.royhome.api.controller

import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LogController {
  @PutMapping("log")
  fun log(
    @RequestBody body: Any,
  ) {
    println(body.toString());
  }
}
