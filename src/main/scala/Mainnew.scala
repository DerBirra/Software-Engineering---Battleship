import model.{GameBoard, Player, ShipType}
import controller.Controller
import aview.TUI
import scala.io.StdIn.*

object Main {
 
  def main(args: Array[String]): Unit = {

    println("Geben sie die Feldgröße an")
    var size = readInt()

    val gameBoard1 = new GameBoard(size)
    val gameBoard2 = new GameBoard(size)
    val controller = new Controller(gameBoard1, gameBoard2)
    val tui1 = new TUI(controller,1)
    val tui2 = new TUI(controller,2)

    controller.add(tui1)
    controller.add(tui2)

    println("Geben sie 'start' ein um das spiel zu starten, oder 'exit' um es zu beenden.")

    var input: String = ""
    input = readLine()

    while (input != null) {

      input match {
        case "start" => tui1.processInput(input)
        case "exit" => sys.exit(0)
        case _ => 
          println("Ungültiger Befehl. Bitte 'start' oder 'exit' eingeben.")
          input = readLine()
      }
    }
  }
}