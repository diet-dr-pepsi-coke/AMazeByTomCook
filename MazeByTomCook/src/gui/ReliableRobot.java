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
	 private DistanceSensor directionSensor;

	public ReliableRobot() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Provides the robot with a reference to the controller to cooperate with.
	 * The robot memorizes the controller such that this method is most likely called only once
	 * and for initialization purposes. The controller serves as the main source of information
	 * for the robot about the current position, the presence of walls, the reaching of an exit.
	 * The controller is assumed to be in the playing state.
	 * @param controller is the communication partner for robot
	 * @throws IllegalArgumentException if controller is null, 
	 * or if controller is not in playing state, 
	 * or if controller does not have a maze
	 */
	@Override
	public void setController(Controller controller) {
		this.controller = controller;

	}

	/**
	 * Adds a distance sensor to the robot such that it measures in the given direction.
	 * This method is used when a robot is initially configured to get ready for operation.
	 * The point of view is that one mounts a sensor on the robot such that the robot
	 * can measure distances to obstacles or walls in that particular direction.
	 * For example, if one mounts a sensor in the forward direction, the robot can tell
	 * with the distance to a wall for its current forward direction, more technically,
	 * a method call distanceToObstacle(FORWARD) will return a corresponding distance.
	 * So a robot with a left and forward sensor will internally have 2 DistanceSensor
	 * objects at its disposal to calculate distances, one for the forward, one for the
	 * left direction.
	 * A robot can have at most four sensors in total, and at most one for any direction.
	 * If a robot already has a sensor for the given mounted direction, adding another
	 * sensor will replace/overwrite the current one for that direction with the new one.
	 * @param sensor is the distance sensor to be added
	 * @param mountedDirection is the direction that it points to relative to the robot's forward direction
	 */
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		DistanceSensor directionSensor = sensor;
		directionSensor.setSensorDirection(mountedDirection);

	}

	/**
	 * Provides the current position as (x,y) coordinates for 
	 * the maze as an array of length 2 with [x,y].
	 * @return array of length 2, x = array[0], y = array[1]
	 * and ({@code 0 <= x < width, 0 <= y < height}) of the maze
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		return controller.getCurrentPosition();
	}

	/**
	 * Provides the robot's current direction.
	 * @return cardinal direction is the robot's current direction in absolute terms
	 */	
	@Override
	public CardinalDirection getCurrentDirection() {
		return controller.getCurrentDirection();
	}

	/**
	 * Returns the current battery level.
	 * The robot has a given battery level (energy level) 
	 * that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call 
	 * for sensor distance2Obstacle may use less energy than a move forward operation.
	 * If battery {@code level <= 0} then robot stops to function and hasStopped() is true.
	 * @return current battery level, {@code level > 0} if operational. 
	 */
	@Override
	public float getBatteryLevel() {
		return batteryLevel;
	}

	/**
	 * Sets the current battery level.
	 * The robot has a given battery level (energy level) 
	 * that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call 
	 * for distance2Obstacle may use less energy than a move forward operation.
	 * If battery {@code level <= 0} then robot stops to function and hasStopped() is true.
	 * @param level is the current battery level
	 * @throws IllegalArgumentException if level is negative 
	 */
	@Override
	public void setBatteryLevel(float level) {
		this.batteryLevel = level;

	}

	/**
	 * Gives the energy consumption for a full 360 degree rotation.
	 * Scaling by other degrees approximates the corresponding consumption. 
	 * @return energy for a full rotation
	 */
	@Override
	public float getEnergyForFullRotation() {
		// return 12
		// (( energy cost of a 90 degree rotation is 3, so 4 90 degree turns = 12 ))
		return 12;
	}

	/**
	 * Gives the energy consumption for moving forward for a distance of 1 step.
	 * For simplicity, we assume that this equals the energy necessary 
	 * to move 1 step and that for moving a distance of n steps 
	 * takes n times the energy for a single step.
	 * @return energy for a single step forward
	 */
	@Override
	public float getEnergyForStepForward() {
		// return 6
		// (( as dictated in the project requirements ))
		return 6;
	}

	/** 
	 * Gets the distance traveled by the robot.
	 * The robot has an odometer that calculates the distance the robot has moved.
	 * Whenever the robot moves forward, the distance 
	 * that it moves is added to the odometer counter.
	 * The odometer reading gives the path length if its setting is 0 at the start of the game.
	 * The counter can be reset to 0 with resetOdomoter().
	 * @return the distance traveled measured in single-cell steps forward
	 */
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

	/**
	 * Turn robot on the spot for amount of degrees. 
	 * If robot runs out of energy, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * @param turn is the direction to turn and relative to current forward direction. 
	 */
	@Override
	public void rotate(Turn turn) {
		 if (getBatteryLevel() > 0 && !hasStopped()) {
		 		switch (turn) {
				case LEFT :
					controller.keyDown(UserInput.LEFT, 0);
					setBatteryLevel(getBatteryLevel() - (1/4)*getEnergyForFullRotation());
					break;
				case RIGHT :
					controller.keyDown(UserInput.RIGHT, 0);
					setBatteryLevel(getBatteryLevel() - (1/4)*getEnergyForFullRotation());
					break;
		 		case AROUND : // turn left twice
					controller.keyDown(UserInput.LEFT, 0);
					controller.keyDown(UserInput.LEFT, 0);
					setBatteryLevel(getBatteryLevel() - (2/4)*getEnergyForFullRotation());
					break; }}
		 if (getBatteryLevel() <= 0) {
				stopped = true; }
	}

	/**
	 * Moves robot forward a given number of steps. A step matches a single cell.
	 * If the robot runs out of energy somewhere on its way, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * If the robot hits an obstacle like a wall, it remains at the position in front 
	 * of the obstacle and also hasStopped() == true as this is not supposed to happen.
	 * This is also helpful to recognize if the robot implementation and the actual maze
	 * do not share a consistent view on where walls are and where not.
	 * @param distance is the number of cells to move in the robot's current forward direction 
	 * @throws IllegalArgumentException if distance not positive
	 */
	@Override
	public void move(int distance) {
		 int counter = 0;
		 for (int i=0; i<distance; i++) {
		  	if (getBatteryLevel() > 0 && !hasStopped()) {
					controller.keyDown(UserInput.UP, 0);
					setBatteryLevel(getBatteryLevel() - getEnergyForStepForward());
					counter ++;
					odometer++;
			if (counter != distance) {
		 		stopped = true;}
			if (getBatteryLevel() <= 0) {
				stopped = true; }
	}}}

	/**
	 * Makes robot move in a forward direction even if there is a wall
	 * in front of it. In this sense, the robot jumps over the wall
	 * if necessary. The distance is always 1 step and the direction
	 * is always forward.
	 * If the robot runs out of energy somewhere on its way, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level.
	 * If the robot tries to jump over an exterior wall and
	 * would land outside of the maze that way,  
	 * it remains at its current location and direction,
	 * hasStopped() == true as this is not supposed to happen.
	 */
	@Override
	public void jump() {
		 if (getBatteryLevel() > 0 && !hasStopped()) {
			 	// don't need to check if border wall because StatePlaying already does that
				controller.keyDown(UserInput.JUMP, 0);
				setBatteryLevel(getBatteryLevel() - 40); // cost of jumping as outlined
		 if (getBatteryLevel() <= 0) {
				stopped = true; }}

	}

	/**
	 * Tells if the current position is right at the exit but still inside the maze. 
	 * The exit can be in any direction. It is not guaranteed that 
	 * the robot is facing the exit in a forward direction.
	 * @return true if robot is at the exit, false otherwise
	 */
	@Override
	public boolean isAtExit() {
		 Maze maze = controller.getMazeConfiguration();
		 int[] supposedExit = maze.getExitPosition();
		 // auto-generated fix for Exception in getCurrentPosition()
			if (supposedExit == controller.getCurrentPosition()) {
				 return true;
			 }
		return false;
	}

	/**
	 * Tells if current position is inside a room. 
	 * @return true if robot is inside a room, false otherwise
	 */	
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

	/**
	 * Tells if the robot has stopped for reasons like lack of energy, 
	 * hitting an obstacle, etc.
	 * Once a robot is has stopped, it does not rotate or 
	 * move anymore.
	 * @return true if the robot has stopped, false otherwise
	 */
	@Override
	public boolean hasStopped() {
		return stopped;
	}

	/**
	 * Tells the distance to an obstacle (a wall) 
	 * in the given direction.
	 * The direction is relative to the robot's current forward direction.
	 * Distance is measured in the number of cells towards that obstacle, 
	 * e.g. 0 if the current cell has a wallboard in this direction, 
	 * 1 if it is one step forward before directly facing a wallboard,
	 * Integer.MaxValue if one looks through the exit into eternity.
	 * The robot uses its internal DistanceSensor objects for this and
	 * delegates the computation to the DistanceSensor which need
	 * to be installed by calling the addDistanceSensor() when configuring
	 * the robot.
	 * @param direction specifies the direction of interest
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise
	 * @throws Exception 
	 */
	@Override
	public int distanceToObstacle(Direction direction) {
		 if (getBatteryLevel() > 0 && !hasStopped()) { 
			 float[] battery = {batteryLevel};
			 int[] curPos = controller.getCurrentPosition();
			 CardinalDirection curDir = controller.getCurrentDirection();
			 // auto-generated fix for unhandled exception type Exception
			 int dist = 0;
			try {
				dist = directionSensor.distanceToObstacle(curPos, curDir, battery);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 setBatteryLevel(getBatteryLevel() - directionSensor.getEnergyConsumptionForSensing());
			 if (getBatteryLevel() <= 0) {
				stopped = true; }
			 return dist; }
		 return 0;
	}

	/**
	 * Tells if a sensor can identify the exit in the given direction relative to 
	 * the robot's current forward direction from the current position.
	 * It is a convenience method is based on the distanceToObstacle() method and transforms
	 * its result into a boolean indicator.
	 * @param direction is the direction of the sensor
	 * @return true if the exit of the maze is visible in a straight line of sight
	 * @throws UnsupportedOperationException if robot has no sensor in this direction
	 * or the sensor exists but is currently not operational
	 */
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		 if (distanceToObstacle(direction) == Integer.MAX_VALUE) {
				return true; }
		 else {
				return false; }
	}

	/**
	 * Method is not requird for project 3
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

		/**
		 * Method is not required for project 3
		 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
