import javax.swing._
import java.awt._
import java.awt.event._
import pos.model.POSParams

object MainMenuApp extends App:
  val frame = new JFrame("Select Application to Run")
  frame.setSize(300, 200) 
  frame.setLayout(new GridLayout(3, 1))

  val antsButton = new JButton("Start Ants Swarm")
  val robotsButton = new JButton("Start Robots Swarm")
  val posButton = new JButton("Start POS Simulation")

  antsButton.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent): Unit = {
      val n = JTextField(5)
      
      val panel = JPanel()
      panel.add(JLabel("food units:"))
      panel.add(n)
      val result = JOptionPane.showConfirmDialog(null, panel, "Enter ants swarm Parameters", JOptionPane.OK_CANCEL_OPTION)
      if (result == JOptionPane.OK_OPTION)
        new Thread(() => antsswarm.App.main(Array(n.getText()))).start()
    }
  })

  robotsButton.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent): Unit =
      val n = new JTextField(5)

      val panel = new JPanel()
      panel.add(new JLabel("n robots:"))
      panel.add(n)
      val result = JOptionPane.showConfirmDialog(null, panel, "Enter Robot swarm Parameters", JOptionPane.OK_CANCEL_OPTION)
      if (result == JOptionPane.OK_OPTION)
        new Thread(() => robotswarm.App.main(Array(n.getText()))).start()
  })

  posButton.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent): Unit =
      val n = new JTextField(5)
      val w = new JTextField(5)
      val c1 = new JTextField(5)
      val c2 = new JTextField(5)

      val panel = new JPanel()
      panel.add(new JLabel("n agents:"))
      panel.add(n)
      panel.add(Box.createHorizontalStrut(15))
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
        new Thread(() => pos.App.main(Array(n.getText(), w.getText(), c1.getText(), c2.getText()))).start()
  })

  frame.add(antsButton)
  frame.add(robotsButton)
  frame.add(posButton)

  SwingUtilities.invokeLater(new Runnable {
    def run(): Unit = {
      frame.setVisible(true)
    }
  })

