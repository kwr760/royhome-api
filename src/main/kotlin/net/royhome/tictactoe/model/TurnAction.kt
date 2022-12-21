package net.royhome.tictactoe.model

import java.util.UUID

data class TurnAction(
  val sessionId: UUID,
  val board: String,
)
