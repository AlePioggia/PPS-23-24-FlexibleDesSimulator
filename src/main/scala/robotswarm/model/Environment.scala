package robotswarm.model

import utils.Position
import collection.mutable.Set

trait Grid:
    val width: Int
    val height: Int

case class Environment (val width: Int, val height: Int, val robots: Set[Robot] = Set.empty) extends Grid:
    protected val grid = Array.fill[Option[Robot]](width, height)(None)
    var pickupObjects = Array.fill[Boolean](width, height)(false)

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
            if isPickupObj(nextPos) then pickupObj(robot)
            placeRobot(robot)
        else
            throw new IllegalArgumentException("Invalid position")

    def isPickupObj = (pos: Position) => pickupObjects(pos.x)(pos.y)
    
    def placeRandomPickupObjs(n: Int): Unit =
        if n > width * height then throw new IllegalArgumentException("Too many objects") 
        if n == 0 then return
        val random = new scala.util.Random
        var (x, y) = generateRandomCoordinates()
        while isRobotHere(Position(x, y)) || isPickupObj(Position(x, y)) do
            x = random.nextInt(width)
            y = random.nextInt(height)
        addPickupObj(Position(x, y))
        placeRandomPickupObjs(n - 1)

    def getRobotAt(pos: Position): Option[Robot] =
        if isRobotHere(pos) then grid(pos.x)(pos.y)
        else None

    def isRobotCarrying(id: Int): Boolean =
        robots.find(_.id == id).exists(_.isCarrying)

    private def generateRandomCoordinates(): (Int, Int) =
        val random = new scala.util.Random
        (random.nextInt(width), random.nextInt(height))

    private def placeRobot(robot: Robot): Unit = grid(robot.pos.x)(robot.pos.y) = Some(robot)

    private def removeRobot(robot: Robot): Unit = grid(robot.pos.x)(robot.pos.y) = None

    private def pickupObj(robot: Robot): Unit =
        robot.pickUp()
        removePickupObj(robot.pos)