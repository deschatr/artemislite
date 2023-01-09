package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTest {
	
	Dice dice;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		dice = new Dice("dice");
	}

	@Test
	void testDice() {
		assertEquals("dice",dice.getName());
		assertEquals(6,dice.getSides());
	}

	@Test
	void testDiceInt() {
		dice = new Dice("DICE",12);
		assertEquals("DICE",dice.getName());
		assertEquals(12,dice.getSides());
	}

	@Test
	void testGetRoll() {
		int lastRoll;
		for (int i=0; i<100; i++) {
			lastRoll = dice.roll();
			assertEquals(lastRoll,dice.getLastRoll());
		}
	}

	@Test
	void testDistribution() {
		double[] rolls = new double[8];
		
		for (int i=0; i<1200; i++) {
			rolls[dice.roll()]++;
		}
		
		double[] stats = { 0, 200, 200, 200, 200, 200, 200, 0 };

		assertArrayEquals(stats,rolls,40);
	}

	@Test
	void testSetSides() {
		dice.setSides(7);
		assertEquals(7,dice.getSides());
	}
	
	@Test
	void testSetSidesInvalid() {
		assertThrows(IllegalArgumentException.class, () -> { dice.setSides(0); });
	}
	

}
