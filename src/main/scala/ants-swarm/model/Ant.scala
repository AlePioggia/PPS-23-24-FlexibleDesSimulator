package `ants-swarm`.model

import core.model.Agent
import utils.Position
import utils.Direction

class Ant(val id: Int, var pos: Position, var dir: Direction, val nestPos: Position) extends Agent {
    
}
