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
public abstract class NetworkObject{
	protected String name;
	protected boolean incident;
	
	//Methods
	/**
	 * Automatically generated hashcode function
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Automatically generated equals function
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NetworkObject other = (NetworkObject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
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
