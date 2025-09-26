package net.royhome.api.config

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SwaggerController {
    @RequestMapping("/")
    @Suppress("FunctionOnlyReturningConstant")
    fun root(): String = "redirect:/swagger-ui/"

    @RequestMapping("/swagger")
    @Suppress("FunctionOnlyReturningConstant")
    fun swagger(): String = "redirect:/swagger-ui/"
}
