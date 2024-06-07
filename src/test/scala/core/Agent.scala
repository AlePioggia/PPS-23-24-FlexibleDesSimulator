package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.Position
import utils.Direction
import core.model.Agent

class AgentSimulatorSpec extends AnyFlatSpec with Matchers {
    class TestAgent(val id: Int)(var pos: Position)(var dir: Direction) extends Agent

    "An Agent" should "be created correctly" in {
        val agent = TestAgent(1)(Position(0, 0))(Direction.North)
        agent.pos should be(Position(0, 0))
        agent.dir should be(Direction.North)
    } 

    "An Agent" should "correctly handle move events" in {
        val agent = TestAgent(1)(Position(0, 1))(Direction.North)
        agent.move()
        agent.pos should be(Position(0, 0))
        agent.dir should be(Direction.North)
        agent.setDirection(Direction.East)
        agent.dir should be (Direction.East)
        agent.move()
        agent.pos should be(Position(1, 0))
    }

    "An Agent" should "calculate the next position correctly" in {
        val agent = TestAgent(1)(Position(0, 1))(Direction.North)
        agent.nextPosition() should be(Position(0, 0))
        agent.setDirection(Direction.East)
        agent.nextPosition() should be(Position(1, 1))
    }

    "An Agent" should "be able to interact with an object" in {
        val agent = TestAgent(1)(Position(0, 0))(Direction.North)
        agent.interactWithObject()
    }
}

