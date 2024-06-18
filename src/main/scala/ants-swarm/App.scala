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

        val ants: Set[Ant] = Set(
            Ant(1, Position(2, 1), Direction.North, Position(0, 0)),
            Ant(2, Position(3, 1), Direction.North, Position(0, 0)),
            Ant(3, Position(2, 2), Direction.North, Position(0, 0)),
            Ant(4, Position(5, 2), Direction.North, Position(0, 0)),
        )

        ants.foreach(environment.agentManager.addAgent)
        val objects = List(Position(1, 1), Position(1, 3), Position(2, 4))
        objects.foreach(environment.objectManager.addObject)
        println(environment.objectManager.objsPosList)


        //simulator.schedule(AllAntMovesEvent(0, environment))

        var n: Int = 1
        for until <- n to 100 do
            ants.foreach { ant =>
                simulator.schedule(AntMoveEvent(n, ant, environment))
                println(n)
                n = n + 1
            }

        val view = AntSwarmView(environment, simulator)
        val controller = AntsSwarmController(environment, simulator, view)

        controller.initialize()
        view.setVisible(true)
