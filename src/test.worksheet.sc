enum Ship:
    case Carrier, Battleship, Destroyer, Submarine, PatrolBoat
    
enum Shipsize:
    case Two, Three, Four, Five  

case class Battleship(stype: Ship, size: Shipsize)

val ship1= Battleship(Ship.Carrier, Shipsize.Five)
val ship2= Battleship(Ship.Battleship, Shipsize.Five)
val ship3= Battleship(Ship.Destroyer, Shipsize.Four)
val ship4= Battleship(Ship.Submarine, Shipsize.Three)
val ship5= Battleship(Ship.PatrolBoat, Shipsize.Two)

ship1.size
ship2.size
ship3.size
ship4.size
ship5.size

