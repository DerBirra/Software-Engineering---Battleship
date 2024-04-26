
enum Ship:
    case Carrier, Battleship, Destroyer, Submarine, PatrolBoat
    
enum Shipsize:
    case Two, Three, Four, Five 

enum Shipindex:
    case Two, Three, Four, Five

case class Battleship(stype: Ship, size: Shipsize, index: Shipindex)

val ship1= Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
val ship2= Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two)
val ship3= Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two)
val ship4= Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two)
val ship5= Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two)


ship2.index
ship1.size
ship2.size
ship3.size
ship4.size
ship5.size

// val gameField = new GameField(fieldSize, fieldSize)
//     gameField.generateField()
//     println(gameField.printField())
case class Player(name: String) {
   override def toString(): String = name
}
val player = Player("Miki")
player.name

class GameField (row : Int, col : Int) {

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
        if (orientation == 'h') (0 until size).forall(i => field(shipRow)(shipCol + i) == ' ')
        else (0 until size).forall(i => field(shipRow + i)(shipCol) == ' ')

        if (inBounds && cellsOccupied) {
        // Schiff platzieren
        if (orientation == 'h') {
        for (i <- 0 until size) {
            field(shipRow)(shipCol + i) = 'O'
            ships = ships :+ (shipRow, shipCol + i)
        }
        } else {
        for (i <- 0 until size) {
            field(shipRow + i)(shipCol) = 'O'
            ships = ships :+ (shipRow + i, shipCol)
            }
        }
        true
        } else false
    }

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

    def attack(position: (Int, Int)): Boolean = {
        val (rowIdx, colIdx) = position
        if (field(rowIdx)(colIdx) == 'O') {
            field(rowIdx)(colIdx) = 'X' // Treffer markieren
            ships = ships.filterNot { case (r, c) => r == rowIdx && c == colIdx }
            true // Treffer
            } else {
            field(rowIdx - 1)(colIdx - 1) = '*' // Fehlschlag markieren
            false // Fehlschlag
            }
    }

    def isGameOver(): Boolean = ships.isEmpty

    def isCellContent(rowIdx: Int, colIdx: Int, char: Char): Boolean = {
        cell(rowIdx, colIdx) match {
            case Some(content) => content == char
            case None => false
        }
    }
}

val field = new GameField(9,9)
field.generateField()
field.printField()
//field.cell(1,1,Some('x'))
//field.isCellContent(1,1,'x')

field.placeShip(ship1, (0, 1), 'v')
field.placeShip(ship2, (2, 2), 'h')
field.placeShip(ship3, (3, 3), 'h')
field.placeShip(ship4, (4, 4), 'h')
field.placeShip(ship5, (5, 5), 'h')

field.printField()
field.attack((1, 1))
field.attack((1, 2))
field.attack((1, 3))
field.attack((1, 4))
field.attack((1, 5))
field.attack((1, 6))
field.attack((2, 2))
field.attack((5, 5))
field.printField()