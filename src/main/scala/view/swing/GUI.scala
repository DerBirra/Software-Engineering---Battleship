package view.swing

import controller.Controller
import util.observer.*
import scala.swing._
import scala.swing.event._

class GUI(controller: Controller) extends MainFrame with Observer{    

    title = "BattleShip"
    preferredSize = new Dimension(1000, 1000)
    visible=true

    val label = new Label {
        text = "Waiting for updates..."
    }

    val button = new Button {
        text = "Notify Observers"
    }

    contents = new BoxPanel(Orientation.Vertical) {
        contents += label
        contents += button
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
        visible = true
      case None =>
        println("Dialog wurde abgebrochen")
        sys.exit(0)
    }
  }

    override def update: Unit = {
        label.text = "GUI: Observable updated"
  }
}
