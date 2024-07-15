package `antsswarm`

import antsswarm.model.AntsEnvironment
import antsswarm.model.AntSwarmSimulator
import antsswarm.model.Ant
import utils.Position
import utils.Direction
import antsswarm.model.AntMoveEvent
import antsswarm.model.AllAntMovesEvent
import antsswarm.view.AntSwarmView
import antsswarm.controller.AntsSwarmController

object App:
    def main(args: Array[String]): Unit = 
        val environment = AntsEnvironment(10, 10)
        val simulator = AntSwarmSimulator()
        val n = args(0).toInt

        val ants: Set[Ant] = Set(
            Ant(1, Position(2, 1), Direction.North, Position(0, 0)),
            Ant(2, Position(3, 1), Direction.North, Position(0, 0)),
            Ant(3, Position(2, 2), Direction.North, Position(0, 0)),
            Ant(4, Position(5, 2), Direction.North, Position(0, 0)),
        )

        ants.foreach(environment.agentManager.addAgent)
        environment.placeRandomPickupObjs(n)

        val view = AntSwarmView(environment, simulator)
        val controller = AntsSwarmController(environment, simulator, view)

        controller.initialize()
        view.setVisible(true)
        controller.simulate(200)
