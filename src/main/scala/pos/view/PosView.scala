package pos.view

import pos.model.PosEnvironment
import core.view.View
import pos.model.PosSimulator
import javax.swing.JPanel
import java.awt.Color
import javax.swing.BorderFactory
import utils.Position

class PosView(override val environment: PosEnvironment, override val simulator: PosSimulator) extends View(environment, simulator):
    override def drawGrid(): Unit =
        gridPanel.removeAll()
        for {
        y <- 0 until environment.height
        x <- 0 until environment.width
        } {
            val cell: JPanel = new JPanel
            cell.setBackground(environment.agentManager.getAgentAt(Position(x, y)).isDefined match {
                case true => Color.BLACK
                case false => Color.WHITE
            })
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK))
            gridPanel.add(cell)
        }
        gridPanel.revalidate()
        gridPanel.repaint()
