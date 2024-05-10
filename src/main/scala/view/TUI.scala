package view

import model.{GameField, Player}
import controller.Controller
import util.Observer


class TUI(controller: Controller) extends Observer {

    def startGame(input: String): Unit = {

        val player = controller.createPlayer()
        var gameField = controller.createGameField()

        controller.placeShips(gameField, gameField.getShipsToPlace())

        gameLoop(gameField)

    }

    private def gameLoop(gameField: GameField): Unit = {

        gameField.toString()

        var gameState = true

        while (gameState) {

            var exit = gameField.attackOnce(gameField)

            if(exit == true){
                gameState = false
                println("Das Spiel wurde beendet.")
                return
            }

            //println(gameField.toString())
            update

        }
    
    } 

    override def update: Unit = println(controller.updateGameField)

}
