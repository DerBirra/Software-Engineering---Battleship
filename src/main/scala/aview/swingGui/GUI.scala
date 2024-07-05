package aview.swingGui

import controller.controllerComponent.controllerIf
import model.modelComponent.modelImpl.Position
import util.Observer
import aview.swingGui.MenuBar
import scala.util.{Try, Success, Failure}
import scala.swing._
import scala.swing.event._

class GUI(controller: controllerIf) extends MainFrame with Observer {

  private var gameSize = 0
  private var player1name = ""
  private var player2name = ""
  private var currentPlayer = 1
  private var shipsToPlace = 1
  private var placingShips = true
  private var horizontalPlacement = true

  title = "BattleShip"
  preferredSize = new Dimension(1000, 1000)
  visible = true

  val result = InputDialog.showDialog("Spiel Details eingeben")

  result match {
    case Some((number, name1, name2)) =>
      println(s"Spielfeld Größe: $number, Name 1: $name1, Name 2: $name2")
      gameSize = number
      player1name = name1
      player2name = name2
      visible = true
    case None =>
      println("Dialog wurde abgebrochen")
      sys.exit(0)
  }

  val statusLabel = new Label {
    text = "Spiel Startet. Platziere Schiff 1"
  }

  val toggleOrientationButton = new ToggleButton("Horizontal") {
    reactions += {
      case ButtonClicked(_) =>
        horizontalPlacement = !horizontalPlacement
        text = if (horizontalPlacement) "Horizontal" else "Vertikal"
    }
  }

  val button = new Button {
    text = "Start Game"
  }

  // Spielfeld erstellen
  val gameBoard = new GameBoard(gameSize, handleButtonClick)

  val statusPanel = new StatusPanel

  menuBar = MenuBar.create(controller)

  def handleButtonClick(x: Int, y: Int): Unit = {
    if (placingShips) {
      placeShip(x, y)
    } else {
      makeMove(x, y)
    }
  }

  def placeShip(x: Int, y: Int): Unit = {
  val position = (x, y)
  
  // Überprüfen, ob es noch Schiffe zum Platzieren gibt
  if (shipsToPlace > 0) {
    val ship = controller.getShipsToPlace(currentPlayer).head
    val orientation = if (horizontalPlacement) 'H' else 'V'

    val resultTry : Try[Unit] = controller.placeShip(currentPlayer, ship, position, orientation)

    if (resultTry.isSuccess) {
      shipsToPlace -= 1
      statusPanel.updateShips(5 - shipsToPlace)

      if (shipsToPlace == 0) {
        // Alle Schiffe wurden platziert
        placingShips = false
        currentPlayer = if (currentPlayer == 1) 2 else 1

        if (currentPlayer == 1) {
          // Wechsel zum Angriffsmodus für Spieler 1
          statusLabel.text = "Angriff Modus. Spieler 1 beginnt"
          controller.setCurrentPlayer(1)
          statusPanel.updatePlayer(player1name)
        } else if (currentPlayer == 2) {
          // Spieler 2 ist dran, Schiffe müssen erneut platziert werden
          placingShips = true
          shipsToPlace = 5
          statusLabel.text = s"Spieler $currentPlayer platziert jetzt Schiffe"
        }
      } else {
        // Noch nicht alle Schiffe platziert
        statusLabel.text = s"Platziere Schiff ${6 - shipsToPlace}"
      }
    } else {
      // Fehler beim Platzieren des Schiffs
      // Hier könnten Sie eine Fehlermeldung anzeigen oder eine entsprechende Aktion durchführen
    }
  }
}

  def makeMove(x: Int, y: Int): Unit = {
    if (controller.makeMove(currentPlayer, new Position(x, y))) {
      gameBoard.updateButtonText(x, y, "X")
    } else {
      gameBoard.updateButtonText(x, y, "O")
    }
    currentPlayer = if (currentPlayer == 1) 2 else 1
    controller.setCurrentPlayer(currentPlayer)
    statusPanel.updatePlayer(if (currentPlayer == 1) player1name else player2name)
    statusPanel.updateShots(controller.getShotsCount(currentPlayer)) // Update shots count
    statusLabel.text = s"Spieler $currentPlayer ist an der Reihe"
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += statusPanel
    contents += toggleOrientationButton
    contents += statusLabel
    contents += button
    contents += gameBoard
    border = Swing.EmptyBorder(30, 30, 10, 30)
  }

  listenTo(button)
  reactions += {
    case ButtonClicked(b) =>
      controller.startGame(1)
  }

  override def update: Unit = {
    // Update logic if needed
  }
}