package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Robot
import utils.Position
import utils.Direction

class RobotSimulatorSpec extends AnyFlatSpec with Matchers {
    "A Robot" should "be created correctly" in {
        val robot = new Robot(1)(Position(0, 0))(Direction.North)
        robot.position should be(Position(0, 0))
        robot.direction should be(Direction.North)
    } 

    // "A Robot " should "correctly handle move events" in {
    //     val robot = new Robot(1)(Position(0, 0))(Direction.North)
    //     robot.move()
    //     robot.position should be(Position(0, 1))
    //     robot.direction should be(Direction.North)
    //     robot.setDirection(Direction.East)
    //     robot.direction should be (Direction.East)
    //     robot.move()
    //     robot.position should be(Position(1, 1))
    // }
}