package controller.controllerComponent

import util.memento.{GameState, GameMemento, Caretaker}
import util.Observable
import util.command.Command
import model.modelComponent.modelImpl.{GameBoard, Ship, Position, PlaceShipStategyTemplate}
import scala.util.{Try, Success, Failure}

trait controllerIf extends Observable {
  def setPlaceShipStrategy(strategy: PlaceShipStategyTemplate): Unit

  def startGame(player: Int): Unit

  def startGameWithStrategy(player: Int): Unit

  def isGameOver(): Boolean

  def getShipsToPlace(player: Int): List[Ship]

  def isCellContent(player: Int, row: Int, col: Int, or: Char): Boolean

  def getGameBoardSize(): Int

  def placeShip(player: Int, ship: Ship, position: (Int, Int), orientation: Char): Try[Unit]

  def makeMove(player: Int, position: Position): Boolean

  def getPlayerBoard(player: Int, hidden: Boolean = true): String

  def getOpponentBoard(player: Int): String

  def executeCommand(command: Command): Unit

  def saveState(): Unit

  def restoreState(): Unit

  def setCurrentPlayer(player: Int): Unit

  def getCurrentPlayer: Int

  def getShotsCount(player: Int): Int 

  def processXML(filePath: String): Unit

  def processJSON(filePath: String): Unit

  def saveToJSON(): Unit

  def saveToXML(): Unit
}
