package core.model

import scala.collection.mutable.Set
import utils.Position

trait Grid:
    val width: Int
    val height: Int
    val objectManager: PickupObjsManager = PickupObjsManager(width, height)
    val agentManager: AgentManager = AgentManager(width, height)

class Environment(val width: Int, val height: Int) extends Grid:

    def moveAgent(agent: Agent): Unit =
        val nextPos = nextPosition(agent)
        if agentManager.isPositionValid(nextPos) then
            agentManager.removeAgent(agent)
            preMoveActions(agent)
            if objectManager.isObjectAt(nextPos) then interactWithObject(agent)
            agentManager.placeAgent(agent)
            postMoveActions(agent)
        else
            throw new IllegalArgumentException("Invalid position")

    def preMoveActions(agent: Agent): Unit = agent.move() 

    def postMoveActions(agent: Agent): Unit = ()

    def nextPosition(agent: Agent): Position = agent.nextPosition()

    def interactWithObject(agent: Agent): Unit = agent.interactWithObject()

    def neighbors(pos: Position): List[Position] =
        val (x, y) = (pos.x, pos.y)
        val neighbors = List(
            Position(x - 1, y),
            Position(x + 1, y),
            Position(x, y - 1),
            Position(x, y + 1)
        )
        neighbors.filter(agentManager.isPositionValid)