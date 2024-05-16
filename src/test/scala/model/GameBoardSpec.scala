package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameBoardSpec extends AnyWordSpec {
  val ship1 = Ship(ShipType.Carrier, ShipSize.Five)
  val ship2 = Ship(ShipType.Battleship, ShipSize.Five)
  val ship3 = Ship(ShipType.Destroyer, ShipSize.Four)
  val ship4 = Ship(ShipType.Submarine, ShipSize.Three)
  val ship5 = Ship(ShipType.PatrolBoat, ShipSize.Two)

  "GameBoard" should {
    "generate a field" in {
      val gameBoard = GameBoard(9)
      gameBoard.generateField()
      gameBoard.printField() should not be empty
      val fieldString = gameBoard.printField()
      fieldString should include(" # ")
      gameBoard.printField().length should be > 0
    }

    "manage cells" in {
      val gameBoard = GameBoard(9)
      gameBoard.generateField()
      gameBoard.cell(1, 1, Some('x')) shouldEqual Some('x')
      gameBoard.isCellContent(1, 1, 'x') shouldEqual true
      gameBoard.cell(10, 10) shouldEqual None
    }

    "place ships" in {
      val gameBoard = GameBoard(9)
      gameBoard.generateField()
      gameBoard.placeShip(ship1, (1, 1), 'h') shouldEqual true
      gameBoard.placeShip(ship2, (9, 9), 'h') shouldEqual false
      gameBoard.placeShip(ship3, (1, 2), 'h') shouldEqual false
      gameBoard.placeShip(ship4, (2, 2), 'h') shouldEqual true
      gameBoard.placeShip(ship5, (1, 2), 'h') shouldEqual false
    }

    "attack ships" in {
      val gameBoard = GameBoard(9)
      gameBoard.generateField()
      gameBoard.placeShip(ship1, (1, 1), 'h')
      gameBoard.placeShip(ship2, (2, 2), 'h')
      gameBoard.placeShip(ship3, (3, 3), 'h')
      gameBoard.placeShip(ship4, (4, 4), 'h')
      gameBoard.placeShip(ship5, (5, 5), 'h')
      gameBoard.attack((1, 1)) shouldEqual true
      gameBoard.attack((1, 1)) shouldEqual false
      gameBoard.attack((1, 6)) shouldEqual false
      gameBoard.attack((2, 2)) shouldEqual true
      gameBoard.attack((3, 3)) shouldEqual true
      gameBoard.attack((3, 3)) shouldEqual false
      gameBoard.attack((4, 4)) shouldEqual true
      gameBoard.attack((5, 5)) shouldEqual true
    }

    "indicate game over" in {
      val gameBoard = GameBoard(9)
      gameBoard.generateField()
      gameBoard.placeShip(ship1, (1, 1), 'h')
      gameBoard.attack((1, 1))
      gameBoard.attack((1, 2))
      gameBoard.attack((1, 3))
      gameBoard.attack((1, 4))
      gameBoard.attack((1, 5))
      gameBoard.isGameOver() shouldEqual true
    }

    "printField method with hidden=true" should {
      "return a string with hidden cells" in {
        val gameBoard = GameBoard(9)
        gameBoard.generateField()
        gameBoard.placeShip(ship1, (1, 1), 'h')
        gameBoard.attack((1, 1))
        gameBoard.attack((1, 2))
        gameBoard.attack((1, 3))
        gameBoard.attack((1, 4))
        gameBoard.attack((1, 7))
        val fieldString = gameBoard.printField(hidden = true)
        fieldString should include(" # ")
        fieldString should include ("X")
        fieldString should  include("*")
      }
    }

    "GameBoard printField method with hidden=false" should {
    "return a string with revealed cells" in {
      val gameBoard = GameBoard(9)
      gameBoard.generateField()
      gameBoard.generateField()
      gameBoard.placeShip(ship1, (1, 1), 'h')
      gameBoard.attack((1, 1))
      gameBoard.attack((1, 2))
      gameBoard.attack((1, 3))
      gameBoard.attack((1, 4))
      gameBoard.attack((1, 7))
      val fieldString = gameBoard.printField(hidden = false)
      fieldString should include(" # ")
      fieldString should include("X")
      fieldString should include("*")
    }
  }

    "placeShip method with horizontal orientation" should {
      "return true if ship can be placed" in {
        val gameBoard = GameBoard(9)
        gameBoard.generateField()
        val ship = Ship(ShipType.Carrier, ShipSize.Five)
        gameBoard.placeShip(ship, (1, 1), 'h') shouldEqual true
      }
    }

    "placeShip method with vertical orientation" should {
      "return true if ship can be placed" in {
        val gameBoard = GameBoard(9)
        gameBoard.generateField()
        val ship = Ship(ShipType.Carrier, ShipSize.Five)
        gameBoard.placeShip(ship, (1, 1), 'v') shouldEqual true
      }
    }
    "GameBoard getSize method" should {
      "return the size of the game board" in {
        val size = 9
        val gameBoard = GameBoard(size)
        gameBoard.getSize() shouldEqual size
      }
    }
    "GameBoard getShipsToPlace method" should {
      "return a list of ships to place" in {
        val gameBoard = GameBoard(9)
        val expectedShips = List(
          Ship(ShipType.Carrier, ShipSize.Five),
          Ship(ShipType.Destroyer, ShipSize.Four),
          Ship(ShipType.Submarine, ShipSize.Three),
          Ship(ShipType.PatrolBoat, ShipSize.Two)
        )
        gameBoard.getShipsToPlace() shouldEqual expectedShips
      }
    }
}
}
