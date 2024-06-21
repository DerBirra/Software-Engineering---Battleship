package model.modelComponent.modelImpl

import model.modelComponent.GameBoardInterface

trait PlaceShipStategyTemplate {

  def createNewGameField(size:Int): GameBoardInterface = {
    var gameBoard = new GameBoard(size)
    gameBoard.generateField()
    placeShips(gameBoard)
    gameBoard
  }

  def placeShips(gameBoard: GameBoardInterface) : Unit // abstract
}
