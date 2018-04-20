package Metro;

import java.time.Duration;

/**
 * Represents a subway station with a geographical position defined by it's latitude and longitude.
 * The geographical position is final (we can't physically move a subway station).
 * Identified by it's name, there can't be two stations with the same name but 
 * there also can't be two stations with the same position.
 * Every station has a default waiting time of 1 minute 30 seconds.
 * The station can have an incident, by default there isn't. 
 * @author Nicolas
 *
 */
public class Station extends NetworkObject{
	private final float latitude;
	private final float longitude;
	private Duration waitingTime;
	
	
	//Constructors
	
	public Station(final float latitude, final float longitude, final Duration waitingTime, final String name, final boolean incident) throws WrongValueException{
		super();
		if (!DataChecker.checkCoordinate(latitude) || !DataChecker.checkCoordinate(longitude)) {
			throw new WrongValueException("Invalid coordinate's format\nLatitude = "
					+ latitude
					+ "\nLongitude= "
					+ longitude
					+ "\nCoordinates must be between -90 and 90 included");
		}
		this.latitude = latitude;
		this.longitude = longitude;
		if(!DataChecker.checkDuration(waitingTime)) {
			throw new WrongValueException("Invalid duration : " + waitingTime.toString());
		}
		this.waitingTime = waitingTime;
		if(!DataChecker.checkString(name)) {
			throw new WrongValueException("Name can't be null or blank");
		}
		this.name = name;
		this.incident = incident;
	}
	
	public Station(final float latitude, final float longitude, final Duration waitingTime, final String name) throws WrongValueException{
		//By default there are no incidents when we create the station
		this(latitude,longitude,waitingTime, name, true);
	}
	
	public Station(final float latitude, final float longitude, final String name) throws WrongValueException{
		// the mean of the waiting time in a subway station is 1 minute 30 seconds, it will be our default value here
		this(latitude,longitude, Duration.ofMinutes(1).plus(Duration.ofSeconds(30)), name, true);
	}

	
	
	//Methods
	
	
	
	//Getters & Setters

	public Duration getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Duration waitingTime) {
		this.waitingTime = waitingTime;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}
}
