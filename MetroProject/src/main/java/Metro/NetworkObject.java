package Metro;

/**
 * Abstract class of an object of the network, it will either be a Station or a Line.
 * Is Comparable, two objects with the same name are the same objects
 * Two arguments :
 * 		- name is the name of the object, used to differentiate objects
 * 		- incident is a boolean that tells if there is an incident on the line (if it's available to use)
 * Automatically generated getters and setters.
 * @author Nicolas
 *
 */
public abstract class NetworkObject implements Comparable{
	private String name;
	private boolean incident;
	
	//Methods
	
	
	
	//Getters & Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIncident() {
		return incident;
	}

	public void setIncident(boolean incident) {
		this.incident = incident;
	}
	
	
}
