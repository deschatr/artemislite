package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComponentTest {

	public static class TestComponent extends Component {
		public TestComponent(String name) { super(name); }
	}
	
	TestComponent testComponent;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testName() {
		testComponent = new TestComponent("name");
		assertEquals("name",testComponent.getName());
		
		testComponent = new TestComponent(null);
		assertNull(testComponent.getName());
	}
	
	@Test
	void testGetName() {
		testComponent = new TestComponent("name");
		assertEquals("name",testComponent.getName());
	}

	@Test
	void testSetName() {
		testComponent = new TestComponent("");
		
		testComponent.setName("NOM");
		assertEquals("NOM",testComponent.getName());
		
		testComponent.setName(null);
		assertNull(testComponent.getName());
	}


}
