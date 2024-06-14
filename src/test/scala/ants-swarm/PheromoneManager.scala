package `antsswarm`

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import antsswarm.model.PheromoneManager
import utils.Position
import antsswarm.model.Ant

class PheromoneManagerSpec extends AnyFlatSpec with Matchers {
    it should "update grid when increasing pheromones" in {
        val ant = Ant(1, Position(0, 0), null, Position(0, 0))
        val pheromoneManager = PheromoneManager(10, 10)
        pheromoneManager.increasePheromone(Position(0, 0), 10, ant.id)
        pheromoneManager.pheromone(Position(0, 0)) should be(10)
        pheromoneManager.increasePheromone(Position(0, 0), 1, ant.id)
        pheromoneManager.pheromone(Position(0, 0)) should be(11)
    }

    it should "throw an exception when increasing pheromones out of bounds" in {
        val ant = Ant(1, Position(0, 0), null, Position(0, 0))
        val pheromoneManager = PheromoneManager(10, 10)
        assertThrows[IndexOutOfBoundsException] {
            pheromoneManager.increasePheromone(Position(10, 10), 10, ant.id)
        }
    }

    it should "evaporate pheromones at the right rate" in {
        val ant = Ant(1, Position(0, 0), null, Position(0, 0))
        val pheromoneManager = PheromoneManager(10, 10)
        pheromoneManager.increasePheromone(Position(0, 0), 10, ant.id)
        pheromoneManager.increasePheromone(Position(0, 1), 15, ant.id)
        pheromoneManager.evaporatePheromones(0.5)
        pheromoneManager.pheromone(Position(0, 0)) should be(5)
        pheromoneManager.pheromone(Position(0, 1)) should be (7.5) 
    }

    it should "not evaporate pheromones below 0" in {
        val ant = Ant(1, Position(0, 0), null, Position(0, 0))
        val pheromoneManager = PheromoneManager(10, 10)
        pheromoneManager.increasePheromone(Position(0, 0), 1, ant.id)
        pheromoneManager.evaporatePheromones(1)
        pheromoneManager.pheromone(Position(0, 0)) should be(0)
    }
}

