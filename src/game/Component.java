package game;

/**
 * @author Nicolas Deschatrettes 40343717
 * Base class for game components
 */
public abstract class Component {

	// Component name
	private String name;
	
	/**
	 * Constructor for component
	 * @param name
	 */
	public Component(String name) {
		this.name = name;
	}
	
	/**
	 * Gets name
	 * @return name of component
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets name
	 * @param name of component
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}