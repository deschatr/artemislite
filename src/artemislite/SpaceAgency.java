/**
 * 
 */
package artemislite;

/**
 * @author Nicolas
 *
 */
public enum SpaceAgency {
	
	AEB {
		@Override
		public String fullName() { return "Brazilian Space Agency"; } 
	},
	ASI {
		@Override
		public String fullName() { return "Italian Space Agency"; } 
	},
	CSA {
		@Override
		public String fullName() { return "Canadian Space Agency"; } 
	},
	JAXA {
		@Override
		public String fullName() { return "Japan Aerospace Exploration Agency"; } 
	},
	KARI {
		@Override
		public String fullName() { return "Korean Aerospace Research Institute"; } 
	},
	NASA {
		@Override
		public String fullName() { return "National Aeronautics and Space Administration"; } 
	},
	POLSA {
		@Override
		public String fullName() { return "Polish Space Agency"; } 
	},
	SSAU {
		@Override
		public String fullName() { return "State Space Agency of Ukraine"; } 
	},
	UKSA {
		@Override
		public String fullName() { return "UK Space Agency"; } 
	};
	
	public abstract String fullName();
	
}
