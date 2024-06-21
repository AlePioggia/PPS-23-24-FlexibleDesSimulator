package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import model.RobotEnvironment
import utils.Position
import model.Robot
import utils.Direction

class EnvironmentSpec extends AnyFlatSpec with Matchers {

    it should "pick an object up correctly" in {
        val env = new RobotEnvironment(10, 10)
        val robot = Robot(1)(Position(0, 1))(Direction.North)
        robot.goal = Position(0, 0)
        env.isRobotCarrying(1) should be (false)
        env.agentManager.addAgent(robot)
        env.objectManager.addObject(Position(0, 0))
        env.moveAgent(robot)
        env.isRobotCarrying(1) should be (true)
    }

    it should "pick an object up only if it's it's goal" in {
        val env = new RobotEnvironment(10, 10)
        val robot = Robot(1)(Position(0, 1))(Direction.South)
        robot.goal = Position(2, 2)
        env.objectManager.addObject(Position(1, 2))
        env.objectManager.addObject(Position(2, 2))
        env.agentManager.addAgent(robot)
        env.moveAgent(robot)
        env.agentManager.agents.head.dir = Direction.East
        env.moveAgent(robot)
        env.isRobotCarrying(1) should be (false)
        env.moveAgent(robot)
        env.isRobotCarrying(1) should be (true)
    }
}