package `antsswarm`.controller

import antsswarm.model.AntsEnvironment
import antsswarm.model.AntSwarmSimulator
import antsswarm.view.AntSwarmView
import core.controller.Controller
import antsswarm.model.AllAntMovesEvent

class AntsSwarmController(environment: AntsEnvironment, override val simulator: AntSwarmSimulator, override val view: AntSwarmView) extends Controller(environment, simulator, view):
    override def schedule(): Unit =
        simulator.schedule(AllAntMovesEvent(0, environment))
