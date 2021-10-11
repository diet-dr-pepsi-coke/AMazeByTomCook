package generation;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import generation.CardinalDirection;
import generation.SingleRandom;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable {
	public SingleRandom random = SingleRandom.getRandom();
	public Dictionary<Wallboard, Integer> boards = new Hashtable<Wallboard, Integer>();
	public ArrayList<Wallboard> walls = new ArrayList<Wallboard>();
	
	public MazeBuilderBoruvka() {
		super();
		System.out.println("MazeBuilderBoruvka uses Boruvka's algorithm to generate maze.");
	}
	
	public void setEdgeWeight(Wallboard w) {
		// This method first checks the wallboard to see if it is a 
		// border using isPartofBorder() method from Floorplan.java.
		// If it is not a border, then create an integer value "weight"
		// using the nextInt() method from SingleRandom.java. Store
		// the weight value and the non-border wallboard in an Dictionary
		// where you can access the key of the wallboard to find its weight.
		// Then, go to the neighbor of this wallboard and set the weight of 
		// the same board to the same integer value.
		if (floorplan.isPartOfBorder(w)) {
			System.out.println("Border wall included in internal wallboard list.");
			return;
		}
		if (boards.get(w) == -1) {
			if (walls.indexOf(w) != walls.size() - 1) {
			int weight = random.nextIntWithinInterval(0,1000);
			boards.put(w, weight);
			for (Wallboard i : walls) {
				if (w.getNeighborX() == i.getX() && w.getNeighborY() == i.getY() && w.getDirection().oppositeDirection() == i.getDirection()) {
					boards.put(i, weight);
				}
			}
			}
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
		int index = 0;
		for (Wallboard i : walls) { 
			setEdgeWeight(i);
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y< height; y++) {
				ArrayList<Wallboard> compareWeight = new ArrayList<Wallboard>();
				System.out.println("new");
				while (index < walls.size() && walls.get(index).getX() == x && walls.get(index).getY() == y) {
					//System.out.println(x);
					//System.out.println(y);
					compareWeight.add(walls.get(index));
						index ++;
				}
				int least = 1001;
				for (Wallboard i : compareWeight) {
					if (boards.get(i) < least) {
						least = boards.get(i);
					}
				System.out.println(least);
				}
		}}
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
		for (int x = 0; x < width; x++) {
			for (int y = 0; y< height; y++) {
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East) ;
		for (CardinalDirection cd : CardinalDirection.values()) {
			wallboard.setLocationDirection(x, y, cd);
			if (!floorplan.isPartOfBorder(wallboard)) // 
			{
				walls.add(new Wallboard(x, y, cd));
			//	walls.add(new Wallboard(wallboard.getNeighborX(),wallboard.getNeighborY(), cd.oppositeDirection()));
				
			}
		}
			}
		}
		for (Wallboard i : walls) {
			// initialize every wallboard to have a weight of -1
			boards.put(i, -1);
		}
		return walls;
	}
	
	public int wallboardInfo(Wallboard w) {
		int x = w.getX();
		int y = w.getY();
		int neighborX = w.getNeighborX();
		int neighborY = w.getNeighborY();
		CardinalDirection cd = w.getDirection();
		int intCD = 0;
		switch (cd) {
		case North:
			intCD = 1;
		case East:
			intCD = 2;
		case South:
			intCD = 3;
		case West:
			intCD = 4;
		}
		return 0;
	}
}
	
	
