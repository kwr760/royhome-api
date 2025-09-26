package net.royhome.tracker.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tracker.model.Group
import net.royhome.tracker.repository.GroupRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class GroupServiceTests {
    private lateinit var underTest: GroupService
    private lateinit var groupRepositoryMock: GroupRepository

    @BeforeEach
    fun setUp() {
        groupRepositoryMock = mockk(relaxed = true)
        underTest = GroupService(groupRepositoryMock)
    }

    @Test
    fun `GroupService getGroup`() {
        // Arrange
        val userId = UUID.randomUUID()
        val groupsMock: Set<Group> = mockk()
        every { groupRepositoryMock.findByUserId(any()) } answers { groupsMock }

        // Act
        val response = underTest.findGroupsByUserId(userId)

        // Assert
        Assertions.assertEquals(groupsMock, response)
        verify(exactly = 1) { groupRepositoryMock.findByUserId(any()) }
    }

    @Test
    fun `GroupService saveGroup`() {
        // Arrange
        val group = Group(name = "group")
        val result = Group(UUID.randomUUID(), name = "group")
        every { groupRepositoryMock.save(any()) } answers { result }

        // Act
        val response = underTest.createGroup(group)

        // Assert
        Assertions.assertEquals(result, response)
        verify(exactly = 1) { groupRepositoryMock.save(any()) }
    }

    @Test
    fun `GroupService updateGroup`() {
        // Arrange
        val group = Group(name = "group")
        val result = Group(UUID.randomUUID(), name = "group")
        every { groupRepositoryMock.save(any()) } answers { result }

        // Act
        val response = underTest.updateGroup(group)

        // Assert
        Assertions.assertEquals(result, response)
        verify(exactly = 1) { groupRepositoryMock.save(any()) }
    }

    @Test
    fun `GroupService deleteByGroupId`() {
        // Arrange
        val groupId = UUID.randomUUID()
        every { groupRepositoryMock.deleteById(any()) } answers { }

        // Act
        val response = underTest.deleteByGroupId(groupId)

        // Assert
        Assertions.assertEquals(Unit, response)
        verify(exactly = 1) { groupRepositoryMock.deleteById(any()) }
    }
}
