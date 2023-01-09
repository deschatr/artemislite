package artemislite;

import game.Square;
import game.Player;

/**
 * implementation of a blank square
 * @author Nicolas Deschatrettes 40343717
 *
 */
public class BlankSquare extends Square {

	/**
	 * constructor
	 * @param name
	 */
	public BlankSquare(String name) {
		super(name);
	}
	
	@Override
	public void executeOnLanding(Player player) {
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		userInterface.println(player,"You have landed on the blank square.");
		userInterface.println(player,"Nothing happens here...");
	}
	
}
