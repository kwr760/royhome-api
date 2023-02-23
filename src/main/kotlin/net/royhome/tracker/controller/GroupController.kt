package net.royhome.tracker.controller

import java.util.UUID
import net.royhome.api.constant.Constant
import net.royhome.api.model.Response
import net.royhome.api.model.Result
import net.royhome.tracker.model.Group
import net.royhome.tracker.service.GroupService
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(
  val service: GroupService
) {
  @GetMapping("groups")
  fun findGroupsByUserId(@RequestParam userId: UUID): ResponseEntity<Response<Set<Group>>> {
    return try {
      val groups = service.findGroupsByUserId(userId)
      ResponseEntity.ok(Response(groups, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
  @PostMapping("group")
  fun createGroup(@RequestBody group: Group): ResponseEntity<Response<Group>> {
    return try {
      group.groupId = UUID.randomUUID()
      val result = service.createGroup(group)
      ResponseEntity.ok(Response(result, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
  @DeleteMapping("group/{groupId}")
  fun deleteByGroupId(@PathVariable groupId: UUID): ResponseEntity<Response<Unit>> {
    return try {
      val result = service.deleteByGroupId(groupId)
      ResponseEntity.ok(Response(result, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
  @PutMapping("group/{groupId}")
  fun updateGroup(@PathVariable groupId: UUID, @RequestBody group: Group): ResponseEntity<Response<Group>> {
    return try {
      group.groupId = groupId
      val result = service.updateGroup(group)
      ResponseEntity.ok(Response(result, Result(true, Constant.SUCCESS)))
    } catch (e: DataAccessException) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response(null, Result(false, e.message.toString())))
    }
  }
}
