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
    val n = args(0).toInt
    val environment = new RobotEnvironment(10, 10)
    val simulator = new RobotSwarmSimulator
    
    environment.placeRandomAgents(n, 1)
    environment.placeRandomPickupObjs(n)

    simulator.setup(environment)

    val view = new RobotSwarmView(environment, simulator)
    val controller = new RobotSwarmController(environment, simulator, view)

    controller.initialize()
    view.setVisible(true)
    controller.simulateUntilStop()

