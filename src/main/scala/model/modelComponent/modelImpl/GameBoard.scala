package model.modelComponent.modelImpl

import model.modelComponent.modelImpl.{Ship,ShipType,Position}
import util.Observable

import scala.collection.mutable.ListBuffer
import scala.util.Try
import model.modelComponent.GameBoardInterface

case class GameBoard(size: Int) extends GameBoardInterface{

    var field: Array[Array[Char]] = Array.ofDim[Char](size, size)
    private var ships: List[(Int, Int)] = List() // Liste der Schiffpositionen

    def generateField(): Unit = {

        // Spielfeld mit Leerzeichen füllen
        for {

            i <- 0 until size
            j <- 0 until size

        } field(i)(j) = ' '
    }

    def printField(hidden: Boolean = true): String = {
        // Oberen Rand des Spielfelds erzeugen
        val top = "+" + "-" * size * 3 + "+\n"

        val horizontalCoords = "\n " + (1 until size+1).map(i => f"$i%2d ").mkString + "\n"

        // Feldinhalte erzeugen
        val fieldContent = (for {
            i <- 0 until size
            j <- 0 until size
        } yield {
            // Rahmen links
            val leftBorder = if (j == 0) "|" else ""

            // Inhalt der Zelle, wenn nicht leer als # und wenn bereits angegriffen als X
            val cellContent = if (field(i)(j) == ' ') {

            if (hidden) " # " else " # "

            } else if (field(i)(j) == 'X') {

                    " X "
            } else if (field(i)(j) == '*') {

                    " * "
            }else {

                if (hidden) " # " else s" ${field(i)(j)} "

            }

            // Rahmen rechts
            val rightBorder = if (j == size - 1) "| " +(1+i) +"\n" else ""

            leftBorder + cellContent + rightBorder
        }).mkString

        // Unteren Rand des Spielfelds erzeugen
        val bottom = "+" + "-" * size * 3 + "+\n"

        // Das gesamte Spielfeld zusammenbauen
        horizontalCoords + top + fieldContent + bottom
    }

     def placeShip(ship: Ship, position: (Int, Int), orientation: Char): Try[Unit] = {
        Try {
            val (shipRow, shipCol) = position

            val valShipSize = ship.size match {
                case ShipSize.Two => 2
                case ShipSize.Three => 3
                case ShipSize.Four => 4
                case ShipSize.Five => 5
            }

            val inBounds =
                if (orientation == 'h') shipCol + valShipSize - 1 <= size
                else shipRow + valShipSize - 1 <= size

            val cellsOccupied =
                if (orientation == 'h') (0 until valShipSize).forall(i => isCellContent(shipRow, shipCol + i, ' '))
                else (0 until valShipSize).forall(i => isCellContent(shipRow + i, shipCol, ' '))

            if (inBounds && cellsOccupied) {
                // Platzieren des Schiffs
                if (orientation == 'h') {
                    for (i <- 0 until valShipSize) {
                        cell(shipRow, shipCol + i, Some('O'))
                        ships = ships :+ (shipRow, shipCol + i)
                    }
                } else {
                    for (i <- 0 until valShipSize) {
                        cell(shipRow + i, shipCol, Some('O'))
                        ships = ships :+ (shipRow + i, shipCol)
                    }
                }
            } else {
                throw new IllegalArgumentException("Das Schiff kann nicht an dieser Position platziert werden.")
            }
        }
    }


    def getShipsToPlace(): List[Ship] = {

        List(Ship(ShipType.Carrier, ShipSize.Five),
             Ship(ShipType.Destroyer, ShipSize.Four),
             Ship(ShipType.Submarine, ShipSize.Three),
             Ship(ShipType.PatrolBoat, ShipSize.Two) )
             
    }

    def cell(rowIdx: Int, colIdx: Int, value: Option[Char] = None): Option[Char] = {
        // wenn die Koordinaten innerhalb des Spielfelds liegen
        if (rowIdx >= 1 && rowIdx <= size && colIdx >= 1 && colIdx <= size) {
        // Wenn ein Wert angegeben ist, setze die Zelle auf diesen Wert
        value.foreach { v =>
            field(rowIdx - 1)(colIdx - 1) = v
        }
        // Gib den aktuellen Wert der Zelle zurück
        Some(field(rowIdx - 1)(colIdx - 1))
        } else {
        // Fehlermeldung ausgeben und None zurückgeben
        println("Ungültige Zellkoordinaten")
        None
        }
    }

    def isCellContent(rowIdx: Int, colIdx: Int, char: Char): Boolean = {

        cell(rowIdx, colIdx) match {
            case Some(content) => content == char
            case None => false

        }
    
    }

    def isGameOver(): Boolean = ships.isEmpty


    def attack(position: (Int, Int)): Boolean = {
        val (rowIdx, colIdx) = position
        val targetChar = cell(rowIdx, colIdx)

        targetChar match {
            case Some('O') =>
                cell(rowIdx, colIdx, Some('X')) // Treffer markieren
                ships = ships.filterNot { case (r, c) => r == rowIdx && c == colIdx }
                true // Treffer
            case Some('X') => true
            case Some('*') => false
            case _ =>
                cell(rowIdx, colIdx, Some('*')) // Fehlschlag markieren
                false // Fehlschlag
        }
    }



    def getSize(): Int = {

        return size

    }
}