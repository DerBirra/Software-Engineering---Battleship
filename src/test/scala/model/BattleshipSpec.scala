package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Ship
import model.GameField

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
    }
}
