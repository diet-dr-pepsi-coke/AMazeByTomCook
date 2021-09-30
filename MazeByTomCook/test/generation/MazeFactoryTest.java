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


/* Test methods of the Maze Factory class to make sure maze is correct 
*/

public class MazeFactoryTest {
	
	// private variables
	// width and height taken from FloorplanTest.java
		private int width = 4;
		private int height = 4;
		private Stub stub;
		private MazeFactory testedMaze;
		
		
	/* Need to set up the test for the maze by creating the stub,
	 * the maze from the MazeFactory, choosing a builder (DFS, Prim, 
	 * Boruvka) setting the skill level, and waiting until the maze
	 * is built.
	 */
	public void setup(int skillLevel, Builder builder, boolean isPerfect) {
		stub = new Stub(skillLevel, builder, isPerfect);
		testedMaze = new MazeFactory();
		/* Use our new MazeFactory object to see if the builder
		 * thread is complete using the inherit method
		 */
		testedMaze.waitTillDelivered();
	}
}
