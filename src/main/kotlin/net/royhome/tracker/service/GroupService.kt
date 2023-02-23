package net.royhome.tracker.service

import java.util.UUID
import net.royhome.tracker.model.Group
import net.royhome.tracker.repository.GroupRepository
import org.springframework.stereotype.Service

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
