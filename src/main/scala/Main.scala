package main.scala

import model.{GameBoard, Player, ShipType}
import controller.Controller
import view.TUI
import scala.io.StdIn.*
import scala.io.AnsiColor.*


object Main {
 
  def main(args: Array[String]): Unit = {

    println(s"$GREEN" +"Geben sie die Feldgröße an")
    var size = readInt()

    val gameBoard1 = new GameBoard(size)
    val gameBoard2 = new GameBoard(size)
    val controller = new Controller(gameBoard1, gameBoard2)
    val tui1 = new TUI(controller,1)

    controller.add(tui1)

    println(s"$GREEN" +"Geben sie 'start', 'load', 'save' oder 'exit' ein.")

    var input: String = ""
    input = readLine()

    while (input != null) {

      tui1.processInput(input)

    }
  }
}