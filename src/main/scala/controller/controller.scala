package controller

import util.memento.{GameState,GameMemento,Caretaker}
import util.observer.Observable
import util.command.Command
import util.command.{PlaceShipCommand,AttackCommand}
import model.{GameBoard,Ship, Position}

class Controller(var gameBoard1: GameBoard, var gameBoard2: GameBoard) extends Observable{

    private var caretaker: Caretaker = new Caretaker()
    private var currentPlayer: Int = 1
    private var state: GameState = _

    def startGame(): Unit = {

        state = GameState(gameBoard1, gameBoard2, currentPlayer)

        state.gameBoard1.generateField()
        state.gameBoard2.generateField()
        notifyObservers

    }

    def isGameOver(): Boolean = {

        return state.gameBoard1.isGameOver() || state.gameBoard2.isGameOver()

    }

    def getShipsToPlace(player: Int): List[Ship] = {

        if (player == 1) state.gameBoard1.getShipsToPlace()
        else state.gameBoard2.getShipsToPlace()

    }

    def isCellContent(player: Int, row: Int, col: Int, or: Char): Boolean = {

        if (player == 1) state.gameBoard1.isCellContent(row,col,or)
        else state.gameBoard2.isCellContent(row,col,or)

    }

    def getGameBoardSize(): Int = {

        return state.gameBoard1.getSize()

    }

    def placeShip(player: Int, ship: Ship, position: (Int, Int), orientation: Char): Boolean = {

        var stateBoolean = false
        val command = new PlaceShipCommand
        

        if (player == 1) stateBoolean = state.gameBoard1.placeShip(ship,position,orientation)
        else stateBoolean = state.gameBoard2.placeShip(ship,position,orientation)

        executeCommand(command)
        notifyObservers
        stateBoolean
        


    }

    def makeMove(player: Int, position: Position): Boolean = {

        val x = position.getX()
        val y = position.getY()
        val command = new AttackCommand

        val hit = if (player == 1) state.gameBoard2.attack(new Position(position.getX(),position.getY())) else state.gameBoard1.attack(new Position(position.getX(),position.getY()))
        
        executeCommand(command)
        notifyObservers
        hit

    }

    def getPlayerBoard(player: Int, hidden: Boolean = true): String = {
    
        if (player == 1) state.gameBoard1.printField(hidden) else state.gameBoard2.printField(hidden)
    
    }

    def getOpponentBoard(player: Int): String = {
    
        if (player == 1) state.gameBoard2.printField(hidden = true) else state.gameBoard1.printField(hidden = true)
    
    }

    def executeCommand(command: Command): Unit = {

        command.execute(this)

    }

    def saveState(): Unit = {

        caretaker.addMemento(GameMemento(state))

    }

    def restoreState(): Unit = {

        caretaker.getMemento.foreach(memento => state = memento.getState)
        notifyObservers
    
    }

    def setCurrentPlayer(player: Int): Unit = {
        this.currentPlayer = player
    }

    def getCurrentPlayer: Int = {
        currentPlayer
    }

}
