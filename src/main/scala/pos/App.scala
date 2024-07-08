package pos

import utils.Position
import pos.model.PosEnvironment
import pos.model.PosSimulator
import pos.view.PosView
import pos.controller.PosController
import pos.model.POSParams

object App:
    def main(args: Array[String]): Unit =
        val fitnessFunction: Position => Double = pos => pos.x * pos.x + pos.y * pos.y
        val w = args(0).toDouble
        val c1 = args(1).toDouble
        val c2 = args(2).toDouble

        val params = POSParams.builder().withW(w).withC1(c1).withC2(c2).withR1(0.5).withR2(0.5).build()
        val environment: PosEnvironment = PosEnvironment(10, 10)(fitnessFunction)(params)
        val simulator = PosSimulator()

        environment.setup(5)
        
        val view = PosView(environment, simulator)
        val controller = PosController(environment, simulator, view)
        
        controller.initialize()
        view.setVisible(true)
        controller.simulate(10)
        val result = controller.getResult()
        println(s"Best position: ${result._1}, fitness: ${result._2}")

    
