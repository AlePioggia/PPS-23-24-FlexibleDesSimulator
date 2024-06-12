package `antsswarm`.model

import core.model.Environment
import core.model.Agent
import utils.Position
import utils.Direction

class AntsEnvironment (width: Int, height: Int) extends Environment (width, height):
    val pheromoneManager = PheromoneManager(width, height)

    override def moveAgent(agent: Agent): Unit = 
        agent match {
            case ant: Ant =>
                val nextPos = if ant.carryingFood then moveToNest(ant) else moveRandomly(ant)
                if agentManager.isPositionValid(nextPos) then 
                    agentManager.removeAgent(agent)
                    val oldPos = agent.pos
                    agent.pos = nextPos
                    if objectManager.isObjectAt(nextPos) then interactWithObject(agent)
                    agentManager.placeAgent(agent)
                    pheromoneManager.increasePheromone(oldPos, 0.5)
                else 
                    throw new IllegalArgumentException("Invalid position")

        }
    
    private def moveToNest(ant: Ant): Position = 
        val pos = ant.pos
        val nestPos = ant.nestPos
        if pos.x > nestPos.x then Direction.West.nextPosition(pos)
        else if pos.x < nestPos.x then Direction.East.nextPosition(pos)
        else if pos.y > nestPos.y then Direction.North.nextPosition(pos)
        else if pos.y < nestPos.y then Direction.South.nextPosition(pos)
        else Direction.North.nextPosition(pos)
    
    private def moveRandomly(ant: Ant): Position = ant.pos

        
