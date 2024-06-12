package `antsswarm`.controller

import antsswarm.model.AntsEnvironment
import antsswarm.model.AntSwarmSimulator
import antsswarm.view.AntSwarmView
import core.controller.Controller

class AntsSwarmController(environment: AntsEnvironment, override val simulator: AntSwarmSimulator, override val view: AntSwarmView) extends Controller(environment, simulator, view)
