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
	private final float LATITUDE;
	private final float LONGITUDE;
	private final String NAME;
	
	
	//Constructors
	
	public Station(final float latitude, final float longitude, final String name, final Duration waitingTime, final boolean incident){
		Object[] o = {latitude,longitude,name,waitingTime,incident};
		if(DataChecker.checkNulls(o)) {
			throw new IllegalArgumentException("At least one argument is null");
		}
		if (!DataChecker.checkCoordinate(latitude) || !DataChecker.checkCoordinate(longitude)) {
			throw new IllegalArgumentException("Invalid coordinate's format\nLatitude = "
					+ latitude
					+ "\nLongitude= "
					+ longitude
					+ "\nCoordinates must be between -90 and 90 included");
		}
		if(!DataChecker.checkDuration(waitingTime)) {
			throw new IllegalArgumentException("Invalid duration : " + waitingTime.toString());
		}
		if(!DataChecker.checkString(name)) {
			throw new IllegalArgumentException("Name can't be null or blank");
		}
		
		this.LATITUDE = latitude;
		this.LONGITUDE = longitude;
		this.waitingTime = waitingTime;
		this.NAME = name;
		this.incident = incident;
	}
	
	public Station(final float latitude, final float longitude, final String name, final Duration waitingTime){
		//By default there are no incidents when we create the station
		this(latitude, longitude, name, waitingTime, false);
	}
	
	public Station(final float latitude, final float longitude, final String name, final boolean incident){
		//By default there are no incidents when we create the station
		this(latitude, longitude, name, Duration.ofMinutes(1).plus(Duration.ofSeconds(30)), incident);
	}
	
	public Station(final float latitude, final float longitude, final String name){
		// the mean of the waiting time in a subway station is 1 minute 30 seconds, it will be our default value here
		this(latitude,longitude, name, Duration.ofMinutes(1).plus(Duration.ofSeconds(30)), false);
	}

	
	
	//Methods
	
	/**
	 * Automatically generated method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
		return result;
	}

	/**
	 * Compares this object with another, two Stations with the same name are considered the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (!NAME.equals(other.NAME))
			return false;
		return true;
	}
	
	/**
	 * Automatically generated toString
	 */
	@Override
	public String toString() {
		return "Station [LATITUDE=" + LATITUDE + ", LONGITUDE=" + LONGITUDE + ", name=" + NAME + ", incident="
				+ incident + ", waitingTime=" + waitingTime + "]";
	}
	
	//Getters & Setters

	public float getLatitude() {
		return LATITUDE;
	}

	public float getLongitude() {
		return LONGITUDE;
	}

	public String getName() {
		return NAME;
	}
}
