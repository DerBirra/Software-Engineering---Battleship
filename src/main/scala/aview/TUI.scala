package aview

import model.{GameBoard, Player, Position}
import controller.Controller
import util.Observer
import scala.io.StdIn
import util.Observable

class TUI(controller: Controller, player: Int) extends Observer {

    def processInput(input: String): Unit = {
        input match {
            case "start" => gameLoop()
            case "exit" => sys.exit(0)
            case _ => sys.exit(1)
        
        }
    }

    def gameLoop(): Unit = {

        println("Spieler 1 geben Sie ihren Namen an: ")
        val player1 = Player(scala.io.StdIn.readLine())

        println("Spieler 2 geben Sie ihren Namen an: ")
        val player2 = Player(scala.io.StdIn.readLine())

        controller.startGame()

        placeShips(1) //Spieler 1 Schiffe platzieren
        placeShips(2) //Spieler 2 Schiffe platzieren

        var currentPlayer = 1

        while(!isGameOver()) {

            controller.makeMove(currentPlayer, attackOnce(currentPlayer))

            currentPlayer = if (currentPlayer == 1) 2 else 1

            Thread.sleep(1000)

        }

        println(s"Spieler $currentPlayer hat gewonnen!")
        sys.exit(0)

    }

    def isGameOver(): Boolean = {

        controller.isGameOver()

    }

    def placeShips(player: Int): Unit = {

            println(s"Spieler $player: Platziere deine Schiffe")

            val shipList = controller.getShipsToPlace(player)
            var index = 0

            while (index < shipList.length) {

                val position = readCoordinates()
                val orientation = readOrientation()

                if (!(controller.placeShip(player, shipList(index), position, orientation))) {

                    controller.placeShip(player, shipList(index), position, orientation)
                    
                } else {
                    
                    controller.placeShip(player, shipList(index), position, orientation)
                    index +=1;

                }
            
            }

    }

    def readCoordinates(): (Int, Int) = {

        val row = readCoordinate("Zeile")
        val col = readCoordinate("Spalte")

        (row, col)
    }

    def readCoordinate(coordType: String): Int = {

        println(s"Bitte geben Sie die $coordType an:")
        var input = scala.io.StdIn.readLine()
        var coordinate = input.toIntOption

        while (coordinate.isEmpty || coordinate.get < 0 || coordinate.get >= controller.getGameBoardSize()+1) {

            println(s"Ungültige Eingabe für die $coordType. Bitte geben Sie eine Zahl zwischen 1 und ${controller.getGameBoardSize()} ein.")
            input = scala.io.StdIn.readLine()
            coordinate = input.toIntOption
        }
        coordinate.get
    }

    def readOrientation(): Char = {
        
        println("Bitte geben Sie die Ausrichtung des Schiffs an (h für horizontal, v für vertikal):")
        var input = scala.io.StdIn.readLine().toLowerCase

        while(input != "h" && input != "v") {

            println("Ungültige Eingabe für die Ausrichtung. Bitte geben Sie 'h' für horizontal oder 'v' für vertikal ein.")
            input = scala.io.StdIn.readLine().toLowerCase
        }
        input.head
    }

    def attackOnce(player: Int): Position = {
        val coordinates = getAttackedCoordinates()
        val row = coordinates._1
        val col = coordinates._2

        var isCell = controller.isCellContent(player, row, col, 'X') || controller.isCellContent(player, row, col, '*')

        while(isCell) {

            println("Bitte eine noch nicht verwendete Koordinate nehmen.")

            val coordinates = getAttackedCoordinates()
            val row = coordinates._1
            val col = coordinates._2
            isCell = controller.isCellContent(player, row, col, 'X') || controller.isCellContent(player,row, col, '*')
        }
            
        return new Position(row,col)
    }

    def getAttackedCoordinates(): (Int, Int) = {
    
        var row = getPosition("Zeile")

        if (row == -1) return (-1,-1)

            while(row < 0 || row > controller.getGameBoardSize()+1){

                println("Ungültige Eingabe für die Zeile.")
                row = getPosition("Zeile")
            }

        var col = getPosition("Spalte")

        if (col == -1) return (-1,-1)

        while(col < 0 || col > controller.getGameBoardSize()+1){

            println("Ungültige Eingabe für die Spalte.")
            col = getPosition("Spalte")

        }

        (row, col)
    
    }

    def getPosition(linie: String): Int = {

        println(s"Bitte geben Sie die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")

        val input = scala.io.StdIn.readLine()

        input.toLowerCase match {

        case "exit" => sys.exit(0)

            case _ => input.toIntOption match {

            case Some(value) => value
            case None => getPosition(linie)

            }
        }
    }

    def displayBoards(): Unit = {
        println(s"Spieler $player: Dein eigenes Spielbrett")
        println(controller.getPlayerBoard(player, hidden = false))
        println(s"Spieler $player: Das gegnerische Spielbrett")
        println(controller.getOpponentBoard(player))
    }
    

    override def update: Unit = displayBoards()
}
