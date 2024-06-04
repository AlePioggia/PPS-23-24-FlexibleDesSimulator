package robotswarm.model

import utils.Position
import collection.mutable.Set

trait Grid:
    val width: Int
    val height: Int

case class Environment (val width: Int, val height: Int, val robots: Set[Robot] = Set.empty) extends Grid:
    protected val grid = Array.fill[Option[Robot]](width, height)(None)
    private val pickupObjects = Array.fill[Boolean](width, height)(false)

    def addRobot(robot: Robot): Unit =
        if isPositionValid(robot.pos) then
            robots += robot
            placeRobot(robot)
        else
            throw new IllegalArgumentException("Invalid position")

    def addPickupObj(pos: Position): Unit = pickupObjects(pos.x)(pos.y) = true 

    def removePickupObj(pos: Position): Unit = pickupObjects(pos.x)(pos.y) = false

    def isPositionValid(pos: Position): Boolean =
        val (x, y) = (pos.x, pos.y)
        x >= 0 && x < width && y >= 0 && y < height && grid(pos.x)(pos.y).isEmpty

    def isRobotHere(pos: Position): Boolean =
        !grid(pos.x)(pos.y).isEmpty

    def moveRobot(robot: Robot): Unit =
        val nextPos = robot.nextPosition()
        if isPositionValid(nextPos) then
            removeRobot(robot)
            robot.move()
            if isPickupObj(nextPos) then robot.pickUp()
            placeRobot(robot)
        else
            throw new IllegalArgumentException("Invalid position")

    def isPickupObj = (pos: Position) => pickupObjects(pos.x)(pos.y)
    
    def getRobotAt(pos: Position): Option[Robot] =
        if isRobotHere(pos) then grid(pos.x)(pos.y)
        else None

    def isRobotCarrying(id: Int): Boolean =
        robots.find(_.id == id).exists(_.isCarrying)

    private def placeRobot(robot: Robot): Unit = grid(robot.pos.x)(robot.pos.y) = Some(robot)

    private def removeRobot(robot: Robot): Unit = grid(robot.pos.x)(robot.pos.y) = None