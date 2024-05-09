package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Ship
import model.GameField
import scala.annotation.meta.field

class GameFieldSpec extends AnyWordSpec {
    val ship1 = Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
    val ship2 = Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two)
    val ship3 = model.Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two)
    val ship4 = model.Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two)
    val ship5 = model.Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two)

    "Gamefield"should{
        "Field" in{
            val gameField = new GameField(9,9)
            val testField = new GameField(9,9)
            gameField.generateField()
            testField.generateField()

            gameField.printField() should not be empty
            testField.printField() should not be empty

            val fieldString = gameField.printField()
            fieldString should include(" # ") 
            
            gameField.printField() shouldEqual testField.printField()

            gameField.printField().length should be > 0 
            testField.printField().length should be > 0
        }
        "Cell" in{
            val field = new GameField(9,9)
            field.generateField()
            field.cell(1,1,Some('x')) shouldEqual Some('x')
            field.isCellContent(1,1,'x') shouldEqual true
            field.cell(10, 10) shouldEqual None
        }
        "Placing"in{
            val placeField = new GameField(9,9)
            placeField.generateField()
            placeField.placeShip(ship1,(1,1), 'h') shouldEqual true
            placeField.placeShip(ship2,(9,9), 'h') shouldEqual false
            placeField.placeShip(ship3,(1,2), 'h') shouldEqual false
            placeField.placeShip(ship4,(2,2), 'h') shouldEqual true
            placeField.placeShip(ship5,(1,2), 'h') shouldEqual false

            val largerShip = Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
            val anotherShip = Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two)
            val yetAnotherShip = Battleship(Ship.Battleship, Shipsize.Four, Shipindex.Two)
            val largeVerticalShip = Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two)

            placeField.placeShip(largerShip, (5, 5), 'h') shouldEqual true
            placeField.placeShip(anotherShip, (1, 5), 'v') shouldEqual false
            placeField.placeShip(yetAnotherShip, (1, 6), 'v') shouldEqual true
            placeField.placeShip(largeVerticalShip, (6, 1), 'v') shouldEqual true
            placeField.printField() should include("O")
        }
        "Attacking"in{
            val attackField = new GameField(9,9)
            attackField.generateField()
            attackField.placeShip(ship1,(1,1), 'h')
            attackField.placeShip(ship2,(2,2), 'h')
            attackField.placeShip(ship3,(3,3), 'h')
            attackField.placeShip(ship4,(4,4), 'h')
            attackField.placeShip(ship5,(5,5), 'h')
            attackField.attack((1,1)) shouldEqual true
            attackField.attack((1,1)) shouldEqual false
            attackField.attack((1,6)) shouldEqual false
            attackField.attack((2,2)) shouldEqual true
            attackField.attack((3,3)) shouldEqual true
            attackField.attack((3,3)) shouldEqual false
            attackField.attack((4,4)) shouldEqual true
            attackField.attack((5,5)) shouldEqual true
        }
        "Gameover"in{
            val ship1 = Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
            val attackField = new GameField(9,9)
            attackField.generateField()
            attackField.placeShip(ship1, (1, 1), 'h')
            attackField.attack((1,1)) 
            attackField.attack((1,2)) 
            attackField.attack((1,3)) 
            attackField.attack((1,4)) 
            attackField.attack((1,5)) 
            attackField.isGameOver() shouldEqual true
        }   
    }
}
