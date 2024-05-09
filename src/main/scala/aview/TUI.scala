package aview

import model.{GameField, Player, Ship, Shipsize, Shipindex, Battleship}

class TUI {

  def createPlayer(): Player = {
    println("Bitte geben Sie ihren Namen ein:")

    Player(scala.io.StdIn.readLine())
  }

  def createGameField(): GameField = {
    println("Bitte geben Sie eine gewünschte Feldgröße ein:")

    val fieldSize = scala.io.StdIn.readInt()
    val gameField = new GameField(fieldSize, fieldSize)
    gameField.generateField()
    println(gameField.printField())

    gameField
  }

  def getShipsToPlace(): List[Battleship] = {
    List(
      Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two),
      Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two),
      Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two),
      Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two),
      Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two)
    )
  }

  def placeShip(gameField: GameField, ship: Battleship): Unit = {

    var placed  = false

    while (!placed) {
      println(s"Platzieren Sie das Schiff ${ship.stype}:(größe ${ship.size})")

      val (row, col) = readCoordinates(gameField.row)
      val orientation = readOrientation()

      placed = gameField.placeShip(ship, (row, col), orientation)

      if (!placed) {
        println(s"Konnte Schiff ${ship.stype} nicht platzieren. Bitte versuchen Sie es erneut.")
      } else{
        println(s"Schiff ${ship.stype} erfolgreich platziert.")
      }
    }

  }

  def placeShips(gameField: GameField, ships: List[Battleship]): Unit = {

    ships.foreach { ship =>
      placeShip(gameField, ship)
    }

    println("Alle Schiffe platziert. Das Spiel beginnt.")
    
  }

  def startGame(): Unit = {
  
    val player = createPlayer()
    var gameField = createGameField()

    placeShips(gameField, getShipsToPlace())

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
    var input = scala.io.StdIn.readLine()
    var coordinate = input.toIntOption

    while (coordinate.isEmpty || coordinate.get < 0 || coordinate.get >= fieldSize) {
      println(s"Ungültige Eingabe für die $coordType. Bitte geben Sie eine Zahl zwischen 1 und ${fieldSize-1} ein.")
      input = scala.io.StdIn.readLine()
      coordinate = input.toIntOption
    }
    
    coordinate.get
  }

  private def readOrientation(): Char = {
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

    input.toLowerCase match{
      case "exit" => -1
      case _ => input.toIntOption match{
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

    while(isCell){
      println("Bitte eine noch nicht verwendete Koordinate nehmen.")

      val coordinates = getAttackedCoordinates(gameField)
      val row = coordinates._1
      val col = coordinates._2
      isCell = gameField.isCellContent(row, col, 'X') || gameField.isCellContent(row, col, '*')
    }

    gameField.attack(row, col)
    
    return false
  }

  private def gameLoop(gameField: GameField): Unit = {

    gameField.printField()

    var gameState = true

    while (gameState) {

      var exit = attackOnce(gameField)

      if(exit == true){
        gameState = false
        println("Das Spiel wurde beendet.")
        return
      }

      println(gameField.printField())

    }
  }
}
