import scala.annotation.meta.field
import java.lang.reflect.Field
import scala.collection.View.Empty
import model.Battleship
import model.GameField
import model.Player
object Battleships{
  def main(args: Array[String]): Unit ={
    println("Bitte geben Sie ihren Namen ein:")
    val student = Player(scala.io.StdIn.readLine())
    println("Willkommen " +student.name + " Bitte geben Sie eine gewünschte Feld größe ein:")
    val fieldSize = scala.io.StdIn.readInt()

    val gameField = new GameField(fieldSize, fieldSize)
    gameField.generateField()
    println(gameField.printField())

    val gameState = true

    while (gameState) {
    
      print("Bitte geben sie die Zeile an in der sie ein Schuss abgeben möchten ")
      val rowInput = scala.io.StdIn.readLine().toInt
      println()
      print("Bitte geben sie die Spalte an in der sie einen Schuss abgeben möchten ")
      val colInput = scala.io.StdIn.readLine().toInt

      val isCell = gameField.isCellContent(rowInput, colInput, 'x')

      if (isCell) {

        println("Bitte eine noch nicht verwendete Koordinate nehmen.")

      } else {

        gameField.cell(rowInput, colInput, Some('X'));
        println(gameField.printField())

      }
  } 

  }

}