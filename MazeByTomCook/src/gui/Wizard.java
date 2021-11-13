package gui;

import static org.junit.Assert.assertEquals;
import java.lang.Math;

import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;
import generation.CardinalDirection;

/**
 * This interface implements the RobotDriver interface for a specific 
 * driver called Wizard.
 * 
 * Responsibilities: The Wizard uses a cheating method to solve the 
 * maze by simply following the pre-created path to the exit made with 
 * Maze.getNeighborCloserToExit(). 
 * 
 * If the Wizard driver runs into a wall, the robot driver crashes and
 * the maze game will terminate. Thus, the wizard can use sensors to make
 * sure it does not run into walls, but it does not need to.
 * 
 * This class was auto-generated as an implementation of the RobotDriver class.
 * 
 * Collaborators: RobotDriver, ReliableRobot, Maze, DistanceSensor
 * 
 * @author Tom Cook
 *
 */

public class Wizard implements RobotDriver {
	
	 private int totalEnergyConsumption;
	 private int totalPathTravelled;
	 private Robot robot;
	 private Maze maze;
	 
	public Wizard() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	* Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, could be the ReliableRobot or UnreliableRobot.
	 * @param r robot to operate driver
	 */
	@Override
	public void setRobot(Robot r) {
		 this.robot = r;
		// this.robot = robot

	}
	
	/**
	 * Provides the robot driver with the maze information.
	 * The wizard relies on this information to find the exit.
	 * @param maze represents the maze, must be non-null and a fully functional maze object.
	 */
	@Override
	public void setMaze(Maze maze) {
		 this.maze = maze;

	}
	
	/**
	 * Drives the robot towards the exit following
	 * its solution strategy and given the exit exists and  
	 * given the robot's energy supply lasts long enough. 
	 * When the robot reached the exit position and its forward
	 * direction points to the exit the search terminates and 
	 * the method returns true.
	 * If the robot failed due to lack of energy or crashed, the method
	 * throws an exception.
	 * If the method determines that it is not capable of finding the
	 * exit it returns false, for instance, if it determines it runs
	 * in a cycle and can't resolve this.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		 while (robot.isAtExit() == false) {
				drive1Step2Exit(); }
				// assertEquals(robot.getCurrentPosition(), maze.getExitPosition()); }
		 while (robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD) == false) {
				robot.rotate(Turn.LEFT);
				return true; }
		return true;
	}
	
	/**
	 * Drives the robot one step towards the exit following
	 * its solution strategy and given the exists and 
	 * given the robot's energy supply lasts long enough.
	 * It returns true if the driver successfully moved
	 * the robot from its current location to an adjacent
	 * location.
	 * At the exit position, it rotates the robot 
	 * such that if faces the exit in its forward direction
	 * and returns false. 
	 * If the robot failed due to lack of energy or crashed, the method
	 * throws an exception. 
	 * @return true if it moved the robot to an adjacent cell, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		int[] curPos = robot.getCurrentPosition();
		int[] desiredPos = maze.getNeighborCloserToExit(curPos[0], curPos[1]);
		// find the difference between the cells (i.e. subtract
		// 	the x and y values from each other) to see which direction
		// 	the neighbor cell is
		int[] direction = {desiredPos[0] - curPos[0], desiredPos[1] - curPos[1]};
		// North : (0,-1) South : (0,1) East : (1,0) West : (-1,0)
		CardinalDirection desiredDir = CardinalDirection.getDirection(direction[0], direction[1]);
		if (robot.getCurrentDirection() == desiredDir) {
			robot.move(1);
			return true; }
	    if (robot.getCurrentDirection() == desiredDir.oppositeDirection().rotateClockwise()) {
			robot.rotate(Turn.LEFT); 
			return true; }
		else if (robot.getCurrentDirection() == desiredDir.oppositeDirection()) {
			robot.rotate(Turn.AROUND); 
			return true; }
		else if (robot.getCurrentDirection() == desiredDir.rotateClockwise()) {
			robot.rotate(Turn.RIGHT); 
			return true; }
		return false; 
	}
		
	
	/**
	 * Returns the total energy consumption of the journey, i.e.,
	 * the difference between the robot's initial energy level at
	 * the starting position and its energy level at the exit position. 
	 * This is used as a measure of efficiency for a robot driver.
	 * @return the total energy consumption of the journey
	 */
	@Override
	public float getEnergyConsumption() {
		return totalEnergyConsumption;
	}

	/**
	 * Returns the total length of the journey in number of cells traversed. 
	 * Being at the initial position counts as 0. 
	 * This is used as a measure of efficiency for a robot driver.
	 * @return the total length of the journey in number of cells traversed
	 */
	@Override
	public int getPathLength() {
		return totalPathTravelled;
	}


}
