package comp102x.assignment;

/**
 * The Football class represents a football. It contains information on the current location of the football and the destination of it.
 */
public class Football {
	
	/** The starting x position of the football in the space.*/
	int startX;
	
	/** The starting y position of the football in the space.*/
	int startY;
	
	/** The starting z position of the football in the space. Left for further extension of the program.*/
	int startZ;
	
	/** The final x position of the football in the space.*/
	int destinationX;
	
	/** The final y position of the football in the space.*/
	int destinationY;
	
	/** The final z position of the football in the space. Left for further extension of the program.*/
	int destinationZ;

	/**
	 * Returns the starting x position of the football.
	 * @return the starting x position of the football.
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Sets the starting x position of the football.
	 * @param startX the starting x position that is going to be updated to.
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Returns the starting y position of the football.
	 * @return the starting y position of the football.
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Sets the starting y position of the football.
	 * @param startY the starting y position that is going to be updated to.
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	/**
	 * Returns the starting z position of the football.
	 * @return the starting z position of the football.
	 */
	public int getStartZ() {
		return startZ;
	}

	/**
	 * Sets the starting z position of the football.
	 * @param startZ the starting z position that is going to be updated to.
	 */
	public void setStartZ(int startZ) {
		this.startZ = startZ;
	}

	/**
	 * Returns the destination x position of the football.
	 * @return the destination x position of the football.
	 */
	public int getDestinationX() {
		return destinationX;
	}
	
	/**
	 * Sets the destination x position of the football.
	 * @param destinationX the destination x position that is going to be updated to. 
	 */
	public void setDestinationX(int destinationX) {
		this.destinationX = destinationX;
	}

	/**
	 * Returns the destination y position of the football.
	 * @return the destination y position of the football.
	 */
	public int getDestinationY() {
		return destinationY;
	}

	/**
	 * Sets the destination y position of the football.
	 * @param destinationY the destination y position that is going to be updated to. 
	 */
	public void setDestinationY(int destinationY) {
		this.destinationY = destinationY;
	}

	/**
	 * Returns the destination z position of the football.
	 * @return the destination z position of the football.
	 */
	public int getDestinationZ() {
		return destinationZ;
	}

	/**
	 * Sets the destination z position of the football.
	 * @param destinationZ the destination z position that is going to be updated to. 
	 */
	public void setDestinationZ(int destinationZ) {
		this.destinationZ = destinationZ;
	}
	
}
