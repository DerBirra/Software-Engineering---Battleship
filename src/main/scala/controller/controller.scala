package controller

import model.{GameField,Ship,Shipsize,Battleship,Shipindex,Player}
import util.Observable
import scala.compiletime.uninitialized

class controller extends Observable{
  
    var gameField:GameField = uninitialized

    def createPlayer(): Player = {
    
        println("Bitte geben Sie ihren Namen ein:")

        Player(scala.io.StdIn.readLine())
    
    }

    def createGameField(): GameField = {

        println("Bitte geben Sie eine gewünschte Feldgröße ein:")

        val fieldSize = scala.io.StdIn.readInt()
        gameField = new GameField(fieldSize, fieldSize)
        gameField.generateField()
        println(gameField.printField())

        gameField
    }

    def placeShips(gameField: GameField, ships: List[Battleship]): Unit = {

        ships.foreach { ship =>
            placeShip(gameField, ship)
        }

        println("Alle Schiffe platziert. Das Spiel beginnt.")
    
    }

    def placeShip(gameField: GameField, ship: Battleship): Unit = {

        var placed  = false

        while (!placed) {
            println(s"Platzieren Sie das Schiff ${ship.stype}:(größe ${ship.size})")

            val (row, col) = gameField.readCoordinates(gameField.row)
            val orientation = gameField.readOrientation()

            placed = gameField.placeShip(ship, (row, col), orientation)

            if (!placed) {
    
                println(s"Konnte Schiff ${ship.stype} nicht platzieren. Bitte versuchen Sie es erneut.")
    
            } else{
    
                println(s"Schiff ${ship.stype} erfolgreich platziert.")
    
            }
    
        }

    }

    def updateGameField: String = gameField.printField()
}
