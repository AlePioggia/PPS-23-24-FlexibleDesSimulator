import javax.swing._
import java.awt._
import java.awt.event._
import pos.model.POSParams

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
      val w = new JTextField(5)
      val c1 = new JTextField(5)
      val c2 = new JTextField(5)

      val panel = new JPanel()
      panel.add(new JLabel("w:"))
      panel.add(w)
      panel.add(Box.createHorizontalStrut(15))
      panel.add(new JLabel("c1:"))
      panel.add(c1)
      panel.add(Box.createHorizontalStrut(15))
      panel.add(new JLabel("c2:"))
      panel.add(c2)

      val result = JOptionPane.showConfirmDialog(null, panel, "Enter POS Parameters", JOptionPane.OK_CANCEL_OPTION)
      if (result == JOptionPane.OK_OPTION)
        new Thread(() => pos.App.main(Array(w.getText(), c1.getText(), c2.getText()))).start()
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

object Try extends App:
  PrologEngine.testDoubleArray(Array(1.0, 2.0, 3.0))

