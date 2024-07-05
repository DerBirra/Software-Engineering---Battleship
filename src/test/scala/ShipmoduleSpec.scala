import com.google.inject.{Guice, Injector}
import model.modelComponent.GameBoardInterface
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.modelComponent.modelImpl.GameBoard
import controller.controllerComponent.controllerIf
import controller.controllerComponent.controllerImpl.Controller

class ShipmoduleSpec extends AnyWordSpec {

  "A Shipmodule" should {
    "bind GameBoardInterface to GameBoard and controllerIf to Controller" in {
      val injector: Injector = Guice.createInjector(new Shipmodule)

      // Verify GameBoardInterface binding
      val gameBoard1 = injector.getInstance(classOf[GameBoardInterface])
      gameBoard1 shouldBe a[GameBoard]
      gameBoard1.getSize() shouldEqual 9

      // Verify controllerIf binding
      val controller = injector.getInstance(classOf[controllerIf])
      controller shouldBe a[Controller]
      
      // Ensure the controller uses the correct game boards
      controller.asInstanceOf[Controller].gameBoard1 shouldBe gameBoard1
      controller.asInstanceOf[Controller].gameBoard2 shouldBe a[GameBoardInterface]
      controller.asInstanceOf[Controller].gameBoard2.getSize() shouldEqual 9
    }
  }
}