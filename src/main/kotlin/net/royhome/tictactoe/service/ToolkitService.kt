package net.royhome.tictactoe.service

import net.royhome.tictactoe.constant.PieceEnum
import org.springframework.stereotype.Service

@Service
class ToolkitService {
  fun getRandomPiece(): PieceEnum {
    return listOf(PieceEnum.X, PieceEnum.O).random()
  }
  fun getOtherPiece(piece: String): PieceEnum {
    return when (piece) {
      PieceEnum.X.name -> PieceEnum.O
      PieceEnum.O.name -> PieceEnum.X
      else -> getRandomPiece()
    }
  }
  fun getNextPlayer(board: String): PieceEnum {
    val xCount = board.count { it == PieceEnum.X.name[0] }
    val oCount = board.count { it == PieceEnum.O.name[0] }
    return if (xCount - oCount > 0) PieceEnum.O else PieceEnum.X
  }
}
