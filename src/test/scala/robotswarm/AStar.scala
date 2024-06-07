package robotswarm

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import robotswarm.model.Robot
import utils.Position
import utils.Direction
import robotswarm.model.Battery
import robotswarm.model.ai.AStar
import core.model.Environment

class AStarSpec extends AnyFlatSpec with Matchers {
    
    it should "return the correct path from start to target" in {
        val start = Position(0, 0)
        val goal = Position(2, 2)

        val environment: Environment = Environment(3, 3)

        val path = AStar.findPath(start, goal, environment)

        path should contain theSameElementsAs List(Direction.East, Direction.East, Direction.South, Direction.South)
    }

    it should "return an empty path if the start and target are the same" in {
        val start = Position(0, 0)
        val goal = Position(0, 0)
        val environment: Environment = Environment(2, 2)

        val path = AStar.findPath(start, goal, environment)

        path shouldEqual List.empty
    }

    it should "handle no possible path" in {
        val start = Position(0, 0)
        val goal = Position(2, 2)
        val environment: Environment = Environment(1, 1)

        val path = AStar.findPath(start, goal, environment)

        path shouldEqual List.empty
    }
}
