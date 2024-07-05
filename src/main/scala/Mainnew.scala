import model.modelComponent.modelImpl.{GameBoard, Player, ShipType}
import controller.controllerComponent.controllerImpl.Controller
import controller.controllerComponent.controllerIf
import aview.TUI
import aview.swingGui.GUI

import scala.io.StdIn.*
import java.awt.GraphicsEnvironment
import com.google.inject.Injector
import com.google.inject.Guice

object Main {
 
  def main(args: Array[String]): Unit = {
    val injector: Injector = Guice.createInjector(new Shipmodule, new FileIOModule)

    println("Geben sie die Feldgröße an")
    var size = readInt()

    val controller = injector.getInstance(classOf[controllerIf])
    // val controller = summon[controllerIf]
    val tui1 = new TUI(controller,1)
    val tui2 = new TUI(controller,2)
    controller.add(tui1)
    controller.add(tui2)
    

    if (!GraphicsEnvironment.isHeadless) {

      val gui = new GUI(controller)
      controller.add(gui)

    } else {

      println("GUI Anwendung wird nicht untersützt, demnach nicht gestartet.")
      
    }

    //controller.processJSON("src/main/scala/files/jsondatei.json")
    //controller.processXML("src/main/scala/files/xmldatei.xml")

    println("Geben sie 'start' ein um das spiel zu starten, oder 'exit' um es zu beenden.")

    var input: String = ""
    input = readLine()

    while (input != null) {

      input match {
        case "start" => 
          tui1.processInput(input) 
          tui2.processInput(input)
          tui2.gameLoop()

        case "exit" => sys.exit(0)
        case _ => 
          println("Ungültiger Befehl. Bitte 'start' oder 'exit' eingeben.")
          input = readLine()
      }
    }
  }
}