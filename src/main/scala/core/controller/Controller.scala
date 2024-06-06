package core.controller

import robotswarm.model.RobotEnvironment
import core.simulator.Simulator
import core.view.View

class Controller(var environment: RobotEnvironment, val simulator: Simulator, val view: View):
    def initialize(): Unit =
        view.initializeUI()
        view.drawGrid()
