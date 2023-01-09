package artemislite;

import game.Player;
import game.Square;

/**
 * implementation of a start square, awarding resources when a player passes over it or lands on it
 * @author Nicolas Deschatrettes 40343717
 *
 */
public class StartSquare extends Square {

	private int award; // the award for the players
	
	/**
	 * constructor
	 * @param name
	 * @param award
	 */
	public StartSquare(String name, int award) {
		super(name);
		this.award = award;
	}
	
	/**
	 * overriding executeOnLanding to define behaviour when a player lands
	 */
	@Override
	public void executeOnLanding(Player player) throws IllegalArgumentException {
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		ArtemisLiteGame artemisLiteGame = ArtemisLiteGame.getInstance();
		userInterface.println(player,"As you land on the start square, you receive " + artemisLiteGame.formatResources(award) + " of funding.");
		((ArtemisLitePlayer)player).addResources(award);
		artemisLiteGame.displayResources(player);

	}
	
	/**
	 * overriding executeOnPassing to define behaviour when a player passes
	 */
	@Override
	public void executeOnPassing(Player player) {
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		ArtemisLiteGame artemisLiteGame = ArtemisLiteGame.getInstance();
		((ArtemisLitePlayer)player).addResources(award);
		userInterface.println(player,"As you pass by the start square, you receive " + artemisLiteGame.formatResources(award) + " of funding.");
		artemisLiteGame.displayResources(player);
	}
	
	/**
	 * sets the award
	 * @param award
	 */
	public void setAward(int award) {
		this.award = award;
	}
	
	/**
	 * returns the award
	 * @return
	 */
	public int getAward() {
		return award;
	}
	
}
