package pos.model.utilities

import utils.Position
import scala.util.Random

object PositionUtils:
    def findValidPosition(width: Int, height: Int, occupiedPositions: Set[Position]): Position =
        val random = Random
        var posX = random.nextInt(width)
        var posY = random.nextInt(height)
        var position = Position(posX, posY)

        while occupiedPositions.contains(position) do
            posX = random.nextInt(width)
            posY = random.nextInt(height)
            position = Position(posX, posY)
        
        position

    def boundPosition(pos: Position, width: Int, height: Int): Position =
        val x = Math.max(0, Math.min(width - 1, pos.x))
        val y = Math.max(0, Math.min(height - 1, pos.y))
        Position(x, y)

    def randomVelocity(): Position =
        Position(Random.nextInt(5) - 2, Random.nextInt(5) - 2)
