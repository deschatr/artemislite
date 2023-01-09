package artemislite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import game.CircularBoard;
import game.Dice;
import game.Player;
import game.Square;
import game.TurnGame;

/**
 * @author Aidan Campbell 40057128
 * @author Conor Mc Mahon
 * @author Nicolas Deschatrettes 40343717
 * @author Ronan Lavery
 *
 */
public class ArtemisLiteGame extends TurnGame {
	
	private static int MAXTURNCOUNT = 100;
	
	private static int DICE_NUMBER = 2;
	private static int DICE_SIDES  = 6;
	
	private static String[] PROGRAM_NAME		= { "Exploration",
													"Landing Systems",
													"Lunar Space Station",
													"Launch System and Spaceship" };
	
	private static String[][] PROJECT_NAME		= {	{ "Commercial Lunar Payload Services", "Volatiles investigating Polar Exploration Rover" },
													{ "Exploration Extravehicular Activity System", "Human Landing System", "Lunar Terran Vehicle" },
													{ "Power and Propulsion Element", "Habitation and Logistics Outpost", "Deep Space Logistics" },
													{ "Space Launch System Block 1", "Orion Crew Vehicle" } };
	
	private static int[][][] PROJECT_COSTS	= 	{ { { 5, 5, 5, 5, 5}, {5, 5, 5, 5, 5} },                                  // total 50
													{ {8, 8, 8, 8, 8}, {8, 8, 8, 8, 8}, {8, 8, 8, 8, 8} },                // total 120
													{ {10, 10, 10, 10, 10}, {10, 10, 10, 10, 10}, {10, 10, 10, 10, 10} }, // total 150
													{ {20, 20, 20, 20, 20}, {20, 20, 20, 20, 20} } };                     // total 200
	
	private static int[][][] PROJECT_FINES	= 	{ { {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1} },
													{ {2, 2, 2, 2, 2}, {2, 2, 2, 2, 2}, {2, 2, 2, 2, 2} },
													{ {3, 3, 3, 3, 3}, {3, 3, 3, 3, 3}, {3, 3, 3, 3, 3} },
													{ {4, 4, 4, 4, 4}, {4, 4, 4, 4, 4} } };
	
	private static int START_FUNDING = 40;
	private static int AWARD_FUNDING = 20;

	private static ArtemisLiteGame instance = new ArtemisLiteGame("ArtemisLite");
	private ArtemisLiteUserInterface userInterface;
	private CircularBoard board;
	private ArrayList<Dice> dice;
	private ArrayList<Program> programs;
			
	private ArtemisLiteGame(String name) {
		super(name);	
	}
	
	public static final ArtemisLiteGame getInstance() {
		return instance;
	}
	
	public void initialize() {
		String menuSelection;
		
		setMaxTurnCount(MAXTURNCOUNT);
		
		createDice();
		createBoard();
		
		setRepeat();
		resetFinish();
		resetQuit();
		
		userInterface = ArtemisLiteUserInterface.getInstance();
		
		Menu mainMenu = new Menu();
		mainMenu.addItem("1", new MenuItem("Start a game"));
		mainMenu.addItem("2", new MenuItem("Read the rules"));
		mainMenu.addItem("x", new MenuItem("Exit"));
		
		userInterface.println("Choose one of the following options:");
		
		do {
						
			menuSelection = userInterface.askMenu(mainMenu);
		
			switch (menuSelection) {
			case "1":
				break;
			case "2":
				executeDisplayRules();
				break;
			case "x":
				setQuit();
			}
		} while (!menuSelection.equals("1") && !isQuitting());
		
		if (!isQuitting()) {
			createPlayers();
			userInterface.waitForEnter();
		}
	}
	
	public void executeTurn(Player player) throws IllegalArgumentException {
		
		if (!(player instanceof ArtemisLitePlayer)) throw new IllegalArgumentException("player is not of ArtemisLitePlayer class");
		
		// rolls dice and move token
		// actions linked to squares will be executed by the squares the token passes over or lands on
		userInterface.println("");
		userInterface.println(player,"You have rolled a " + dice.get(0).roll() + " and a " + dice.get(1).roll()
				+ ", a total of " + (dice.get(0).getLastRoll() + dice.get(1).getLastRoll()) + ".");
		((ArtemisLitePlayer)player).getToken().moveBy(board, dice.get(0).getLastRoll() + dice.get(1).getLastRoll());
		
		// develop a project method call
		if (!isQuitting() && !isFinishing()) executeDevelopProject(player);
		
		// displays end of turn message
		if (!isQuitting() && !isFinishing()) 
			userInterface.println(player, "End of your turn, " + player.getName()
				+ " (" + ((ArtemisLitePlayer)player).getSpaceAgency() + ").");
	}
	
	public void finalize() {
		
		if (isSuccess()) {
			userInterface.println("");
			userInterface.println("Congratulations to all players, all the projects are new ready for launch!");
			userInterface.println("Here is the final state of play:");
			executeDisplayPlayerState();
			userInterface.printFile("conclusion.txt");
			userInterface.println("");
		} else if (isFailure()) {
			userInterface.println("Unfortunately "
					+ ((ArtemisLitePlayer)bankruptPlayer()).getNameWithAgency() + " has gone bankrupt, and the Artemis program has failed...");
			userInterface.println("Here is the final state of play:");
			executeDisplayPlayerState();
			userInterface.println("Better luck next time.");
			userInterface.println("");
		} else if (this.getTurnCount() == MAXTURNCOUNT){
			userInterface.println("The maximum number of turns has been reached...");
			userInterface.println("Here is the final state of play:");
			executeDisplayPlayerState();
			userInterface.println("Better luck next time.");
			userInterface.println("");
		} else {
			userInterface.println("Thanks for playing.");
			userInterface.println("Here is the final state of play:");
			executeDisplayPlayerState();
			userInterface.println("Good Bye.");
			userInterface.println("");
		}
		
		clearPlayers();
		board.clear();
		clearDice();
		
	}
	
	public void executeDisplayRules() {
		userInterface.printFile("rules.txt");
		userInterface.waitForEnter();
	}

	public void executeDisplayPlayerState() {
		
		ListIterator<Player> playerIterator = playerListIterator();
		
		userInterface.println("");
		userInterface.println("Players state : ");
		while (playerIterator.hasNext()) {
			Player player = playerIterator.next();
			userInterface.println("| " + ((ArtemisLitePlayer)player).getNameWithAgency()
				+ ", funding: " + formatResources(((ArtemisLitePlayer)player).getResources()));
			userInterface.println("| Projects:");
			int projectCount = 0;
			for (Program program : programs) {
				ListIterator<Project> projectIterator = program.projectListIterator();
				while (projectIterator.hasNext()) {
					Project project = projectIterator.next();
					if (project.getOwner() == player) {
						userInterface.println("| - " + project.getName() + " (" + project.getPhase().name() + "), Program: " + program.getName());
						projectCount++;
					}
				}
			}
			if (projectCount == 0) userInterface.println("| - None");
			userInterface.println("| Current position: " + formatSquareName(((ArtemisLitePlayer)player).getToken().getSquare()));
			userInterface.println("");
		}
	}
	
	public void executeDisplayBoardState() {
		userInterface.println("Board state:");
		Square square = board.firstSquare();
		do {
			userInterface.println("| " + square.getName());
			if (square instanceof ProjectSquare) {
				Project project = ((ProjectSquare)square).getProject();
				userInterface.println("| Program: " + project.getProgram().getName());
				userInterface.println("| Project: " + project.getName());
				userInterface.println("| Currently in " + project.getPhase().name() + ", " + project.getPhase().description());
				if (project.getOwner() == null) {
					userInterface.println("| This project is available for bidding");
				} else {
					userInterface.println("| Project owned by " + ((ArtemisLitePlayer)project.getOwner()).getNameWithAgency());
				}
			}
			
			ArrayList<Player> playersOnSquare = getPlayersOnSquare(square);
			if (playersOnSquare.size() >0) {
				userInterface.println("| Player currently on this square:");
				ListIterator<Player> playerIterator = playersOnSquare.listIterator();
				while (playerIterator.hasNext())
						userInterface.println("| - " + ((ArtemisLitePlayer)playerIterator.next()).getNameWithAgency());
			}
			
			userInterface.println("");
		} while ((square = board.nextSquare(square)) != board.firstSquare());
	}
	
	public void executeDevelopProject(Player player) {
		Project project;
		ArrayList<Project> candidates = new ArrayList<>();
		Program program;
		ListIterator<Project> projectIterator;
		Menu developMenu;
		String menuSelection;
		int candidateCounter;
		boolean selectable;
		
		do {
			candidates.clear();
			candidateCounter = 0;
			developMenu = new Menu();
			developMenu.addItem("o", new MenuItem("Display the board state"));
			developMenu.addItem("p", new MenuItem("Display the players state"));
			developMenu.addItem("e", new MenuItem("End turn"));
			developMenu.addItem("q", new MenuItem("Quit game"));
			
			ListIterator<Program> programIterator = programs.listIterator();
			while (programIterator.hasNext()) {
				program = programIterator.next();
				projectIterator = program.projectListIterator();
				while (projectIterator.hasNext()) {
					project = projectIterator.next();
					if (project.isOwner(player)) {
						selectable = program.isOwner(player) && !project.isCompleted() && project.cost() <= ((ArtemisLitePlayer)player).getResources();
						candidates.add(project);
						developMenu.addItem(Integer.toString(candidates.size()),
								new MenuItem(project.getName() + " "
										+ (project.isCompleted()? "(completed)" : "(" + project.getPhase().name() + ")")
										+ ", Program: " + project.getProgram().getName()
										+ (project.isCompleted()? "" : ", develop for " + formatResources(project.cost())),selectable));
						if (selectable) candidateCounter++;
					}
				}
			}
			
			do {
				userInterface.println("");
				userInterface.println("Select a project to develop, or one of the menu options:");
				menuSelection = userInterface.askMenu(player,developMenu);
				if (menuSelection.equals("o")) executeDisplayBoardState();
				if (menuSelection.equals("p")) executeDisplayPlayerState();
			} while (menuSelection.equals("o") || menuSelection.equals("p"));
		
			if (!menuSelection.equals("e") && !menuSelection.equals("q")) {
				project = candidates.get(Integer.parseInt(menuSelection)-1);
				((ArtemisLitePlayer)player).subResources(project.cost());
				project.develop();
				displayResources(player);
			}
			
		} while (!menuSelection.equals("e") && !menuSelection.equals("q") && !isSuccess());
		
		if (menuSelection.equals("q")) setQuit();
	}
	
	/*
	 * Methods creating components of the game: menu, dice, board/squares/programs/projects and players.
	 */
	
	private void createDice() {
		dice = new ArrayList<>();
		for (int loop = 0; loop<DICE_NUMBER; loop++) dice.add(new Dice("dice"+(loop+1),DICE_SIDES));
	}
	
	private void clearDice() {
		dice.clear();
	}
	
	private void createBoard() {
		Program program;
		Project project;
		Square square;
		
		programs = new ArrayList<>();
		board = new CircularBoard("ArtemisLite");
		board.addSquare(new StartSquare("Start square", AWARD_FUNDING));
		
		for (int programIndex = 0; programIndex < PROGRAM_NAME.length; programIndex++) {
			//creating the structure
			program = new Program(PROGRAM_NAME[programIndex]);
			
			for (int projectIndex = 0; projectIndex < PROJECT_NAME[programIndex].length; projectIndex++) {
				// creating the project square
				square = new ProjectSquare("Project square");
				// adding the square to the board
				board.addSquare(square);
				// creating the project
				project = new Project(PROJECT_NAME[programIndex][projectIndex],PROJECT_COSTS[programIndex][projectIndex],PROJECT_FINES[programIndex][projectIndex],square);
				// adding the project to the program
				program.addProject(project);
			}
			
			// adding the structure to the ArrayList of programs
			programs.add(program);
			
			// adding blank square after second program
			if (programIndex == 1) board.addSquare(new BlankSquare("Blank square"));
		}
	}
	
	private void createPlayers() {
		String playerName;
		
		// getting the number of players from the user
		userInterface.println("");
		userInterface.println("How many players?");
		String answer = userInterface.askInput("Please enter a number from 2 to 4. (enter q to quit game)","^[2-4,q]$");
		
		if (answer.equals("q")) {
			setQuit();
		} else {
			// getting the number of players from the user answer
			int playerNumber = Integer.parseInt(answer);
			
			// shuffling the space agencies
			ArrayList<SpaceAgency> agencies = new ArrayList<>();
			for (SpaceAgency agency : SpaceAgency.values()) agencies.add(agency);
			Collections.shuffle(agencies);
			ListIterator<SpaceAgency> agencyIterator = agencies.listIterator();
			
			// creating players
			ArtemisLitePlayer player;
			for (int playerIndex = 1; playerIndex<=playerNumber; playerIndex++) {
				userInterface.println("");
				do {
					playerName = userInterface.askInput("Enter the name of player " + playerIndex + ".",".{1,}").trim();
					if (!isPlayerNameAvailable(playerName)) {
						userInterface.println("");
						userInterface.println("This name is already taken, please try again.");
					}					
				} while (!isPlayerNameAvailable(playerName));
				player = new ArtemisLitePlayer(playerName,board.firstSquare(),agencyIterator.next(),START_FUNDING);
				addPlayer(player);
				userInterface.println(player.getName() + " will play as the director of " + ((ArtemisLitePlayer)player).getSpaceAgency().name()
						+ ", the " + ((ArtemisLitePlayer)player).getSpaceAgency().fullName() + ".");
			}
		}
	}
	
	/**
	 * tests if the name is already taken by a player
	 * @param playerName
	 * @return
	 */
	private boolean isPlayerNameAvailable(String playerName) {
		ListIterator<Player> playerIterator = playerListIterator();
		while (playerIterator.hasNext()) {
			if (playerIterator.next().getName().equalsIgnoreCase(playerName))
				return false;
		}
		return true;
	}
	
	public String formatResources(int resources) {
		return "$" + ((Integer)resources).toString() + "M";
	}
	
	public String formatSquareName(Square square) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(square.getName());
		if (square instanceof ProjectSquare) {
			stringBuilder.append(" ");
			stringBuilder.append(((ProjectSquare)square).getProject().getName());
			stringBuilder.append(", program: ");
			stringBuilder.append(((ProjectSquare)square).getProject().getProgram().getName());
		}
		
		return stringBuilder.toString();
	}
	
	public void displayResources(Player player) {
		userInterface.println(player,"Your funding is now at " + formatResources(((ArtemisLitePlayer)player).getResources()));
	}
	
	
	public ArrayList<Player> getPlayersOnSquare(Square square) {
		ArrayList<Player> result = new ArrayList<Player>();
		ListIterator<Player> playerIterator = playerListIterator();
		while (playerIterator.hasNext()) {
			Player player = playerIterator.next();
			if (((ArtemisLitePlayer)player).getToken().getSquare() == square) 
				result.add(player);
		}
		return result;
	}
	
	public boolean isSuccess() {
		for (Program program : programs)
			if (!program.isCompleted())
				return false;
		return true;
	}
	
	public Player bankruptPlayer() {
		ListIterator<Player> playerIterator = playerListIterator();
		while (playerIterator.hasNext()) {
			Player player = playerIterator.next();
			if (((ArtemisLitePlayer)player).getResources() < 0)
				return player;
		}
		return null;
	}
	
	public boolean isFailure() {
		return bankruptPlayer()!=null;
	}
	
}