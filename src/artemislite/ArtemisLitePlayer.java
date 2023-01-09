package artemislite;

import game.Player;
import game.Square;
import game.Token;

/**
 * @author Nicolas
 * Class extending the player class to include ArtemisLite specific features
 */
public class ArtemisLitePlayer extends Player {

	private Token token; // player's token
	private int resources; // player's resources
	private SpaceAgency spaceAgency; // player's space agency
	
	/**
	 * Constructor
	 * @param name
	 * @param square
	 * @param spaceAgency 
	 * @param resources
	 */
	public ArtemisLitePlayer(String name, Square square, SpaceAgency spaceAgency, int resources) {
		super(name);
		token = new Token(name,this,square);
		this.spaceAgency = spaceAgency;
		this.resources = resources;
	}

	public ArtemisLitePlayer(String name, Token token, SpaceAgency spaceAgency, int resources) {
		super(name);
		this.token = token;
		this.spaceAgency = spaceAgency;
		this.resources = resources;
	}
	
	/**
	 * returns the current resource balance
	 * @return
	 */
	public int getResources() {
		return resources;
	}
	
	/**
	 * sets the resource balance
	 * @param resources
	 */
	public void setResources(int resources) {
		this.resources = resources;
	}
	
	/**
	 * adds resources to the player's balance
	 * @param resources
	 */
	public void addResources(int resources) {
		this.resources += resources;
	}
	
	/**
	 * substracts resources from the player's balance
	 * @param resources
	 */
	public void subResources(int resources) {
		this.resources -= resources;
	}
	
	/**
	 * returns the player's space agency
	 * @return
	 */
	public SpaceAgency getSpaceAgency() {
		return spaceAgency;
	}
	
	/**
	 * sets the player's space agency
	 * @param spaceAgency
	 */
	public void setSpaceAgency(SpaceAgency spaceAgency) {
		this.spaceAgency = spaceAgency;
	}
	
	public String getNameWithAgency() {
		StringBuilder sb = new StringBuilder(getName());
		sb.append(" (");
		sb.append( getSpaceAgency().name());
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * returns the player's token
	 * @return
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * sets the player's token
	 * @param token
	 */
	public void setToken(Token token) {
		this.token = token;
	}
	
}
