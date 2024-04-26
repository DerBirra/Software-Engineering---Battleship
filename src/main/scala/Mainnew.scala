import scala.annotation.meta.field
import java.lang.reflect.Field
import scala.collection.View.Empty
import model.Battleship
import model.GameField
import model.Player
import aview.TUI
object Battleships{
  def main(args: Array[String]): Unit ={
   val gameUI = new TUI()
    gameUI.startGame()
  }
}