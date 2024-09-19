package `antsswarm`.model

import core.model.Environment
import core.model.Agent
import utils.Position
import utils.Direction
import scala.util.Random
import core.model.Grid
import utils.Constants

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
        pheromoneManager.evaporatePheromones(Constants.PheromoneEvaporationRate)
        pheromoneManager.increasePheromone(agent.pos, Constants.PheromoneDiffusionRate, agent.id)

    def nestPositions: Set[Position] = agentManager.agents.map(agent => agent match {case ant: Ant => ant.nestPos}).toSet

    private def moveToNest(ant: Ant): Position = 
        val pos = ant.pos
        val nestPos = ant.nestPos

        if pos == nestPos then
            ant.carryingFood = false
            moveToFood(ant)
        else 
            val nextDirection = Direction.directionFrom(pos, nestPos)

            nextDirection.nextPosition(pos)
    
    private def moveToFood(ant: Ant): Position = 
        val n = neighbors(ant.pos)
        if n.isEmpty then return ant.pos
        if (Random.nextDouble() < Constants.RandomMoveProbability)
            n(Random.nextInt(n.size))
        else
            neighbors(ant.pos)
                .filter(p => pheromoneManager.pheromoneSource(p) != Some(ant.id))
                .map(p => (p, pheromoneManager.pheromone(p)))
                .groupBy(_._2)
                .maxByOption(_._1)
                .flatMap { case (_, positions) =>
                    val bestPositions = positions.map(_._1)
                    if bestPositions.nonEmpty then Some(bestPositions(Random.nextInt(bestPositions.size)))
                    else None
                }
                .getOrElse(n(Random.nextInt(n.size)))

        
