package model

import model.modelComponent.GameBoardInterface

class PlaceShipStrategy extends PlaceShipStategyTemplate {

  override def placeShips(gameBoard: GameBoardInterface): Unit = {
    
    val ships = gameBoard.getShipsToPlace()

    val positions = List(
        ((1,1), 'h'),
        ((2,2), 'h'),
        ((3,3), 'h'),
        ((4,4), 'h')
    )
    for ((ship, pos) <- ships.zip(positions)) {
      val (position, orientation) = pos
      gameBoard.placeShip(ship, position, orientation)
    }
  }
  
}

