package pos.model

import utils.Position
import utils.Direction
import core.model.Agent
import scala.util.Random

case class State(var pos: Position, var velocity: Position)
case class Best(var personalBest: Position, var globalBest: Position)

class Particle (val id: Int)(var state: State, var best: Best) extends Agent:
    var pos = state.pos
    var dir: Direction = _
    override def move(): Unit = 
        if state.velocity != Position(0, 0) then
            pos = nextPosition()
            
    override def nextPosition(): Position = 
        val (w, c1, c2, r1, r2) = (0.5, 1.5, 1.5, 0.5, 0.5)
        
        val x = (w * state.velocity.x 
            + c1 * r1 * (best.personalBest.x - state.pos.x) 
            + c2 * r2 * (best.globalBest.x - state.pos.x)).toInt
        
        val y = (w * state.velocity.y 
            + c1 * r1 * (best.personalBest.y - state.pos.y) 
            + c2 * r2 * (best.globalBest.y - state.pos.y)).toInt

        state.velocity = Position(x, y)
        Position(pos.x + state.velocity.x, pos.y + state.velocity.y)
