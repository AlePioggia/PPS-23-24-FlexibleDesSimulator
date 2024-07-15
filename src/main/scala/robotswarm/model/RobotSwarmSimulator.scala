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
    var paths = Map.empty[RobotId, ReversibleIterator[Direction]]

    override def handleEvent(event: Event): Unit = event match
        case RobotMoveEvent(_, robot, environment) =>
            environment.moveAgent(robot)
        case AllRobotMovesEvent(_, environment) =>
            environment.agentManager.agents.foreach(robot => {
                val iterator = paths(robot.id)
                if iterator.hasNext then
                    val direction: Direction = iterator.next()
                    robot.dir = direction
                    if environment.agentManager.isPositionValid(environment.nextPosition(robot)) then 
                        environment.moveAgent(robot)
                    else iterator.previous()
            })
        case _ => super.handleEvent(event)

    def setup(environment: RobotEnvironment): Map[RobotId, Iterator[Direction]] = {
        val robots = environment.agentManager.agents.asInstanceOf[scala.collection.mutable.Set[Robot]]
        val objects = environment.objectManager.objsPosList
        var assignments = TaskAllocator.assignTasks(robots, objects)        
        paths ++= assignments.map { case (robot, goal) => {robot.goal = goal; (robot.id, ReversibleIterator(AStar.findPath(robot.pos, goal, environment).iterator))}}
        paths
    }

import scala.collection.mutable

class ReversibleIterator[T](underlying: Iterator[T]) extends Iterator[T] {
    private val buffer = mutable.Stack[T]()
    private var lastElement: Option[T] = None

    override def hasNext: Boolean = underlying.hasNext || buffer.nonEmpty

    override def next(): T = {
        if (buffer.nonEmpty) {
            lastElement = Some(buffer.pop())
        } else {
            lastElement = Some(underlying.next())
        }
        lastElement.get
    }

    def previous(): Unit = {
        lastElement.foreach(buffer.push)
        lastElement = None
    }
}

object ReversibleIterator {
    def apply[T](iterator: Iterator[T]): ReversibleIterator[T] = new ReversibleIterator(iterator)
}