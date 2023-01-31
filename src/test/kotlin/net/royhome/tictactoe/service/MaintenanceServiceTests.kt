package net.royhome.tictactoe.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.royhome.tictactoe.repository.GameRepository
import net.royhome.tictactoe.repository.PlayerRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MaintenanceServiceTests {
  private lateinit var gameRepositoryMock: GameRepository
  private lateinit var playerRepositoryMock: PlayerRepository
  private lateinit var underTest: MaintenanceService

  @BeforeEach
  fun setUpBeforeEach() {
    gameRepositoryMock = mockk(relaxed = true)
    playerRepositoryMock = mockk(relaxed = true)
    underTest = MaintenanceService(gameRepositoryMock, playerRepositoryMock)
  }

  @Test
  fun removeOldGames() {
    // Arrange
    every { playerRepositoryMock.deleteWhenAnHourOld() } returns Unit
    every { gameRepositoryMock.deleteWhenAnHourOld() } returns Unit

    // Act
    underTest.removeOldGames()

    // Assert
    verify(exactly = 1) { playerRepositoryMock.deleteWhenAnHourOld() }
    verify(exactly = 1) { gameRepositoryMock.deleteWhenAnHourOld() }
  }
}
