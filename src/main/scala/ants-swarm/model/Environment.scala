package `antsswarm`.model

import core.model.Environment
import core.model.Agent
import utils.Position
import utils.Direction
import scala.util.Random

class AntsEnvironment (width: Int, height: Int) extends Environment (width, height):
    val pheromoneManager = PheromoneManager(width, height)
    var oldPos: Position = _

    override def preMoveActions(agent: Agent): Unit =
        oldPos = agent.pos
        agent.pos = nextPosition(agent)

    override def nextPosition(agent: Agent): Position = 
        agent match
            case ant: Ant =>
                if ant.carryingFood then moveToNest(ant) else moveToFood(ant)
    
    override def postMoveActions(): Unit =
        pheromoneManager.increasePheromone(oldPos, 0.5)

    private def moveToNest(ant: Ant): Position = 
        val pos = ant.pos
        val nestPos = ant.nestPos

        val nextDirection = 
            if pos.x > nestPos.x then Direction.West
            else if pos.x < nestPos.x then Direction.East
            else if pos.y > nestPos.y then Direction.North
            else if pos.y < nestPos.y then Direction.South
            else Direction.North

        nextDirection.nextPosition(pos)
    
    private def moveToFood(ant: Ant): Position = 
        neighbors(ant.pos)
            .map(p => (p, pheromoneManager.pheromone(p)))
            .groupBy(_._2)
            .maxByOption(_._1)
            .flatMap { case (_, positions) =>
                val bestPositions = positions.map(_._1)
                if bestPositions.nonEmpty then Some(bestPositions(Random.nextInt(bestPositions.size)))
                else None
            }
            .getOrElse(randomDirection().nextPosition(ant.pos))
    
    private def randomDirection(): Direction = 
        val directions = List(Direction.North, Direction.East, Direction.South, Direction.West)
        directions(Random.nextInt(directions.size))

        
