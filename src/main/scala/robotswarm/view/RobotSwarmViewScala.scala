package robotswarm.view

import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import robotswarm.controller.RobotSwarmController
import robotswarm.model.{Environment, RobotSwarmSimulator}
import utils.Position

class RobotSwarmView(val environment: Environment, val simulator: RobotSwarmSimulator) extends JFrame {
  private val gridPanel: JPanel = new JPanel

  def initializeUI(): Unit = {
    setTitle("Robot Swarm Simulator")
    setSize(800, 800)
    setLayout(new BorderLayout())

    gridPanel.setLayout(new GridLayout(environment.height, environment.width))
    add(gridPanel, BorderLayout.CENTER)

    val stepButton: JButton = new JButton("Step")
    stepButton.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        simulator.step()
        drawGrid()
      }
    })
    add(stepButton, BorderLayout.SOUTH)
  }

  def drawGrid(): Unit = {
    gridPanel.removeAll()
    for (y <- 0 until environment.height) {
      for (x <- 0 until environment.width) {
        val cell: JPanel = new JPanel
        val pos = new Position(x, y)
        if (environment.getRobotAt(pos).isDefined) {
          cell.setBackground(Color.RED)
        } else {
          cell.setBackground(Color.WHITE)
        }
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK))
        gridPanel.add(cell)
      }
    }
    gridPanel.revalidate()
    gridPanel.repaint()
  }
}
