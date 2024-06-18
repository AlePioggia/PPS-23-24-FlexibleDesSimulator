package robotswarm

import controller.RobotSwarmController
import model.{RobotSwarmSimulator}
import view.RobotSwarmView
import scala.collection.mutable.Set
import utils.Position
import utils.Direction
import model.Robot
import model.AllRobotMovesEvent
import model.Battery
import model.RobotEnvironment
import model.ai.TaskAllocator
import model.ai.AStar

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
    environment.agentManager.addAgent(robot1)
    environment.agentManager.addAgent(robot2)
    environment.agentManager.addAgent(robot3)
    environment.agentManager.addAgent(robot4)
    println(environment.agentManager.agents)
    val objects = List(Position(0, 2), Position(9, 7), Position(3, 3), Position(4, 5))
    
    for obj <- objects do
      environment.objectManager.addObject(obj)

    simulator.setup(robots, environment, objects)

    val view = new RobotSwarmView(environment, simulator)
    val controller = new RobotSwarmController(environment, simulator, view)

    controller.initialize()
    view.setVisible(true)
    controller.simulate(100)

