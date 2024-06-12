package `antsswarm`

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import antsswarm.model.Ant
import utils.Position
import utils.Direction
import antsswarm.model.AntsEnvironment

class EnvironmentSpec extends AnyFlatSpec with Matchers {
    it should "move ant towards base if it's carrying food" in {
        val env = AntsEnvironment(10, 10)
        val nestPos = Position(0, 0)
        val ant = Ant(1, Position(1, 2), Direction.North, nestPos)
        ant.carryingFood = true
        env.agentManager.addAgent(ant)
        env.moveAgent(ant)
        env.moveAgent(ant)
        env.moveAgent(ant)
        ant.pos should be(Position(0, 0))
    }

    it should "correctly increase pheromone levels after moving" in {
        val env = AntsEnvironment(10, 10)
        val nestPos = Position(0, 0)
        val ant = Ant(1, Position(1, 2), Direction.North, nestPos)
        ant.carryingFood = true
        env.agentManager.addAgent(ant)
        env.moveAgent(ant)
        env.pheromoneManager.pheromone(Position(1, 2)) should be > 0.0
        env.moveAgent(ant)
        env.pheromoneManager.pheromone(Position(0, 2)) should be > 0.0
    }

    it should "move in the right direction, based on pheromone levels of neighbors" in {
        val env = AntsEnvironment(10, 10)
        val nestPos = Position(0, 0)
        val ant = Ant(1, Position(1, 2), Direction.North, nestPos)
        env.agentManager.addAgent(ant)

        env.pheromoneManager.increasePheromone(Position(1, 3), 1.0)
        env.pheromoneManager.increasePheromone(Position(2, 2), 2.0)
        env.pheromoneManager.increasePheromone(Position(0, 2), 0.5)

        env.moveAgent(ant)

        ant.pos should be(Position(2, 2))
    }
}