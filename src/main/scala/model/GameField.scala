package model
class GameField (val row : Int, val col : Int) {

    var field: Array[Array[Char]] = Array.ofDim[Char](row, col)
    private var ships: List[(Int, Int)] = List() // Liste der Schiffpositionen

    def generateField(): Unit = {

        // Spielfeld mit Leerzeichen füllen
        for {

            i <- 0 until row
            j <- 0 until col

        } field(i)(j) = ' '
    }

    def printField(): String = {
        // Oberen Rand des Spielfelds erzeugen
        val top = "+" + "-" * col * 3 + "+\n"

        // Feldinhalte erzeugen
        val fieldContent = (for {
            i <- 0 until row
            j <- 0 until col
        } yield {
            // Rahmen links
            val leftBorder = if (j == 0) "|" else ""

            // Inhalt der Zelle, wenn nicht leer als # und wenn bereits angegriffen als X
            val cellContent = if (field(i)(j) == ' ') " # " else s" ${field(i)(j)} "

            // Rahmen rechts
            val rightBorder = if (j == col - 1) "|\n" else ""

            leftBorder + cellContent + rightBorder
        }).mkString

        // Unteren Rand des Spielfelds erzeugen
        val bottom = "+" + "-" * col * 3 + "+\n"

        // Das gesamte Spielfeld zusammenbauen
        top + fieldContent + bottom
    }

    def placeShip(ship: Battleship, position: (Int, Int), orientation: Char): Boolean = {
        val (shipRow, shipCol) = position
        val size = ship.size match {
        case Shipsize.Two => 2
        case Shipsize.Three => 3
        case Shipsize.Four => 4
        case Shipsize.Five => 5
        }

        // Überprüfen, ob das Schiff innerhalb des Spielfelds platziert werden kann
        val inBounds =
        if (orientation == 'h') shipCol + size - 1 <= col
        else shipRow + size - 1 <= row

        // Überprüfen, ob die Zellen, die das Schiff einnehmen würde, frei sind
        val cellsOccupied =
        if (orientation == 'h') (0 until size).forall(i => isCellContent(shipRow,shipCol + i,  ' '))
        else (0 until size).forall(i => isCellContent(shipRow + i,shipCol ,' '))

        if (inBounds && cellsOccupied) {
        // Schiff platzieren
        if (orientation == 'h') {
        for (i <- 0 until size) {
            cell(shipRow,shipCol + i,Some('O'))
            ships = ships :+ (shipRow, shipCol + i)
        }
        } else {
        for (i <- 0 until size) {
            cell(shipRow + i,shipCol,Some('O'))
            ships = ships :+ (shipRow + i, shipCol)
            }
        }
        true
        } else{ 
            println("Das Schiff kann nicht an dieser Position platziert werden. Bitte wählen Sie eine andere Position.")
            false
            }
    }
    def getShipsToPlace(): List[Battleship] = {

        List(Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two),
             Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two),
             Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two),
             Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two),
             Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two) )
             
    }

    def readCoordinates(fieldSize: Int): (Int, Int) = {

        println("Bitte geben Sie die Zeile und Spalte an, wo das Schiff platziert werden soll:")

        val row = readCoordinate("Zeile", fieldSize)
        val col = readCoordinate("Spalte", fieldSize)

        (row, col)
    
    }

    def readCoordinate(coordType: String, fieldSize: Int): Int = {

        println(s"Bitte geben Sie die $coordType an:")
        var input = scala.io.StdIn.readLine()
        var coordinate = input.toIntOption

        while (coordinate.isEmpty || coordinate.get < 0 || coordinate.get >= fieldSize) {

            println(s"Ungültige Eingabe für die $coordType. Bitte geben Sie eine Zahl zwischen 1 und ${fieldSize-1} ein.")
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

    def getAttackedCoordinates(gameField: GameField): (Int, Int) = {
    
        var row = getPosition("Zeile")

        if (row == -1) return (-1,-1)

            while(row < 0 || row > gameField.row){

                println("Ungültige Eingabe für die Zeile.")
                row = getPosition("Zeile")

            }

        var col = getPosition("Spalte")

        if (col == -1) return (-1,-1)

        while(col < 0 || col > gameField.col){

            println("Ungültige Eingabe für die Spalte.")
            col = getPosition("Spalte")

        }

        (row, col)
    
    }


    def getPosition(linie: String): Int = {

        println(s"Bitte geben Sie die $linie an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")

        val input = scala.io.StdIn.readLine()

        input.toLowerCase match {

        case "exit" => -1

            case _ => input.toIntOption match {

            case Some(value) => value
            case None => getPosition(linie)

            }
        }
    }

    def attackOnce(gameField: GameField): Boolean = {
    
        val coordinates = getAttackedCoordinates(gameField)
        val row = coordinates._1
        val col = coordinates._2

        if (row == -1 || col == -1) return true

        var isCell = gameField.isCellContent(row, col, 'X') || gameField.isCellContent(row, col, '*')

        while(isCell) {

            println("Bitte eine noch nicht verwendete Koordinate nehmen.")

            val coordinates = getAttackedCoordinates(gameField)
            val row = coordinates._1
            val col = coordinates._2
            isCell = gameField.isCellContent(row, col, 'X') || gameField.isCellContent(row, col, '*')
        }

        gameField.attack(row, col)
            
        return false

    }


    def attack(position: (Int, Int)): Boolean = {
        val (rowIdx, colIdx) = position
        val targetChar = cell(rowIdx, colIdx)

        targetChar match {
            case Some('O') =>
                cell(rowIdx, colIdx, Some('X')) // Treffer markieren
                ships = ships.filterNot { case (r, c) => r == rowIdx && c == colIdx }
                true // Treffer
            case _ =>
                cell(rowIdx, colIdx, Some('*')) // Fehlschlag markieren
                false // Fehlschlag
        }
    }

    def isGameOver(): Boolean = ships.isEmpty

    def cell(rowIdx: Int, colIdx: Int, value: Option[Char] = None): Option[Char] = {
    // wenn die Koordinaten innerhalb des Spielfelds liegen
    if (rowIdx >= 1 && rowIdx <= row && colIdx >= 1 && colIdx <= col) {
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
  
}
