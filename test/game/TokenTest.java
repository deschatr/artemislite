package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenTest {
	
	static class MySquare extends Square {
		public MySquare(String name) { super(name); }
		@Override
		public void executeOnPassing(Player player) {
			super.setName("PASS");
			super.executeOnPassing(player);
		}
		@Override
		public void executeOnLanding(Player player) { 
			super.setName("LAND");
			super.executeOnLanding(player);
		}
	}
	
	CircularBoard testBoard = new CircularBoard("Test Board");
	MySquare[] squares = new MySquare[5];
	Token testToken;
	Player testPlayer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		squares[0] = new MySquare("Square1");
		squares[1] = new MySquare("Square2");
		squares[2] = new MySquare("Square3");
		squares[3] = new MySquare("Square4");
		squares[4] = new MySquare("Square5");
		for (MySquare square : squares) testBoard.addSquare(square);
		testPlayer = new Player("Test player");
		
	}

	@Test
	void testTokenString() {
		testToken = new Token("Test token");
		assertEquals("Test token",testToken.getName());
	}

	@Test
	void testTokenStringPlayerSquare() {
		testToken = new Token("Test token",testPlayer,testBoard.firstSquare());
		assertEquals("Test token",testToken.getName());
		assertEquals(testPlayer,testToken.getPlayer());
		assertEquals(testBoard.firstSquare(),testToken.getSquare());
	}

	@Test
	void testGetSetSquare() {
		testToken = new Token("Test token");
		assertNull(testToken.getSquare());
		testToken.setSquare(squares[0]);
		assertEquals(squares[0],testToken.getSquare());
	}

	@Test
	void testGetSetPlayer() {
		testToken = new Token("Test token");
		assertNull(testToken.getPlayer());
		testToken.setPlayer(testPlayer);
		assertEquals(testPlayer,testToken.getPlayer());
	}

	@Test
	void testGetMoves() {
		testToken = new Token("Test token");
		assertEquals(0,testToken.getMoves());
		testToken.setMoves(10);
		assertEquals(10,testToken.getMoves());
	}

	@Test
	void testMoveBy() {
		testToken = new Token("Test token",testPlayer,testBoard.firstSquare());
		testToken.moveBy(testBoard, 2);
		assertEquals(squares[2],testToken.getSquare());
		assertEquals("PASS",squares[1].getName());
		assertEquals("LAND",squares[2].getName());
		testToken.moveBy(testBoard, 3);
		assertEquals(squares[0],testToken.getSquare());
		assertEquals("PASS",squares[3].getName());
		assertEquals("PASS",squares[4].getName());
		assertEquals("LAND",squares[0].getName());
		testToken.moveBy(testBoard, -4);
		assertEquals(squares[1],testToken.getSquare());
		assertEquals("PASS",squares[4].getName());
		assertEquals("PASS",squares[3].getName());
		assertEquals("PASS",squares[2].getName());
		assertEquals("LAND",squares[1].getName());
	}
	
	@Test
	void testMoveByException() {
		testToken = new Token("Test token",testPlayer,testBoard.firstSquare());
		assertThrows(IllegalArgumentException.class,() -> { testToken.moveBy(null,2); } );
	}

}
