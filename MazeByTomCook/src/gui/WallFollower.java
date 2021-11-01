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

	public WallFollower() {
	}

	@Override
	public void setRobot(Robot r) {
		this.robot = r;

	}

	@Override
	public void setMaze(Maze maze) {
		mazeConfig = maze;

	}
	
	@Override
	public boolean drive2Exit() throws Exception {
		// before robot gets to the exit, keep driving 1 step
		while (!robot.isAtExit()) {
			drive1Step2Exit();
		}
		return true;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		// check to see if we are looking at the exit, and if so, make a break for it
		if (robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
			while (!robot.isAtExit()) {
				robot.move(1);
				return true;
			}
		}
		// we want to know when the robot is at the exit so we do not pass it
		if (robot.isAtExit() == true) {
			return true;
		}
		if (robot.isAtExit() == false) {
			// first check if there is a wall to your left
			if (robot.distanceToObstacle(Direction.LEFT) == 0) {
				// if not, check if there is a wall ahead
				if (robot.distanceToObstacle(Direction.FORWARD) == 0) {
					// walls in front and to the left so we must turn right
					robot.rotate(Turn.RIGHT);
					return true;
				}
				else {
					// no wall in front of us so we move up one step
					robot.move(1);
					totalPath ++;
					return true;
				}
			}
			else {
				// no wall to our left so we turn left before anything else and also take a step
				robot.rotate(Turn.LEFT);
				robot.move(1);
				//System.out.println(robot.distanceToObstacle(Direction.FORWARD));
				totalPath++;
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
		return robot.getOdometerReading();
	}


}
