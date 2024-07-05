package model.modelComponent.modelImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.{Success, Failure}

class PlaceShipStrategySpec extends AnyWordSpec {

  "A PlaceShipStrategy" should {
    
    "place ships at the specified positions" in {
      val gameBoard = GameBoard(10)
      val strategy = new PlaceShipStrategy

      val newGameBoard = strategy.createNewGameField(10)

      newGameBoard.getSize() shouldEqual 10

      val fieldString = newGameBoard.printField(hidden = true)
      fieldString should not be empty
      fieldString should include(" # ")

      newGameBoard.isCellContent(1, 1, 'O') shouldEqual true
      newGameBoard.isCellContent(1, 2, 'O') shouldEqual true
      newGameBoard.isCellContent(2, 2, 'O') shouldEqual true
      newGameBoard.isCellContent(2, 3, 'O') shouldEqual true
      newGameBoard.isCellContent(3, 3, 'O') shouldEqual true
      newGameBoard.isCellContent(3, 4, 'O') shouldEqual true
      newGameBoard.isCellContent(4, 4, 'O') shouldEqual true
      newGameBoard.isCellContent(4, 5, 'O') shouldEqual true
    }

    "create a new game field and place ships" in {
      val strategy = new PlaceShipStrategy
      val gameBoard = strategy.createNewGameField(10)

      gameBoard.getSize() shouldEqual 10

      val fieldString = gameBoard.printField(hidden = true)
      fieldString should not be empty
      fieldString should include(" # ")

      gameBoard.isCellContent(1, 1, 'O') shouldEqual true
      gameBoard.isCellContent(1, 2, 'O') shouldEqual true
      gameBoard.isCellContent(2, 2, 'O') shouldEqual true
      gameBoard.isCellContent(2, 3, 'O') shouldEqual true
      gameBoard.isCellContent(3, 3, 'O') shouldEqual true
      gameBoard.isCellContent(3, 4, 'O') shouldEqual true
      gameBoard.isCellContent(4, 4, 'O') shouldEqual true
      gameBoard.isCellContent(4, 5, 'O') shouldEqual true
    }
  }
}