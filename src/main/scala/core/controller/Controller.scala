package core.controller

import core.simulator.Simulator
import core.view.View
import core.model.Environment
import core.simulator.Event
import scala.annotation.tailrec
import core.simulator.State
import scala.compiletime.ops.boolean

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
        view.showResult()
        view.dispose()

    def simulateUntilStop(): Unit =
        @tailrec
        def simulateStep(): Unit =
            if !simulator.shouldStop then
                for
                    _ <- Some(schedule())
                    _ <- Some(simulator.step())
                    _ <- Some(view.drawGrid())
                    _ <- Some(if checkState() then {simulator.state = State.Ended; view.showResult(); view.dispose();})
                    _ <- Some(Thread.sleep(1000)) 
                yield ()
                simulateStep() 

        simulateStep() 

    def schedule(): Unit = 
        simulator.schedule(BaseEvent())

    def checkState(): Boolean = false