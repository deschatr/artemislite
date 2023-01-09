/**
 * 
 */
package game;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Nicolas Deschatrettes 40343717
 * implements a 1D circular board
 */
public class CircularBoard extends Component implements OneDimensionalBoard {

	// array list storing the board circuit
	private LinkedList<Square> squares = new LinkedList<>();
	
	/**
	 * constructor 
	 * @param name
	 */
	public CircularBoard(String name) {
		super(name);
	}
	
	/**
	 * tests if the board is empty or not
	 * @return true if the board does not contain any square, false otherwise
	 */
	public boolean isEmpty() {
		return squares.isEmpty();
	}
	
	/**
	 * returns the number of squares on the board
	 * @return the number of square on the board
	 */
	public int size() {
		return squares.size();
	}
	
	/**
	 * tests if a square is on the board
	 * @return true if the square is on the board, false otherwise 
	 */
	public boolean containsSquare(Square square) {
		return squares.contains(square);
		
	}
	
	/**
	 * adds a space to the board
	 * @param Square square to add to the board
	 */
	public void addSquare(Square square) {
		squares.add(square);
	}
	
	public void clear() {
		squares.clear();
	}
	
	/**
	 * tests is a square has a next square
	 * @return true if the square has a next square
	 */
	public boolean hasNextSquare(Square square) {
		return squares.contains(square);
	}
	
	/**
	 * returns the next square to the given square
	 * @param Square
	 * @return Square
	 */
	public Square nextSquare(Square square) {
		ListIterator<Square> squareIterator;
		try {
			squareIterator = squares.listIterator(squares.indexOf(square));
			squareIterator.next();
		
			if (squareIterator.hasNext()) {
				return squareIterator.next();
			} else {
				return squares.getFirst();
			}
		} catch (IndexOutOfBoundsException exception) {
			return null; // square not on the board
		}
	}
	
	/**
	 * test if a square has a previous square
	 * @param square the square to test
	 * @return true if the square has a previous square
	 */
	public boolean hasPreviousSquare(Square square) {
		return squares.contains(square);
	}
	
	/**
	 * returns the first previous square to the given square
	 * @param Square
	 * @return Square
	 */
	public Square previousSquare(Square square) {
		ListIterator<Square> squareIterator;
		try {
			squareIterator = squares.listIterator(squares.indexOf(square));
		
			if (squareIterator.hasPrevious()) {
				return squareIterator.previous();
			} else {
				return squares.getLast();
			}
		} catch (IndexOutOfBoundsException exception) {
			return null; // square not on the board
		}
		
	}
	
	/**
	 * returns the first square on the board
	 * @return the first square on the board
	 * @throws IndexOutOfBoundsException
	 */
	public Square firstSquare() {
		try {
			return squares.getFirst();
		} catch (NoSuchElementException exception) {
			return null;
		} catch (NullPointerException exception) {
			return null;
		}
	}
	
	/**
	 * returns the last square on the board
	 * @return the last square on the board
	 * @throws IndexOutOfBoundsException
	 */
	public Square lastSquare() {
		try {
			return squares.getLast();
		} catch (NoSuchElementException exception) {
			return null;
		} catch (NullPointerException exception) {
			return null;
		}
	}

}