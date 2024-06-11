package `antsswarm`.model

import utils.Position
import scala.compiletime.ops.double

class PheromoneManager(val width: Int, val height: Int):
    private val pheromones = Array.fill[Double](width, height)(0.0)

    def increasePheromone(pos: Position, amount: Double): Unit = 
        pheromones(pos.x)(pos.y) += amount

    def evaporatePheromones(rate: Double): Unit = 
        for x <- 0 until width do
            for y <- 0 until height do
                pheromones(x)(y) = (pheromones(x)(y) * (1 - rate)).max(0)

    def pheromone(pos: Position): Double =
        pheromones(pos.x)(pos.y)

