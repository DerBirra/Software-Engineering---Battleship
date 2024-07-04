package model.modelComponent.modelImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.modelComponent.GameBoardInterface

class PlaceShipStrategyTemplateSpec extends AnyWordSpec {

  class TestPlaceShipStrategy extends PlaceShipStategyTemplate {
    override def placeShips(gameBoard: GameBoardInterface): Unit = {
      gameBoard.placeShip(Ship(ShipType.PatrolBoat, ShipSize.Two), (1, 1), 'h')
    }
  }

  "A PlaceShipStategyTemplate" should {

    "create a new game field and place ships" in {
      val strategy = new TestPlaceShipStrategy
      val gameBoard = strategy.createNewGameField(10)

      gameBoard.getSize() shouldEqual 10

      val fieldString = gameBoard.printField(hidden = true)
      fieldString should not be empty
      fieldString should include(" # ")

      // Verify ships have been placed
      gameBoard.isCellContent(1, 1, 'O') shouldEqual true
      gameBoard.isCellContent(1, 2, 'O') shouldEqual true
    }
  }
}
