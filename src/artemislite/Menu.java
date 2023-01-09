/**
 * 
 */
package artemislite;

import java.util.TreeMap;
import java.util.Comparator;

/**
 * @author Nicolas
 *
 */
public class Menu {
	
	private TreeMap<String,MenuItem> items = new TreeMap<String,MenuItem>(new MenuItemComparator<String>());
	private String currentKey;
	
	public class MenuItemComparator<Text> implements Comparator<Text> {
	
	    public boolean isStringInteger(String string) {
	    	// Check if the string is empty
	        if(string.isEmpty()) return false;
	        for(int i = 0; i < string.length(); i++) {
	        	// Check for negative Integers
	            if(i == 0 && string.charAt(i) == '-') {
	                if(string.length() == 1) return false;
	                else continue;
	            }
	            if(Character.digit(string.charAt(i),10) < 0) return false;
	        }
	        return true;
	    }
		
		@Override
		public int compare(Text key1, Text key2) {
			int result = 0;
			if (key1 instanceof String && key2 instanceof String) {
				if (isStringInteger((String)key1)) {
					if (isStringInteger((String)key2)) {
						result = Integer.parseInt((String)key1)-Integer.parseInt((String)key2);
					} else {
						result = -1;
					}
				} else if (isStringInteger((String)key2)) {
					result = 1;
				} else {
					result = ((String)key1).compareToIgnoreCase((String)key2);
				}
			}
			return result;
		}
	}
	
	public Menu() {
		currentKey = null;
	}
	
	public void addItem(String key, MenuItem item) throws IllegalArgumentException {
		if (key == null || items.containsKey(key)) throw new IllegalArgumentException("MenuItem key already exists.");
		items.put(key,item);
	}
	
	public String nextKey() throws IndexOutOfBoundsException {
		if (items.isEmpty()) throw new IndexOutOfBoundsException();
		if (currentKey == null) {
			currentKey = items.firstKey();
		} else {
			currentKey = items.higherKey(currentKey);
		}
		return currentKey;
	}
	
	public boolean hasNextKey() {
		if (items.isEmpty() || currentKey == items.lastKey()) return false;
		return true;
	}
	
	public MenuItem getItem(String key) throws IndexOutOfBoundsException {
		if (!items.containsKey(key)) throw new IndexOutOfBoundsException();
		return items.get(key);
	}
	
	public boolean containsKey(String key) {
		return items.containsKey(key);
	}
	
	public void resetKey() {
		currentKey = null;
	}

}
