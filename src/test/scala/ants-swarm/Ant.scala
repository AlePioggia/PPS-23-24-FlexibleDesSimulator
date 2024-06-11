package `ants-swarm`

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import ants$minusswarm.model.Ant
import utils.Position
import utils.Direction

class AntSpec extends AnyFlatSpec with Matchers {
    "An Ant" should "be created correctly" in {
        val ant = Ant(1, Position(0, 0), Direction.North, Position(1, 0))
        ant.pos should be(Position(0, 0))
        ant.dir should be(Direction.North)
    }
}
