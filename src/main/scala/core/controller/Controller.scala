package core.controller

import core.simulator.Simulator
import core.view.View
import core.model.Environment
import core.simulator.Event

class BaseEvent extends Event:
    def time: Double = 0.0

class Controller(var environment: Environment, val simulator: Simulator, val view: View):
    def initialize(): Unit =
        view.initializeUI()
        view.drawGrid()

    def simulate(n: Int): Unit =
        for _ <- 0 until n do
            simulator.step()
            view.drawGrid()
            Thread.sleep(1000)

    def schedule(): Unit = 
        simulator.schedule(BaseEvent())