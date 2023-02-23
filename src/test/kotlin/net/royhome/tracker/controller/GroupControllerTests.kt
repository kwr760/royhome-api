package net.royhome.tracker.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID
import net.royhome.api.model.Response
import net.royhome.api.model.Result
import net.royhome.tracker.model.Group
import net.royhome.tracker.service.GroupService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class GroupControllerTests {
  private lateinit var underTest: GroupController
  private lateinit var groupServiceMock: GroupService

  @BeforeEach
  fun setUp() {
    groupServiceMock = mockk(relaxed = true)
    underTest = GroupController(groupServiceMock)
  }

  @Test
  fun `GroupController findByUserId - accepts request and returns response`() {
    // Arrange
    val userId = UUID.randomUUID()
    val groupsMock: Set<Group> = mockk()
    every { groupServiceMock.findGroupsByUserId(any()) } answers { groupsMock }
    val expectedResponse = ResponseEntity.ok(Response(groupsMock, Result(true, "Success")))

    // Act
    val response = underTest.findGroupsByUserId(userId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.findGroupsByUserId(any()) }
  }
  @Test
  fun `GroupController findByUserId - handles exception well`() {
    // Arrange
    val userId = UUID.randomUUID()
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { groupServiceMock.findGroupsByUserId(any()) } throws exception
    val expectedResponse = ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Response(null, Result(false, errorMessage)))

    // Act
    val response = underTest.findGroupsByUserId(userId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.findGroupsByUserId(any()) }
  }

  @Test
  fun `GroupController createGroup - accepts request and returns response`() {
    // Arrange
    val userId = UUID.randomUUID()
    val group = Group(userId, name = "group")
    val result = Group(UUID.randomUUID(), userId, name = "group")
    every { groupServiceMock.createGroup(any()) } answers { result }
    val expectedResponse = ResponseEntity.ok(Response(result, Result(true, "Success")))

    // Act
    val response = underTest.createGroup(group)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.createGroup(any()) }
  }
  @Test
  fun `GroupController createGroup - handles exception well`() {
    // Arrange
    val userId = UUID.randomUUID()
    val group = Group(userId, name = "group")
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { groupServiceMock.createGroup(any()) } throws exception
    val expectedResponse = ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Response(null, Result(false, errorMessage)))

    // Act
    val response = underTest.createGroup(group)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.createGroup(any()) }
  }

  @Test
  fun `GroupController deleteByGroupId - accepts request and returns response`() {
    // Arrange
    val groupId = UUID.randomUUID()
    every { groupServiceMock.deleteByGroupId(any()) } answers { }
    val expectedResponse = ResponseEntity.ok(Response(Unit, Result(true, "Success")))

    // Act
    val response = underTest.deleteByGroupId(groupId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.deleteByGroupId(any()) }
  }
  @Test
  fun `GroupController deleteByGroupId - handles exception well`() {
    // Arrange
    val groupId = UUID.randomUUID()
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { groupServiceMock.deleteByGroupId(any()) } throws exception
    val expectedResponse = ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Response(null, Result(false, errorMessage)))

    // Act
    val response = underTest.deleteByGroupId(groupId)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.deleteByGroupId(any()) }
  }

  @Test
  fun `GroupController updateGroup - accepts request and returns response`() {
    // Arrange
    val groupId = UUID.randomUUID()
    val userId = UUID.randomUUID()
    val group = Group(UUID.randomUUID(), userId, name = "group name")
    val result = Group(groupId, userId, name = "group name")
    every { groupServiceMock.updateGroup(any()) } answers { result }
    val expectedResponse = ResponseEntity.ok(Response(result, Result(true, "Success")))

    // Act
    val response = underTest.updateGroup(groupId, group)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.updateGroup(any()) }
  }
  @Test
  fun `GroupController updateGroup - handles exception well`() {
    // Arrange
    val groupId = UUID.randomUUID()
    val userId = UUID.randomUUID()
    val group = Group(userId, name = "group")
    val errorMessage = "Error Message"
    val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
    every { groupServiceMock.updateGroup(any()) } throws exception
    val expectedResponse = ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Response(null, Result(false, errorMessage)))

    // Act
    val response = underTest.updateGroup(groupId, group)

    // Assert
    Assertions.assertEquals(expectedResponse, response)
    verify(exactly = 1) { groupServiceMock.updateGroup(any()) }
  }
}
