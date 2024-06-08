package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import core.model.Environment
import utils.Position
import robotswarm.model.Robot
import utils.Direction
import core.model.Agent

class EnvironmentSpec extends AnyFlatSpec with Matchers {
    class TestAgent(val id: Int)(var pos: Position)(var dir: Direction) extends Agent

    "An Environment" should "be created correctly" in {
        val env = new Environment(10, 10)
        env.width should be(10)
        env.height should be(10)
    }

    it should "have a valid position" in {
        val env = new Environment(10, 10)
        env.isPositionValid(Position(0, 0)) should be(true)
        env.isPositionValid(Position(9, 9)) should be(true)
        env.isPositionValid(Position(10, 10)) should be(false)
        env.isPositionValid(Position(-1, -1)) should be(false)
    }

    it should "add an agent correctly" in {
        val env = new Environment(10, 10)
        val agent = TestAgent(1)(Position(0, 0))(Direction.North)
        env.addAgent(agent)
        env.agents should contain(agent)
        env.isPositionValid(Position(0, 0)) should be(false)
    }

    it should "not add a robot with an invalid position" in {
        val env = new Environment(10, 10)
        val agent = TestAgent(1)(Position(10, 10))(Direction.North)
        assertThrows[IllegalArgumentException] {
            env.addAgent(agent)
        }
    }

    it should "move a robot correctly" in {
        val env = new Environment(10, 10)
        val agent = TestAgent(1)(Position(0, 1))(Direction.North)
        env.addAgent(agent)
        env.moveAgent(agent)
        agent.pos should be(Position(0, 0))
    }

    it should "place an object correctly" in {
        val env = new Environment(10, 10)
        env.addObject(Position(0, 0))
        env.isObjectAt(Position(0, 0)) should be(true)
        env.removeObject(Position(0, 0))
        env.isObjectAt(Position(0, 0)) should be (false)
    }

    it should "indicate correctly the neighbors of a position" in {
        val env = new Environment(10, 10)
        env.neighbors(Position(0, 0)) should contain theSameElementsAs List(
            Position(0, 1), Position(1, 0)
        )
        env.neighbors(Position(9, 9)) should contain theSameElementsAs List(
            Position(9, 8), Position(8, 9)
        )
        env.neighbors(Position(5, 5)) should contain theSameElementsAs List(
            Position(5, 4), Position(4, 5), Position(5, 6), Position(6, 5)
        )
    }

    it should "return all the objects, by displaying a list of positions" in {
        val env = new Environment(10, 10)
        env.addObject(Position(0, 0))
        env.addObject(Position(0, 1))
        env.addObject(Position(1, 0))
        env.objsPosList() should contain theSameElementsAs List(
            Position(0, 0), Position(0, 1), Position(1, 0)
        )
    }
}

