package net.royhome.tictactoe.model

import java.util.UUID

data class StartAction (
  val sessionId: UUID,
  val name: String,
)
