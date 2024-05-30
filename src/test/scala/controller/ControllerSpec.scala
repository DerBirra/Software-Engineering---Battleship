package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.{GameBoard, Ship, ShipType, ShipSize, Position}

class ControllerSpec extends AnyWordSpec {
  val gameBoard1 = GameBoard(9)
  val gameBoard2 = GameBoard(9)
  val controller = new Controller(gameBoard1, gameBoard2)

  "Controller" should {
        "start the game" in {
        controller.startGame(1)
        gameBoard1.printField() should not be empty
        gameBoard2.printField() should not be empty
        }

        "get ships to place" in {
        val shipsToPlacePlayer1 = controller.getShipsToPlace(1)
        val shipsToPlacePlayer2 = controller.getShipsToPlace(2)
        shipsToPlacePlayer1 should not be empty
        shipsToPlacePlayer2 should not be empty
        // Ensure the returned list contains valid ships
        // This might require mocking or stubbing the GameBoard methods
        }

        "check cell content" in {
        val cellContentPlayer1 = controller.isCellContent(1, 1, 1, 'h')
        val cellContentPlayer2 = controller.isCellContent(2, 1, 1, 'h')
        cellContentPlayer1 shouldEqual false // Assuming the cell is empty initially
        cellContentPlayer2 shouldEqual false // Assuming the cell is empty initially
        }

        "get game board size" in {
        val boardSize = controller.getGameBoardSize()
        boardSize shouldEqual 9 // Assuming the board size is set to 9 in the constructor
        }

        "place ship" in {
        val ship = Ship(ShipType.Carrier, ShipSize.Five)
        val position = (1, 1)
        val orientation = 'h'
        controller.placeShip(1, ship, position, orientation) shouldEqual true
        controller.placeShip(2, ship, position, orientation) shouldEqual true
        // Check if ship is correctly placed on the board
        // This might require mocking or stubbing the GameBoard methods
        }

        "make move" in {
        val position = Position(1, 1)
        val hit = controller.makeMove(1, position)
        val hit2 = controller.makeMove(2, position)
        hit shouldEqual true // Assuming the move results in a hit
        hit2 shouldEqual true
        }

        "get player board" in {
        val player1Board = controller.getPlayerBoard(1)
        val player2Board = controller.getPlayerBoard(2)
        player1Board should not be empty
        player2Board should not be empty
        // Check if the returned board strings are valid
        }

        "get opponent board" in {
        val opponentBoardPlayer1 = controller.getOpponentBoard(1)
        val opponentBoardPlayer2 = controller.getOpponentBoard(2)
        opponentBoardPlayer1 should not be empty
        opponentBoardPlayer2 should not be empty
        // Check if the returned board strings are valid
        }
        "Controller isGameOver method" should {
            "return true if either game board is over" in {
                val gameBoard1 = GameBoard(9)
                val gameBoard2 = GameBoard(9)
                val controller = new Controller(gameBoard1, gameBoard2)
        
                // Assuming gameBoard1 is over
                gameBoard1.placeShip(Ship(ShipType.Carrier, ShipSize.Five), (1, 1), 'h')
                gameBoard1.attack((1, 1))
                gameBoard1.attack((1, 2))
                gameBoard1.attack((1, 3))
                gameBoard1.attack((1, 4))
                gameBoard1.attack((1, 5))
                controller.isGameOver() shouldEqual true
            }
        }

        "Controller makeMove method for player 1" should {
            "make move on gameBoard1" in {
                val gameBoard1 = GameBoard(9)
                val gameBoard2 = GameBoard(9)
                val controller = new Controller(gameBoard1, gameBoard2)
                
                val position = Position(1, 1)
                
                val hit = controller.makeMove(1, position)
                
                hit shouldEqual false // Assuming the move is a miss
            }
        }

    }
}
