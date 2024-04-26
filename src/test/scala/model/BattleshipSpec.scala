package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Ship
import model.GameField
import scala.annotation.meta.field

class BattleshipSpec extends AnyWordSpec{

    "Battleship" should{
        "Ships" in{
            val ship1 = Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
            ship1.size shouldEqual Shipsize.Five
            val ship2 = Battleship(Ship.Destroyer, Shipsize.Three, Shipindex.Two)
            ship2.stype shouldEqual Ship.Destroyer
            val ship3 = model.Battleship(Ship.Carrier, Shipsize.Three, Shipindex.Two)
            ship3.index shouldEqual Shipindex.Two
        }
        "Field" in{
         val gameField = new GameField(9,9)
         val testField = new GameField(9,9)
         gameField.generateField()
         testField.generateField()
         /*val expectedField = 
            """+---------------------------+
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
| #  #  #  #  #  #  #  #  # |
+---------------------------+"""*/
         gameField.printField() shouldEqual testField.printField()
        }
    }

    "Player"in{
        val player = Player("Miki")
        player.name shouldEqual "Miki"
    }
    "Cell" in{
        val field = new GameField(9,9)
        field.generateField()
        field.cell(1,1,Some('x')) shouldEqual Some('x')
        field.isCellContent(1,1,'x') shouldEqual true
    }
}
