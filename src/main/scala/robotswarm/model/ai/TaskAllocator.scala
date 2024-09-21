package robotswarm.model.ai

import scala.collection.mutable
import robotswarm.model.Robot
import utils.Position
import scala.compiletime.ops.double

object TaskAllocator:
    def assignTasks(robots: scala.collection.mutable.Set[Robot], objects: List[Position]): Map[Robot, Position] = 
        val availableObjects = objects.to(mutable.Queue)

        robots
            .filter(_ => availableObjects.nonEmpty)
            .map {
                robot => 
                    val closestObject = availableObjects.minBy(obj => AStarUtils.manhattanDistance(robot.pos, obj))
                    availableObjects -= closestObject
                    robot -> closestObject
            }
            .toMap