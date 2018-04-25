package Metro;

import java.time.Duration;

/**
 * Abstract class of an object of the network, it will either be a Station or a Line.
 * Is Comparable, two objects with the same name are the same objects
 * Two arguments :
 * 		- WaitingTime is the time required to leave the object.
 * 		- incident is a boolean that tells if there is an incident on the line (if it's available to use)
 * Automatically generated getters and setters.
 * @author Nicolas
 *
 */
public abstract class NetworkObject{
	protected boolean incident;
	protected Duration waitingTime = Duration.ofMinutes(1).plus(Duration.ofSeconds(30));
	
	//Methods
	
	
	//Getters & Setters

	public boolean isIncident() {
		return incident;
	}

	public void setIncident(boolean incident) {
		this.incident = incident;
	}

	public Duration getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Duration waitingTime) {
		if(DataChecker.checkNull(waitingTime)) {
			throw new IllegalArgumentException("At least one argument is null");
		}
		if(!DataChecker.checkDuration(waitingTime)) {
			throw new IllegalArgumentException("Invalid duration : " + waitingTime.toString());
		}
		this.waitingTime = waitingTime;

	}
}
