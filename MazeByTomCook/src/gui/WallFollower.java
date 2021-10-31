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
 * This algorithm uses a flow chart to navigate the maze without
 * any actual knowledge of the maze's construction, like the wizard.
 * 
 * Essentially, this traversal method checks a couple steps:
 * 1) Are we at the exit? If so, then we are done.
 * 2) Is there a wall to our left? If not, turn left. If so, check the next step.
 * 3) Is there a wall in front of us? If not, move forward.
 * 4) If there are walls in front and to the left, then turn right.
 * 
 * Using these conditions, the WallFollower navigates the maze by sticking to
 * the left wall of the maze the entire time. To check if there are walls
 * to the left and in front, the WallFollower implements distance sensors
 * in each direction to determine the distance to the nearest wallboard. 
 * 
 * The WallFollower also keeps track of its total energy usage and path 
 * traveled for efficiency purposes and to make sure it does not use too 
 * much energy.
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
	}

	@Override
	public void setRobot(Robot r) {
		this.robot = r;

	}

	@Override
	public void setMaze(Maze maze) {
		mazeConfig = maze;

	}
	/*
	 * This method initializes the distance sensors by providing them a maze
	 * object to use for distance calculations and a mounted direction to 
	 * tell them in which direction to look for walls. 
	 */
	public void setSensorMazes() {
		Fsensor.setMaze(mazeConfig);
		Bsensor.setMaze(mazeConfig);
		Lsensor.setMaze(mazeConfig);
		Rsensor.setMaze(mazeConfig);
		Fsensor.setSensorDirection(Direction.FORWARD);
		Bsensor.setSensorDirection(Direction.BACKWARD);
		Lsensor.setSensorDirection(Direction.LEFT);
		Rsensor.setSensorDirection(Direction.RIGHT);
	}

	@Override
	public boolean drive2Exit() throws Exception {
		robot.addDistanceSensor(Fsensor, Direction.FORWARD);
		while (!robot.isAtExit()) {
			drive1Step2Exit();
		}
		if (robot.isAtExit() == true) {
			System.out.println("here");
		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		if (robot.isAtExit() == true) {
			System.out.println("here");
		}
		if (robot.isAtExit() == false) {
			if (Lsensor.distanceToObstacle(robot.getCurrentPosition(), robot.getCurrentDirection(), null) == 0) {
				if (Fsensor.distanceToObstacle(robot.getCurrentPosition(), robot.getCurrentDirection(), null) == 0) {
					robot.rotate(Turn.RIGHT);
					//System.out.println(robot.distanceToObstacle(Direction.FORWARD));
					return true;
				}
				else {
					robot.move(1);
					//System.out.println(robot.distanceToObstacle(Direction.FORWARD));
					totalPath ++;
					return true;
				}
			}
			else {
				robot.rotate(Turn.LEFT);
				robot.move(1);
				//System.out.println(robot.distanceToObstacle(Direction.FORWARD));
				totalPath++;
				return true;
			}
		}
		else {
			System.out.println("here");
			
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
		return robot.getOdometerReading();
	}

}
