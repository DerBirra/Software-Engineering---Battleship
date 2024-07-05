package aview.swingGui

import scala.swing._
import scala.swing.event._

class StatusPanel extends BoxPanel(Orientation.Horizontal) {

  val shipsLabel = new Label {
    text = "Schiffe: 0"
  }

  val shotsLabel = new Label {
    text = "Schüsse: 0"
  }

  val playerLabel = new Label {
    text = "Spieler: "
  }

  contents += shipsLabel
  contents += Swing.HGlue
  contents += playerLabel
  contents += Swing.HGlue
  contents += shotsLabel

  border = Swing.EmptyBorder(10, 10, 10, 10)

  def updateShips(count: Int): Unit = {
    shipsLabel.text = s"Schiffe: $count"
  }

  def updateShots(count: Int): Unit = {
    shotsLabel.text = s"Schüsse: $count"
  }

  def updatePlayer(name: String): Unit = {
    playerLabel.text = s"Spieler: $name"
  }
}