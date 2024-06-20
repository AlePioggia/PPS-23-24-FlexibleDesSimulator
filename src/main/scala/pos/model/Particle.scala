package pos.model

import utils.Position
import utils.Direction
import core.model.Agent
import scala.util.Random

case class Best(var personalBest: Position, var personalBestFitness: Double, var globalBest: Position)

case class Particle (val id: Int)(var pos: Position, var velocity: Position, var best: Best) extends Agent:
    var dir: Direction = _

    def moveTo(nextPos: Position): Unit = 
        if velocity != Position(0, 0) then
            pos = nextPos