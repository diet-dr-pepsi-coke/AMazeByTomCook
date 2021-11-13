package gui;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

import generation.Maze;
import gui.Robot.Direction;

public class WallFollowerTest {
	private WallFollower wallFollower;
	private ReliableRobot robot;
	private Controller controller;
	private Maze mazeConfig;
	private StatePlaying testState;

	public WallFollowerTest() {
		// TODO Auto-generated constructor stub
	}	
	
	@Before
	public void setUp() {
		MazeFileReader mfr = new MazeFileReader("test/data/input.xml");
		mazeConfig = mfr.getMazeConfiguration();
		robot = new ReliableRobot();
			robot.setMaze(mazeConfig);
			ReliableSensor Fsensor = new ReliableSensor();
			robot.addDistanceSensor(Fsensor, Direction.FORWARD);
			ReliableSensor Lsensor = new ReliableSensor();
			robot.addDistanceSensor(Lsensor, Direction.LEFT);
			ReliableSensor Rsensor = new ReliableSensor();
			robot.addDistanceSensor(Rsensor, Direction.RIGHT);
			ReliableSensor Bsensor = new ReliableSensor();
			robot.addDistanceSensor(Bsensor, Direction.BACKWARD);
		wallFollower = new WallFollower();
			wallFollower.setRobot(robot);
		controller = new Controller();
			controller.setRobotAndDriver(robot, wallFollower);
		testState = new StatePlaying();
			testState.setMazeConfiguration(mazeConfig);
			testState.start(controller, null);
		System.out.println("setup " + mazeConfig);
	}
	/**
	 Test case: Ensure the WallFollower can get to the exit
	 * <p>
	 * Method under test: drive2Exit()
	 * <p>
	 * It is correct if the WallFollower ends at the exit position
	 */
	@Test
	public void testDrive2Exit(){
		robot.setController(controller);
		wallFollower.setMaze(mazeConfig);
		System.out.println("wall follower " + testState.getMazeConfiguration());
		try {
			wallFollower.drive2Exit();
		} catch (Exception e) {
			e.printStackTrace();
		assertTrue(robot.isAtExit());
		}
	}
	
	/**
	 Test case: See if the WallFollower has moved a step towards the exit
	 * <p>
	 * Method under test: drive1Step2Exit()
	 * <p>
	 * It is correct if the WallFollower is in a different position than
	 * where it began.
	 */
	@Test
	public void testDrive1Step2Exit(){
	}
	
	/**
	Test case: Check the return of its energy consumption
	 * <p>
	 * Method under test: getEnergyConsumption()
	 * <p>
	 * It is correct if energy consumption after driving to the exit
	 * is equal to 2415.
	 */
	@Test
	public void testGetEnergyConsumption() {
	}
	
	/**
	 Test case: See if the path length correctly measures how 
	 far the robot has traveled.
	 * <p>
	 * Method under test: getPathLength()
	 * <p>
	 * It is correct if the pathLength at the end of drive2Exit()
	 * is equal to 291.
	 */
	@Test
	public void testGetPathLength() {
	}
}
