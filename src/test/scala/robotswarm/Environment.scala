package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.RobotEnvironment
import utils.Position
import robotswarm.model.Robot
import utils.Direction

class EnvironmentSpec extends AnyFlatSpec with Matchers {

    it should "move a robot correctly" in {
        val env = new RobotEnvironment(10, 10)
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        env.addAgent(robot)
        env.moveAgent(robot)
        robot.pos should be(Position(0, 1))
    }

    it should "pick an object up correctly" in {
        val env = new RobotEnvironment(10, 10)
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        env.isRobotCarrying(1) should be (false)
        env.addAgent(robot)
        env.addObject(Position(0, 1))
        env.moveAgent(robot)
        env.isRobotCarrying(1) should be (true)
    }

    it should "correctly place a random amount of objects on the grid" in {
        val env = new RobotEnvironment(10, 10)
        env.placeRandomPickupObjs(10)
        env.objects.flatten.count(_ == true) should be (10)
    }

    it should "refuse to place more objects than the grid can hold" in {
        val env = new RobotEnvironment(10, 10)
        assertThrows[IllegalArgumentException] {
            env.placeRandomPickupObjs(101)
        }
    }
}