package robotswarm

import robotswarm.controller.RobotSwarmController
import robotswarm.model.{RobotSwarmSimulator}
import robotswarm.view.RobotSwarmView
import scala.collection.mutable.Set
import utils.Position
import utils.Direction
import model.Robot
import model.AllRobotMovesEvent
import robotswarm.model.Battery
import robotswarm.model.RobotEnvironment

object App:
  def main(args: Array[String]): Unit =
    val robots: Set[Robot] = scala.collection.mutable.Set.empty
    val environment = new RobotEnvironment(10, 10)
    val simulator = new RobotSwarmSimulator

    val robot1 = Robot(1)(new Position(0, 0))(Direction.North)(Battery.Low)
    val robot2 = Robot(2)(new Position(9, 9))(Direction.South)
    robots.add(robot1)
    robots.add(robot2)
    environment.addAgent(robot1)
    environment.addAgent(robot2)
    environment.addObject(Position(0, 2))
    environment.addObject(Position(9, 7))

    for (i <- 0 until 8)
      val time = i * 1.0
      simulator.schedule(new AllRobotMovesEvent(time, environment))

    val view = new RobotSwarmView(environment, simulator)
    val controller = new RobotSwarmController(environment, simulator, view)

    controller.initialize()
    view.setVisible(true)

