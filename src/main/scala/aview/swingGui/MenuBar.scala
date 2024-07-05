package aview.swingGui

import scala.swing._
import scala.swing.event._

import controller.controllerComponent.controllerIf

object MenuBar {

  def create(controller: controllerIf): MenuBar = new MenuBar {
    // Menü "Settings" erstellen
    val settingsMenu = new Menu("Settings") {
      mnemonic = Key.S

      contents += new MenuItem(Action("Save to XML") {
        controller.saveToXML() // Implementiere die Methode im Controller
      })

      contents += new MenuItem(Action("Save to JSON") {
        controller.saveToJSON() // Implementiere die Methode im Controller
      })
    }

    // Menü "Exit" erstellen
    val exitMenu = new Menu("Exit") {
      mnemonic = Key.E

      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }

    // Menüs zur MenuBar hinzufügen
    contents += settingsMenu
    contents += Swing.HGlue // Platzhalter, um das nächste Menü nach rechts zu schieben
    contents += exitMenu
  }
}