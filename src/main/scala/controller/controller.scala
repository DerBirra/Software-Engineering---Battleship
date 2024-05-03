package controller

import model.GameField
import util.Observable
import sys.process._


class Controller(var gameField:GameField) extends Observable {

    def createGameField(row: Int, cell: Int): Unit = {

        gameField = new GameField(row,cell)

        clearConsole()
        println(gameField.toString())
        notifyObservers

    }

    def updateGameField: String = gameField.toString

    def getGamFieldSize: Int = {

        return gameField.getGamFieldSize()

    }


    def clearConsole(): Unit = {
    
        println("\u001b[2J")
    
    }
  


}
