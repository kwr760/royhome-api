package net.royhome.api

import net.royhome.api.constant.Constant
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableTransactionManagement
class Application

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<Application>(*args)
  val startFile = File(Constant.STARTED_FILENAME)
  val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
  startFile.writeText(timestamp + System.lineSeparator())
}
