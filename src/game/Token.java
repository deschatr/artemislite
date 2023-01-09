/**
 * 
 */
package game;

/**
 * @author Nicolas Deschatrettes 40343717
 * implements a token component with movement
 */
public class Token extends Component {
	Square square; // Square where the token currently is
	Player player; // Player the token belongs to
	int moves; // moves left to execute
	
	/**
	 * constructor
	 * @param name
	 */
	public Token(String name) {
		super(name);
	}
	
	/**
	 * constructor
	 * @param name name of the token
	 * @param player player the token belongs to
	 * @param square square the token is located on
	 */
	public Token(String name, Player player, Square square) {
		super(name);
		this.player = player;
		this.square = square;
	}
	
	/**
	 * returns the current square the token is on
	 * @return square the token is located on
	 */
	public Square getSquare() {
		return square;
	}
	
	/**
	 * sets the token on a square
	 * @param square square the token is located on
	 */
	public void setSquare(Square square) {
		this.square = square;
	}
	
	/**
	 * returns the owner of the token
	 * @return player the token belongs to
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * sets the owner of the token
	 * @param player player the token belongs to
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * returns the number of moves left for the token
	 * @return number of moves left
	 */
	public int getMoves() {
		return moves;
	}
	
	/**
	 * sets the number of moves left for the token
	 * @param moves number of moves left
	 */
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	/**
	 * moves the token by a given number of squares
	 * @param board the board the token is on
	 * @param totalMoves the total number of moves to execute
	 * @throws IllegalArgumentException
	 */
	public void moveBy(OneDimensionalBoard board, int totalMoves) throws IllegalArgumentException {
		if (board == null) {
			throw new IllegalArgumentException("board is null"); // if the board is null , throw an exception 
		} else {
			moves = totalMoves; // starts the count from totalMoves to 0
			while (moves != 0) {
				// loop for forward moves
				while (moves > 0) {
					if (board.hasNextSquare(square)) square = board.nextSquare(square); // move to the next square
					moves--; // decrement the number of moves left
					if (moves == 0) {
						square.executeOnLanding(player); // if it is the last move, land on the square
					} else {
						square.executeOnPassing(player); // if it is not the last move, pass over the square
					}
				}
				// loop for backward moves
				while (moves < 0) {
					if (board.hasPreviousSquare(square)) square = board.previousSquare(square); // move to the previous square
					moves++; // decrement the number of moves left
					if (moves == 0) {
						square.executeOnLanding(player); // if it is the last move, land on the square
					} else {
						square.executeOnPassing(player); // if it is not the last move, pass over the square
					}
				}
			}
		}
	}
}