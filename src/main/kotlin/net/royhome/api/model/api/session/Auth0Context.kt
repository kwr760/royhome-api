package net.royhome.api.model.api.session

data class Auth0Context(
  val app: String?,
  val role: String?,
  val data: String?,
)
