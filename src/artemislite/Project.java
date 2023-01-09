/**
 * 
 */
package artemislite;

import game.Player;
import game.Square;
import game.Component;

/**
 * @author Nicolas
 *
 */
public class Project extends Component {
	
	private Player owner;
	Program program;
	private ProjectPhase phase = new Phase0();
	private int[] costs = new int[5];
	private int[] fines = new int[5];
	
	public Project(String name, int[] costs, int[] fines, Square square) throws IllegalArgumentException {
		super(name);
		if (square instanceof ProjectSquare && costs.length == this.costs.length) {
			((ProjectSquare)square).setProject(this);
			this.costs = costs.clone();
			this.fines = fines.clone();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public abstract class ProjectPhase {
		public abstract int cost();
		public abstract int fine();
		public abstract void develop(Project project);
		public boolean isFinal() { return false; }
		public boolean isInitial() { return false; }
		public String name() { return ""; }
		public String description() { return ""; }
		public String previousMilestones() { return ""; }
	}
	
	public class Phase0 extends ProjectPhase {
		public int cost() { return costs[0]; }
		public int fine() { return 0; }
		public void develop(Project project) { setPhase(new PhaseA()); }
		@Override
		public String name() { return "phase 0"; }
		@Override
		public String description() { return "concept studies"; }
		@Override
		public boolean isInitial() { return true; }
	}
	
	public class PhaseA extends ProjectPhase {
		public int cost() { return costs[1]; }
		public int fine() { return fines[0]; }
		public void develop(Project project) { setPhase(new PhaseB()); }
		@Override
		public String name() { return "phase A"; }
		@Override
		public String description() { return "concept and technology development"; }
		@Override
		public String previousMilestones() { return "MDR (Mission Definition Review)"; }
	}
	
	public class PhaseB extends ProjectPhase {
		public int cost() { return costs[2]; }
		public int fine() { return fines[1]; }
		public void develop(Project project) { setPhase(new PhaseC()); }
		@Override
		public String name() { return "phase B"; }
		@Override
		public String description() { return "preliminary design and technology completion"; }
		@Override
		public String previousMilestones() { return "PRR (Preliminary Requirements Review)"; }
	}
	
	public class PhaseC extends ProjectPhase {
		public int cost() { return costs[3]; }
		public int fine() { return fines[2]; }
		public void develop(Project project) { setPhase(new PhaseD()); }
		@Override
		public String name() { return "phase C"; }
		@Override
		public String description() { return "final design and fabrication"; }
		@Override
		public String previousMilestones() { return "PDR (Preliminary Design Review)"; }
	}
	
	public class PhaseD extends ProjectPhase {
		public int cost() { return costs[4]; }
		public int fine() { return fines[3]; }
		public void develop(Project project) { setPhase(new PhaseE()); }
		@Override
		public String name() { return "phase D"; }
		@Override
		public String description() { return "system assembly, integration"; }
		@Override
		public String previousMilestones() { return "CDR (Critical Design Review)"; }
	}
	
	public class PhaseE extends ProjectPhase {
		public PhaseE() {}
		public int cost() { return 0; }
		public int fine() { return fines[4]; }
		public void develop(Project project) {}
		public boolean isFinal() { return true; }
		@Override
		public String name() { return "phase E"; }
		@Override
		public String description() { return "operations and sustainment"; }
		@Override
		public String previousMilestones() { return "FAR (Flight Acceptance Review)"; }
	}
	
	public void executeDevelopPhase() {

	}
	
	public void setOwner(Player player) {
		this.owner = player;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public boolean isOwner(Player player) {
		return player == owner;
	}
	
	public boolean hasOwner() {
		return ! (phase instanceof Phase0);
	}
	
	public void setProgram(Program program) {
		this.program = program;
	}
	
	public Program getProgram() {
		return program;
	}
	
	public void develop() throws NullPointerException {
		if (owner == null) throw new NullPointerException("Element owner is null...");
		
		ArtemisLiteUserInterface userInterface = ArtemisLiteUserInterface.getInstance();
		ArtemisLiteGame artemisLiteGame = ArtemisLiteGame.getInstance();

		phase.develop(this);
		
		userInterface.println(owner,"Congratulations, the " + getName() + " project, part of the " + program.getName() + " program, has had a successful " + phase.previousMilestones() + ".");
		userInterface.println(owner,"The project is now in " + phase.name() + ": " + phase.description() + ".");
		if (isCompleted()) userInterface.println(owner,"The project is now waiting for launch...");
		if (artemisLiteGame.isSuccess()) artemisLiteGame.setFinish();
	}
	
	public boolean isCompleted() {
		return phase.isFinal();
	}
	
	public void setPhase(ProjectPhase phase) {
		this.phase = phase;
	}
	
	public ProjectPhase getPhase() {
		return phase;
	}
	
	public int cost() {
		return phase.cost();
	}
	
	public int fine() {
		return phase.fine();
	}
}
