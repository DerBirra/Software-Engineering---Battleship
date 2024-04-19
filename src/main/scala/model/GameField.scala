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

    def printField(): String = {
        val builder = new StringBuilder

        // oberen Rand des Spielfelds ausgeben
        builder.append("+" + "-" * col * 3 + "+\n")

        for {
        // indexe i und j für alle Zellen
                i <- 0 until row
                j <- 0 until col
        } {
        // Rahmen links
         if (j == 0) builder.append("|")

         // Inhalt der Zelle wenn nicht leer als # und wenn bereits angegriffen als X
         if (field(i)(j) == ' ') builder.append(" # ")
         else builder.append(" " + field(i)(j) + " ")

        // Rahmen rechts
         if (j == col - 1) builder.append("|\n")
        }

        // unteren Rand des Spielfelds ausgeben
        builder.append("+" + "-" * col * 3 + "+\n")
        builder.toString()
    }   


    def setCell(rowIdx: Int, colIdx: Int, value: Char): Unit = {
    
        // wenn die Koordinaten innerhalb des Spielfelds liegen
        if (rowIdx >= 1 && rowIdx < row+1 && colIdx >= 1 && colIdx < col+1) {
        
            // Zelle auf angegriffen setzen
            field(rowIdx-1)(colIdx-1) = value
        
        } else {
            // Error Nachricht ausgeben
            println("Ungültige Zellkoordinaten")
        
        }
    }

    def getCell(rowIdx: Int, colIdx: Int): Option[Char] = {
    
        // wenn die Koordinaten innerhalb des Spielfelds liegen
        if (rowIdx >= 0 && rowIdx < row && colIdx >= 0 && colIdx < col) {
        
            // Inhalt der Zelle zurückgeben
            Some(field(rowIdx)(colIdx))
        
        } else {
        
            println("Ungültige Zellkoordinaten")
            None
        
        }
    }

    def isCellContent(rowIdx: Int, colIdx: Int, char: Char): Boolean = {
    
        getCell(rowIdx, colIdx) match {
        
            case Some(content) => content == char
            case None => false
        
        }
    
    }
  
}
