package pos.view

import pos.model.PosEnvironment
import core.view.View
import pos.model.PosSimulator
import javax.swing.JPanel
import java.awt.Color
import javax.swing.BorderFactory
import utils.Position
import javax.swing.JOptionPane

class PosView(override val environment: PosEnvironment, override val simulator: PosSimulator) extends View(environment, simulator):

    override def showResult(): Unit = 
        JOptionPane.showMessageDialog(null, "Position: " + environment.globalBest + " fitness: " + environment.globalBestFitness, "Simulation Over", JOptionPane.INFORMATION_MESSAGE)