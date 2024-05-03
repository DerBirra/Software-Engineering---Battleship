package view

import model.{GameField, Player}
import controller.Controller
import util.Observer
import sys.process._


class TUI(controller: Controller) extends Observer {



    val gameField = new GameField(controller.getGamFieldSize, controller.getGamFieldSize)

    def startGame(input: String): Unit = {

        var gameState = false

        input match {

            case "start" => println("Info >> Spiel wird gestartet.")
                        controller.createGameField(controller.getGamFieldSize, controller.getGamFieldSize)
                        gameState = true
        }
        
        while (gameState) {

            var row = 0
            var col = 0

            println("Input >> Bitte Spalte angeben in der geschossen werden soll.")

            val rowInput = scala.io.StdIn.readLine()

            rowInput.toInt match {

                case n if n <= 0 && n >= controller.getGamFieldSize + 1 => println("Warnung >> Falsche angabe. Bitte einen Wert ueber 0 und unter " + (controller.getGamFieldSize+1) + " angeben.")

                case n if n > 0 && n < controller.getGamFieldSize + 1 => println("Info >> Spalte makiert.")
                                                        row = rowInput.toInt

                case _ => println("Warnung >> Falsche angabe. Es wurde ein unerwarteter Wert angegeben. Spiel wird beendet.")
                          gameState = false

            }

            println("Input >> Bitte Zeile angeben in der geschossen werden soll.")

            val colInput = scala.io.StdIn.readLine()

            colInput.toInt match {

                case n if n <= 0 && n >= controller.getGamFieldSize + 1 => println("Warnung >> Falsche angabe. Bitte einen Wer ueber 0 und unter " + (controller.getGamFieldSize+1) + " angeben.")

                case n if n > 0 && n < controller.getGamFieldSize + 1 => println("Info >> Zeile makiert.")
                                                        col = colInput.toInt

                case _ => println("Warnung >> Falsche angabe. Es wurde ein unerwarteter Wert angegeben. Spiel wirde beendet.")
                          gameState = false

            }

            val isCell = gameField.isCellContent(row, col, 'x')

            if (isCell) {
                
                println("Warnung >> Bitte eine noch nicht verwendete Koordinate nehmen.")

            } else {

                gameField.cell(row, col, Some('x'))
                println(controller.updateGameField)

            
            }

           

        }

        
    }

     override def update: Unit = println(controller.updateGameField)

}
