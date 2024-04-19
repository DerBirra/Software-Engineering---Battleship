
enum Ship:
    case Carrier, Battleship, Destroyer, Submarine, PatrolBoat
    
enum Shipsize:
    case Two, Three, Four, Five 

enum Shipindex:
    case Two, Three, Four, Five

case class Battleship(stype: Ship, size: Shipsize, index: Shipindex)

val ship1= Battleship(Ship.Carrier, Shipsize.Five, Shipindex.Two)
val ship2= Battleship(Ship.Battleship, Shipsize.Five, Shipindex.Two)
val ship3= Battleship(Ship.Destroyer, Shipsize.Four, Shipindex.Two)
val ship4= Battleship(Ship.Submarine, Shipsize.Three, Shipindex.Two)
val ship5= Battleship(Ship.PatrolBoat, Shipsize.Two, Shipindex.Two)


ship2.index
ship1.size
ship2.size
ship3.size
ship4.size
ship5.size
case class Cell(value: Int){
    def isSet:Boolean = value != 0
}
val cell1 = Cell(2)
cell1.isSet

val cell2= Cell(0)
cell2.isSet

case class Matrix[T](rows: Vector[Vector[T]]){
    def this(size: Int, filling: T) = this(Vector.tabulate(size,size){(row, col)=>filling})
    val size: Int = rows.size
    def cell(row: Int, col: Int): T = rows(row)(col)
    def row(row: Int) = rows(row)
    def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size,size){(row,col)=>filling})
    def replaceCell(row:Int, col:Int, cell: T):Matrix[T] = copy(rows.updated(row,rows(row).updated(col,cell)))
}

val matrix = Matrix(Vector(Vector(cell1,cell2)))

//val field = GameField(9,9)

