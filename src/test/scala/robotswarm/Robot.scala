package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Robot
import utils.Position
import utils.Direction
import robotswarm.model.Battery

class RobotSimulatorSpec extends AnyFlatSpec with Matchers {
    "A Robot" should "be created correctly" in {
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        robot.pos should be(Position(0, 0))
        robot.dir should be(Direction.North)
    } 

    "A Robot" should "correctly handle move events" in {
        val robot = Robot(1)(Position(0, 0))(Direction.South)
        robot.move()
        robot.pos should be(Position(0, 1))
        robot.dir should be(Direction.South)
        robot.setDirection(Direction.East)
        robot.dir should be (Direction.East)
        robot.move()
        robot.pos should be(Position(1, 1))
    }

    "A Robot" should "stop accordingly if low on battery" in {
        val robot = Robot(1)(Position(0, 0))(Direction.South)(Battery.Low) // low = 4 steps
        for (i <- 0 until 4) robot.move()     
        robot.pos should be(Position(0, 4))
        robot.move()
        robot.pos should be(Position(0, 4))
    }

    "A Robot" should "calculate the next position correctly" in {
        val robot = Robot(1)(Position(0, 1))(Direction.North)
        robot.nextPosition() should be(Position(0, 0))
        robot.setDirection(Direction.East)
        robot.nextPosition() should be(Position(1, 1))
    }

    "A Robot" should "be able to carry a single object" in {
        val robot = Robot(1)(Position(0, 0))(Direction.North)
        robot.pickUp()
        robot.isCarrying should be(true)
        robot.drop()
        robot.isCarrying should be(false)
    }
}