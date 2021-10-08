package generation;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import generation.SingleRandom;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable {
	public SingleRandom random;
	public Dictionary<Wallboard, Integer> boards;
	
	public MazeBuilderBoruvka() {
		super();
		System.out.println("MazeBuilderBoruvka uses Boruvka's algorithm to generate maze.");
	}
	
	public void setEdgeWeight(int x, int y, Wallboard w) {
		// This method first checks the wallboard to see if it is a 
		// border using isPartofBorder() method from Floorplan.java.
		// If it is not a border, then create an integer value "weight"
		// using the nextInt() method from SingleRandom.java. Store
		// the weight value and the non-border wallboard in an Dictionary
		// where you can access the key of the wallboard to find its weight.
		// Then, go to the neighbor of this wallboard and set the weight of 
		// the same board to the same integer value.
		if (floorplan.isPartOfBorder(w)) {
			return;
		}
		else {
			int weight = random.nextInt();
			boards.put(w, weight);	
			CardinalDirection cd = w.getDirection();
			int neighborX = w.getNeighborX();
			int neighborY = w.getNeighborY();
			
			
		}	
	}

	
	public int getEdgeWeight(Wallboard w) {
		// Using the wallboard w, find the key in the Dictionary from the
		// set method and return its corresponding value, the weight
		// of the wallboard.
			
		return boards.get(w);
	}
	
	@Override 
	protected void generatePathways() {
		//pseudocode
		
		
		floorplan.initialize();
		createListOfInternalWallboards();
		
	}
	/**
	 * Updates a list of all wallboards that could be removed from the maze based on wallboards towards new cells.
	 * For the given x, y coordinates, one checks all four directions
	 * and for the ones where one can tear down a wallboard, a 
	 * corresponding wallboard is added to the list of wallboards.
	 * Taken from MazeBuilderPrim algorithm.
	 * @param x the x coordinate of interest
	 * @param y the y coordinate of interest
	 * @param wallboards the new elements should be added to, must not be null
	 */
	private ArrayList<Wallboard> createListOfInternalWallboards() {
		ArrayList<Wallboard> walls = new ArrayList<Wallboard>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y< height; y++) {
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East) ;
		for (CardinalDirection cd : CardinalDirection.values()) {
			wallboard.setLocationDirection(x, y, cd);
			if (!floorplan.isPartOfBorder(wallboard)) // 
			{
				walls.add(new Wallboard(x, y, cd));
			}
		}
			}
		}
		for (Wallboard i : walls) {
			System.out.println(i)	;
		}
		return walls;
	}
}
	
	
