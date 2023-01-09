package game;

public interface OneDimensionalBoard {

	public void addSquare(Square square);
	public void clear();
	public boolean isEmpty();
	public int size();
	public boolean containsSquare(Square square);
	
	public boolean hasNextSquare(Square square);
	public Square nextSquare(Square square);
	public boolean hasPreviousSquare(Square square);
	public Square previousSquare(Square square);
	
	public Square firstSquare();
	public Square lastSquare();
	
}