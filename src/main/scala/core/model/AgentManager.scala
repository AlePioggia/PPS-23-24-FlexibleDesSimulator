package core.model

import scala.collection.mutable.Set
import utils.Position


class AgentManager(val width: Int, val height: Int):
    protected val grid = Array.fill[Option[Agent]](width, height)(None)
    val agents = Set.empty[Agent]

    def addAgent(agent: Agent): Unit = 
        if isPositionValid(agent.pos) then {agents += agent; placeAgent(agent)}
        else throw IllegalArgumentException("Invalid position")

    def removeAgent(agent: Agent): Unit = {grid(agent.pos.x)(agent.pos.y) = None}

    def getAgentAt(pos: Position): Option[Agent] = grid(pos.x)(pos.y)

    def moveAgent(agent: Agent): Unit =
        val nextPos = agent.nextPosition()
        if isPositionValid(nextPos) then
            removeAgent(agent)
            agent.move()
            placeAgent(agent)
        else
            throw new IllegalArgumentException("Invalid position")

    def isPositionValid(pos: Position): Boolean =
        pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height && grid(pos.x)(pos.y).isEmpty

    def placeAgent(agent: Agent): Unit = grid(agent.pos.x)(agent.pos.y) = Some(agent)

