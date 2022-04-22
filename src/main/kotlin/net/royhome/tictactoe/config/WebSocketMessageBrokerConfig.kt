package net.royhome.tictactoe.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketMessageBrokerConfig : WebSocketMessageBrokerConfigurer {
  override fun configureMessageBroker(config: MessageBrokerRegistry) {
    config.enableSimpleBroker(
      "/connect",
      "/game"
    )
  }

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry
      .addEndpoint("/tictactoe")
      .setAllowedOrigins(
        "https://royk.us",
        "https://royhome.net",
        "https://www.royk.us",
        "https://www.royhome.net"
      )
  }
}
