package robotswarm.model

import utils.Position
import utils.Direction

case class Robot(id: Int)(var pos: Position)(var dir: Direction):
    def move(): Unit =
        pos = dir match
            case Direction.North => pos.copy(y = pos.y + 1)
            case Direction.East => pos.copy(x = pos.x + 1)
            case Direction.South => pos.copy(y = pos.y - 1)
            case Direction.West => pos.copy(x = pos.x - 1)

    def setDirection(dest: Direction): Unit =
        dir = dest
end Robot

