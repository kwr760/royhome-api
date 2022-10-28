package net.royhome.tictactoe.model

import net.royhome.tictactoe.constant.GameActionEnum
import net.royhome.tictactoe.constant.ResultEnum

data class Response(
  var result: ResultEnum,
  val action: GameActionEnum? = null,
  val opponent: Player? = null,
  val board: String? = null,
)
