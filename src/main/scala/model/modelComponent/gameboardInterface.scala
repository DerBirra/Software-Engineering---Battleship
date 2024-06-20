package model.modelComponent

import model.Ship
import scala.util.Try

trait GameBoardInterface {
  def generateField(): Unit

  def printField(hidden: Boolean = true): String

  def placeShip(ship: Ship, position: (Int, Int), orientation: Char): Try[Unit]

  def getShipsToPlace(): List[Ship]

  def cell(rowIdx: Int, colIdx: Int, value: Option[Char] = None): Option[Char]

  def isCellContent(rowIdx: Int, colIdx: Int, char: Char): Boolean

  def isGameOver(): Boolean

  def attack(position: (Int, Int)): Boolean

  def getSize(): Int
}
