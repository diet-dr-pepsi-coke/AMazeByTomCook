/**
 * 
 */
package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;

/**
 * This interface implements the DistanceSensor interface for a specific 
 * sensor type considered unreliable because it sometimes fails and must
 * stop and be repaired before continuing.
 * 
 * Responsibilities: This class is responsible mainly for computing the distance to
 * the nearest wallboard in the particular sensor direction. The robot itself
 * will decide which sensor in which direction will use this sensor, so it will
 * be used heavily in the robot and robot driver classes. The sensor receives the maze 
 * and is also responsible for keeping track of how much using the sensor costs
 * for the energy consumption. The sensor at certain points will break down and
 * not be usable until it is repaired, so it adds another layer of challenge onto 
 * the automatic traversal of the maze.
 * 
 * Collaborators: ReliableRobot, Wizard, DistanceSensor, Maze, Distance, Floorplan
 * 
 * @author Tom Cook
 *
 */
public class UnreliableSensor implements DistanceSensor {

	/**
	 * 
	 */
	public UnreliableSensor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaze(Maze maze) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSensorDirection(Direction mountedDirection) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getEnergyConsumptionForSensing() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
