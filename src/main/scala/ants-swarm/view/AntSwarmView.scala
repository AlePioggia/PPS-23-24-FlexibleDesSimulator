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
        ant match {
            case Some(a) => 
                if a.carryingFood then
                    var label: JLabel = new JLabel 
                    label.setForeground(Color.WHITE)
                    label.setText("C")
                    cell.add(label)
            case None => () 
        }
        environment.objectManager.isObjectAt(pos) match {
            case true => cell.setBackground(Color.RED)
            case false => ()
        }

        ant match {
            case Some(ant) => if ant.carryingFood then cell.setToolTipText("pippo")
            case None => ()
        }

        val pheromoneLevel = environment.pheromoneManager.pheromone(pos)
        var pheromoneLabel: JLabel = new JLabel(f"$pheromoneLevel%.1f")
        pheromoneLabel.setForeground(Color.WHITE)
        cell.add(pheromoneLabel)

        cell