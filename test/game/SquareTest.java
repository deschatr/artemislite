package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SquareTest {

	static Player player;
	static MySquare square;
	
	static class MySquare extends Square {
		public MySquare(String name) { super(name); }
		public void executeOnPassing(Player player) {
			super.setName("PASS");
			super.executeOnPassing(player);
		}
		public void executeOnLanding(Player player) { 
			super.setName("LAND");
			super.executeOnLanding(player);
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		player = new Player("Alan");
		square = new MySquare("Square 1");
	}

	@Test
	void testSetName() {
		square.setName("SQUARE 1");
		assertEquals("SQUARE 1",square.getName());
	}

	@Test
	void testGetName() {
		assertEquals("Square 1",square.getName());
	}
	
	@Test
	void testExecuteOnPassing() {
		square.executeOnPassing(null);
		assertEquals("PASS",square.getName());
	}
	
	@Test
	void testExecuteOnLanding() {
		square.executeOnLanding(null);
		assertEquals("LAND",square.getName());
	}

}
