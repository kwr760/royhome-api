package net.royhome.api.model

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Resume(
        @Id
        @Type(type = "pg-uuid")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID = UUID.randomUUID(),
        val name: String = "",
        val address: String = "",
        val phone: String = "",
        val displayPhone: Boolean = false,
        val email: String = "",
        val summary: String = "",
  )
