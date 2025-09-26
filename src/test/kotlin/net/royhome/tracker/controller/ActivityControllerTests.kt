package net.royhome.tracker.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.api.model.Response
import net.royhome.api.model.Result
import net.royhome.tracker.model.Activity
import net.royhome.tracker.model.Group
import net.royhome.tracker.service.ActivityService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.UUID

class ActivityControllerTests {
    private val platform = "platform"
    private val name = "activity"
    private val progress = "progress"
    private val group = Group(UUID.randomUUID(), name = "group")
    private lateinit var underTest: ActivityController
    private lateinit var activityServiceMock: ActivityService

    @BeforeEach
    fun setUp() {
        activityServiceMock = mockk(relaxed = true)
        underTest = ActivityController(activityServiceMock)
    }

    @Test
    fun `ActivityController createActivity - accepts request and returns response`() {
        // Arrange
        val activity = Activity(platform = platform, progress = progress, activity = name, group = group)
        val result =
            Activity(UUID.randomUUID(), platform = platform, progress = progress, activity = name, group = group)
        every { activityServiceMock.createActivity(any()) } answers { result }
        val expectedResponse = ResponseEntity.ok(Response(result, Result(true, "Success")))

        // Act
        val response = underTest.createActivity(activity)

        // Assert
        Assertions.assertEquals(expectedResponse, response)
        verify(exactly = 1) { activityServiceMock.createActivity(any()) }
    }

    @Test
    fun `ActivityController createActivity - handles exception well`() {
        // Arrange
        val activity = Activity(platform = platform, progress = progress, activity = name, group = group)
        val errorMessage = "Error Message"
        val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
        every { activityServiceMock.createActivity(any()) } throws exception
        val expectedResponse = ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response(null, Result(false, errorMessage)))

        // Act
        val response = underTest.createActivity(activity)

        // Assert
        Assertions.assertEquals(expectedResponse, response)
        verify(exactly = 1) { activityServiceMock.createActivity(any()) }
    }

    @Test
    fun `ActivityController deleteByActivityId - accepts request and returns response`() {
        // Arrange
        val activityId = UUID.randomUUID()
        every { activityServiceMock.deleteByActivityId(any()) } answers { }
        val expectedResponse = ResponseEntity.ok(Response(Unit, Result(true, "Success")))

        // Act
        val response = underTest.deleteByActivityId(activityId)

        // Assert
        Assertions.assertEquals(expectedResponse, response)
        verify(exactly = 1) { activityServiceMock.deleteByActivityId(any()) }
    }

    @Test
    fun `ActivityController deleteByActivityId - handles exception well`() {
        // Arrange
        val activityId = UUID.randomUUID()
        val errorMessage = "Error Message"
        val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
        every { activityServiceMock.deleteByActivityId(any()) } throws exception
        val expectedResponse = ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response(null, Result(false, errorMessage)))

        // Act
        val response = underTest.deleteByActivityId(activityId)

        // Assert
        Assertions.assertEquals(expectedResponse, response)
        verify(exactly = 1) { activityServiceMock.deleteByActivityId(any()) }
    }

    @Test
    fun `ActivityController updateActivity - accepts request and returns response`() {
        // Arrange
        val activity = Activity(platform = platform, progress = progress, activity = name, group = group)
        val activityId = UUID.randomUUID()
        val result =
            Activity(UUID.randomUUID(), platform = platform, progress = progress, activity = name, group = group)
        every { activityServiceMock.updateActivity(any()) } answers { result }
        val expectedResponse = ResponseEntity.ok(Response(result, Result(true, "Success")))

        // Act
        val response = underTest.updateActivity(activityId, activity)

        // Assert
        Assertions.assertEquals(expectedResponse, response)
        verify(exactly = 1) { activityServiceMock.updateActivity(any()) }
    }

    @Test
    fun `ActivityController updateActivity - handles exception well`() {
        // Arrange
        val activity = Activity(platform = platform, progress = progress, activity = name, group = group)
        val activityId = UUID.randomUUID()
        val errorMessage = "Error Message"
        val exception: DataAccessException = DataRetrievalFailureException(errorMessage)
        every { activityServiceMock.updateActivity(any()) } throws exception
        val expectedResponse = ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response(null, Result(false, errorMessage)))

        // Act
        val response = underTest.updateActivity(activityId, activity)

        // Assert
        Assertions.assertEquals(expectedResponse, response)
        verify(exactly = 1) { activityServiceMock.updateActivity(any()) }
    }
}
