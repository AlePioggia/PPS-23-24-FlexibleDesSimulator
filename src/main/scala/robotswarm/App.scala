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
import robotswarm.model.ai.TaskAllocator
import robotswarm.model.ai.AStar

object App:
  def main(args: Array[String]): Unit =
    val robots: Set[Robot] = Set.empty
    val environment = new RobotEnvironment(10, 10)
    val simulator = new RobotSwarmSimulator

    val robot1 = Robot(1)(new Position(3, 2))(Direction.North)(Battery.Low)
    val robot2 = Robot(2)(new Position(9, 9))(Direction.South)
    val robot3 = Robot(3)(new Position(5, 5))(Direction.East)
    val robot4 = Robot(4)(new Position(9, 0))(Direction.West)
    robots.add(robot1)
    robots.add(robot2)
    robots.add(robot3)
    robots.add(robot4)
    environment.addAgent(robot1)
    environment.addAgent(robot2)
    environment.addAgent(robot3)
    environment.addAgent(robot4)
    val objects = List(Position(0, 2), Position(9, 7), Position(3, 3), Position(4, 5))
    
    for obj <- objects do
      environment.addObject(obj)

    var setup = simulator.setup(robots, environment, objects)
    println(setup)

    for until <- 1 to 10 do
      simulator.schedule(AllRobotMovesEvent(0, environment))

    val view = new RobotSwarmView(environment, simulator)
    val controller = new RobotSwarmController(environment, simulator, view)

    controller.initialize()
    view.setVisible(true)

