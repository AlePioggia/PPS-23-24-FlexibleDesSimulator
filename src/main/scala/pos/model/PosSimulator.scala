package pos.model

import core.simulator.Event
import core.simulator.BasicSimulator

case class PosMoveEvent(time: Double, particle: Particle, environment: PosEnvironment) extends Event
case class AllPosMovesEvent(time: Double, environment: PosEnvironment) extends Event

class PosSimulator extends BasicSimulator:
    var iterationCount: Int = 0
    var maxIterations: Int = 20
    var lastBestFitness: Double = Double.MaxValue
    var stagnationCounter: Int = 0
    var maxStagnation: Int = 5

    override def handleEvent(event: Event): Unit = event match
        case PosMoveEvent(_, particle, environment) =>
            environment.moveAgent(particle)
        case AllPosMovesEvent(_, environment) =>
            environment.agentManager.agents.foreach(particle => {
                environment.moveAgent(particle)
            })
        case _ => super.handleEvent(event)

    override def shouldStop: Boolean = 
        iterationCount >= maxIterations || stagnationCounter >= maxStagnation
    