package pos.model.utilities

import utils.Position
import scala.util.Random

object PositionUtils:
    def findValidPosition(width: Int, height: Int, occupiedPositions: Set[Position]): Position =
        def generatePositionStream(): LazyList[Position] =
            LazyList.continually(Position(Random.nextInt(width), Random.nextInt(height)))

        generatePositionStream().find(pos => !occupiedPositions.contains(pos)).get

    def boundPosition(pos: Position, width: Int, height: Int): Position =
        val x = Math.max(0, Math.min(width - 1, pos.x))
        val y = Math.max(0, Math.min(height - 1, pos.y))
        Position(x, y)

    def randomVelocity(): Position =
        Position(Random.nextInt(5) - 2, Random.nextInt(5) - 2)
