package view

import model.{GameBoard, Player, Position}
import controller.Controller
import util.{Observer,TimerAddon}
import scala.io.StdIn
import java.io.IOException
import util.Observable

class TUI(controller: Controller, var player: Int) extends Observer {

    private var player1name = ""
    private var player2name = ""

    def processInput(input: String): Unit = {

        input match {

            case "start" => gameLoop()
            case "load" => loadGame()
            case "exit" => sys.exit(0)
            case _ => sys.exit(1)
        
        }

    }

    def gameLoop(): Unit = {

        println("Spieler 1 geben Sie ihren Namen an: ")
        player1name = Player(scala.io.StdIn.readLine()).toString

        println("Spieler 2 geben Sie ihren Namen an: ")
        player2name = Player(scala.io.StdIn.readLine()).toString

        controller.startGame()

        placeShips(1) //Spieler 1 Schiffe platzieren
        placeShips(2) //Spieler 2 Schiffe platzieren

        val tm = new TimerAddon()
        tm.start()

        var currentPlayer = player

        while(!isGameOver()) {

            if (controller.makeMove(currentPlayer, attackOnce(currentPlayer))) {

                println("Getroffen.")

            } else {

                println("Nicht getroffen")

            }

            currentPlayer = if (currentPlayer == 1) 2 else 1
            player = currentPlayer
            //println(currentPlayer)

            Thread.sleep(1000)

        }

        

        val getGameTime = tm.getElapsedTime
        tm.stop()

        println(s"Spieler $currentPlayer hat gewonnen! Das Spiel ging $getGameTime Sekunden")
        sys.exit(0)

    }

    def loadGame(): Unit = {

        

    }

    def isGameOver(): Boolean = {

        controller.isGameOver()

    }

    def placeShips(player: Int): Unit = {

        if (player == 1) {

            println(s"Spieler $player1name: Platziere deine Schiffe")


        } else {

            println(s"Spieler $player2name: Platziere deine Schiffe")


        }
        
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

            println(s"Ungültige Eingabe für die $coordType. Bitte geben Sie eine Zahl zwischen 1 und ${controller.getGameBoardSize()-1} ein.")
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
    
        
        var coordinates = getAttackedCoordinates()

        var row = coordinates._1
        var col = coordinates._2

        var isCell = controller.isCellContent(player, row, col, 'X') || controller.isCellContent(player, row, col, '*')

        while(isCell) {

            println("Bitte eine noch nicht verwendete Koordinate nehmen.")

            coordinates = getAttackedCoordinates()
            row = coordinates._1
            col = coordinates._2
            isCell = controller.isCellContent(player, row, col, 'X') || controller.isCellContent(player,row, col, '*')

            if (!isCell) {

                return new Position(row,col)

            }

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

        if (player == 1) {
            
            println(s"$player1name bitte gib die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")

        } else {

            println(s"$player2name bitte gib die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")

        } 

        var input = scala.io.StdIn.readLine()

        input.toLowerCase match {

        case "exit" => sys.exit(0)

            case _ => input.toIntOption match {

            case Some(value) => value
            case None => getPosition(linie)

            }
        }

        

    }

    def displayBoards(): Unit = {
        println(s"Spieler $player1name: Dein eigenes Spielbrett")
        println(controller.getPlayerBoard(1, hidden = false))
        println(s"Spieler $player1name: Das gegnerische Spielbrett")
        println(controller.getOpponentBoard(1))
        println(s"Spieler $player2name: Dein eigenes Spielbrett")
        println(controller.getPlayerBoard(2, hidden = false))
        println(s"Spieler $player2name: Das gegnerische Spielbrett")
        println(controller.getOpponentBoard(2))
    }
    

    override def update: Unit = displayBoards()



}
