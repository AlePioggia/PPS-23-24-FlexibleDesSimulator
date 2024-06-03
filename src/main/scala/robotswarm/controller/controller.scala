package robotswarm.controller

//implement controller based on model and view
import robotswarm.model.{RobotSwarmSimulator, RobotMoveEvent, AllRobotMovesEvent}
import robotswarm.model.Environment
import robotswarm.view.RobotSwarmView

class RobotSwarmController(var environment: Environment, val simulator: RobotSwarmSimulator, val view: RobotSwarmView):
    def initialize(): Unit =
        view.initializeUI()
        view.drawGrid()

    def stepSimulation(): Unit =
        simulator.step()
        view.drawGrid()