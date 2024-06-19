package pos.model

import core.model.Environment
import utils.Position
import core.model.Agent

class PosEnvironment(width: Int, height: Int)(fitnessFunction: Position => Double) extends Environment(width, height):
    var globalBest: Position = Position(Int.MaxValue, Int.MaxValue)
    var globalBestFitness: Double = Double.MaxValue

    override def preMoveActions(agent: Agent): Unit = agent.move()
