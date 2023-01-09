/**
 * 
 */
package artemislite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import game.Player;

/**
 * @author Nicolas
 *
 */

public class ArtemisLiteUserInterface {
	
	private static final ArtemisLiteUserInterface instance = new ArtemisLiteUserInterface();
	private CommandLineInterface cli;
	
	private ArtemisLiteUserInterface() {
		cli = new CommandLineInterface(System.in,System.out);
	}
	
	public static ArtemisLiteUserInterface getInstance() {
		return instance;
	}
	
	public void newLine() {
		cli.println("");
	}
	
	public String getAnswer(String regex, String errorMsg) {
		String text;
		Pattern pattern;
		Matcher matcher;
		boolean matched;
		do {
			cli.print("> ");
			text = cli.nextLine().strip();
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(text);
			matched = matcher.find();
			if (!matched) cli.println(errorMsg);
		} while (!matched);
		return text;
	}

	public void println(String text) {
		cli.println(text);
	}
	
	public void println(Player player, String text) {
		cli.println(((ArtemisLitePlayer)player).getNameWithAgency() + ", " + text);
	}
	
	public void printFile(String filename) {
		String line;
		File file = new File(filename);
		try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((line = bufferedReader.readLine()) != null) {
				cli.println(line);
			}
		} catch (IOException exception) {
			cli.println("Error reading file: " + filename);
		}
	}
	
	public void waitForEnter() {
		cli.println("");
		cli.print("Press Enter to continue...");
		cli.nextLine();
		cli.println("");
	}
	
	public String getInput() {
		cli.print("> ");
		return cli.nextLine().strip();
	}
	
	public String getInput(Player player) {
		cli.print(((ArtemisLitePlayer)player).getNameWithAgency() + "> ");
		return cli.nextLine().strip();
	}
	
	public String askInput(String text, String regex) {
		String result;
		Pattern pattern;
		Matcher matcher;
		boolean matched;
		do {
			println(text);
			result = getInput();
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(result);
			matched = matcher.find();
			if (!matched) cli.println("Invalid input... Please try again.\n");
		} while (!matched);
		return result;
	}

	
	public String askInput(Player player, String text, String regex) {
		String result;
		Pattern pattern;
		Matcher matcher;
		boolean matched;
		do {
			println(player,text);
			result = getInput(player);
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(result);
			matched = matcher.find();
			if (!matched) cli.println("Invalid input... Please try again.\n");
		} while (!matched);
		return result;
	}
	
	private String formatKey(String key, int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int charCount = key.length(); charCount <= length; charCount++)
			stringBuilder.append(" ");
		stringBuilder.append(key);
		return stringBuilder.toString();
	}
	
	public void printMenu(Menu menu) {
		
		MenuItem menuItem;
		String currentKey;
		
		menu.resetKey();
		
		while (menu.hasNextKey()) {
			currentKey = menu.nextKey();
			menuItem = menu.getItem(currentKey);
			if (menuItem.isEnabled()) {
				cli.println(formatKey(currentKey,2) + " : " + menuItem.getValue());
			} else {
				cli.println(formatKey("-",2) + " : " + menuItem.getValue());
			}
		}
		cli.println("");
	}
	
	public void printMenu(Player player, Menu menu) {
		printMenu(menu);
	}
	
	public String getMenuSelection(Menu menu) {
		String userInput;
		cli.print("> ");
		userInput = cli.nextLine().strip();
		if (!menu.containsKey(userInput)) return null;
		return userInput;
	}
	
	public String getMenuSelection(Player player, Menu menu) {
		String userInput;
		cli.print(((ArtemisLitePlayer)player).getNameWithAgency() + "> ");
		userInput = cli.nextLine().strip();
		if (!menu.containsKey(userInput)) return null;
		if (!menu.getItem(userInput).isEnabled()) return null;
		return userInput;
	}
	
	public String askMenu(Menu menu) {
		String result;
		do {
			printMenu(menu);
			result = getMenuSelection(menu);
			if (result == null) cli.println("Invalid input... Please try again.\n");
		} while (result == null);
		return result; 
	}
	
	public String askMenu(Player player, Menu menu) {
		String result;
		do {
			printMenu(player, menu);
			result = getMenuSelection(player,menu);
			if (result == null) cli.println("Invalid input... Please try again.\n");
		} while (result == null);
		return result; 
	}
	
}
