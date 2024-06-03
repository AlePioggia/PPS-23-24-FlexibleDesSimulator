package robotswarm

import robotswarm.controller.RobotSwarmController
import robotswarm.model.{Environment, RobotSwarmSimulator}
import robotswarm.view.RobotSwarmView
import scala.collection.mutable.Set
import utils.Position
import utils.Direction
import model.Robot
import model.AllRobotMovesEvent

object App:
  def main(args: Array[String]): Unit =
    val robots: Set[Robot] = scala.collection.mutable.Set.empty
    val environment = new Environment(10, 10, robots)
    val simulator = new RobotSwarmSimulator

    val robot1 = new Robot(1)(new Position(0, 0))(Direction.North)
    val robot2 = new Robot(2)(new Position(9, 9))(Direction.South)
    robots.add(robot1)
    robots.add(robot2)
    environment.addRobot(robot1)
    environment.addRobot(robot2)

    for (i <- 0 until 5)
      val time = i * 1.0
      simulator.schedule(new AllRobotMovesEvent(time, environment))

    val view = new RobotSwarmView(environment, simulator)
    val controller = new RobotSwarmController(environment, simulator, view)

    controller.initialize()
    view.setVisible(true)

