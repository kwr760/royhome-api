package net.royhome.tictactoe.model

import net.royhome.tictactoe.constant.MessageActionEnum

data class Message(
  val action: MessageActionEnum,
  val game: Game? = null,
  val reason: String = "",
)
