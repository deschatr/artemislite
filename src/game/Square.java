package game;

/**
 * @author Nicolas Deschatrettes 40343717
 * implementation of a base square class, this square does nothing
 */
public class Square extends Component {
	
	/**
	 * constructor
	 * @param name square name
	 */
	public Square(String name) {
		super(name);
	}
	
	/**
	 * executes the actions when a player lands on the square
	 * @param player
	 */
	public void executeOnLanding(Player player) { }
	
	/**
	 * exeutes the actions when a player passes over the square
	 * @param player
	 */
	public void executeOnPassing(Player player) { }

}