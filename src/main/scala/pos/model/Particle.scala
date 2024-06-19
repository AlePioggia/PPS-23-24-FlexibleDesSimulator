package pos.model

import utils.Position
import utils.Direction
import core.model.Agent
import scala.util.Random

case class State(var pos: Position, var velocity: Position)
case class Best(var personalBest: Position, var personalBestFitness: Double, var globalBest: Position)

case class Particle (val id: Int)(var state: State, var best: Best) extends Agent:
    var pos = Position(0, 0)
    var dir: Direction = _

    def moveTo(pos: Position): Unit = 
        if state.velocity != Position(0, 0) then
            state.pos = pos