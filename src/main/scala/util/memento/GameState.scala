package util.memento

import model.GameBoard

case class GameState(gameBoard1: GameBoard, gameBoard2: GameBoard, currentPlayer: Int)
