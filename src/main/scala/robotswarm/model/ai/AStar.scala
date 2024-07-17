package robotswarm.model.ai

import scala.collection.mutable
import core.model.Environment
import utils.Position
import utils.Direction
import _root_.robotswarm.model.Robot
import robotswarm.model.RobotEnvironment

object AStar:
    def findPath(start: Position, goal: Position, environment: RobotEnvironment): List[Direction] =
        val openSet = mutable.PriorityQueue.empty[Node](Ordering.by(-_.f))
        val cameFrom = mutable.Map[Position, Position]()
        val gScore = mutable.Map[Position, Double]().withDefaultValue(Double.PositiveInfinity)
        val fScore = mutable.Map[Position, Double]().withDefaultValue(Double.PositiveInfinity)
        
        gScore(start) = 0
        fScore(start) = manhattanDistance(start, goal)
        openSet.enqueue(Node(start, 0, fScore(start)))

        while openSet.nonEmpty do
            val current = openSet.dequeue().position

            if current == goal then
                return reconstructPath(cameFrom, current)

            for neighbor <- environment.neighbors(current) do
                if !environment.isObstacle(neighbor, goal) then
                    val tentativeGScore = gScore(current) + 1
                    if tentativeGScore < gScore(neighbor) then
                        cameFrom(neighbor) = current
                        gScore(neighbor) = tentativeGScore
                        fScore(neighbor) = gScore(neighbor) + manhattanDistance(neighbor, goal)
                        if !openSet.exists(_.position == neighbor) then
                            openSet.enqueue(Node(neighbor, gScore(neighbor), fScore(neighbor)))

        List.empty

    private def manhattanDistance(start: Position, goal: Position): Double =
        Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y)

    private def reconstructPath(cameFrom: mutable.Map[Position, Position], current: Position): List[Direction] =
        if !cameFrom.contains(current) then List.empty
        else
            val prev = cameFrom(current)
            calculateDirection(prev, current) :: reconstructPath(cameFrom, prev)

    private def calculateDirection(from: Position, to: Position): Direction =
        if to.x > from.x then Direction.East
        else if to.x < from.x then Direction.West
        else if to.y > from.y then Direction.South
        else Direction.North

case class Node(position: Position, g: Double, h: Double):
  def f: Double = g + h
