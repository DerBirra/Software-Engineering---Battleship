package view.swing

import controller.Controller
import util.observer.*
import scala.swing._
import scala.swing.event._

class GUI(controller: Controller) extends MainFrame with Observer {

    private var gameSize = 0
    private var player1name = ""
    private var player2name = ""

    title = "BattleShip"
    preferredSize = new Dimension(1000, 1000)
    visible=true

    val label = new Label {
        text = "Waiting for updates..."
    }

    val button = new Button {
        text = "Notify Observers"
    }

    val boardSize = 10
    val buttons = Array.ofDim[Button](boardSize, boardSize)

    // Spielfeld erstellen
    val gameBoard = new GridPanel(boardSize, boardSize) {
      for {
        i <- 0 until boardSize
        j <- 0 until boardSize
      } {
        buttons(i)(j) = new Button {
          text = ""
          reactions += {
            case ButtonClicked(_) => handleButtonClick(i, j)
          }
        }
        contents += buttons(i)(j)
      }
    }

    def handleButtonClick(x: Int, y: Int): Unit = {
      println(s"($x, $y)")
      // controller.handleMove(x, y) oder ähnliches
    }

    contents = new BoxPanel(Orientation.Vertical) {
        contents += label
        contents += button
        contents += gameBoard
        border = Swing.EmptyBorder(30, 30, 10, 30)
    }

    listenTo(button)
    reactions += {
        case ButtonClicked(b) =>
        controller.startGame()
        
    }

    def initialize(): Unit = {
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
    }

    def getGameSize(): Int = {

      return gameSize

    }

    def getPlayer1Name(): String = {

      return player1name

    }

    def getPlayer2Name(): String = {

      return player2name

    }

    override def update: Unit = {
        label.text = "GUI: Observable updated"
  }
}
