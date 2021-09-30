/**
 * 
 */
package generation;

import generation.Order;

/**
 *
 *
 */
public class Stub implements Order {
	public Builder builder;
	public Order order;
	public int skillLevel;
	public boolean isPerfect;
	/**
	 * 
	 */
	public Stub(int skillLevel, Builder builder, boolean ifPerfect) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deliver(Maze mazeConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}

}
