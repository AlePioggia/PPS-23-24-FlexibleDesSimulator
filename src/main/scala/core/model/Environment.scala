package core.model

import scala.collection.mutable.Set
import utils.Position

trait Grid:
    val width: Int
    val height: Int
    def isPositionValid(pos: Position): Boolean
    def isObjectAt(pos: Position): Boolean
    def addObject(pos: Position): Unit
    def removeObject(pos: Position): Unit

class Environment(val width: Int, val height: Int, val agents: Set[Agent] = Set.empty) extends Grid:
    protected val grid = Array.fill[Option[Agent]](width, height)(None)
    var objects = Array.fill[Boolean](width, height)(false)

    def addAgent(agent: Agent): Unit =
        if isPositionValid(agent.pos) then
            agents += agent
            placeAgent(agent)
        else
            throw new IllegalArgumentException("Invalid position")

    def addObject(pos: Position): Unit = objects(pos.x)(pos.y) = true 

    def removeObject(pos: Position): Unit = objects(pos.x)(pos.y) = false

    def isPositionValid(pos: Position): Boolean =
        val (x, y) = (pos.x, pos.y)
        x >= 0 && x < width && y >= 0 && y < height && grid(pos.x)(pos.y).isEmpty

    def isObjectAt(pos: Position): Boolean =
        objects(pos.x)(pos.y)

    def objsPosList(): List[Position] =
        val objs = for
            x <- 0 until width
            y <- 0 until height
            if objects(x)(y)
        yield Position(x, y)
        objs.toList

    def moveAgent(agent: Agent): Unit =
        val nextPos = agent.nextPosition()
        if isPositionValid(nextPos) then
            removeAgent(agent)
            agent.move()
            if isObjectAt(nextPos) then interactWithObject(agent)
            placeAgent(agent)
        else
            throw new IllegalArgumentException("Invalid position")

    def getAgentAt(pos: Position): Option[Agent] =
        if grid(pos.x)(pos.y).isDefined then grid(pos.x)(pos.y)
        else None

    private def placeAgent(agent: Agent): Unit = grid(agent.pos.x)(agent.pos.y) = Some(agent)

    private def removeAgent(agent: Agent): Unit = grid(agent.pos.x)(agent.pos.y) = None

    def interactWithObject(agent: Agent): Unit = agent.interactWithObject()

    def neighbors(pos: Position): List[Position] =
        val (x, y) = (pos.x, pos.y)
        val neighbors = List(
            Position(x - 1, y),
            Position(x + 1, y),
            Position(x, y - 1),
            Position(x, y + 1)
        )
        neighbors.filter(isPositionValid)