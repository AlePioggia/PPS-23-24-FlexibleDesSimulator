package robotswarm.controller

import robotswarm.model.{RobotSwarmSimulator, RobotMoveEvent, AllRobotMovesEvent}
import robotswarm.view.RobotSwarmView
import robotswarm.model.RobotEnvironment
import core.controller.Controller

class RobotSwarmController(environment: RobotEnvironment, override val simulator: RobotSwarmSimulator, override val view: RobotSwarmView) extends Controller(environment, simulator, view):
    override def schedule(): Unit =
        simulator.schedule(AllRobotMovesEvent(0, environment))