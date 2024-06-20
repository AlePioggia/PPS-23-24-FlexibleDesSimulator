package pos.controller

import pos.model.PosEnvironment
import pos.model.PosSimulator
import pos.view.PosView
import core.controller.Controller
import pos.model.AllPosMovesEvent

class PosController(environment: PosEnvironment, override val simulator: PosSimulator, override val view: PosView) extends Controller(environment, simulator, view):
    override def schedule(): Unit =
        simulator.schedule(AllPosMovesEvent(0, environment))
