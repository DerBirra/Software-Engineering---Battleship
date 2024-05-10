package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayerSpec extends AnyWordSpec {

  "Player" should {
    "initialize correctly with a name" in {
      val playerName = "TestPlayer"
      val player = Player(playerName)
      player.name shouldEqual playerName
    }

    "have correct string representation" in {
      val playerName = "TestPlayer"
      val player = Player(playerName)
      player.toString() shouldEqual playerName
    }
  }
}
