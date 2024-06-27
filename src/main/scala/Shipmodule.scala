import controller.controllerComponent.controllerIf
import controller.controllerComponent.controllerImpl.Controller
import model.modelComponent.GameBoardInterface
import model.modelComponent.modelImpl.GameBoard
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class Shipmodule extends AbstractModule with ScalaModule{
    override def configure(): Unit = {
    bind(classOf[GameBoardInterface])
      .toInstance(GameBoard(size = 9)) // You can modify the size as needed

    bind(classOf[controllerIf])
      .to(classOf[Controller])
  }
    // given GameBoardInterface = GameBoard(size = 9)
    // given controllerIf = Controller(summon[GameBoardInterface], summon[GameBoardInterface])
}
