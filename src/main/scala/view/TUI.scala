package view

import model.{GameField, Player}
import controller.Controller
import util.Observer
import sys.process._


class TUI(controller: Controller) extends Observer {



    val gameField = new GameField(controller.getGamFieldSize, controller.getGamFieldSize)

    def startGame(input: String): Unit = {

        var gameState = true
        println(gameField.toString())
        controller.placeShips()


        while (gameState) {

            println("Bitte geben Sie die Zeile an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")
            val rowInput = scala.io.StdIn.readLine()

            rowInput.toLowerCase match {

                case "exit" =>
                println("Das Spiel wurde beendet.")
                gameState = false

                case _ =>
                
                rowInput.toIntOption match {

                case Some(row) if row >= 0 && row < gameField.getGamFieldSize() =>
                println("Bitte geben Sie die Spalte an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")
                val colInput = scala.io.StdIn.readLine()

                colInput.toLowerCase match {
                case "exit" =>
                println("Das Spiel wurde beendet.")
                gameState = false

                case _ =>
                
                    colInput.toIntOption match {
                    
                        case Some(col) if col >= 0 && col < gameField.getGamFieldSize() =>
                        
                            val isCell = gameField.isCellContent(row, col, 'x')
                            
                            if (isCell) {
                            
                            println("Bitte eine noch nicht verwendete Koordinate nehmen.")
                            
                            } else {
                            
                            gameField.cell(row, col, Some('x'))
                            println(gameField.toString())
                            
                            }

                        case _ =>

                        println("Ungültige Eingabe für die Spalte.")

                    }
              
                }

                case _ =>

                    println("Ungültige Eingabe für die Zeile.")

                }
            }
        }   

    }

     override def update: Unit = println(controller.updateGameField)

}
