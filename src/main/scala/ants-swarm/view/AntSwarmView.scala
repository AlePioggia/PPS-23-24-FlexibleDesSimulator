package `antsswarm`.view

import antsswarm.model.AntsEnvironment
import antsswarm.model.AntSwarmSimulator
import core.view.View
import javax.swing.JPanel
import utils.Position
import antsswarm.model.Ant
import javax.swing.JLabel
import java.awt.Color
import javax.swing.BorderFactory

class AntSwarmView(override val environment: AntsEnvironment, override val simulator: AntSwarmSimulator) extends View(environment, simulator):

    override def updatePanel(cell: JPanel, pos: Position): JPanel = 
        val ant = environment.agentManager.getAgentAt(pos).map(_.asInstanceOf[Ant])
        environment.objectManager.isObjectAt(pos) match {
            case true => cell.setBackground(Color.RED)
            case false => ()
        }

        val pheromoneLevel = environment.pheromoneManager.pheromone(pos)
        val pheromoneLabel = new JLabel(f"$pheromoneLevel%.1f")
        cell.add(pheromoneLabel)

        cell