package main.scala

import model.{GameField, Player, Ship, Battleship}
import controller.Controller
import view.TUI
import scala.io.StdIn.readLine


object Main {
 
    def main(args: Array[String]): Unit = {

      val controller = new Controller()

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