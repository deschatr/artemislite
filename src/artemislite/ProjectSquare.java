package artemislite;

import game.Square;

import java.util.ArrayList;
import java.util.ListIterator;

import game.Player;

public class ProjectSquare extends Square {
	
	private Project project;
	
	public ProjectSquare(String name) {
		super(name);
	}
	
	public ProjectSquare(String name, Project project) {
		super(name);
		this.project = project;
	}

	@Override
	public void executeOnLanding(Player player) {
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		
		if (!project.hasOwner()) {
			executeBidProject(player, project);
		} else if (!project.isOwner(player)) {
			executePayProjectFine(player, project);
		} else {
			userInterface.println(player,"You have landed on the " + project.getName() + " project which you already won.");
		}
	}
		
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public void executeBidProject(Player player, Project project) {
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		ArtemisLiteGame artemisLiteGame = ArtemisLiteGame.getInstance();
		
		int playerCounter = 0;
		Player possibleBidder;
		String answer;
		ListIterator<Player> playerIterator = artemisLiteGame.playerListIterator(player);
		
		Menu purchaseMenu = new Menu();
		purchaseMenu.addItem("1",new MenuItem("Bid for the project"));
		purchaseMenu.addItem("2",new MenuItem("Do not bid for the project"));
		purchaseMenu.addItem("o", new MenuItem("Display the board state"));
		purchaseMenu.addItem("p", new MenuItem("Display the players state"));
		purchaseMenu.addItem("q", new MenuItem("Quit game"));
		
		do {
			if (!playerIterator.hasNext()) playerIterator = artemisLiteGame.playerListIterator();
			possibleBidder = playerIterator.next();
			
			if (project.cost() > ((ArtemisLitePlayer)possibleBidder).getResources()) {
				userInterface.println(possibleBidder,"You do not have enough funding to bid for the project" + project.getName() + ", part of the " + project.getProgram().getName() + " program.");
				answer = "2";
			} else {
			
				do {
					userInterface.println(possibleBidder,"You have the opportunity to bid for the project " + project.getName() + ", part of the " + project.getProgram().getName() + " program.");
					userInterface.println(possibleBidder,"You would need to spend " + artemisLiteGame.formatResources(project.cost()) + " to fund this project.");
					
					ListIterator<Project> projectIterator = project.getProgram().projectListIterator();
					ArrayList<Player> otherOwners = new ArrayList<Player>();
					
					while (projectIterator.hasNext()) {
						Project otherProject = projectIterator.next();
						if (otherProject != project && otherProject.getOwner() != null && !otherOwners.contains(otherProject.getOwner()))
							otherOwners.add(otherProject.getOwner());
					}
				
					if (otherOwners.size() == 0) {
						userInterface.println(possibleBidder,"All the projects from this program are currently available for bidding.");
					} else if (otherOwners.size()==1 && otherOwners.contains(possibleBidder)) {
						userInterface.println(possibleBidder,"You are the only player currently developing a project from the same program this project belongs to.");
					} else if (otherOwners.size()==1) {
						userInterface.println(possibleBidder,"Another player is currently developing a project from the same program this project belongs to");
					} else {
						userInterface.println(possibleBidder,"Other players are currently developing a project from the same program this project belongs to");
					}
				
					artemisLiteGame.displayResources(possibleBidder);
				
					userInterface.println("");
					userInterface.println(possibleBidder,"Would you like to bid for this project?");

					answer = userInterface.askMenu(possibleBidder, purchaseMenu);
					if (answer.equals("o")) artemisLiteGame.executeDisplayBoardState();
					if (answer.equals("p")) artemisLiteGame.executeDisplayPlayerState();
				} while (answer.equals("o") || answer.equals("p"));
			}
			
		} while (++playerCounter < artemisLiteGame.countPlayers() && answer.equals("2"));
		
		switch (answer) {
		case "1":
			((ArtemisLitePlayer)possibleBidder).subResources(project.cost());
			project.setOwner(possibleBidder);
			project.develop();
			artemisLiteGame.displayResources(possibleBidder);
			break;
		case "2":
			userInterface.println("No takers...");
			break;
		case "q":
			artemisLiteGame.setQuit();
		}
	}
	
	public void executePayProjectFine(Player player, Project project) {
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		ArtemisLiteGame artemisLiteGame = ArtemisLiteGame.getInstance();
		
		String menuSelection;
		
		Menu penaltyMenu = new Menu();
		penaltyMenu.addItem("1", new MenuItem("Collect the fine"));
		penaltyMenu.addItem("2", new MenuItem("Cancel the fine"));
		penaltyMenu.addItem("o", new MenuItem("Display the board state"));
		penaltyMenu.addItem("p", new MenuItem("Display the players state"));
		penaltyMenu.addItem("q", new MenuItem("Quit game"));
		
		userInterface.println(player,"You have landed on the " + project.getName() + " project which has already been won by " + project.getOwner().getName() + ", and you may have to pay a fine.");
		do {
			userInterface.println("");
			userInterface.println(project.getOwner(),"Would you like to collect the fine (" +  artemisLiteGame.formatResources(project.fine())+ ")?");
			menuSelection = userInterface.askMenu(project.getOwner(),penaltyMenu);
			if (menuSelection.equals("o")) artemisLiteGame.executeDisplayBoardState();
			if (menuSelection.equals("p")) artemisLiteGame.executeDisplayPlayerState();
		} while (menuSelection.equals("o") || menuSelection.equals("p"));
		
		switch (menuSelection) {
		case "1":
			((ArtemisLitePlayer)project.getOwner()).addResources(project.fine());
			((ArtemisLitePlayer)player).subResources(project.fine());
			if (((ArtemisLitePlayer)player).getResources() < 0) {
				artemisLiteGame.setFinish();
			} else {
				artemisLiteGame.displayResources(project.getOwner());
				artemisLiteGame.displayResources(player);
			}
			break;
		case "2":
			break;
		case "q":
			artemisLiteGame.setQuit();
		}
	}
	
}