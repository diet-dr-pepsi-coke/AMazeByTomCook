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
		/* use getExitPosition() from MazeContainer.java
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
		// assert equal exit = 1
	}
	
	public final void testPathToExit() {
		// Randomly select a cell to begin with from the maze.
		// Use getNeighborCloserToExit() function to keep returning the next
		// cell closest to the exit.
		// assert true the distance to exit for the returned neighbor cell 
		// is less than the starting cell.
		// do this on a loop for each cell in the maze, making sure
		// it always comes back true and that the getNeighbor does
		// not return null, as that would fail the test
	}
	
	public final void testStartPosition() {
		//Use getPositionWithMaxDistance() to find where the set starting point
		// is and then loop through all the existing nodes noting their
		// distances to the exit and compare.
		// if a node in the loop has a distance that is larger than the initial
		// returned by the get method, then the test fails.
		// so store the largest distance in a variable "maximumDistance"
		// and at the end assert equals (maximumDistance, return value of
		// getPositionWithMaxDistance.
	}
	
	public final void testMinimalSpanningTree() {
		// Using the hasNoWall() method from Floorplan.java, iterate
		// through each node in the maze and each direction for 
		// the nodes calling this function. Keep a counter of
		// how many times hasNoWall() returns true, and at the end of
		// the iteration compare using assertEquals the counter of nodes
		// without walls and the number of nodes. MSP should have n-1 edges
		// (absence of walls) plus the exit which puts it at n walls.
	}
	
	public final void testNoRooms() {
		// Travel to each node in the 2D array using for loops.
		// At each node, attempt to move into the next node to the left.
		// If it worked, move again into the next node to the left.
		// Repeat until you are either back in the original node or you
		// were not able to move into one of those nodes. If you made it
		// back to the original node, then there is a room of minimum size 
		// and the test fails. If after iterating over every node in the array
		// this condition does not come true, then the test passes>
	}
}
	
