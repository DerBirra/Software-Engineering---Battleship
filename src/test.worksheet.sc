enum Ships:
    case Carrier, Battleship, Destroyer, Submarine, PatolBoat
    
enum Shipsizes:
    case Two, Three, Four, Five  

case class Battleships(stype: Ships, size: Shipsizes)