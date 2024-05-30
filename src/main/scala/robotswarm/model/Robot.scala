package robotswarm.model

import utils.{Position, Direction}

case class Robot(id: Int)(var pos: Position)(var dir: Direction):
    def move(): Unit = pos = calculateNextPosition()

    def setDirection(dest: Direction): Unit = dir = dest

    def nextPosition(): Position = calculateNextPosition()
    
    private def calculateNextPosition(): Position = dir.nextPosition(pos)

end Robot

