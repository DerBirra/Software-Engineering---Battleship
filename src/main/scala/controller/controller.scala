package controller

import util.{Observable, GameState, GameMemento}
import model.{GameBoard,Ship, Position}

class Controller(var gameBoard1: GameBoard, var gameBoard2: GameBoard) extends Observable{

    private var state: GameState = _

    def startGame(): Unit = {

        gameBoard1.generateField()
        gameBoard2.generateField()
        notifyObservers

    }

    def isGameOver(): Boolean = {

        return gameBoard1.isGameOver() || gameBoard2.isGameOver()

    }

    def getShipsToPlace(player: Int): List[Ship] = {

        if (player == 1) gameBoard1.getShipsToPlace()
        else gameBoard2.getShipsToPlace()

    }

    def isCellContent(player: Int, row: Int, col: Int, or: Char): Boolean = {

        if (player == 1) gameBoard1.isCellContent(row,col,or)
        else gameBoard2.isCellContent(row,col,or)

    }

    def getGameBoardSize(): Int = {

        return gameBoard1.getSize()

    }

    def placeShip(player: Int, ship: Ship, position: (Int, Int), orientation: Char): Boolean = {

        var state = false

        if (player == 1) state = gameBoard1.placeShip(ship,position,orientation)
        else state = gameBoard2.placeShip(ship,position,orientation)
        notifyObservers
        return state
        


    }

    def makeMove(player: Int, position: Position): Boolean = {

        val x = position.getX()
        val y = position.getY()

        val hit = if (player == 1) gameBoard2.attack(new Position(position.getX(),position.getY())) else gameBoard1.attack(new Position(position.getX(),position.getY()))
        notifyObservers
        hit

    }

    def getPlayerBoard(player: Int, hidden: Boolean = true): String = {
    
        if (player == 1) gameBoard1.printField(hidden) else gameBoard2.printField(hidden)
    
    }

    def getOpponentBoard(player: Int): String = {
    
        if (player == 1) gameBoard2.printField(hidden = true) else gameBoard1.printField(hidden = true)
    
    }

    def setState(state: GameState): Unit = {
        this.state = state
    }

    def getState: GameState = {
        state
    }

    def createMemento(): GameMemento = {
        new GameMemento(state)
    }

    def restoreMemento(memento: GameMemento): Unit = {
        state = memento.getState
    }

}
