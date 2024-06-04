package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Environment
import utils.Position
import robotswarm.model.Robot
import utils.Direction

class EnvironmentSpec extends AnyFlatSpec with Matchers {
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

    it should "add a robot correctly" in {
        val env = new Environment(10, 10)
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        env.addRobot(robot)
        env.robots should contain(robot)
        env.isPositionValid(Position(0, 0)) should be(false)
    }

    it should "not add a robot with an invalid position" in {
        val env = new Environment(10, 10)
        val robot = Robot(1)(Position(10, 10))(Direction.North)
        assertThrows[IllegalArgumentException] {
            env.addRobot(robot)
        }
    }

    it should "move a robot correctly" in {
        val env = new Environment(10, 10)
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        env.addRobot(robot)
        env.moveRobot(robot)
        robot.pos should be(Position(0, 1))
    }

    it should "place an object correctly" in {
        val env = new Environment(10, 10)
        env.addPickupObj(Position(0, 0))
        env.isPickupObj(Position(0, 0)) should be(true)
        env.removePickupObj(Position(0, 0))
        env.isPickupObj(Position(0, 0)) should be (false)
    }

    it should "pick an object up correctly" in {
        val env = new Environment(10, 10)
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        env.isRobotCarrying(1) should be (false)
        env.addRobot(robot)
        env.addPickupObj(Position(0, 1))
        env.moveRobot(robot)
        env.isRobotCarrying(1) should be (true)
    }

    it should "correctly place a random amount of objects on the grid" in {
        val env = new Environment(10, 10)
        env.placeRandomPickupObjs(10)
        env.pickupObjects.flatten.count(_ == true) should be (10)
    }

    it should "refuse to place more objects than the grid can hold" in {
        val env = new Environment(10, 10)
        assertThrows[IllegalArgumentException] {
            env.placeRandomPickupObjs(101)
        }
    }
}