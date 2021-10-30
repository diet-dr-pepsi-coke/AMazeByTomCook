package gui;

import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;
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
	ReliableSensor Fsensor;
	ReliableSensor Bsensor;
	ReliableSensor Lsensor;
	ReliableSensor Rsensor;

	public WallFollower() {
		Fsensor = new ReliableSensor();
		Bsensor = new ReliableSensor();
		Lsensor = new ReliableSensor();
		Rsensor = new ReliableSensor();
		Fsensor.setSensorDirection(Direction.FORWARD);
		Bsensor.setSensorDirection(Direction.BACKWARD);
		Lsensor.setSensorDirection(Direction.LEFT);
		Rsensor.setSensorDirection(Direction.RIGHT);
	}

	@Override
	public void setRobot(Robot r) {
		this.robot = r;

	}

	@Override
	public void setMaze(Maze maze) {
		mazeConfig = maze;

	}
	public void setSensorMazes() {
		Fsensor.setMaze(mazeConfig);
		Bsensor.setMaze(mazeConfig);
		Lsensor.setMaze(mazeConfig);
		Rsensor.setMaze(mazeConfig);
	}

	@Override
	public boolean drive2Exit() throws Exception {
		while (!robot.isAtExit()) {
			drive1Step2Exit();
		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		if (robot.isAtExit() == false) {
			if (Lsensor.distanceToObstacle(robot.getCurrentPosition(), robot.getCurrentDirection(), null) == 0) {
				if (Fsensor.distanceToObstacle(robot.getCurrentPosition(), robot.getCurrentDirection(), null) == 0) {
					System.out.println("turning right because wallboard ahead");
					robot.rotate(Turn.RIGHT);
				}
				else {
					System.out.println("moving forward bc no wallboard ahead");
					robot.move(1);
					return true;
				}
			}
			else {
				System.out.println("turning left because no wallboard to my left");
				robot.rotate(Turn.LEFT);
				robot.move(1);
				return true;
			}
		}
		else {
			
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
