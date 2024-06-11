package `antsswarm`.model

import utils.Position

class PheromoneManager(val width: Int, val height: Int):
    private val pheromones = Array.fill[Double](width, height)(0.0)

    def increasePheromone(pos: Position, amount: Double): Unit = 
        pheromones(pos.x)(pos.y) += amount

    def pheromone(pos: Position): Double =
        pheromones(pos.x)(pos.y)

