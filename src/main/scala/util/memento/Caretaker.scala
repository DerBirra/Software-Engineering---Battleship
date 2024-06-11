package util.memento

import util.memento.GameMemento

class Caretaker {

    private var mementos: List[GameMemento] = List()

    def addMemento(memento: GameMemento): Unit = {

        mementos = memento :: mementos

    }

    def getMemento: Option[GameMemento] = {
        val memento = mementos.headOption
        if (memento.isDefined) {
            mementos = mementos.tail
        }
        memento
  }
  
}
