import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import robotswarm.AllRobotMovesEvent;
import robotswarm.RobotSwarmSimulator;
import scala.collection.mutable.Set;
import utils.Position;
import utils.Direction;
import robotswarm.model.Robot;
import robotswarm.model.Environment;
import core.BasicSimulator;
import robotswarm.RobotMoveEvent;

public class RobotSwarmView extends JFrame {
    private Environment environment;
    private RobotSwarmSimulator simulator;
    private JPanel gridPanel;

    public RobotSwarmView(Environment environment, RobotSwarmSimulator simulator) {
        this.environment = environment;
        this.simulator = simulator;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Robot Swarm Simulator");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(environment.height(), environment.width()));
        add(gridPanel, BorderLayout.CENTER);

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepSimulation();
            }
        });
        add(stepButton, BorderLayout.SOUTH);

        drawGrid();
    }

    private void drawGrid() {
        gridPanel.removeAll();
        System.out.println(environment);
        for (int y = 0; y < environment.height(); y++) {
            for (int x = 0; x < environment.width(); x++) {
                JPanel cell = new JPanel();
                Position pos = new Position(x, y);
                if (environment.getRobotAt(pos).isDefined()) {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(cell);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void stepSimulation() {
        simulator.step();
        drawGrid();
    }

    public static void main(String[] args) {
        Set<Robot> robots = scala.collection.mutable.Set$.MODULE$.<Robot>empty();
        Environment environment = new Environment(10, 10, robots);
        RobotSwarmSimulator simulator = new RobotSwarmSimulator();

        Robot robot1 = new Robot(1, new Position(0, 0), Direction.North$.MODULE$);
        Robot robot2 = new Robot(2, new Position(9, 9), Direction.South$.MODULE$);
        robots.add(robot1);
        robots.add(robot2);
        environment.addRobot(robot1);
        environment.addRobot(robot2);

        for (int i = 0; i < 5; i++) {
            double time = i * 1.0;
            simulator.schedule(new AllRobotMovesEvent(time, environment));
        }

        RobotSwarmView view = new RobotSwarmView(environment, simulator);
        view.setVisible(true);
    }
}
