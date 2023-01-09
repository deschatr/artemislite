package game;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * @author Ronan 
 * implementation of the base class for a game
 * handles players, quitting and repeating of the game
 */
public abstract class Game extends Component {
	private ArrayList<Player> players = new ArrayList<>();
	private boolean repeat = false, quit = false, run = false, finish = false;

	public Game(String name) {
		super(name);
	}
	
	/**
	 * method for game implementations to execute initialization
	 */
	public abstract void initialize();
	
	/**
	 * method for game implementation to execute the game
	 */
	public abstract void execute();
	
	/**
	 * method for game implementation to execute finalization
	 */
	public abstract void finalize();
	
	/**
	 * runs the game
	 */
	public void run() {
			
		do {
			
			run = true;
			
			// executes the game initilization
			initialize();
			
			// executes the game,will not execute if quit is set
			if (!quit) execute();
			
			// executes the game finalization, will execute even if quit is set
			finalize();
			
			run = false;
			
		} while (repeat && !quit); // loop if repeat is set and quit is not set
		
	}
	
	/**
	 * adds a player to the game
	 * @param player player to be added to the game
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void clearPlayers() {
		players.clear();
	}
	
	/**
	 * gives access to the list of players through an iterator
	 * @return a ListIterator for the list of players
	 */
	public ListIterator<Player> playerListIterator() {
		return players.listIterator();
	}
	
	/**
	 * gives access to the list of players through an iterator
	 * @param player player to start the iterator at
	 * @return a ListIterator for the list of players
	 * @throws IllegalArgumentException
	 */
	public ListIterator<Player> playerListIterator(Player player) throws IllegalArgumentException {
		int playerIndex = players.indexOf(player);
		// if there isn't any players, an exception is thrown
		if (playerIndex<0) throw new IllegalArgumentException();
		return players.listIterator(players.indexOf(player));
	}
	
	/**
	 * counts the number of players
	 * @return the number of players
	 */
	public int countPlayers() {
		return players.size();
	}
	
	/**
	 * test if a player is part of the game
	 * @param player
	 * @return
	 */
	public boolean isPlayer(Player player) {
		return players.contains(player);
	}
	
	/**
	 * sets the repeat feature of the gam, the game will repeat after ending
	 */
	public void setRepeat() {
		this.repeat = true;
	}
	
	/**
	 * resets the repeat feature, the game will not repeat after ending
	 */
	public void resetRepeat() {
		this.repeat = false;
	}
	
	/**
	 * returns the status of the repeat feature
	 * @return returns true of the repeat feature is set
	 */
	public boolean isRepeating() {
		return repeat;
	}
	
	/**
	 * sets the quit feature, the game will initialize, finalize and quit. It will not execute.
	 */
	public void setQuit() {
		quit=true;
	}
	
	/**
	 * resets the quit feature, the game will initialize, execute and finalize.
	 */
	public void resetQuit() {
		quit = false;
	}
	
	/**
	 * returns the status of the quit feature
	 * @return returns true if the qui feature is set
	 */
	public boolean isQuitting() {
		return quit;
	}
	
	public boolean isRunning() {
		return run;
	}
	
	public boolean isFinishing() {
		return finish;
	}
	
	public void setFinish() {
		finish = true;
	}
	
	public void resetFinish() {
		finish = false;
	}
	
}