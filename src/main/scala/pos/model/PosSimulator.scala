package pos.model

import core.simulator.Event
import core.simulator.BasicSimulator
import utils.Position

case class PosMoveEvent(time: Double, particle: Particle, environment: PosEnvironment) extends Event
case class AllPosMovesEvent(time: Double, environment: PosEnvironment) extends Event

class PosSimulator extends BasicSimulator:
    override def handleEvent(event: Event): Unit = event match
        case PosMoveEvent(_, particle, environment) =>
            environment.moveAgent(particle)
        case AllPosMovesEvent(_, environment) =>
            environment.agentManager.agents.foreach(particle => {
                environment.moveAgent(particle)
            })
        case _ => super.handleEvent(event)