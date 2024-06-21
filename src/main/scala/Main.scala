package main.scala

import model.{GameBoard, Player, ShipType}
import controller.Controller
import view.TUI
import view.swing.GUI
import scala.io.StdIn.*
import scala.io.AnsiColor.*


object Main {
 
  def main(args: Array[String]): Unit = {

    println(s"$GREEN" +"Geben sie die Feldgröße an")
    var size = readInt()

    var gameBoard1 = new GameBoard(size)
    var gameBoard2 = new GameBoard(size)
    var controller = new Controller(gameBoard1, gameBoard2)
    
    initializeGUI(controller)
    initializeTUI(controller)
    
    

    
  }

  def initializeTUI(controller: Controller): Unit = {

    val tui = new TUI(controller, 1)
    controller.add(tui)


    println(s"$GREEN" +"Geben sie 'start' oder 'exit' ein." + s"$RESET")

    var input: String = ""
    input = readLine()

    tui.processInput(input)



  }

  def initializeGUI(controller: Controller): Unit = {

    val gui = new GUI(controller)
    controller.add(gui)


  }
}