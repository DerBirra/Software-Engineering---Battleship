// package controller
// import org.scalatest.matchers.should.Matchers
// import org.scalatest.wordspec.AnyWordSpec
// import model.{GameField, Ship, Shipsize, Battleship, Shipindex, Player}
// import util.Observer

// class ControllerSpec extends AnyWordSpec with Matchers {

//   "A Controller" when {
//     "observed by an Observer" should {
//       val controller = new controller
//       val observer = new ObserverMock
//       controller.add(observer)

//       "notify its Observer after creating a player" in {
//         controller.createPlayer()
//         observer.isUpdated shouldBe true
//       }

//       "notify its Observer after creating a game field" in {
//         controller.createGameField()
//         observer.isUpdated shouldBe true
//       }

//       "notify its Observer after placing ships" in {
//         val gameField = new GameField(10, 10) // Beispiel für ein Spielbrett mit Größe 10x10
//         val ship1 = Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
//         val ship2 = Battleship(Ship.Destroyer, Shipsize.Three, Shipindex.Two)
//         val ships = List(ship1, ship2)
//         controller.placeShips(gameField, ships)
//         observer.isUpdated shouldBe true
//       }

//       "notify its Observer after updating the game field" in {
//         controller.updateGameField
//         observer.isUpdated shouldBe true
//       }
//     }
//   }

//   // Mock Observer class
//   class ObserverMock extends Observer {
//     var isUpdated: Boolean = false
//     override def update: Unit = isUpdated = true
//   }
// }
