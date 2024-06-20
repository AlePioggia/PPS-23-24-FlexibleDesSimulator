package pos

import utils.Position
import pos.model.PosEnvironment
import pos.model.PosSimulator
import pos.view.PosView
import pos.controller.PosController

object App:
    def main(args: Array[String]): Unit =
        val fitnessFunction: Position => Double = pos => pos.x * pos.x + pos.y * pos.y
        val environment: PosEnvironment = PosEnvironment(10, 10)(fitnessFunction)
        val simulator = PosSimulator()

        environment.setup(20)
        
        val view = PosView(environment, simulator)
        val controller = PosController(environment, simulator, view)
        
        controller.initialize()
        view.setVisible(true)
        controller.simulate(10)
        val result = controller.getResult()
        println(s"Best position: ${result._1}, fitness: ${result._2}")

    
