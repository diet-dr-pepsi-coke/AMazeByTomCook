package gui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import generation.CardinalDirection;
import generation.Maze;
import gui.Constants;
import gui.Constants.UserInput;

/**
 * This interface implements the Robot class and is an auto-generated 
 * subclass.
 * 
 * Responsibilities: The ReliableRobot is responsible for creating the sensors
 * used in the robot driver to detect obstacles (wallboards). It creates sensors
 * in any of four directions pertaining to the orientation of the robot. 
 * It also works closely with the Controller to move the robot with the driver
 * using key inputs. This class is also responsible for having sensors that
 * detect if the robot is in a room or if the robot is at the exit. The class
 * has functions that move and turn the robot, which call upon the controller. 
 * It is also responsible for the energy costs of movement and keeping track
 * of the robot's number of cells traversed. 
 * 
 * Collaborators: Controller, RobotDriver, Wizard, DistanceSensor
 * @author Tom Cook
 *
 */

public class ReliableRobot implements Robot {
	
	 private float batteryLevel = 3500;
	 private int odometer = 0;
	 private boolean stopped = false;
	 private Controller controller;
	 private DistanceSensor frontSensor;
	 private DistanceSensor leftSensor;
	 private DistanceSensor rightSensor;
	 private DistanceSensor backSensor;
	 private Maze maze;

	public ReliableRobot() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;

	}
	/**
	 * Provides the robot with a definitive maze to work with.
	 * For testing purposes mostly.
	 * @param maze is the maze configuration
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		switch (mountedDirection) {
		// depending on the direction, initialize the mounted sensor on that side to look
		// that direction
		case FORWARD:
			frontSensor = sensor;
			frontSensor.setSensorDirection(mountedDirection);
			break;
		case LEFT:
			leftSensor = sensor;
			leftSensor.setSensorDirection(mountedDirection);
			break;
		case RIGHT:
			rightSensor = sensor;
			rightSensor.setSensorDirection(mountedDirection);
			break;
		case BACKWARD:
			backSensor = sensor;
			backSensor.setSensorDirection(mountedDirection);
			break;
		}

	}
	
	/*
	 * This method initializes the distance sensors by providing them a maze
	 * object to use for distance calculations and a mounted direction to 
	 * tell them in which direction to look for walls. 
	 */
	public void setSensorMazes() {
		// get the maze configuration from the controller
			frontSensor.setMaze(controller.getMazeConfiguration());
			leftSensor.setMaze(controller.getMazeConfiguration());
			rightSensor.setMaze(controller.getMazeConfiguration());
			backSensor.setMaze(controller.getMazeConfiguration());
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		return controller.getCurrentPosition();
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return controller.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		return batteryLevel;
	}

	@Override
	public void setBatteryLevel(float level) {
		this.batteryLevel = level;

	}

	@Override
	public float getEnergyForFullRotation() {
		// return 12
		// (( energy cost of a 90 degree rotation is 3, so 4 90 degree turns = 12 ))
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		// return 6
		// (( as dictated in the project requirements ))
		return 6;
	}

	@Override
	public int getOdometerReading() {
		return odometer;
	}

	/** 
     * Resets the odometer counter to zero.
     */
	@Override
	public void resetOdometer() {
		this.odometer = 0;

	}

	@Override
	public void rotate(Turn turn) {
		// check to make sure we have not stopped and we have battery left
		 if (getBatteryLevel() > 0 && !hasStopped()) {
		 		switch (turn) {
				case LEFT :
					controller.keyDown(UserInput.LEFT, 0);
					batteryLevel -= (1/4)*getEnergyForFullRotation();
					break;
				case RIGHT :
					controller.keyDown(UserInput.RIGHT, 0);
					batteryLevel -= (1/4)*getEnergyForFullRotation();
					break;
		 		case AROUND : 
		 			// turn left twice
					controller.keyDown(UserInput.LEFT, 0);
					controller.keyDown(UserInput.LEFT, 0);
					batteryLevel -= (2/4)*getEnergyForFullRotation();
					break; }}
		 // check to see if we used the last of our battery to make the turn
		 if (getBatteryLevel() <= 0) {
				stopped = true; }
	}

	@Override
	public void move(int distance) {
		if (getBatteryLevel() > 0 && !hasStopped()) {
		// foo variable to keep track of how many steps we took to compare
		// with the distance we were supposed to travel
		 int counter = 0;
		 // loop for however many cells we are supposed to traverse
		 	for (int i=0; i<distance; i++) {
					controller.keyDown(UserInput.UP, 0);
					batteryLevel -= getEnergyForStepForward();
					// increment counter if we did move the step
					counter ++;
					odometer++; }
			// make sure the counter of actual steps moved is equal to
			// the amount of steps we called to move, and if it is not 
			// equal, then we must have stopped for some reason
			if (counter != distance) {
		 		stopped = true;}
			if (getBatteryLevel() <= 0) {
				stopped = true; }
	}}

	@Override
	public void jump() {
		 if (getBatteryLevel() > 0 && !hasStopped()) {
			 	// don't need to check if border wall because StatePlaying already does that
				controller.keyDown(UserInput.JUMP, 0);
				batteryLevel -= 40; // cost of jumping as outlined
		 if (getBatteryLevel() <= 0) {
				stopped = true; }}

	}

	@Override
	public boolean isAtExit() {
		Maze maze = controller.getMazeConfiguration();
		int[] supposedExit = null;
		if (this.maze != null) {
			supposedExit = this.maze.getExitPosition();
		}
		else {
		 supposedExit = maze.getExitPosition();
		}
			try {
				// check the maze's exit cell with the current position cell to see if they match
				if (supposedExit[0] == getCurrentPosition()[0] && supposedExit[1] == getCurrentPosition()[1]) {
					 return true;
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean isInsideRoom() {
		Maze maze = controller.getMazeConfiguration();
			int x = controller.getCurrentPosition()[0];
			int y = controller.getCurrentPosition()[1];
		if (maze.isInRoom(x,y)) {
			return true;
		}
		// return assertEquals(maze.isInRoom(x,y))
		return false;
	}

	@Override
	public boolean hasStopped() {
		return stopped;
	}

	@Override
	public int distanceToObstacle(Direction direction) {
		// check to make sure we haven't stopped and battery is still good
		 if (getBatteryLevel() > 0 && !hasStopped()) { 
			 float[] battery = {batteryLevel};
			 int[] curPos = controller.getCurrentPosition();
			 CardinalDirection curDir = controller.getCurrentDirection();
			 // the distance to obstacle:: what we want to return in the end
			 int dist = 0;
		try {
			// which sensor we use depends on the direction we are looking
			switch (direction) {
			case FORWARD:
				// must make sure sensor is working before we try to use it
				if (frontSensor.isOperational()) {
					dist = frontSensor.distanceToObstacle(curPos, curDir, battery); 
					break; }
				else {
					switchFrontSensor(curPos, curDir, battery); }
			case LEFT:
				if (leftSensor.isOperational()) {
					dist = leftSensor.distanceToObstacle(curPos, curDir, battery); 
					break; }
				else {
					switchLeftSensor(curPos, curDir, battery); }
			case RIGHT:
				if (rightSensor.isOperational()) {
					dist = rightSensor.distanceToObstacle(curPos, curDir, battery); 
					break; }
				else {
					switchRightSensor(curPos, curDir, battery); }
			case BACKWARD:
				if (backSensor.isOperational()) {
					dist = backSensor.distanceToObstacle(curPos, curDir, battery); 
					break; }
				else {
					switchBackSensor(curPos, curDir, battery); }
			}
		}
		catch(Exception e) {e.printStackTrace(); }
			 batteryLevel -= 1;
			 if (getBatteryLevel() <= 0) {
				stopped = true; 
				return 0;}
			 return dist; }
		 System.out.println("Stopped.");
		 return 0;
	}

	/**
	 * Method to turn the robot so that another sensor can calculate the distance
	 * to an obstacle in the event that the front sensor fails
	 * @param curPos current position of the robot
	 * @param curDir current direction of the robot
	 * @param battery current battery level
	 * @return distance to wallboard in this direction
	 */
	private int switchFrontSensor(int[] curPos, CardinalDirection curDir, float[] battery) {
		int dist = 0;
		// check to see which sensor is working and rotate to have that
		// sensor look in the current direction, then rotate the robot back
		// to its original position
		try {
		if (leftSensor.isOperational()) {
			rotate(Turn.RIGHT);
			dist = leftSensor.distanceToObstacle(curPos, curDir, battery);
			rotate(Turn.LEFT);
			batteryLevel -= (1/2)*getEnergyForFullRotation();
			}
		else if (rightSensor.isOperational()) {
			rotate(Turn.LEFT);
			dist = rightSensor.distanceToObstacle(curPos, curDir, battery);
			rotate(Turn.RIGHT);
			batteryLevel -= (1/2)*getEnergyForFullRotation();
			}
		else if (backSensor.isOperational()) {
			rotate(Turn.AROUND);
			dist = backSensor.distanceToObstacle(curPos, curDir, battery);
			rotate(Turn.AROUND);
			batteryLevel -= getEnergyForFullRotation();
			}
		else {
			// if there are no operational sensors, we simply wait for the
			// original sensor to be repaired and then use that one again
			while (!frontSensor.isOperational()) {
				System.out.println("Waiting for sensor to be repaired");
				continue; }
			dist = frontSensor.distanceToObstacle(curPos, curDir, battery); 
			} }
		catch (Exception e) {}
		return dist;
	}
	
	/**
	 * Method to turn the robot so that another sensor can calculate the distance
	 * to an obstacle in the event that the left sensor fails
	 * @param curPos current position of the robot
	 * @param curDir current direction of the robot
	 * @param battery current battery level
	 * @return distance to wallboard in this direction
	 */
	private int switchLeftSensor(int[] curPos, CardinalDirection curDir, float[] battery) {
		int dist = 0;
		try {
		if (backSensor.isOperational()) {
			rotate(Turn.RIGHT);
			dist = backSensor.distanceToObstacle(curPos, curDir, battery);
			rotate(Turn.LEFT);
			batteryLevel -= (1/2)*getEnergyForFullRotation();
			}
		else if (frontSensor.isOperational()) {
			rotate(Turn.LEFT);
			dist = frontSensor.distanceToObstacle(curPos, curDir, battery);
			rotate(Turn.RIGHT);
			batteryLevel -= (1/2)*getEnergyForFullRotation();
			}
		else if (rightSensor.isOperational()) {
			rotate(Turn.AROUND);
			dist = rightSensor.distanceToObstacle(curPos, curDir, battery);
			rotate(Turn.AROUND);
			batteryLevel -= getEnergyForFullRotation();
			}
		else {
			while (!leftSensor.isOperational()) {
				System.out.println("Waiting for sensor to be repaired");
				continue; }
			dist = leftSensor.distanceToObstacle(curPos, curDir, battery); 
			} }
		catch (Exception e) {}
		return dist;
	}
	
	/**
	 * Method to turn the robot so that another sensor can calculate the distance
	 * to an obstacle in the event that the right sensor fails
	 * @param curPos current position of the robot
	 * @param curDir current direction of the robot
	 * @param battery current battery level
	 * @return distance to wallboard in this direction
	 */
	private int switchRightSensor(int[] curPos, CardinalDirection curDir, float[] battery) {
		int dist = 0;
		try {
			if (frontSensor.isOperational()) {
				rotate(Turn.RIGHT);
				dist = frontSensor.distanceToObstacle(curPos, curDir, battery);
				rotate(Turn.LEFT);
				batteryLevel -= (1/2)*getEnergyForFullRotation();
				}
			else if (backSensor.isOperational()) {
				rotate(Turn.LEFT);
				dist = backSensor.distanceToObstacle(curPos, curDir, battery);
				rotate(Turn.RIGHT);
				batteryLevel -= (1/2)*getEnergyForFullRotation();
				}
			else if (leftSensor.isOperational()) {
				rotate(Turn.AROUND);
				dist = leftSensor.distanceToObstacle(curPos, curDir, battery);
				rotate(Turn.AROUND);
				batteryLevel -= getEnergyForFullRotation();
				}
			else {
				while (!rightSensor.isOperational()) {
					System.out.println("Waiting for sensor to be repaired");
					continue; }
				dist = rightSensor.distanceToObstacle(curPos, curDir, battery); 
				 } }
		catch (Exception e) {}
		return dist;
		}
	
	/**
	 * Method to turn the robot so that another sensor can calculate the distance
	 * to an obstacle in the event that the back sensor fails
	 * @param curPos current position of the robot
	 * @param curDir current direction of the robot
	 * @param battery current battery level
	 * @return distance to wallboard in this direction
	 */
	private int switchBackSensor(int[] curPos, CardinalDirection curDir, float[] battery) {
		int dist = 0;
		try {
			if (rightSensor.isOperational()) {
				rotate(Turn.RIGHT);
				dist = rightSensor.distanceToObstacle(curPos, curDir, battery);
				rotate(Turn.LEFT);
				batteryLevel -= (1/2)*getEnergyForFullRotation();
				}
			else if (leftSensor.isOperational()) {
				rotate(Turn.LEFT);
				dist = leftSensor.distanceToObstacle(curPos, curDir, battery);
				rotate(Turn.RIGHT);
				batteryLevel -= (1/2)*getEnergyForFullRotation();
				}
			else if (frontSensor.isOperational()) {
				rotate(Turn.AROUND);
				dist = frontSensor.distanceToObstacle(curPos, curDir, battery);
				rotate(Turn.AROUND);
				batteryLevel -= getEnergyForFullRotation();
				}
			else {
				// if there are no operational sensors, we simply wait for the
				// original sensor to be repaired and then use that one again
				while (!backSensor.isOperational()) {
					System.out.println("Waiting for sensor to be repaired");
					continue; }
				dist = backSensor.distanceToObstacle(curPos, curDir, battery); 
				}} 
		catch (Exception e) {}
		return dist;
	}
	
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		 if (distanceToObstacle(direction) == Integer.MAX_VALUE) {
				return true; }
		 else {
				return false; }
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Ensures the sensor is not in a failure state
	 * @param sensor which sensor we are investigating
	 * @return true for operational, false for not
	 */
	public boolean checkSensorWorking(DistanceSensor sensor) {	
		return sensor.isOperational();
	}

}
