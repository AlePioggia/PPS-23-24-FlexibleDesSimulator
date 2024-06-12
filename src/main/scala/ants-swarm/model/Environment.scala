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
                val nextPos = if ant.carryingFood then moveToNest(ant) else moveToFood(ant)
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
    
    private def moveToFood(ant: Ant): Position = 
        val pheromoneLevels = neighbors(ant.pos).map(p => (p, pheromoneManager.pheromone(p)))
        val maxPheromone = pheromoneLevels.maxBy(_._2)._2
        val bestPositions = pheromoneLevels.filter(_._2 == maxPheromone).map(_._1)
        if bestPositions.nonEmpty then bestPositions(scala.util.Random.nextInt(bestPositions.size))
        else randomDirection().nextPosition(ant.pos)
    
    private def randomDirection(): Direction = 
        val directions = List(Direction.North, Direction.East, Direction.South, Direction.West)
        directions(scala.util.Random.nextInt(directions.size))

        
