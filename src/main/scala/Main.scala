package main.scala

import model.{GameBoard, Player, ShipType, TimerAddon}
import controller.Controller
import view.TUI
import scala.io.StdIn.*
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.{Success, Failure}


object Main {
 
  def main(args: Array[String]): Unit = {

    println("Geben sie die Feldgröße an")
    var size = readInt()

    val gameBoard1 = new GameBoard(size)
    val gameBoard2 = new GameBoard(size)
    val controller = new Controller(gameBoard1, gameBoard2)
    val tui1 = new TUI(controller,1)



    controller.add(tui1)

    println("Geben sie 'start' ein um das spiel zu starten, oder 'exit' um es zu beenden.")

    var input: String = ""
    input = readLine()

    while (input != null) {

      tui1.processInput(input)

    }
  }
}