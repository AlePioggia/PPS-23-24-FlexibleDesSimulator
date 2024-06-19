package pos.model

import utils.Position
import utils.Direction
import core.model.Agent

case class State(pos: Position, dir: Direction, velocity: Position)
case class Best(personalBest: Position, globalBest: Position)

class Particle (val id: Int)(var state: State, var best: Best) extends Agent:
    var pos = state.pos
    var dir: Direction = state.dir
    override def move(): Unit = ???
    override def setDirection(dir: Direction): Unit = ???
    override def nextPosition(): Position = ???
    override def interactWithObject(): Unit = ???
