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

class View (val environment: Environment, val simulator: Simulator) extends JFrame:
    protected val gridPanel: JPanel = new JPanel

    def initializeUI(): Unit = 
        setSize(800, 800)
        setLayout(new BorderLayout())
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        gridPanel.setLayout(new GridLayout(environment.height, environment.width))
        add(gridPanel, BorderLayout.CENTER)

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


