/**
 * 
 */
package generation;

import generation.Order;
import generation.MazeContainer;

/**
 *
 *
 */
public class Stub implements Order {
	public Builder builder;
	public Order order;
	public int skillLevel;
	public boolean isPerfect;
	public MazeContainer container;
	/**
	 * 
	 */
	public Stub(int skillLevel, Builder builder, boolean ifPerfect) {
		// TODO Auto-generated constructor stub
		this.skillLevel = skillLevel;
		this.builder = builder;
		this.isPerfect = ifPerfect;
	}

	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return skillLevel;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return builder;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return isPerfect;
	}

	@Override
	public int getSeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deliver(Maze mazeConfig) {
		container = (MazeContainer) mazeConfig;
		
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}
	public MazeContainer getMaze() {
		return container;
	}

}
