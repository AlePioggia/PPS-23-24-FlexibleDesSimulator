package robotswarm.model

import utils.Position
import collection.mutable.Set

trait Grid:
    val width: Int
    val height: Int
end Grid

case class Environment (val width: Int, val height: Int, val robots: Set[Robot] = Set.empty) extends Grid:
    private val grid = Array.ofDim[Option[Robot]](width, height)

    def addRobot(robot: Robot): Unit =
        if isPositionValid(robot.pos) then
            robots += robot
            grid(robot.pos.x)(robot.pos.y) = Some(robot)
        else
            throw new IllegalArgumentException("Invalid position")

    def isPositionValid(pos: Position): Boolean =
        val (x, y) = (pos.x, pos.y)
        x >= 0 && x < width && y >= 0 && y < height
    
    def moveRobot(robot: Robot): Unit =
        val nextPos = robot.nextPosition()
        if isPositionValid(nextPos) then
            grid(robot.pos.x)(robot.pos.y) = None
            robot.move()
            grid(robot.pos.x)(robot.pos.y) = Some(robot)
        else
            throw new IllegalArgumentException("Invalid position")