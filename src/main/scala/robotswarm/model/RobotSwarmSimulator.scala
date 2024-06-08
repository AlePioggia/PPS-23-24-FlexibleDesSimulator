package robotswarm.model

import core.simulator.{Event, BasicSimulator}
import robotswarm.model.{Robot} 
import utils.Position
import robotswarm.model.ai.TaskAllocator
import utils.Direction
import scala.collection.mutable
import robotswarm.model.ai.AStar

case class RobotMoveEvent(time: Double, robot: Robot, environment: RobotEnvironment) extends Event
case class AllRobotMovesEvent(time: Double, environment: RobotEnvironment) extends Event
type RobotId = Int

class RobotSwarmSimulator extends BasicSimulator:
    var paths = Map.empty[RobotId, Iterator[Direction]]

    override def handleEvent(event: Event): Unit = event match
        case RobotMoveEvent(_, robot, environment) =>
            environment.moveAgent(robot)
        case AllRobotMovesEvent(_, environment) =>
            environment.agentManager.agents.foreach(robot => {
                val iterator = paths(robot.id)
                if iterator.hasNext then
                    val direction: Direction = iterator.next()
                    robot.dir = direction
                    environment.moveAgent(robot)
            })
        case _ => super.handleEvent(event)

    def setup(robots: scala.collection.mutable.Set[Robot], environment: RobotEnvironment, objects: List[Position]): Map[RobotId, Iterator[Direction]] = {
        var assignments = TaskAllocator.assignTasks(robots, objects)        
        paths ++= assignments.map { case (robot, goal) => {robot.goal = goal; (robot.id, AStar.findPath(robot.pos, goal, environment).iterator)}}
        paths
    }