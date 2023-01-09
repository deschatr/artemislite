package game;

import java.util.ListIterator;

/**
 * @author Nicolas Deschatrettes 40343717
 * class extending the base class Game, implements methods for turn based games
 * the main execution method becomes executeTurn(Player)
 */
public abstract class TurnGame extends Game {

	private int maxTurnCount = 0x7FFFFFFF;
	private int turnCount = 0;
	private boolean reset = false;

	public TurnGame(String name) {
		super(name);
	}
	
	public abstract void executeTurn(Player player);
	
	public void execute() {

		ListIterator<Player> playerIterator;
		Player currentPlayer;

		if (countPlayers() == 0) return;

		playerIterator = playerListIterator();
		turnCount = 0;

		while (turnCount < maxTurnCount && !isQuitting() && !isFinishing()) {
			
			currentPlayer = playerIterator.next();

			executeTurn(currentPlayer);
			
			if (!playerIterator.hasNext() && !isQuitting() && !isFinishing()) {
				if (reset) {
					turnCount = 0;
					reset=false;
				} else {
					turnCount++;
				}
				playerIterator = playerListIterator();
			}
			
		}
	}
		
	public int getMaxTurnCount() {
		return maxTurnCount;
	}
	
	public void setMaxTurnCount(int maxTurnCount) {
		this.maxTurnCount = maxTurnCount;
	}
	
	public int getTurnCount() {
		return turnCount;
	}
	
	public void resetCount() {
		if (isRunning()) {
			reset = true;
		} else {
			turnCount = 0;
			reset = false;
		}
	}
}