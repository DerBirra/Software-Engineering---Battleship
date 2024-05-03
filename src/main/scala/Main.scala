package main.scala

import model.{GameField, Player, Ship, Battleship}
import controller.Controller
import view.TUI
import scala.io.StdIn.readLine


object Main {

    def main(args: Array[String]): Unit = {

      println("Bitte geben Sie ihren Namen ein:")
      val student = Player(scala.io.StdIn.readLine())

      println("Willkommen " + student.name + " Bitte geben Sie eine gewünschte Feld größe ein:")
      val fieldSize = scala.io.StdIn.readInt()

      val controller = new Controller(new GameField(fieldSize, fieldSize))

      val tui = new TUI(controller)
      controller.notifyObservers

      var input: String = ""

      println("Geben sie den Befehl zum starten des Spiels ein: start")

      input = readLine()
      tui.startGame(input)

      while(input != "exit") {

        input = readLine()
        tui.startGame(input)

      }

  }

}