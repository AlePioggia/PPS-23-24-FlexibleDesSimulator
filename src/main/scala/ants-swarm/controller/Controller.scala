package `antsswarm`.controller

import antsswarm.model.AntsEnvironment
import antsswarm.model.AntSwarmSimulator
import antsswarm.view.AntSwarmView
import core.controller.Controller
import antsswarm.model.AllAntMovesEvent
import antsswarm.model.Ant

class AntsSwarmController(environment: AntsEnvironment, override val simulator: AntSwarmSimulator, override val view: AntSwarmView) extends Controller(environment, simulator, view):
    override def schedule(): Unit =
        simulator.schedule(AllAntMovesEvent(0, environment))

    override def checkState(): Boolean = 
        environment.objectManager.objsPosList.isEmpty
        && environment.agentManager.agents.map(x => x.asInstanceOf[Ant]).filter(x => x.carryingFood).size == 0
