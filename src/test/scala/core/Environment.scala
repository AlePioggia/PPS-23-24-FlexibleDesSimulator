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

    it should "move a robot correctly" in {
        val env = new Environment(10, 10)
        val agent = TestAgent(1)(Position(0, 1))(Direction.North)
        env.agentManager.addAgent(agent)
        env.moveAgent(agent)
        agent.pos should be(Position(0, 0))
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

    it should "correctly place a random amount of objects on the grid" in {
        val env = new Environment(10, 10)
        env.placeRandomPickupObjs(10)
        env.objectManager.objsPosList.size should be (10)   
    }

    it should "refuse to place more objects than the grid can hold" in {
        val env = new Environment(10, 10)
        env.placeRandomPickupObjs(101)
        env.objectManager.objsPosList.size should be (100)
    }
}

