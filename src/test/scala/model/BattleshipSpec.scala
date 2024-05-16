// package model
// import org.scalatest.wordspec.AnyWordSpec
// import org.scalatest.matchers.should.Matchers._
// import model.Ship
// import model.GameField
// import scala.annotation.meta.field

// class BattleshipSpec extends AnyWordSpec{
//     val ship1 = Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
//     val ship2 = Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two)
//     val ship3 = model.Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two)
//     val ship4 = model.Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two)
//     val ship5 = model.Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two)
    
//     "Battleshiptype" should{
//         "initialized correct" in{
//             ship1.stype shouldEqual Ship.Carrier
//             ship2.stype shouldEqual Ship.Battleship
//             ship3.stype shouldEqual Ship.Destroyer
//             ship4.stype shouldEqual Ship.Submarine
//             ship5.stype shouldEqual Ship.PatrolBoat
//         }
//     }
//     "Shipsize" should{
//         "initialized correct" in{
//             ship1.size shouldEqual Shipsize.Five
//             ship2.size shouldEqual Shipsize.Five
//             ship3.size shouldEqual Shipsize.Four
//             ship4.size shouldEqual Shipsize.Three
//             ship5.size shouldEqual Shipsize.Two
//         }
//     }
//     "Shipindex" should{
//         "initialized correct" in{
//             ship1.index shouldEqual Shipindex.Two
//             ship2.index shouldEqual Shipindex.Two
//             ship3.index shouldEqual Shipindex.Two
//             ship4.index shouldEqual Shipindex.Two
//             ship5.index shouldEqual Shipindex.Two
//         }
//     }
// }
