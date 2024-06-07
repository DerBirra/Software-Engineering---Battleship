package view

import model.{GameBoard, Player, Position}
import controller.Controller
import util.timer.TimerAddon
import util.observer.{Observer, Observable}
import scala.io.StdIn
import java.io.IOException
import scala.io.AnsiColor.*
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class TUI(controller: Controller, var player: Int) extends Observer {

    private var player1name = ""
    private var player2name = ""

    def processInput(input: String): Unit = {

        input match {

            case "start" => gameLoop()
            case "exit" => sys.exit(0)
            case _ => sys.exit(1)
        
        }

    }

    def gameLoop(): Unit = {

        println(s"$GREEN"+ "Spieler 1 geben Sie ihren Namen an: "+ s"$RESET")
        player1name = Player(scala.io.StdIn.readLine()).toString

        println(s"$GREEN"+ "Spieler 2 geben Sie ihren Namen an: "+ s"$RESET")
        player2name = Player(scala.io.StdIn.readLine()).toString

        controller.startGame()

        placeShips(1) //Spieler 1 Schiffe platzieren
        placeShips(2) //Spieler 2 Schiffe platzieren

        val tm = new TimerAddon()
        tm.start()

        var currentPlayer = player
        var gameEnded = false

        while(!isGameOver() && !gameEnded) {

            println("Geben Sie 'save', 'load', 'exit' oder ENTER ein. (save = Spielstand Speichern, load = letzten Spielstand laden, exit = Beenden, ENTER = nächste Runde")
            val input = scala.io.StdIn.readLine()

            input.toLowerCase match {
                case "save" => saveGame()
                case "load" => loadGame()
                case "exit" => gameEnded = true
                case _ => 
            }

            if (controller.makeMove(currentPlayer, attackOnce(currentPlayer))) {
                println(s"$GREEN"+ "Getroffen."+ s"$RESET")
            } else {
                println(s"$RED"+"Nicht getroffen"+ s"$RESET")
            }
            currentPlayer = if (currentPlayer == 1) 2 else 1
            controller.setCurrentPlayer(currentPlayer)
            player = currentPlayer
            Thread.sleep(1000)

        }

        if (!gameEnded) {
            clearScreen()
            val getGameTime = tm.getElapsedTime
            tm.stop()
            println(s"$GREEN"+ s"Spieler $currentPlayer hat gewonnen! Das Spiel ging $getGameTime Sekunden" + s"$RESET")
            sys.exit(0)
        }      

    }

    def loadGame(): Unit = {

        try {
            controller.restoreState()
            println("Spiel geladen.")
        } catch {
            case e: Exception => e.printStackTrace()
        }

    }

    def saveGame(): Unit = {

        try {
            controller.saveState()
            println("Spiel gespeichert.")
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }

    def isGameOver(): Boolean = {

        controller.isGameOver()

    }

    def placeShips(player: Int): Unit = {

        if (player == 1) {

            println(s"$GREEN" + s"Spieler $player1name: Platziere deine Schiffe"+ s"$RESET")


        } else {

            println(s"$GREEN" + s"Spieler $player2name: Platziere deine Schiffe"+ s"$RESET")


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

        println(s"$GREEN" + s"Bitte geben Sie die $coordType an:"+ s"$RESET")
        var input = scala.io.StdIn.readLine()
        var coordinate = input.toIntOption

        while (coordinate.isEmpty || coordinate.get < 0 || coordinate.get >= controller.getGameBoardSize()+1) {

            println(s"$RED" + s"Ungültige Eingabe für die $coordType. Bitte geben Sie eine Zahl zwischen 1 und ${controller.getGameBoardSize()-1} ein."+ s"$RESET")
            input = scala.io.StdIn.readLine()
            coordinate = input.toIntOption
        }
   
        coordinate.get

    }

    def readOrientation(): Char = {
        
        println(s"$GREEN" +"Bitte geben Sie die Ausrichtung des Schiffs an (h für horizontal, v für vertikal):"+ s"$RESET")
        var input = scala.io.StdIn.readLine().toLowerCase

        while(input != "h" && input != "v") {

            println(s"$RED" +"Ungültige Eingabe für die Ausrichtung. Bitte geben Sie 'h' für horizontal oder 'v' für vertikal ein."+ s"$RESET")
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

            println(s"$RED" +"Bitte eine noch nicht verwendete Koordinate nehmen." + s"$RESET")

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

                println(s"$RED" +"Ungültige Eingabe für die Zeile."+ s"$RESET")
                row = getPosition("Zeile")

            }

        var col = getPosition("Spalte")

        if (col == -1) return (-1,-1)

        while(col < 0 || col > controller.getGameBoardSize()+1){

            println(s"$RED" +"Ungültige Eingabe für die Spalte."+ s"$RESET")
            col = getPosition("Spalte")

        }

        (row, col)
    
    }

    def getPosition(linie: String): Int = {

        if (player == 1) {
            
            println(s"$GREEN" + s"$player1name bitte gib die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):"+ s"$RESET")

        } else {

            println(s"$GREEN" + s"$player2name bitte gib die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):"+ s"$RESET")

        } 

        var input = scala.io.StdIn.readLine()

        input.toLowerCase match {

            case "exit" => sys.exit(0)
            case _ => input.toIntOption.getOrElse(getPosition(linie))

        }

    }

    def displayBoards(): Unit = {
        println(s"$UNDERLINED" + s"Spieler $player1name: Dein eigenes Spielbrett" + s"$RESET")
        println(controller.getPlayerBoard(1, hidden = false))
        println(s"$UNDERLINED" + s"Spieler $player1name: Das gegnerische Spielbrett" + s"$RESET")
        println(controller.getOpponentBoard(1))
        println(s"$UNDERLINED" + s"Spieler $player2name: Dein eigenes Spielbrett" + s"$RESET")
        println(controller.getPlayerBoard(2, hidden = false))
        println(s"$UNDERLINED" + s"Spieler $player2name: Das gegnerische Spielbrett" + s"$RESET")
        println(controller.getOpponentBoard(2))
    }

    private def clearScreen(): Unit = {
        print("\u001b[H\u001b[2J")
    }
    

    override def update: Unit = displayBoards()

}
