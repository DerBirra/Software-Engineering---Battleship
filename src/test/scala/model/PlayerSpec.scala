package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Ship
import model.GameField
import model.Player
import scala.annotation.meta.field

class PlayerSpec extends AnyWordSpec{
 "Player" should {
    
    "be created with a name" in {
      val playerName = "Alice"
      val player = Player(playerName)
      player.name shouldEqual playerName
    }
}
}
