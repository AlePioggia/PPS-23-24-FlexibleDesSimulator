package `antsswarm`.model

import core.model.Agent
import utils.Position
import utils.Direction

class Ant(val id: Int, var pos: Position, var dir: Direction, val nestPos: Position) extends Agent {
    var carryingFood: Boolean = false

    override def interactWithObject(): Unit = 
        if carryingFood then if pos == nestPos then carryingFood = false
        else carryingFood = true
}
