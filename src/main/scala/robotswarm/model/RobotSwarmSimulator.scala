package robotswarm.model

import core.{Event, BasicSimulator}
import robotswarm.model.{Robot} 

case class RobotMoveEvent(time: Double, robot: Robot, environment: RobotEnvironment) extends Event
case class AllRobotMovesEvent(time: Double, environment: RobotEnvironment) extends Event

class RobotSwarmSimulator extends BasicSimulator:
    override def handleEvent(event: Event): Unit = event match
        case RobotMoveEvent(_, robot, environment) =>
            environment.moveAgent(robot)
        case AllRobotMovesEvent(_, environment) =>
            environment.agents.foreach(robot => environment.moveAgent(robot))
        case _ => super.handleEvent(event)