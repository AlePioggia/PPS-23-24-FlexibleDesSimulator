package robotswarm.model

import utils.Position

trait Grid:
    val width: Int
    val height: Int
end Grid

case class Environment (val width: Int, val height: Int) extends Grid:
    private val grid = Array.ofDim[Option[Robot]](width, height)

    def isPositionValid(pos: Position): Boolean =
        val (x, y) = (pos.x, pos.y)
        x >= 0 && x < width && y >= 0 && y < height