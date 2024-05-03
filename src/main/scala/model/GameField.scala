package model

class GameField (row : Int, col : Int) {

    var field: Array[Array[Char]] = Array.ofDim[Char](row, col)

    def generateField(): Unit = {

        // Spielfeld mit Leerzeichen füllen
        for {

            i <- 0 until row
            j <- 0 until col

        } field(i)(j) = ' '
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

    def isCellContent(rowIdx: Int, colIdx: Int, char: Char): Boolean = {
    
        cell(rowIdx, colIdx) match {
        
            case Some(content) => content == char
            case None => false
        
        }
    
    }

    override def toString: String = {

        // Oberen Rand des Spielfelds erzeugen
        val top = "+" + "-" * col * 2 + "+\n"

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
        val bottom = "+" + "-" * col * 2 + "+\n"

        // Das gesamte Spielfeld zusammenbauen
        top + fieldContent + bottom

    }

    def getGamFieldSize(): Int = {

      return row

    }
  
}