package game;

import java.util.Random;

/**
 * @author Aidan Campbell 40054873
 *
 */
public class Dice extends Component{
	
	// number of sides of the die
	private int sides;
	// result of the last roll
	private int lastRoll;
	// the class will use the Random class
	private Random random = new Random();

	/**
	 * Default constructor, dice with 6 sides
	 * @param name
	 */
	public Dice(String name) {
		super(name);
		setSides(6);
	}
	
	/**
	 * Constructor for a dice with given number of sides
	 * @param name
	 * @param sides
	 */
	public Dice(String name, int sides) {
		super(name);
		setSides(sides);
	}
	
	/**
	 * returns a random number between 1 and sides
	 * @return
	 */
	public int roll() {
		// generate random number and store in lastRoll
		lastRoll = random.nextInt(sides)+1;
		return lastRoll;
	}
	
	/**
	 * returns the last roll of the dice
	 * @return
	 */
	public int getLastRoll() {
		return lastRoll;
	}
	
	/**
	 * sets the number of sides for the dice
	 * @param sides
	 */
	public void setSides(int sides) {
		if (sides > 0) {
			this.sides = sides;
		} else {
			throw new IllegalArgumentException("Dice sides: " + sides);
		}
	}
	
	/**
	 * returns the number of sides of the dice
	 * @return
	 */
	public int getSides() {
		return sides;
	}

}
