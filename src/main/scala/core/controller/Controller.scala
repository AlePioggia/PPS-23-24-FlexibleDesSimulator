package core.controller

import core.simulator.Simulator
import core.view.View
import core.model.Environment
import core.simulator.Event
import scala.annotation.tailrec

class BaseEvent extends Event:
    def time: Double = 0.0

class Controller(var environment: Environment, val simulator: Simulator, val view: View):
    def initialize(): Unit =
        view.initializeUI()
        view.drawGrid()

    def simulate(n: Int): Unit =
        @tailrec
        def simulateStep(remainingSteps: Int): Unit =
            if remainingSteps > 0 then
                for
                    _ <- Some(simulator.step())
                    _ <- Some(schedule())
                    _ <- Some(view.drawGrid())
                    _ <- Some(Thread.sleep(1000))
                yield ()
                simulateStep(remainingSteps - 1)
        
        simulateStep(n)

    def schedule(): Unit = 
        simulator.schedule(BaseEvent())