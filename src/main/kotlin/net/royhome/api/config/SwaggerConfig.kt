package net.royhome.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
  @Bean
  fun openApi(): OpenAPI =
    OpenAPI()
      .info(
        Info()
          .title("Royhome API")
          .version("0.0.1")
          .description("API documentation (OpenAPI 3) - migrated from Springfox to springdoc")
      )
}
