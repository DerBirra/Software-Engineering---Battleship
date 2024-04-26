package aview

import model.GameField
import model.Player

class TUI {
    def startGame(): Unit = {
        println("Bitte geben Sie ihren Namen ein:")
        val student = Player(scala.io.StdIn.readLine())
        println("Willkommen " + student.name + " Bitte geben Sie eine gewünschte Feld größe ein:")
        val fieldSize = scala.io.StdIn.readInt()
        val gameField = new GameField(fieldSize, fieldSize)
        gameField.generateField()
        println(gameField.printField())
        var gameState = true

        while (gameState) {
            println("Bitte geben Sie die Zeile an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")
            val rowInput = scala.io.StdIn.readLine()
            if (rowInput.toLowerCase() == "exit") {
                println("Das Spiel wurde beendet.")
                gameState = false
            } else {
                val row = rowInput.toIntOption
                if (row.isEmpty || row.get < 0 || row.get >= fieldSize+1) {
                    println("Ungültige Eingabe für die Zeile.")
                } else {
                    println("Bitte geben Sie die Spalte an, in der Sie einen Schuss abgeben möchten (oder 'exit' zum Beenden):")
                    val colInput = scala.io.StdIn.readLine()
                    if (colInput.toLowerCase() == "exit") {
                        println("Das Spiel wurde beendet.")
                        gameState = false
                    } else {
                        val col = colInput.toIntOption
                        if (col.isEmpty || col.get < 0 || col.get >= fieldSize+1) {
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