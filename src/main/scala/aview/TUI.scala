package aview

import model.{GameBoard, Player, Position, PlaceShipStrategy}
import controller.Controller
import util.Observer
import scala.io.StdIn
import util.Observable
import scala.util.{Try, Success, Failure}

class TUI(controller: Controller, player: Int) extends Observer {

    def processInput(input: String): Unit = {
    controller.startGame(player)
    setplayerName(player)
    input match {
      case "start" =>
        println("Wählen Sie die Methode zum Platzieren der Schiffe: 'freehand' oder 'strategy'")
        val method = scala.io.StdIn.readLine().toLowerCase
        method match {
          case "freehand" =>
            println("Spieler platzieren ihre Schiffe manuell.")
            placeShips(player) //Spieler 1 Schiffe platzieren
          case "strategy" =>
            controller.setPlaceShipStrategy(new PlaceShipStrategy())
            controller.startGameWithStrategy(player)
          case _ =>
            println("Ungültige Eingabe. Bitte wählen Sie 'freehand' oder 'strategy'.")
            processInput("start")
        }
      case "exit" => sys.exit(0)
      case _ => println("Ungültiger Befehl. Bitte 'start' oder 'exit' eingeben.")
    }
    if (player == 2) gameLoop()
  }

    def setplayerName(playerstate: Int ): Unit = {
        if(playerstate == 1){
            println("Spieler 1 geben Sie ihren Namen an: ")
            val player1 = Player(scala.io.StdIn.readLine())
        }else if(playerstate == 2){
            println("Spieler 2 geben Sie ihren Namen an: ")
            val player2 = Player(scala.io.StdIn.readLine())
        }else()
    }

    def gameLoop(): Unit = {

        var currentPlayer = 1
        var gameEnded = false

        while(!isGameOver() && !gameEnded) {

            println("Geben Sie 'save', 'load', 'exit' oder ENTER ein. (save = Spielstand Speichern, load = letzten Spielstand laden, exit = Beenden, ENTER = nächste Runde")
            val input = scala.io.StdIn.readLine()

            input.toLowerCase match {
                case "save" => saveGame()
                case "load" => loadGame()
                case "exit" => gameEnded = true
                case _ => // proceed to next round
            }
            
            if (!gameEnded) {
                controller.makeMove(currentPlayer, attackOnce(currentPlayer))
                currentPlayer = if (currentPlayer == 1) 2 else 1
                Thread.sleep(1000)
            }
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

            controller.placeShip(player, shipList(index), position, orientation) match {
                case Success(_) =>
                    println(s"Schiff ${shipList(index).stype} erfolgreich platziert.")
                    index += 1
                case Failure(exception) =>
                    println(s"Fehler beim Platzieren des Schiffs: ${exception.getMessage}. Versuche es erneut.")
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
        val coordinates = getAttackedCoordinates(player)
        val row = coordinates._1
        val col = coordinates._2

        var isCell = controller.isCellContent(player, row, col, 'X') || controller.isCellContent(player, row, col, '*')

        while(isCell) {

            println("Bitte eine noch nicht verwendete Koordinate nehmen.")

            val coordinates = getAttackedCoordinates(player)
            val row = coordinates._1
            val col = coordinates._2
            isCell = controller.isCellContent(player, row, col, 'X') || controller.isCellContent(player,row, col, '*')
        }
            
        return new Position(row,col)
    }

    def getAttackedCoordinates(player :Int): (Int, Int) = {
    
        var row = getPosition("Zeile", player)

        if (row == -1) return (-1,-1)

            while(row < 0 || row > controller.getGameBoardSize()+1){

                println("Ungültige Eingabe für die Zeile.")
                row = getPosition("Zeile", player)
            }

        var col = getPosition("Spalte", player)

        if (col == -1) return (-1,-1)

        while(col < 0 || col > controller.getGameBoardSize()+1){

            println("Ungültige Eingabe für die Spalte.")
            col = getPosition("Spalte",player)

        }

        (row, col)
    
    }

    def getPosition(linie: String, player :Int): Int = {

        println(s"Spieler $player Bitte geben Sie die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")

        val input = scala.io.StdIn.readLine()

        input.toLowerCase match {

        case "exit" => sys.exit(0)

            case _ => input.toIntOption match {

            case Some(value) => value
            case None => getPosition(linie, player)

            }
        }
    }

    def displayBoards(): Unit = {
        println(s"Spieler $player: Dein eigenes Spielbrett")
        println(controller.getPlayerBoard(player, hidden = false))
        println(s"Spieler $player: Das gegnerische Spielbrett")
        println(controller.getOpponentBoard(player))
    }

      def saveGame(): Unit = {
        try {
            controller.saveState()
            println("Spiel gespeichert.")
        } catch {
            case e: Exception => println(s"Fehler beim Speichern des Spiels: ${e.getMessage}")
        }
    }
    
     def loadGame(): Unit = {
        try {
            controller.restoreState()
            println("Spiel geladen.")
        } catch {
            case e: Exception => println(s"Fehler beim Laden des Spiels: ${e.getMessage}")
        }
    }

    override def update: Unit = displayBoards()
}
