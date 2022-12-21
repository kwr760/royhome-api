package net.royhome.tictactoe.model

import net.royhome.tictactoe.constant.ResultEnum

data class Result(
  var result: ResultEnum,
  var player: Player? = null,
  var opponent: Player? = null,
)
