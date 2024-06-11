package util.memento

import util.memento.GameState

class GameMemento(private val state: GameState) {

    def getState: GameState = state
  
}
