import javax.swing._
import java.awt._
import java.awt.event._

object MainMenuApp extends App {
  // Create the main frame
  val frame = new JFrame("Select Application to Run")
  frame.setSize(300, 200)
  frame.setLayout(new GridLayout(3, 1))

  // Create buttons for each application
  val antsButton = new JButton("Start Ants Swarm")
  val robotsButton = new JButton("Start Robots Swarm")
  val posButton = new JButton("Start POS Simulation")

  // Add action listeners to the buttons
  antsButton.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent): Unit = {
      new Thread(() => antsswarm.App.main(Array.empty)).start()
    }
  })

  robotsButton.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent): Unit = {
      new Thread(() => robotswarm.App.main(Array.empty)).start()
    }
  })

  posButton.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent): Unit = {
      new Thread(() => pos.App.main(Array.empty)).start()
    }
  })

  // Add buttons to the frame
  frame.add(antsButton)
  frame.add(robotsButton)
  frame.add(posButton)

  // Make sure to use the EDT for GUI operations
  SwingUtilities.invokeLater(new Runnable {
    def run(): Unit = {
      frame.setVisible(true)
    }
  })
}
