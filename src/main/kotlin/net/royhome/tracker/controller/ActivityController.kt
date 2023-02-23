package net.royhome.tracker.controller

import java.util.UUID
import net.royhome.api.constant.Constant
import net.royhome.api.model.Response
import net.royhome.api.model.Result
import net.royhome.tracker.model.Activity
import net.royhome.tracker.service.ActivityService
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(
  val service: ActivityService
) {
  @PostMapping("activity")
  fun createActivity(@RequestBody activity: Activity): ResponseEntity<Response<Activity>> {
    return try {
      activity.activityId = UUID.randomUUID()
      val result = service.createActivity(activity)
      ResponseEntity.ok(Response(result, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
  @DeleteMapping("activity/{activityId}")
  fun deleteByActivityId(@PathVariable activityId: UUID): ResponseEntity<Response<Unit>> {
    return try {
      val result = service.deleteByActivityId(activityId)
      ResponseEntity.ok(Response(result, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
  @PutMapping("activity/{activityId}")
  fun updateActivity(
    @PathVariable activityId: UUID,
    @RequestBody activity: Activity
  ): ResponseEntity<Response<Activity>> {
    return try {
      activity.activityId = activityId
      val result = service.updateActivity(activity)
      ResponseEntity.ok(Response(result, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
}
