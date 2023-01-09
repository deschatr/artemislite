package artemislite;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.ListIterator;
import game.Component;
import game.Player;

public class Program extends Component {
	
	private ArrayList<Project> projects = new ArrayList<>();
	
	public Program(String name) {
		super(name);
	}
	
	public Program(String name, ArrayList<Project> projects) {
		super(name);
		this.projects = projects;
	}

	public void addProject(Project project) {
		projects.add(project);
		project.setProgram(this);
	}
	
	public boolean containsProject(Project project) {
		return projects.contains(project);
	}
	
	public ListIterator<Project> projectListIterator() {
		return projects.listIterator();
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public boolean isOwner(Player player) {
		for (Project project : projects)
			if (!project.isOwner(player)) return false;
		return true;
	}
	
	public boolean hasOwner() {
		for (Project project : projects)
			if (project.hasOwner()) return true;
		return false;
	}
	
	public boolean hasOtherOwner(Player player) {
		for (Project project : projects)
			if (project.hasOwner() && !project.isOwner(player)) return true;
		return false;
	}
	
	public boolean isCompleted() {
		for (Project project : projects)
			if (!project.isCompleted()) return false;
		return true;
	}
	
}
