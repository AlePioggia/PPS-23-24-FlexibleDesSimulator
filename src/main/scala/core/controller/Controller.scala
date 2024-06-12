package core.controller

import core.simulator.Simulator
import core.view.View
import core.model.Environment

class Controller(var environment: Environment, val simulator: Simulator, val view: View):
    def initialize(): Unit =
        view.initializeUI()
        view.drawGrid()
