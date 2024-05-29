package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Environment
import utils.Position

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
}