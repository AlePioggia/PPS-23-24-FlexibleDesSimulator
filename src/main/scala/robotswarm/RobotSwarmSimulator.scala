package robotswarm

import core.{Event, BasicSimulator}
import robotswarm.model.{Robot, Environment}

case class RobotMoveEvent(time: Double, robot: Robot, environment: Environment) extends Event
case class AllRobotMovesEvent(time: Double, environment: Environment) extends Event

class RobotSwarmSimulator extends BasicSimulator:
    override def handleEvent(event: Event): Unit = event match
        case RobotMoveEvent(_, robot, environment) =>
            environment.moveRobot(robot)
        case AllRobotMovesEvent(_, environment) =>
            environment.robots.foreach(robot => environment.moveRobot(robot))
        case _ => super.handleEvent(event)