import scala.annotation.meta.field
import java.lang.reflect.Field
import scala.collection.View.Empty
import model.Player

object Battleshipe{
  def main(args: Array[String]): Unit ={
    println("Bitte geben Sie ihren Namen ein:")
    val student = Player(scala.io.StdIn.readLine())
    println("Hello and Welcome to Batteship, " + student.name)
    println("Wie groß möchten sie ihr Feld haben? Die Breite und Höhe muss mindestens 2 Felder breit sein")
    val playField: List[List[Boolean]] = List(
      List(true, false, false, false, true, true, true, true, true),
      List(true, false, false, false, false, false, false, false, false),
      List(false, false, true, false, false, false, false, false, false),
      List(false, false, false, false, false, false, false, false, false),
      List(false, false, false, false, false, false, false, false, false),
      List(false, false, false, false, false, false, false, false, false),
      List(false, false, false, false, false, false, false, false, false),
      List(false, false, false, false, false, false, false, false, false),
      List(false, false, false, false, false, false, false, false, false)
    )
    var fieldvisible: List[List[Boolean]] = List(
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false),
        List(false, false, false, false, false, false, false, false, false)
    )
    printBoard(playField, fieldvisible)
    for(i <-0 to 10){
      fieldvisible = clickField(playField, fieldvisible)
      printBoard(playField, fieldvisible)
    }
   
  }
   def printBoard(board: List[List[Boolean]], visible: List[List[Boolean]]) : Unit = {
    println("-------------------------------------")
    for(i <- 0 to board.length -1){
      for(j<-0 to board(i).length-1){
        
        var symbol = "#"
        if(visible(i)(j) == true){
          symbol = " "
          if(board(i)(j)==true)symbol = "X"
        }
        print("| "+ symbol+" ")
        if(j == board(i).length-1)print("|")
      }
      println("")
      println("-------------------------------------")
    }
    }
    def clickField(board: List[List[Boolean]],visible: List[List[Boolean]]): List[List[Boolean]] ={
      println("Bitte geben Sie die Zeile (1-9) die sie angreifen wollen an:")
      val row = scala.io.StdIn.readLine().toInt -1
      println("Bitte geben Sie die Spalte (1-9) die sie angreifen wollen an:")
      val col = scala.io.StdIn.readLine().toInt -1
      var updated = List(List(false))
      if (row >= 0 && row < visible.length && col >= 0 && col < visible.head.length) {
        updated = visible.updated(row, visible(row).updated(col, true))
      } 
      if(board(row)(col) == true){
        println("\n Sie haben ein Schiff getroffen\n")
    }
      else{
        println("\n Sie haben verfehlt \n")
      }
      return updated
    }
}

