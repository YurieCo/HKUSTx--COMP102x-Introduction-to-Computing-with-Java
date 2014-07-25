package comp102x.assignment;

/**
 * The GameRecord class represents a record for the a game play, including the name of the player, the level of the game play and the score obtained.
 */
public class GameRecord {
	
	/** The name of the player. **/
	private String name;
	
	/** The level of the game play. **/
	private int level;
	
	/** The score of the game play. **/
	private int score;
	
	/**
	 * Constructs a game record with a certain name, level and score.
	 * @param name the name of the player.
	 * @param level the level of the game play.
	 * @param score the score of the game play.
	 */
	public GameRecord(String name, int level, int score) {
		
		this.name = name;
		this.level = level;
		this.score = score;
	}

	/**
	 * Returns the name of the player in the record.
	 * @return the name of the player in the record.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player in the record.
	 * @param name the name of the player to be updated to.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the level of the game play in the record.
	 * @return the level of the game play in the record.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level of the game play in the record.
	 * @param level the level of the game play to be updated to.
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Returns the score of the game play in the record.
	 * @return the score of the game play in the record.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score of the game play in the record.
	 * @param score the score of the game play to be updated to.
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
