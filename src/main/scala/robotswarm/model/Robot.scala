package robotswarm.model

import utils.{Position, Direction}

enum Battery(var steps: Int):
  case Full extends Battery(10)
  case Low extends Battery(4)

trait Robot:
  val id: Int
  var pos: Position
  var dir: Direction
  def move(): Unit
  def setDirection(dest: Direction): Unit
  def nextPosition(): Position

trait BaseRobot extends Robot:
  def move(): Unit = pos = calculateNextPosition()
  def setDirection(dest: Direction): Unit = dir = dest
  def nextPosition(): Position = calculateNextPosition()
  protected def calculateNextPosition(): Position = dir.nextPosition(pos)

trait BatteryPowered extends Robot:
  var battery: Battery
  abstract override def move(): Unit = 
    if battery.steps > 0 then
      super.move()
      battery.steps -= 1

object Robot:
  def apply(id: Int)(pos: Position)(dir: Direction): Robot = new BaseRobotImpl(id, pos, dir)
  def apply(id: Int)(pos: Position)(dir: Direction)(battery: Battery): Robot = new RobotImpl(id, pos, dir, battery)
  
  class BaseRobotImpl(val id: Int, var pos: Position, var dir: Direction) extends BaseRobot

  class RobotImpl(val id: Int, var pos: Position, var dir: Direction, var battery: Battery) 
    extends BaseRobot with BatteryPowered
