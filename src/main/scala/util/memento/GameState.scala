package util.memento

import model.modelComponent.GameBoardInterface

case class GameState(gameBoard1: GameBoardInterface, gameBoard2: GameBoardInterface, currentPlayer: Int)
