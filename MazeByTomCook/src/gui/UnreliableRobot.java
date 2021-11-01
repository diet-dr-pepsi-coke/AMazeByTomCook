package gui;

import generation.CardinalDirection;
import gui.Robot.Direction;

/**
 * 
 * The Unreliable Robot extends the ReliableRobot class and inherits all
 * of its methods. The only change is the implementation of failure
 * and repair processes for the possible unreliable sensors.
 * 
 * Responsibilities: Control everything the ReliableRobot does: moving,
 * rotating, calculating the distance to obstacles, room and exit sensors,
 * odometer, etc. with the inclusion of failure and repair processes.
 * 
 * Collaborators: UnreliableSensor, WallFollower, Controller, StatePlaying
 * 
 * @author Tom Cook
 *
 */

public class UnreliableRobot extends ReliableRobot {
	 public DistanceSensor frontSensor;
	 public DistanceSensor leftSensor;
	 public DistanceSensor rightSensor;
	 public DistanceSensor backSensor;

	public UnreliableRobot() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Selects which sensor to begin the failure and repair process for.
	 * The actual process is handled by the sensor itself.
	 * @param direction is which direction the desired sensor faces
	 * @param meanTimeBetweenFailues is how long the process will wait until
	 * it begins another failure
	 * @param meanTimeToRepair is how long the actual process takes
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		switch (direction) {
		case FORWARD :
			frontSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
			break;
		case LEFT :
			leftSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
			break;
		case RIGHT :
			rightSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
			break;
		case BACKWARD :
			backSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
			break;
		}

	}

		/**
		 * Method is not required for project 3
		 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	public boolean checkSensorWorking(DistanceSensor sensor) {	
		return sensor.isOperational();
	}
}
