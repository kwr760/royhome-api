package net.royhome.tictactoe.model.action

import java.util.UUID

data class EndAction(
    val sessionId: UUID,
    val reason: String,
)
