package util.command

import controller.controllerComponent.controllerImpl.Controller

trait Command {
  
    def execute(controller: Controller): Unit

}

case class AttackCommand() extends Command {

    override def execute(controller: Controller): Unit = {

        println("Attack Command ausgeführt.")

    }

}

case class PlaceShipCommand() extends Command {

    override def execute(controller: Controller): Unit = {

        println("Place Ship Command ausgeführt.")

    }

}
