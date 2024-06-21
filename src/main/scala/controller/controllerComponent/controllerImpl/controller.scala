package controller.controllerComponent.controllerImpl

import util.memento.{GameState, GameMemento, Caretaker}
import util.Observable
import util.command.Command
import model.modelComponent.modelImpl.{GameBoard,Ship, Position, PlaceShipStategyTemplate}
import util.command.{PlaceShipCommand, AttackCommand}
import scala.util.{Try, Success, Failure}
import model.modelComponent.GameBoardInterface
import controller.controllerComponent.controllerIf

class Controller(var gameBoard1: GameBoardInterface, var gameBoard2: GameBoardInterface) extends controllerIf{
    
    private var caretaker: Caretaker = new Caretaker()
    private var currentPlayer: Int = 1
    private var state: GameState = GameState(gameBoard1, gameBoard2, currentPlayer)
    private var placeShipStrategy: Option[PlaceShipStategyTemplate] = None

    def setPlaceShipStrategy(strategy: PlaceShipStategyTemplate): Unit = {
        placeShipStrategy = Some(strategy)
    }

    def startGame(player: Int): Unit = {

        if(player == 2) return

        state.gameBoard1.generateField()
        state.gameBoard2.generateField()
        notifyObservers

    }

    def startGameWithStrategy(player: Int): Unit = {
       (placeShipStrategy) match  {
            case Some(strat) => 
            if(player == 1){
                gameBoard1 = strat.createNewGameField(gameBoard1.getSize()) 
                state = GameState(gameBoard1, gameBoard2, currentPlayer)
            }
            if (player == 2){
                gameBoard2 =strat.createNewGameField(gameBoard2.getSize())
                state = GameState(gameBoard1, gameBoard2, currentPlayer)
            }
            
            notifyObservers
        case None => startGame(player)
        } 
    }

    def isGameOver(): Boolean = state.gameBoard1.isGameOver() || state.gameBoard2.isGameOver()

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

    def placeShip(player: Int, ship: Ship, position: (Int, Int), orientation: Char): Try[Unit] = {
        val result = if (player == 1) state.gameBoard1.placeShip(ship, position, orientation)
                     else state.gameBoard2.placeShip(ship, position, orientation)
        result match {
            case Success(_) =>
                val command = new PlaceShipCommand
                executeCommand(command)
                notifyObservers
                Success(())
            case Failure(exception) =>
                Failure(exception)
        }
    }

    def makeMove(player: Int, position: Position): Boolean = {

        val x = position.getX()
        val y = position.getY()
         val command = new AttackCommand

        val hit = if (player == 1) state.gameBoard2.attack((x,y)) else state.gameBoard1.attack((x,y))
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

    def getCurrentPlayer: Int = currentPlayer
}