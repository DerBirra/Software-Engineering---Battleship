package model

trait PlaceShipStategyTemplate {

  def createNewGameField(size:Int): GameBoard = {
    var gameBoard = new GameBoard(size)
    gameBoard.generateField()
    placeShips(gameBoard)
    gameBoard
  }

  def placeShips(gameBoard: GameBoard) : Unit // abstract
}
