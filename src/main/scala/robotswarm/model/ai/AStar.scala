package robotswarm.model.ai

import scala.collection.mutable
import core.model.Environment
import utils.Position
import utils.Direction
import _root_.robotswarm.model.Robot
import robotswarm.model.RobotEnvironment

object AStar:
    
    def findPath(start: Position, goal: Position, environment: RobotEnvironment): List[Direction] =
        AStarContext(start, goal, environment).search()

    class AStarContext(start: Position, goal: Position, environment: RobotEnvironment):
        val openSet = mutable.PriorityQueue.empty[Node](Ordering.by(-_.f))
        val cameFrom = mutable.Map[Position, Position]()
        val gScore = mutable.Map[Position, Double]().withDefaultValue(Double.PositiveInfinity)
        val fScore = mutable.Map[Position, Double]().withDefaultValue(Double.PositiveInfinity)
        
        gScore(start) = 0
        fScore(start) = AStarUtils.manhattanDistance(start, goal)
        openSet.enqueue(Node(start, 0, fScore(start)))

        def search(): List[Direction] =
            if openSet.isEmpty then return List.empty
            val current = openSet.dequeue().position
            if current == goal then return AStarUtils.reconstructPath(cameFrom, current)
            evaluateAndUpdateNeighbors(current)
            search()

        private def evaluateAndUpdateNeighbors(current: Position): Unit =
            environment
                .neighbors(current)
                .filterNot(neighbor => environment.isObstacle(neighbor, goal))
                .foreach(neighbor => if gScore(current) < gScore(neighbor) then processNeighbor(current, neighbor))

        private def processNeighbor(current: Position, neighbor: Position): Unit =
            cameFrom(neighbor) = current
            gScore(neighbor) = gScore(current) + 1
            fScore(neighbor) = gScore(neighbor) + AStarUtils.manhattanDistance(neighbor, goal)
            if !openSet.exists(_.position == neighbor) then
                openSet.enqueue(Node(neighbor, gScore(neighbor), fScore(neighbor)))

    case class Node(position: Position, g: Double, h: Double):
        def f: Double = g + h

object AStarUtils:
        def manhattanDistance(start: Position, goal: Position): Double =
            Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y)

        def reconstructPath(cameFrom: mutable.Map[Position, Position], current: Position): List[Direction] =
            if !cameFrom.contains(current) then List.empty
            else
                val prev = cameFrom(current)
                calculateDirection(prev, current) :: reconstructPath(cameFrom, prev)

        def calculateDirection(from: Position, to: Position): Direction =
            if to.x > from.x then Direction.East
            else if to.x < from.x then Direction.West
            else if to.y > from.y then Direction.South
            else Direction.North
