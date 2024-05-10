import model.{GameField, Player, Ship, Battleship}
import controller.controller
import aview.TUI
import scala.io.StdIn.readLine
object Battleships{
  def main(args: Array[String]): Unit = {
      val controller = new controller()

      val tui = new TUI(controller)
      controller.notifyObservers

      var input: String = ""

      println("Geben sie den Befehl zum starten des Spiels ein: start,exit")

      input = readLine()
      tui.startGame(input)

      while(input != "exit") {

        input = readLine()
        tui.startGame(input)

      }

  }
}