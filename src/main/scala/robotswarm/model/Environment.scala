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

    def isObstacle(pos: Position, goal: Position) =
        objectManager.isObjectAt(pos) && pos != goal

    private def generateRandomCoordinates(): (Int, Int) =
        val random = new scala.util.Random
        (random.nextInt(width), random.nextInt(height))