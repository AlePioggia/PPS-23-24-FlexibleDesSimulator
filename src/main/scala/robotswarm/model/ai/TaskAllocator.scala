package robotswarm.model.ai

import scala.collection.mutable
import robotswarm.model.Robot
import utils.Position
import scala.compiletime.ops.double

object TaskAllocator:
    def assignTasks(robots: Set[Robot], objects: List[Position]): Map[Robot, Position] = 
        val assignments = mutable.Map[Robot, Position]()
        val availableObjects = objects.to(mutable.Queue)

        for robot <- robots do
            if availableObjects.nonEmpty then
                val closestObject = availableObjects.minBy(obj => manhattanDistance(robot.pos, obj))
                assignments(robot) = closestObject
                availableObjects -= closestObject

        assignments.toMap

    private def manhattanDistance(start: Position, goal: Position): Int =
        Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y)

        

