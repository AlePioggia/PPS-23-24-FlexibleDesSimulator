package `antsswarm`

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import antsswarm.model.PheromoneManager
import utils.Position

class PheromoneManagerSpec extends AnyFlatSpec with Matchers {
    it should "update grid when increasing pheromones" in {
        val pheromoneManager = PheromoneManager(10, 10)
        pheromoneManager.increasePheromone(Position(0, 0), 10)
        pheromoneManager.pheromone(Position(0, 0)) should be(10)
        pheromoneManager.increasePheromone(Position(0, 0), 1)
        pheromoneManager.pheromone(Position(0, 0)) should be(11)
    }
}

