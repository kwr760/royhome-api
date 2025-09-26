package net.royhome.tictactoe.service

import net.royhome.tictactoe.constant.PieceEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToolkitServiceTests {
    private lateinit var underTest: ToolkitService

    @BeforeEach
    fun setUpBeforeEach() {
        underTest = ToolkitService()
    }

    @Test
    fun `get random piece`() {
        // Arrange // Act
        val piece = underTest.getRandomPiece()

        // Assert
        Assertions.assertTrue(listOf(PieceEnum.X, PieceEnum.O).contains(piece))
    }

    @Test
    fun `get O`() {
        // Arrange // Act
        val piece = underTest.getOtherPiece(PieceEnum.X.name)

        // Assert
        Assertions.assertEquals(piece, PieceEnum.O)
    }

    @Test
    fun `get X`() {
        // Arrange // Act
        val piece = underTest.getOtherPiece(PieceEnum.O.name)

        // Assert
        Assertions.assertEquals(piece, PieceEnum.X)
    }

    @Test
    fun `get other piece`() {
        // Arrange // Act
        val piece = underTest.getOtherPiece("")

        // Assert
        Assertions.assertTrue(listOf(PieceEnum.X, PieceEnum.O).contains(piece))
    }

    @Test
    fun `getNextPlayer - X`() {
        // Arrange // Act
        val piece = underTest.getNextPlayer("XO-------")

        // Assert
        Assertions.assertEquals(PieceEnum.X, piece)
    }

    @Test
    fun `getNextPlayer - O`() {
        // Arrange // Act
        val piece = underTest.getNextPlayer("X--------")

        // Assert
        Assertions.assertEquals(PieceEnum.O, piece)
    }
}
