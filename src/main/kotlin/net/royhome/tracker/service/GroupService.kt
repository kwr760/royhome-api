package net.royhome.tracker.service

import net.royhome.tracker.model.Group
import net.royhome.tracker.repository.GroupRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GroupService(
    val repository: GroupRepository
) {
    fun findGroupsByUserId(userId: UUID): Set<Group> {
        return repository.findByUserId(userId)
    }
    fun createGroup(group: Group): Group {
        return repository.save(group)
    }
    fun updateGroup(group: Group): Group {
        return repository.save(group)
    }
    fun deleteByGroupId(groupId: UUID) {
        return repository.deleteById(groupId)
    }
}
