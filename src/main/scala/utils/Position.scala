package utils

import scala.util.Random

case class Position (x: Int, y: Int)

trait Direction:
    def nextPosition(pos: Position): Position

object Direction:
    case object North extends Direction:
        def nextPosition(pos: Position): Position = pos.copy(y = pos.y - 1)
    
    case object East extends Direction:
        def nextPosition(pos: Position): Position = pos.copy(x = pos.x + 1)
    
    case object South extends Direction:
        def nextPosition(pos: Position): Position = pos.copy(y = pos.y + 1)
    
    case object West extends Direction:
        def nextPosition(pos: Position): Position = pos.copy(x = pos.x - 1)

    case object Still extends Direction:
        def nextPosition(pos: Position): Position = pos

    def directionFrom(pos: Position, targetPos: Position): Direction =
        if pos.x > targetPos.x then West
        else if pos.x < targetPos.x then East
        else if pos.y > targetPos.y then North
        else if pos.y < targetPos.y then South
        else Still

end Direction