package aview

import model.{GameField, Player, Ship, Shipsize, Shipindex, Battleship}

class TUI {
  def startGame(): Unit = {
    println("Bitte geben Sie ihren Namen ein:")
    val playerName = scala.io.StdIn.readLine()
    val player = Player(playerName)

    println("Willkommen " + player.name + ". Bitte geben Sie eine gewünschte Feldgröße ein:")
    val fieldSize = scala.io.StdIn.readInt()
    val gameField = new GameField(fieldSize, fieldSize)
    gameField.generateField()
    println(gameField.printField())

    val ships = List(
      Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two),
      Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two),
      Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two),
      Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two),
      Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two)
    )

    ships.foreach { ship =>
      println(s"Platzieren Sie das Schiff ${ship.stype}:(größe ${ship.size})")
      val (row, col) = readCoordinates(fieldSize)
      val orientation = readOrientation()
      val placed = gameField.placeShip(ship, (row, col), orientation)
      if (placed) println(s"Schiff ${ship.stype} erfolgreich platziert.")
      else println(s"Konnte Schiff ${ship.stype} nicht platzieren. Bitte versuchen Sie es erneut.")
    }

    println("Alle Schiffe platziert. Das Spiel beginnt.")
    gameLoop(gameField)
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
    } else coordinate.get
  }

  private def readOrientation(): Char = {
    println("Bitte geben Sie die Ausrichtung des Schiffs an (h für horizontal, v für vertikal):")
    val input = scala.io.StdIn.readLine().toLowerCase
    if (input == "h" || input == "v") input.head
    else {
      println("Ungültige Eingabe für die Ausrichtung. Bitte geben Sie 'h' für horizontal oder 'v' für vertikal ein.")
      readOrientation()
    }
  }

  private def gameLoop(gameField: GameField): Unit = {
    var gameState = true

    while (gameState) {
      println("Bitte geben Sie die Zeile an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")
      val rowInput = scala.io.StdIn.readLine()
      if (rowInput.toLowerCase() == "exit") {
        println("Das Spiel wurde beendet.")
        gameState = false
      } else {
        val row = rowInput.toIntOption
        if (row.isEmpty || row.get < 0 || row.get >= gameField.row) {
          println("Ungültige Eingabe für die Zeile.")
        } else {
          println("Bitte geben Sie die Spalte an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")
          val colInput = scala.io.StdIn.readLine()
          if (colInput.toLowerCase() == "exit") {
            println("Das Spiel wurde beendet.")
            gameState = false
          } else {
            val col = colInput.toIntOption
            if (col.isEmpty || col.get < 0 || col.get >= gameField.col) {
              println("Ungültige Eingabe für die Spalte.")
            } else {
              val isCell = gameField.isCellContent(row.get, col.get, 'x')
              if (isCell) {
                println("Bitte eine noch nicht verwendete Koordinate nehmen.")
              } else {
                gameField.cell(row.get, col.get, Some('x'))
                println(gameField.printField())
              }
            }
          }
        }
      }
    }
  }
}
