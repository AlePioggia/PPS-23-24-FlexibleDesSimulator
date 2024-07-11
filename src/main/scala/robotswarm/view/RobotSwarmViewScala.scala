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

class RobotSwarmView(override val environment: RobotEnvironment, override val simulator: RobotSwarmSimulator) extends View(environment, simulator):

  protected val pickupObjs: JLabel = new JLabel("Pickup objects: 0")
  protected val pickedUpObjs: JLabel = new JLabel("Picked up objects: 0")

  override def updatePanel(cell: JPanel, pos: Position): JPanel =
    val robot = environment.agentManager.getAgentAt(pos).map(_.asInstanceOf[Robot])
    robot match {
      case Some(r) => if r.isCarrying then cell.add(new JLabel("C"))
      case None => ()
    }
    environment.objectManager.isObjectAt(pos) match {
      case true => cell.setBackground(Color.GREEN)
      case false => ()
    }
    cell
  
  override def customizeStatsPanel(): Unit = 
    val pickupObjLabel: JLabel = new JLabel("Pickup Objects: " + environment.objectManager.objsPosList.size)
    statsPanel.add(pickupObjLabel)
    val pickedUpObjsLabel : JLabel = new JLabel("Objects picked up: " + environment.agentManager.agents.map(_.asInstanceOf[Robot].isCarrying).size)
    statsPanel.add(pickedUpObjsLabel)

  override def updateCustomStats(): Unit = 
    pickupObjs.setText("Pickup objects: " + environment.objectManager.objsPosList.size)
    pickedUpObjs.setText("Picked up objects: " + environment.agentManager.agents.map(_.asInstanceOf[Robot].isCarrying).size)


  
