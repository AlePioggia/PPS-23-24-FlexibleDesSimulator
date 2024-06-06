package core.view

import javax.swing.JFrame
import core.model.Environment
import core.simulator.Simulator
import java.awt.event.ActionListener
import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.WindowConstants
import java.awt.GridLayout
import java.awt.event.ActionEvent
import javax.swing.JButton

class View (val environment: Environment, val simulator: Simulator) extends JFrame:
    protected val gridPanel: JPanel = new JPanel

    def initializeUI(): Unit = 
        setSize(800, 800)
        setLayout(new BorderLayout())
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        gridPanel.setLayout(new GridLayout(environment.height, environment.width))
        add(gridPanel, BorderLayout.CENTER)
        addButton("Step", (e: ActionEvent) => {simulator.step(); drawGrid()}, BorderLayout.SOUTH)

    def drawGrid(): Unit = ()

    protected def addButton(name: String, al: ActionListener, bLayout: String): Unit =
        val b: JButton = new JButton(name)
        b.addActionListener(al)
        add(b, bLayout)


