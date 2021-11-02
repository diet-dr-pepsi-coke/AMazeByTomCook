package gui;

import org.junit.Before;

import generation.CardinalDirection;
import generation.Floorplan;
import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

public class UnreliableRobotTest {
	private WallFollower wallFollower;
	private UnreliableRobot robot;
	private Controller controller;
	private Maze mazeConfig;
	private StatePlaying testState;

	public UnreliableRobotTest() {
		// TODO Auto-generated constructor stub
	}

	@Before
	void setUp () {
		MazeFileReader mfr = new MazeFileReader("test/data/input.xml");
		mazeConfig = mfr.getMazeConfiguration();
		robot = new UnreliableRobot();
			robot.setMaze(mazeConfig);
			ReliableSensor Fsensor = new UnreliableSensor();
			robot.addDistanceSensor(Fsensor, Direction.FORWARD);
			ReliableSensor Lsensor = new UnreliableSensor();
			robot.addDistanceSensor(Lsensor, Direction.LEFT);
			ReliableSensor Rsensor = new UnreliableSensor();
			robot.addDistanceSensor(Rsensor, Direction.RIGHT);
			ReliableSensor Bsensor = new UnreliableSensor();
			robot.addDistanceSensor(Bsensor, Direction.BACKWARD);
		wallFollower = new WallFollower();
			wallFollower.setRobot(robot);
		controller = new Controller();
			controller.setRobotAndDriver(robot, wallFollower);
		testState = new StatePlaying();
			testState.setMazeConfiguration(mazeConfig);
			testState.start(controller, null);
	}

	/**
	 * Test case: Ensure the robot has sensor objects in the correct
	 * direction after calling
	 * <p>
	 * Method under test: addDistanceSensor()
	 * <p>
	 * It is correct if the robot has four sensors each facing different
	 * directions.
	 */
	void testAddDistanceSensor() {
	}
	
	/**
	 * Test case: Check if the sensors have a viable maze configuration
	 * <p>
	 * Method under test: setSesnsorMazes()
	 * <p>
	 * It is correct if the maze configuration for the sensors is all the same
	 * and not null.
	 */
	void testSetSensorMazes() {
	}
	
	/**
	 * Test case: See if the current position is correct
	 * <p>
	 * Method under test: getCurrentPosition()
	 * <p>
	 * It is correct if the position matches the starting position
	 * of the maze.
	 */
	void testGetCurrentPosition() {
	}
	
	/**
	 * Test case: See if the current direction is correct
	 * <p>
	 * Method under test: getCurrentDirection()
	 * <p>
	 * It is correct if the direction is east because the robot always
	 * faces east to start.
	 */
	void testGetCurrentDirection() {
	}


	void testGetBatteryLevel() {
	}

	void testSetBatteryLevel() {
	}

	void testGetEnergyForFullRotation() {
	}

	void testGetEnergyForStepForward() {
	}

	void testGetOdometerReading() {
	}

	void testResetOdometer() {
	}

	/**
	 * Test case: See if the robot correctly rotates in each of the 
	 * possible directions and that its internal attributes change as well.
	 * <p>
	 * Method under test: rotate()
	 * <p>
	 * It is correct if the robot now faces the correct direction after each rotation
	 * (i.e. if previously north, and we rotate left, then facing west)
	 */
	void testRotate() {
	}

	/**
	 * Test case: See if the current position changes when the robot calls
	 * the move method
	 * <p>
	 * Method under test: move()
	 * <p>
	 * It is correct if the position after moving matches the position of
	 * the cell directly in front of the robot.
	 */
	void testMove() {
	}

	/**
	 * Test case: Check the functionality of the exit sensor to make sure
	 * the robot is correctly returning the right cell for the exit
	 * <p>
	 * Method under test: isAtExit()
	 * <p>
	 * It is correct if when the robot thinks it is at the exit, it is at
	 * the designated cell position of the exit in input.xml
	 */
	void testIsAtExit() {
	}

	/**
	 * Test case: Check the functionality of the room sensor to ensure the
	 * robot knows when it is in a room
	 * <p>
	 * Method under test: isInsideRoom()
	 * <p>
	 * It is correct if this returns true when the robot is inside of an
	 * alreaady known room.
	 */
	void testIsInsideRoom() {
	}

	/**
	 * Test case: See if the stopped boolean is correct when the battery level
	 * is equal to or less than 0 or the robot has moved into a wall
	 * <p>
	 * Method under test: hasStopped()
	 * <p>
	 * It is correct if this returns true when the battery level is 0 or less,
	 * and if it returns true when the robot purposefully moves into a wall.
	 */
	void testHasStopped() {
	}

	void testDistanceToObstacle() {
	}

	void testCanSeeThroughTheExitIntoEternity() {
	}

	void testStartFailureAndRepairProcess() {
	}

	void testStopFailureAndRepairProcess() {
	}
}
