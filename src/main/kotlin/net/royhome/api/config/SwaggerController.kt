package net.royhome.api.config

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SwaggerController {
  @RequestMapping("/")
  fun root(): String = "redirect:/swagger-ui/"

  @RequestMapping("/swagger")
  fun swagger(): String = "redirect:/swagger-ui/"

}
