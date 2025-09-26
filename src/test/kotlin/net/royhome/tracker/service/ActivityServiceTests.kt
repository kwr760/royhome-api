package net.royhome.tracker.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tracker.model.Activity
import net.royhome.tracker.model.Group
import net.royhome.tracker.repository.ActivityRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class ActivityServiceTests {
    private val platform = "platform"
    private val name = "activity"
    private val progress = "progress"
    private val group = Group(UUID.randomUUID(), name = "group")
    private lateinit var underTest: ActivityService
    private lateinit var activityRepositoryMock: ActivityRepository

    @BeforeEach
    fun setUp() {
        activityRepositoryMock = mockk(relaxed = true)
        underTest = ActivityService(activityRepositoryMock)
    }

    @Test
    fun `ActivityService saveActivity`() {
        // Arrange
        val activity = Activity(platform = platform, progress = progress, activity = name, group = group)
        val result = Activity(
            UUID.randomUUID(),
            platform = platform,
            progress = progress,
            activity = name,
            group = group
        )
        every { activityRepositoryMock.save(any()) } answers { result }

        // Act
        val response = underTest.createActivity(activity)

        // Assert
        Assertions.assertEquals(result, response)
        verify(exactly = 1) { activityRepositoryMock.save(any()) }
    }

    @Test
    fun `ActivityService updateActivity`() {
        // Arrange
        val activity = Activity(platform = platform, progress = progress, activity = name, group = group)
        val result = Activity(
            UUID.randomUUID(),
            platform = platform,
            progress = progress,
            activity = name,
            group = group
        )
        every { activityRepositoryMock.save(any()) } answers { result }

        // Act
        val response = underTest.updateActivity(activity)

        // Assert
        Assertions.assertEquals(result, response)
        verify(exactly = 1) { activityRepositoryMock.save(any()) }
    }

    @Test
    fun `ActivityService deleteByActivityId`() {
        // Arrange
        val activityId = UUID.randomUUID()
        every { activityRepositoryMock.deleteById(any()) } answers { }

        // Act
        val response = underTest.deleteByActivityId(activityId)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { activityRepositoryMock.deleteById(any()) }
    }
}
