package `antsswarm`.model

import core.model.Environment
import core.model.Agent
import utils.Position
import utils.Direction
import scala.util.Random
import core.model.Grid

class AntsEnvironment (width: Int, height: Int) extends Environment (width, height) with Grid:
    val pheromoneManager = PheromoneManager(width, height)
    var oldPos: Position = _

    override def preMoveActions(agent: Agent): Unit =
        oldPos = agent.pos
        agent.pos = nextPosition(agent)

    override def nextPosition(agent: Agent): Position = 
        agent match
            case ant: Ant =>
                if ant.carryingFood then moveToNest(ant) else moveToFood(ant)
    
    override def postMoveActions(agent: Agent): Unit =
        objectManager.removeObject(agent.pos)
        pheromoneManager.evaporatePheromones(0.1)
        pheromoneManager.increasePheromone(agent.pos, 0.5, agent.id)

    def nestPositions: Set[Position] = agentManager.agents.map(agent => agent match {case ant: Ant => ant.nestPos}).toSet

    private def moveToNest(ant: Ant): Position = 
        val pos = ant.pos
        val nestPos = ant.nestPos

        if pos == nestPos then
            ant.carryingFood = false
            moveToFood(ant)
        else 
            val nextDirection = 
                if pos.x > nestPos.x then Direction.West
                else if pos.x < nestPos.x then Direction.East
                else if pos.y > nestPos.y then Direction.North
                else if pos.y < nestPos.y then Direction.South
                else Direction.Still

            nextDirection.nextPosition(pos)
    
    private def moveToFood(ant: Ant): Position = 
        val n = neighbors(ant.pos)
        if n.isEmpty then return ant.pos
        if (Random.nextDouble() < 0.3)
            val randomIndex = Random.nextInt(n.size)
            n(randomIndex)
        else
            val result = neighbors(ant.pos)
                .filter(p => pheromoneManager.pheromoneSource(p) != Some(ant.id))
                .map(p => (p, pheromoneManager.pheromone(p)))
                .groupBy(_._2)
                .maxByOption(_._1)
                .flatMap { case (_, positions) =>
                    val bestPositions = positions.map(_._1)
                    if bestPositions.nonEmpty then Some(bestPositions(Random.nextInt(bestPositions.size)))
                    else None
                }
            result.getOrElse {
                val randomIndex = Random.nextInt(n.size)
                n(randomIndex)
            }

        
