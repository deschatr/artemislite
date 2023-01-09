/**
 * 
 */
package artemislite;

/**
 * @author Nicolas Deschatrettes 40343717
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		
		userInterface.printFile("introduction.txt");
		userInterface.println("");

		ArtemisLiteGame.getInstance().run();

	}
	
}
