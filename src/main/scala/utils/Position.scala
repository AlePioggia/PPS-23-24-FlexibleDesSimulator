package utils

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

end Direction
