/**
 * 
 */
package artemislite;

/**
 * @author Nicolas
 *
 */
public class MenuItem {

	private String value;
	private boolean enabled;

	public MenuItem(String value) {
		this.value = value;
		this.enabled = true;
	}
	
	public MenuItem(String value, boolean enabled) {
		this.value = value;
		this.enabled = enabled;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
}
