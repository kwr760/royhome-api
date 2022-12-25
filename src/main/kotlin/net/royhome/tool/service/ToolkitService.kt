package net.royhome.tool.service

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
}
