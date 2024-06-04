package core.model

import utils.Position
import utils.Direction

trait Agent:
    val id: Int
    var pos: Position
    var dir: Direction
    def move(): Unit = pos = calculateNextPosition()
    def setDirection(dest: Direction): Unit = dir = dest
    def nextPosition(): Position = calculateNextPosition()
    def interactWithObject(): Unit = ()
    protected def calculateNextPosition(): Position = dir.nextPosition(pos)

