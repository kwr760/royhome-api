package net.royhome.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**")
      .allowedOrigins("https://royk.us", "https://royhome.net", "https://www.royk.us", "https://www.royhome.net")
      .allowedMethods("GET", "POST", "PUT")
      .allowCredentials(true)
  }
}
