package net.royhome.tictactoe.model.action

import java.util.UUID

data class PlayAction(
    val sessionId: UUID,
    val board: String,
)
