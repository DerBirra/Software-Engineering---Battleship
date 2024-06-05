package util

import model.GameBoard

class Caretaker {

    private var mementos: List[GameMemento] = List()

    def addMemento(memento: GameMemento): Unit = {

        mementos = mementos :+ memento

    }

    def getMemento(index: Int): GameMemento = {

        mementos(index)

    }

    def getLastMemento(): GameMemento = {

        if (mementos.nonEmpty) {

            val last = mementos.last
            mementos = mementos.dropRight(1)
            last

        } else {

            null

        }

    }
  
}
