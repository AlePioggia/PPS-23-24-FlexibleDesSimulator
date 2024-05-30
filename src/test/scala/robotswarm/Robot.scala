package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Robot
import utils.Position
import utils.Direction

class RobotSimulatorSpec extends AnyFlatSpec with Matchers {
    "A Robot" should "be created correctly" in {
        val robot = new Robot(1)(Position(0, 0))(Direction.North)
        robot.pos should be(Position(0, 0))
        robot.dir should be(Direction.North)
    } 

    "A Robot" should "correctly handle move events" in {
        val robot = new Robot(1)(Position(0, 0))(Direction.North)
        robot.move()
        robot.pos should be(Position(0, 1))
        robot.dir should be(Direction.North)
        robot.setDirection(Direction.East)
        robot.dir should be (Direction.East)
        robot.move()
        robot.pos should be(Position(1, 1))
    }

    "A Robot" should "calculate the next position correctly" in {
        val robot = new Robot(1)(Position(0, 0))(Direction.North)
        robot.nextPosition() should be(Position(0, 1))
        robot.setDirection(Direction.East)
        robot.nextPosition() should be(Position(1, 0))
    }
}