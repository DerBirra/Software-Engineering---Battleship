package controller

import model.GameField
import model.Ship
import model.Shipsize
import model.Battleship
import model.Shipindex
import util.Observable
import sys.process._


class Controller(var gameField:GameField) extends Observable {

    def createGameField(row: Int, cell: Int): Unit = {

        gameField = new GameField(row,cell)

        clearConsole()
        println(gameField.toString())
        notifyObservers

    }

    def placeShips(): Unit = {

        val ships = List( Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two),
                        Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two),
                        Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two),
                        Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two),
                        Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two) )

        ships.foreach { ship =>

            println(s"Platzieren Sie das Schiff ${ship.stype}:(größe ${ship.size})")
                
            val (row, col) = readCoordinates(gameField.getGamFieldSize())
            val orientation = readOrientation()
            val placed = gameField.placeShip(ship, (row, col), orientation)
            
            if (placed) { 
                    
                println(s"Schiff ${ship.stype} erfolgreich platziert.")
                
            } else { 
                    
                println(s"Konnte Schiff ${ship.stype} nicht platzieren. Bitte versuchen Sie es erneut.")
            
            }
        }
    }

    private def readCoordinates(fieldSize: Int): (Int, Int) = {

    println("Bitte geben Sie die Zeile und Spalte an, wo das Schiff platziert werden soll:")

    val row = readCoordinate("Zeile", fieldSize)
    val col = readCoordinate("Spalte", fieldSize)

    (row, col)

    }

    private def readCoordinate(coordType: String, fieldSize: Int): Int = {

    println(s"Bitte geben Sie die $coordType an:")
    val input = scala.io.StdIn.readLine()
    val coordinate = input.toIntOption

        if (coordinate.isEmpty || coordinate.get < 0 || coordinate.get >= fieldSize) {

        println(s"Ungültige Eingabe für die $coordType. Bitte geben Sie eine Zahl zwischen 0 und ${fieldSize - 1} ein.")
        readCoordinate(coordType, fieldSize)

        } else {
            
            coordinate.get

        }
    }

    private def readOrientation(): Char = {

    println("Bitte geben Sie die Ausrichtung des Schiffs an (h für horizontal, v für vertikal):")
    val input = scala.io.StdIn.readLine().toLowerCase

        if (input == "h" || input == "v")  {
            
            input.head
        
        } else {
        
        println("Ungültige Eingabe für die Ausrichtung. Bitte geben Sie 'h' für horizontal oder 'v' für vertikal ein.")
        readOrientation()
        
        }
    }

    def updateGameField: String = gameField.toString

    def getGamFieldSize: Int = {

        return gameField.getGamFieldSize()

    }


    def clearConsole(): Unit = {
    
        println("\u001b[2J")
    
    }
  


}
