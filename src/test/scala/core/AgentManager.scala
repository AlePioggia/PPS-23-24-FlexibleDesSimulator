package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.Position
import utils.Direction
import core.model.AgentManager
import core.model.Agent

class AgentManagerSpec extends AnyFlatSpec with Matchers {
    class TestAgent(val id: Int)(var pos: Position)(var dir: Direction) extends Agent

    it should "add an agent correctly" in {
        val env = AgentManager(10, 10)
        val agent = TestAgent(1)(Position(0, 0))(Direction.North)
        env.addAgent(agent)
        env.agents should contain(agent)
        env.isPositionValid(Position(0, 0)) should be(false)
    }

    it should "not add a robot with an invalid position" in {
        val env = AgentManager(10, 10)
        val agent = TestAgent(1)(Position(10, 10))(Direction.North)
        assertThrows[IllegalArgumentException] {
            env.addAgent(agent)
        }
    }


    it should "move an agent correctly" in {
        val env = AgentManager(10, 10)
        val agent = TestAgent(1)(Position(0, 1))(Direction.North)
        env.addAgent(agent)
        env.moveAgent(agent)
        agent.pos should be(Position(0, 0))
    }
}
