package robotswarm.view

import javax.swing.WindowConstants
import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import robotswarm.controller.RobotSwarmController
import robotswarm.model.{RobotSwarmSimulator}
import core.model.Environment
import robotswarm.model.Robot
import utils.Position
import core.view.View
import robotswarm.model.RobotEnvironment

class RobotSwarmView(override val environment: Environment, override val simulator: RobotSwarmSimulator) extends View(environment, simulator):
  override def drawGrid(): Unit =
    gridPanel.removeAll()
    for {
      y <- 0 until environment.height
      x <- 0 until environment.width
    } {
      val cell: JPanel = new JPanel
      val pos = new Position(x, y)
      val robot: Option[Robot] = environment.getAgentAt(pos).map(_.asInstanceOf[Robot])
      robot match {
        case Some(r) => if r.isCarrying then cell.add(new JLabel("C"))
        case None => ()
      }
      cell.setBackground(environment.getAgentAt(pos).isDefined match {
        case true => Color.RED
        case false => Color.WHITE
      })
      environment.isObjectAt(pos) match {
        case true => cell.setBackground(Color.GREEN)
        case false => ()
      }
      cell.setBorder(BorderFactory.createLineBorder(Color.BLACK))
      gridPanel.add(cell)
    }
    gridPanel.revalidate()
    gridPanel.repaint()