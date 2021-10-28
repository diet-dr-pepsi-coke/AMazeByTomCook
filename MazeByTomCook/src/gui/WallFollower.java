package gui;

import generation.Maze;
import gui.UnreliableRobot;
import gui.UnreliableSensor;

/**
 * This interface is a subclass of the RobotDriver class and
 * inherits all of its methods.
 * 
 * Responsibilities: The WallFollower is responsible for using a 
 * new algorithm to traverse the maze. As opposed to the wizard, 
 * that cheats using the already determined path to the exit, the
 * WallFollower uses an actually useful algorithm that will find 
 * the exit on its own.
 * 
 * Collaborators: DistanceSensor, Robot, RobotDriver, Maze
 * @author Tom Cook
 *
 */

public class WallFollower implements RobotDriver {
	private Robot robot;
	private Maze mazeConfig;
	private int totalPath = 0;
	private int totalEnergy = 0;

	public WallFollower() {
	
	}

	@Override
	public void setRobot(Robot r) {
		this.robot = r;

	}

	@Override
	public void setMaze(Maze maze) {
		this.mazeConfig = maze;

	}

	@Override
	public boolean drive2Exit() throws Exception {

		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		if (!robot.isAtExit()) {
			
		}
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return totalEnergy;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return totalPath;
	}

}
