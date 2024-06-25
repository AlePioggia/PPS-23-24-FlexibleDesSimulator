package pos.model

import core.model.Agent
import scala.util.Random
import _root_.utils.Position
import _root_.utils.Direction

case class Best(var personalBest: Position, var personalBestFitness: Double, var globalBest: Position)

case class Particle (val id: Int)(var pos: Position, var velocity: Position, var best: Best) extends Agent:
    var dir: Direction = _

    def moveTo(nextPos: Position): Unit = 
        if velocity != Position(0, 0) then
            pos = nextPos