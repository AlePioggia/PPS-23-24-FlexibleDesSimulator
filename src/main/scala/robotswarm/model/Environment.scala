package robotswarm.model

import utils.Position
import collection.mutable.Set

trait Grid:
    val width: Int
    val height: Int
end Grid

case class Environment (val width: Int, val height: Int, val robots: Set[Robot] = Set.empty) extends Grid:
    private val grid = Array.fill[Option[Robot]](width, height)(None)

    def addRobot(robot: Robot): Unit =
        if isPositionValid(robot.pos) then
            robots += robot
            placeRobot(robot)
        else
            throw new IllegalArgumentException("Invalid position")

    def isPositionValid(pos: Position): Boolean =
        val (x, y) = (pos.x, pos.y)
        x >= 0 && x < width && y >= 0 && y < height && grid(pos.x)(pos.y).isEmpty
    
    def moveRobot(robot: Robot): Unit =
        val nextPos = robot.nextPosition()
        if isPositionValid(nextPos) then
            removeRobot(robot)
            robot.move()
            placeRobot(robot)
        else
            throw new IllegalArgumentException("Invalid position")

    private def placeRobot(robot: Robot): Unit = grid(robot.pos.x)(robot.pos.y) = Some(robot)

    private def removeRobot(robot: Robot): Unit = grid(robot.pos.x)(robot.pos.y) = None