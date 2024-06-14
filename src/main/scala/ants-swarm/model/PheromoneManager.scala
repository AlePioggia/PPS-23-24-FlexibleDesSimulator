package `antsswarm`.model

import utils.Position
import scala.compiletime.ops.double

class PheromoneManager(val width: Int, val height: Int):
    var pheromones = Array.fill[Double](width, height)(0.0)
    var pheromoneSources = Array.fill[Option[Int]](width, height)(None)

    def increasePheromone(pos: Position, amount: Double, antId: Int): Unit = 
        pheromones(pos.x)(pos.y) += amount
        pheromoneSources(pos.x)(pos.y) = Some(antId) 

    def evaporatePheromones(rate: Double): Unit = 
        for x <- 0 until width do
            for y <- 0 until height do
                pheromones(x)(y) = (pheromones(x)(y) * (1 - rate)).max(0)
                if pheromones(x)(y) == 0 then pheromoneSources(x)(y) = None

    def pheromone(pos: Position): Double =
        pheromones(pos.x)(pos.y)

    def pheromoneSource(pos: Position): Option[Int] =
        pheromoneSources(pos.x)(pos.y)