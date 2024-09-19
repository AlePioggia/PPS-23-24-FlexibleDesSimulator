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
            agentManager.placeAgentAt(agent, nextPos)
            postMoveActions(agent)
            
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

    def populateGrid(): Option[Position] =
        if agentManager.agents.size + objectManager.objsPosList.size >= width * height then
            None
        else
            val random = new scala.util.Random
            var (x, y) = generateRandomCoordinates()
            while agentManager.getAgentAt(Position(x, y)).isDefined || objectManager.isObjectAt(Position(x, y)) do
                x = random.nextInt(width)
                y = random.nextInt(height)
            Some(Position(x, y))

    def placeRandomPickupObjs(n: Int): Unit =
        if n == 0 then return
        populateGrid() match
            case Some(pos) =>
                objectManager.addObject(pos)
                placeRandomPickupObjs(n - 1)
            case None => return

    def placeRandomAgents(n: Int, acc: Int): Unit =
        if n == 0 then return
        populateGrid() match
            case Some(pos) =>
                generateAgent(acc, pos)
                placeRandomAgents(n - 1, acc + 1)
            case None => return

    def generateAgent(id: Int, pos: Position): Unit = ()

    private def generateRandomCoordinates(): (Int, Int) =
        val random = new scala.util.Random
        (random.nextInt(width), random.nextInt(height))