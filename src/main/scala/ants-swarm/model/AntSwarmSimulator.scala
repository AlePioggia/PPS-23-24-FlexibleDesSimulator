package `antsswarm`.model

import core.simulator.Event
import core.simulator.BasicSimulator

case class AntMoveEvent(time: Double, ant: Ant, environment: AntsEnvironment) extends Event
case class AllAntMovesEvent(time: Double, environment: AntsEnvironment) extends Event

class AntSwarmSimulator extends BasicSimulator:
    override def handleEvent(event: Event): Unit = event match
        case AntMoveEvent(_, ant, environment) =>
            environment.moveAgent(ant)
        case AllAntMovesEvent(_, environment) =>
            environment.agentManager.agents.foreach(ant => environment.moveAgent(ant))
        case _ => super.handleEvent(event)
