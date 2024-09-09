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
  val grayCells = scala.collection.mutable.Set[Position]()
  var i: Int = 0
  var initialObjectsSize: Int = environment.objectManager.objsPosList.size
  var isSimulationOver: Boolean = false


  override def updatePanel(cell: JPanel, pos: Position): JPanel =
    val robot = environment.agentManager.getAgentAt(pos).map(_.asInstanceOf[Robot])

    robot match {
      case Some(r) =>
        if r.isCarrying && r.pos == r.goal then
          environment.agentManager.removeAgent(r)
          grayCells += pos
        else
          val label = new JLabel("r") 
          cell.add(label)
      case None => 
        if grayCells.contains(pos) then
          cell.setBackground(Color.GRAY)
    }

    environment.objectManager.isObjectAt(pos) match {
      case true => cell.setBackground(Color.GREEN)
      case false => ()
    }
      
    cell
  
  override def customizeStatsPanel(): Unit = 
    statsPanel.add(pickedUpObjs)

  override def updateCustomStats(): Unit = 
    pickupObjs.setText("Pickup objects: " + environment.objectManager.objsPosList.size)
    pickedUpObjs.setText("Picked up objects: " + simulator.getCurrentCarriedObjectsState(environment))

  override def showResult(): Unit = 
    JOptionPane.showMessageDialog(null, "Simulation is over. All objects have been picked up.", "Simulation Over", JOptionPane.INFORMATION_MESSAGE)


  
