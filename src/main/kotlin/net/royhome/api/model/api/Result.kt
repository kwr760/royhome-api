package net.royhome.api.model.api

data class Result(
  val success: Boolean,
  val code: Int,
  val message: String = "",
)
