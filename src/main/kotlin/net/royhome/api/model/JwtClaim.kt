package net.royhome.api.model

import java.util.UUID

data class JwtClaim(
    val expiration: Long? = null,
    val userId: UUID? = null,
    val email: String? = null,
    val darkMode: String? = null,
    val context: String? = null,
)
