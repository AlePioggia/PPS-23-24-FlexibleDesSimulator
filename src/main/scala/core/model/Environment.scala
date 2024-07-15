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


    def populateGrid(n: Int): Position =
        if n > (width * height) - (agentManager.agents.size + objectManager.objsPosList.size) then throw new IllegalArgumentException("Too many objects") 
        if n == 0 then return Position(0, 0)
        val random = new scala.util.Random
        var (x, y) = generateRandomCoordinates()
        while agentManager.getAgentAt(Position(x, y)).isDefined || objectManager.isObjectAt(Position(x, y)) do
            x = random.nextInt(width)
            y = random.nextInt(height)
        Position(x, y)

    def placeRandomPickupObjs(n: Int): Unit =
        val pos: Position = populateGrid(n)
        if pos.x == 0 && pos.y == 0 then return
        objectManager.addObject(pos)
        placeRandomPickupObjs(n - 1)

    def placeRandomAgents(n: Int, acc: Int): Unit = 
        val pos: Position = populateGrid(n)
        if pos.x == 0 && pos.y ==  0 then return
        generateAgent(acc, pos)
        placeRandomAgents(n - 1, acc + 1)

    def generateAgent(id: Int, pos: Position): Unit = ()

    private def generateRandomCoordinates(): (Int, Int) =
        val random = new scala.util.Random
        (random.nextInt(width), random.nextInt(height))