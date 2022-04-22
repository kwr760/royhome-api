package net.royhome.tictactoe.model

data class Game(
  val sessionId: String,
  var opponentId: String = "",
  var match: Match? = null
)
