package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import generation.MazeFactory;
/* auto-added Order.Builder since it was needed in
 * setup()
 */
import generation.Order.Builder;
import generation.Stub;
import generation.MazeBuilder;
import generation.MazeContainer;
import generation.Distance;


/* Test methods of the Maze Factory class to make sure maze is correct 
*/

public class MazeFactoryTest {
	
	// private variables
	// width and height taken from FloorplanTest.java
		private int width = 4;
		private int height = 4;
		private Stub stub;
		private MazeFactory testedMaze;
		private MazeContainer container;
		private Distance distance;
		
	/* Need to set up the test for the maze by creating the stub,
	 * the maze from the MazeFactory, choosing a builder (DFS, Prim, 
	 * Boruvka) setting the skill level, and waiting until the maze
	 * is built.
	 */
	public final Maze setup(int skillLevel, Builder builder, boolean isPerfect) {
		stub = new Stub(skillLevel, builder, isPerfect);
		testedMaze = new MazeFactory();
		testedMaze.order(stub);
		/* Use our new MazeFactory object to see if the builder
		 * thread is complete using the inherit method
		 */
		testedMaze.waitTillDelivered();
		Maze result = stub.getMaze();
		return result;
	}
	
	/* Need to test to make sure there is an exit built in every maze
	 * and that there is only one.
	 */
	public final void testExitLocation() {
		/* use getExitPosition from MazeContainer.java
		 * to find the cell that is supposed to have
		 * the exit
		 */
		// create an int "exit" that counts how many maze cells have a distance of 
		// one to the exit of the maze.
		// check this by calculating distance to exit with getDistanceToExit() from
		// MazeContainer.java and this comes back as an array of 2 numbers.
		// Loop through the entire 2D array of the maze nodes, calculating 
		// the distance to exit and check to see that one and only one comes back 
		// as 0.
		// assert true exit = 1
	}
	
}
