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
    override def drawGrid() = 
        gridPanel.removeAll()
        for {
            y <- 0 until environment.height
            x <- 0 until environment.width
        } {
            val cell = new JPanel
            val pos = new Position(x, y)
            val ant = environment.agentManager.getAgentAt(pos).map(_.asInstanceOf[Ant])
            ant match {
                case Some(a) => if a.carryingFood then cell.add(new JLabel("C"))
                case None => ()
            }
            cell.setBackground(environment.agentManager.getAgentAt(pos).isDefined match {
                case true => Color.RED
                case false => Color.WHITE
            })
            environment.objectManager.isObjectAt(pos) match {
                case true => cell.setBackground(Color.GREEN)
                case false => ()
            }
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK))
            gridPanel.add(cell)
        }
        gridPanel.revalidate()
        gridPanel.repaint()