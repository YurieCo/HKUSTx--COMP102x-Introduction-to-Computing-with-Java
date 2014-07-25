package comp102x.assignment;

/**
 * The Arrow class represents a arrow that can be used to shoot the goals.
 */
public class Arrow {
	
	/** The current x position of the arrow in the space. Left for positioning the arrow in future extension. */
	int xPos;
	
	/** The current y position of the arrow in the space. Left for positioning the arrow in future extension. */
	int yPos;
	
	/** The current z position of the arrow in the space. Left for positioning the arrow in future extension. */
	int zPos;
	
	/** The "up-down" angle for the arrow.*/
	double tilt;
	
	/** The "left-right" angle for the arrow.*/
	double pan;
	
	/**
	 * Returns the x position of the arrow.
	 * @return the x position of the arrow.
	 */
	public int getxPos() {
		return xPos;
	}
	
	/**
	 * Sets the x position of the arrow.
	 * @param xPos the x position of the arrow to be updated to.
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Returns the y position of the arrow.
	 * @return the y position of the arrow.
	 */
	public int getyPos() {
		return yPos;
	}
	
	/**
	 * Sets the y position of the arrow.
	 * @param yPos the y position of the arrow to be updated to.
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * Returns the z position of the arrow.
	 * @return the z position of the arrow.
	 */
	public int getzPos() {
		return zPos;
	}
	
	/**
	 * Sets the z position of the arrow.
	 * @param zPos the z position of the arrow to be updated to.
	 */
	public void setzPos(int zPos) {
		this.zPos = zPos;
	}
	
	/**
	 * Returns the tilt angle of the arrow.
	 * @return the tilt angle of the arrow.
	 */
	public double getTilt() {
		return tilt;
	}
	
	/**
	 * Sets the tile angle of the arrow.
	 * @param tilt the tilt angle of the arrow to be updated to.
	 */
	public void setTilt(double tilt) {
		this.tilt = tilt;
	}
	
	/**
	 * Returns the pan angle of the arrow.
	 * @return the pan angle of the arrow.
	 */
	public double getPan() {
		return pan;
	}
	
	/**
	 * Sets the pan angle of the arrow.
	 * @param pan the pan angle of the arrow to be updated to.
	 */
	public void setPan(double pan) {
		this.pan = pan;
	}
	
}
