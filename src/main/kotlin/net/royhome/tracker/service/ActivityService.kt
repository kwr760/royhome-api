package net.royhome.tracker.service

import java.util.UUID
import net.royhome.tracker.model.Activity
import net.royhome.tracker.repository.ActivityRepository
import org.springframework.stereotype.Service

@Service
class ActivityService(
  val repository: ActivityRepository
) {
  fun createActivity(activity: Activity): Activity {
    return repository.save(activity)
  }
  fun updateActivity(activity: Activity): Activity {
    return repository.save(activity)
  }
  fun deleteByActivityId(activityId: UUID) {
    return repository.deleteById(activityId)
  }
}
