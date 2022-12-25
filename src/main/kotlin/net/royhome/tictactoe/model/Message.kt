package net.royhome.tictactoe.model

import net.royhome.tictactoe.constant.GameActionEnum

data class Message(
  val action: GameActionEnum,
  val game: Game? = null,
  val reason: String = "",
)
