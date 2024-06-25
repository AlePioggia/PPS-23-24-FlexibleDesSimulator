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
}