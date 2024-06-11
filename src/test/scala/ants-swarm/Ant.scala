package `antsswarm`

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

    it should "be dropping its food when he reaches the nest position" in {
        val ant = Ant(1, Position(0, 0), Direction.South, Position(0, 2))
        ant.carryingFood = true
        ant.move()
        ant.carryingFood should be(true)
        ant.move()
        ant.interactWithObject()
        ant.carryingFood should be(false)
    }

    it should "not pick up food when already carrying food" in {
        val ant = Ant(1, Position(0, 0), Direction.South, Position(0, 2))
        ant.carryingFood = true
        ant.move()
        ant.carryingFood should be(true)
        ant.interactWithObject()
        ant.carryingFood should be(true)
    }   
}
