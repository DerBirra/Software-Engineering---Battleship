package aview.swingGui

import scala.swing._
import scala.swing.event._

class GameBoard(boardSize: Int, handleButtonClick: (Int, Int) => Unit) extends GridPanel(boardSize, boardSize) {

  val buttons = Array.ofDim[Button](boardSize, boardSize)

  // Spielfeld erstellen
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

  def updateButtonText(x: Int, y: Int, text: String): Unit = {
    buttons(x)(y).text = text
  }

  def updateButtonShip(x: Int, y: Int, isShip: Boolean): Unit = {
    buttons(x)(y).text = if (isShip) "S" else ""
  }
}