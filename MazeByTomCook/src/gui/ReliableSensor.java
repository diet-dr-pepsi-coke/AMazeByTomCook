package gui;

import generation.CardinalDirection;
import generation.Floorplan;
import generation.Maze;
import gui.Robot.Direction;

/**
 * This interface implements the DistanceSensor interface for a specific 
 * sensor type considered reliable because it does not ever fail or need
 * to be repaired.
 * 
 * Responsibilities: This class is responsible mainly for computing the distance to
 * the nearest wallboard in the particular sensor direction. The robot itself
 * will decide which sensor in which direction will use this sensor, so it will
 * be used heavily in the robot and robot driver classes. The sensor receives the maze 
 * and is also responsible for keeping track of how much using the sensor costs
 * for the energy consumption.
 * 
 * Collaborators: ReliableRobot, DistanceSensor, Maze, Distance, Floorplan
 * 
 * @author Tom Cook
 *
 */

public class ReliableSensor implements DistanceSensor {
	
	private Maze maze;
	public Direction sensorDirection;
	// private Maze maze
	// private Direction sensorDirection

	public ReliableSensor() {
		// TODO Auto-generated constructor stub
	}

	 /**Tells the distance to an obstacle (a wallboard) that the sensor
	 * measures. The sensor is assumed to be mounted in a particular
	 * direction relative to the forward direction of the robot.
	 * Distance is measured in the number of cells towards that obstacle, 
	 * e.g. 0 if the current cell has a wallboard in this direction, 
	 * 1 if it is one step in this direction before directly facing a wallboard,
	 * Integer.MaxValue if one looks through the exit into eternity.
	 * 
	 * This method requires that the sensor has been given a reference
	 * to the current maze and a mountedDirection by calling 
	 * the corresponding set methods with a parameterized constructor.
	 * 
	 * @param currentPosition is the current location as (x,y) coordinates
	 * @param currentDirection specifies the direction of the robot
	 * @param powersupply is an array of length 1, whose content is modified 
	 * to account for the power consumption for sensing
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise.
	 * @throws Exception with message 
	 * SensorFailure if the sensor is currently not operational
	 * PowerFailure if the power supply is insufficient for the operation
	 * @throws IllegalArgumentException if any parameter is null
	 * or if currentPosition is outside of legal range
	 * ({@code currentPosition[0] < 0 || currentPosition[0] >= width})
	 * ({@code currentPosition[1] < 0 || currentPosition[1] >= height}) 
	 * @throws IndexOutOfBoundsException if the powersupply is out of range
	 * ({@code powersupply < 0} ) 
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		 int curX = currentPosition[0];
		 int curY = currentPosition[1];
		 int openCells = 0; /////keeps track of how far next wallboard is//////
		 CardinalDirection newDir = convertMountedToCardinal(sensorDirection, currentDirection);
		 switch (newDir) {
		////////////// North: CurY--, South: CurY++, West: CurX--, East: CurX++/////////////////
			case North :
		 		while (!maze.hasWall(curX, curY, newDir)) {
					openCells ++;
					 curY--; }
			case South :
				while (!maze.hasWall(curX, curY, newDir)) {
					openCells ++;
					 curY++;  }
			case West :
				while (!maze.hasWall(curX, curY, newDir)) {
					openCells ++;
					 curX--;  }
			case East :
				while (!maze.hasWall(curX, curY, newDir)) {
					openCells ++;
					 curX++;  }
		 		}
		return openCells;
		// floorplan = maze.getFloorplan()
		// curX = currentPosition[0]
		// curY = currentPosition[1]
		// openCells = 0 /////keeps track of how far next wallboard is//////
		// switch (currentDirection) {
		//	case (North/East/South/West) :
		// 		while (!maze.hasWall(curX, curY, currentDirection)) {
		//			openCells ++
		//			CurX ++/-- || CurY++/-- ///////depending on the switch statement///
		////////////// North: CurY--, South: CurY++, West: CurX--, East: CurX++/////////////////
		// return openCells
	}

	/**
	 * Provides the maze information that is necessary to make
	 * a DistanceSensor able to calculate distances.
	 * @param maze the maze for this game
	 * @throws IllegalArgumentException if parameter is null
	 * or if it does not contain a floor plan
	 */
	@Override
	public void setMaze(Maze maze) {
		this.maze = maze;
		// this.maze = maze
	}


	/**
	 * Provides the angle, the relative direction at which this 
	 * sensor is mounted on the robot.
	 * If the direction is left, then the sensor is pointing
	 * towards the left hand side of the robot at a 90 degree
	 * angle from the forward direction. 
	 * @param mountedDirection is the sensor's relative direction
	 * @throws IllegalArgumentException if parameter is null
	 */
	@Override
	public void setSensorDirection(Direction mountedDirection) {
		this.sensorDirection = mountedDirection;
		// this.sensorDirection = mountedDirection

	}

	/**
	 * Returns the amount of energy this sensor uses for 
	 * calculating the distance to an obstacle exactly once.
	 * This amount is a fixed constant for a sensor.
	 * @return the amount of energy used for using the sensor once
	 */
	@Override
	public float getEnergyConsumptionForSensing() {
		return 1; 	//as dictated by the project description
		// return 1 ((as dictated by the project description))
	}
	
	public CardinalDirection convertMountedToCardinal(Direction currentMount, CardinalDirection DirofRobot) {
		CardinalDirection newDir = CardinalDirection.North;
		if (DirofRobot == CardinalDirection.North) {
			switch (currentMount) {
			case FORWARD :
				newDir = CardinalDirection.North;
				break;
			case BACKWARD :
				newDir = CardinalDirection.South;
				break;
			case LEFT :
				newDir = CardinalDirection.East;
				break;
			case RIGHT :
				newDir = CardinalDirection.West;	
				break;
			}}
		if (DirofRobot == CardinalDirection.South) {
			switch (currentMount) {
			case FORWARD :
				newDir = CardinalDirection.South;
				break;
			case BACKWARD :
				newDir = CardinalDirection.North;
				break;
			case LEFT :
				newDir = CardinalDirection.West;
				break;
			case RIGHT :
				newDir = CardinalDirection.East;
				break;
			}}
		if (DirofRobot == CardinalDirection.East) {
			switch (currentMount) {
			case FORWARD :
				newDir = CardinalDirection.East;
				break;
			case BACKWARD :
				newDir = CardinalDirection.West;
				break;
			case LEFT :
				newDir = CardinalDirection.South;
				break;
			case RIGHT :
				newDir = CardinalDirection.North;
				break;
			}}
		if (DirofRobot == CardinalDirection.West) {
			switch (currentMount) {
			case FORWARD :
				newDir = CardinalDirection.West;
				break;
			case BACKWARD :
				newDir = CardinalDirection.East;
				break;
			case LEFT :
				newDir = CardinalDirection.North;
				break;
			case RIGHT :
				newDir = CardinalDirection.South;
				break;
			}}
		return newDir;
	}
	/**
	 * Not used in project 3 yet.
	 * Can leave blank.
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	/**
	 * Not used in project 3.
	 * Can leave blank.
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
