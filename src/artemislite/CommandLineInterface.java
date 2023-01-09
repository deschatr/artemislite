package artemislite;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Conor McMahon 40057128
 * class implementing the interface between the user and ArtemisLiteUserInterface
 */
public class CommandLineInterface {
	
	private static int TEXT_WIDTH = 0;
	private int currentCharCount = 0;
	
	private InputStream inputStream;
	private OutputStream outputStream;
	private PrintStream printStream;
	private BufferedReader bufferedReader;

	/**
	 * constructor
	 * @param inputStream input stream for user input
	 * @param outputStream output stream for user output
	 */
	public CommandLineInterface(InputStream inputStream, OutputStream outputStream) {
		
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
		printStream = new PrintStream(this.outputStream);
	}
	
	/**
	 * print implementation
	 * @param text test to print
	 */
	public void print(String text) {
		
		if (TEXT_WIDTH == 0) {
			printStream.print(text);
		} else {
		// 	processing leading zeros at beginning of line
			if (currentCharCount == 0) {
				int leadingZeros = text.length() - text.stripLeading().length();
				for (int currentCharCount = 1; currentCharCount <= leadingZeros; currentCharCount++)
					printStream.print(" ");
			}
		
			String[] splitText = text.split(" ");
		
			for (String word : splitText) {
				if (currentCharCount + 1 + word.length() > TEXT_WIDTH) {
					printStream.println();
					currentCharCount = 0;
				} 
				if (currentCharCount>0) {
					printStream.print(" ");
					currentCharCount++;
				}
				printStream.print(word);
				currentCharCount+=word.length();
			}
		}
	}
	
	/**
	 * println implementation
	 * @param text text to print
	 */
	public void println(String text) {
		print(text);
		printStream.println();
		currentCharCount = 0;
	}

	/**
	 * captures the user input
	 * @return String with user input
	 */
	public String nextLine() {
		
		try {
			String result = bufferedReader.readLine();
			currentCharCount = 0;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
