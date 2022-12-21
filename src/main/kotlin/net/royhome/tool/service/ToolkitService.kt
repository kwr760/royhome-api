package net.royhome.tool.service

import org.springframework.stereotype.Service

@Service
class ToolkitService {
  fun getRandomBool(): Boolean {
    return listOf(false, true).random()
  }
}
