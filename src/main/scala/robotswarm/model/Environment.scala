package robotswarm.model

import utils.Position
import collection.mutable.Set
import core.model.Environment
import core.model.Agent
import core.model.Grid
import core.model.AgentManager
import core.model.PickupObjsManager

class RobotEnvironment(width: Int, height: Int) extends Environment(width: Int, height: Int) with Grid:
    
    override def interactWithObject(agent: Agent): Unit = agent match
        case robot: Robot =>
            if (robot.pos == robot.goal) then {robot.pickUp(); objectManager.removeObject(robot.pos)}
    
    def isRobotCarrying(id: Int): Boolean =
        agentManager.agents.find(_.id == id).map(_ match
            case robot: Robot => robot.isCarrying
            case _ => false
        ).getOrElse(false)

    def placeRandomPickupObjs(n: Int): Unit =
        if n > width * height then throw new IllegalArgumentException("Too many objects") 
        if n == 0 then return
        val random = new scala.util.Random
        var (x, y) = generateRandomCoordinates()
        while agentManager.getAgentAt(Position(x, y)).isDefined || objectManager.isObjectAt(Position(x, y)) do
            x = random.nextInt(width)
            y = random.nextInt(height)
        objectManager.addObject(Position(x, y))
        placeRandomPickupObjs(n - 1)
    
    private def generateRandomCoordinates(): (Int, Int) =
        val random = new scala.util.Random
        (random.nextInt(width), random.nextInt(height))