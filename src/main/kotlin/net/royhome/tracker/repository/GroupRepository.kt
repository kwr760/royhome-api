package net.royhome.tracker.repository

import net.royhome.tracker.model.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GroupRepository : JpaRepository<Group, UUID> {
    fun findByUserId(userId: UUID): Set<Group>
}
