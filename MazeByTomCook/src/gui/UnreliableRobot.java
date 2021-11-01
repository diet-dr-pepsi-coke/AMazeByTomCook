package gui;

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

	public UnreliableRobot() {
		// TODO Auto-generated constructor stub
	}

}
