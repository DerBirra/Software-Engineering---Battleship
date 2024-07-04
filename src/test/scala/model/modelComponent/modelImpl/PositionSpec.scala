package model.modelComponent.modelImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PositionSpec extends AnyWordSpec {
  "A Position" should {
    "return correct X and Y coordinates" in {
      val position = Position(3, 5)
      position.getX() shouldEqual 3
      position.getY() shouldEqual 5
    }
  }
}
