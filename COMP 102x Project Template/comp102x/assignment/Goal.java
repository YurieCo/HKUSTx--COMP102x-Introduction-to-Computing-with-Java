package comp102x.assignment;

/**
 * The Goal class represents a goal the can be hit but the bullet. The goal can either be stationary or movable.
 */
public class Goal {
	
	/** Indicates the goal is not movable. **/
	public static final int STATIONARY = 1;
	
	/** Indicates the goal is movable. **/
	public static final int MOVABLE = 2;
	
	/** Stores the hit status of the goal. **/
	private boolean isHit;
	
	/** Stores the type of the goal, STATIONARY or MOVABLE. **/
	private int type;
	
	/** Stored the score value the goal is representing. **/
	private int value;
	
	/**
	 * Constructs a goal of a certain type and initializing its hit status.
	 * 
	 * @param type The type of the goal to be created.
	 * @param isHit The initial hit status of the goal.
	 */
	public Goal(int type, boolean isHit) {
		
		this.type = type;
		this.value = 1;
		this.isHit = isHit;
	}
	
	/**
	 * Returns the hit status of the goal.
	 * @return the hit status of the goal.
	 */
	public boolean isHit() {
		return isHit;
	}
	
	/**
	 * Sets the hit status of the goal.
	 * @param isHit the hit status of the goal to be updated to. 
	 */
	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

	/**
	 * Returns the type of the goal.
	 * @return the type of the goal.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type of the goal.
	 * @param type the type of the goal to be updated to.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the score value of the goal.
	 * @return the score value of the goal.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the score value of the goal.
	 * @param value the score value of the goal to be updated to.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
