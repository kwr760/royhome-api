package net.royhome.tictactoe.service

import net.royhome.tictactoe.repository.GameRepository
import net.royhome.tictactoe.repository.PlayerRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MaintenanceService(
    val gameRepository: GameRepository,
    val playerRepository: PlayerRepository,
) {
    @Transactional
    @Scheduled(cron = "0 0/5 * * * *")
    fun removeOldGames() {
        playerRepository.deleteWhenAnHourOld()
        gameRepository.deleteWhenAnHourOld()
    }
}
