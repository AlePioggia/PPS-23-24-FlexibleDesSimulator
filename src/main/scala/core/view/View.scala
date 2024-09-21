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
import utils.Position
import javax.swing.JLabel
import javax.swing.Timer

class View (val environment: Environment, val simulator: Simulator) extends JFrame:
    protected val gridPanel: JPanel = new JPanel
    protected val statsPanel: JPanel = new JPanel
    protected val timerLabel: JLabel = new JLabel("Time: 0")
    protected val stepLabel: JLabel = new JLabel("Steps: 0")
    protected val agentsLabel: JLabel = new JLabel("Agents: 0")
    private var onWindowClosing: Option[() => Unit] = None
    var timer: Timer = _

    def initializeUI(): Unit = 
        setSize(800, 800)
        setLayout(new BorderLayout())
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        gridPanel.setLayout(new GridLayout(environment.height, environment.width))
        add(gridPanel, BorderLayout.CENTER)
        setupStatsPanel()

        addWindowListener(new java.awt.event.WindowAdapter {
            override def windowClosing(e: java.awt.event.WindowEvent): Unit = 
                onWindowClosing.foreach(callback => callback())
                dispose()
        })

    def setupStatsPanel(): Unit = 
        statsPanel.setLayout(new GridLayout(4, 1))
        statsPanel.add(timerLabel)
        statsPanel.add(stepLabel)
        statsPanel.add(agentsLabel)
        customizeStatsPanel()
        add(statsPanel, BorderLayout.NORTH)

        timer = new Timer(1000, new ActionListener {
            def actionPerformed(e: ActionEvent): Unit = updateStats()
        })
        timer.start()

    def customizeStatsPanel(): Unit = ()

    def drawGrid(): Unit = 
        gridPanel.removeAll()
        for {
            y <- 0 until environment.height
            x <- 0 until environment.width
        } {
            val cell: JPanel = new JPanel
            cell.setBackground(environment.agentManager.getAgentAt(Position(x, y)).isDefined match {
                case true => java.awt.Color.BLACK
                case false => java.awt.Color.WHITE
            })
            cell.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK))
            gridPanel.add(updatePanel(cell, Position(x, y)))
        }
        gridPanel.revalidate()
        gridPanel.repaint()

    def updatePanel(cell: JPanel, pos: Position): JPanel = cell

    protected def addButton(name: String, al: ActionListener, bLayout: String): Unit =
        val b: JButton = new JButton(name)
        b.addActionListener(al)
        add(b, bLayout)

    def updateStats(): Unit =
        val elapsedTime = simulator.getElapsedTime / 1000
        timerLabel.setText(s"Time: $elapsedTime")
        stepLabel.setText(s"Steps: ${simulator.currentStep}")
        agentsLabel.setText(s"Agents: ${environment.agentManager.agents.size}")
        updateCustomStats()

    def updateCustomStats(): Unit = ()

    def showResult(): Unit = ()

    def setOnWindowsClosing(callback: () => Unit): Unit = onWindowClosing = Some(callback)


