package robotswarm.model

import utils.{Position, Direction}
import core.model.Agent

enum Battery(var steps: Int):
  case Full extends Battery(10)
  case Low extends Battery(4)

trait Robot extends Agent:
  var isCarrying: Boolean = false
  var goal: Position = _
  def pickUp(): Unit
  def drop(): Unit

trait BaseRobot extends Robot:
  def pickUp(): Unit = isCarrying = true
  def drop(): Unit = isCarrying = false

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
