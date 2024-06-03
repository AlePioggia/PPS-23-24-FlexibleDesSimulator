package robotswarm.view

import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import robotswarm.controller.RobotSwarmController
import robotswarm.model.{Environment, RobotSwarmSimulator}
import utils.Position

class RobotSwarmView(val environment: Environment, val simulator: RobotSwarmSimulator) extends JFrame:
  private val gridPanel: JPanel = new JPanel

  def initializeUI(): Unit = 
    setTitle("Robot Swarm Simulator")
    setSize(800, 800)
    setLayout(new BorderLayout())
    gridPanel.setLayout(new GridLayout(environment.height, environment.width))
    add(gridPanel, BorderLayout.CENTER)
    addButton("Step", (e: ActionEvent) => {simulator.step(); drawGrid()}, BorderLayout.SOUTH)

  def drawGrid(): Unit =
    gridPanel.removeAll()
    for {
      y <- 0 until environment.height
      x <- 0 until environment.width
    } {
      val cell: JPanel = new JPanel
      val pos = new Position(x, y)
      cell.setBackground(environment.getRobotAt(pos).isDefined match {
        case true => Color.RED
        case false => Color.WHITE
      })
      cell.setBorder(BorderFactory.createLineBorder(Color.BLACK))
      gridPanel.add(cell)
    }
    gridPanel.revalidate()
    gridPanel.repaint()

  private def addButton(name: String, al: ActionListener, bLayout: String): Unit =
    val b: JButton = new JButton(name)
    b.addActionListener(al)
    add(b, bLayout)