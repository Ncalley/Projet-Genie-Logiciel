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
public class Station implements Comparable<Station>{
	private boolean incident;
	private Duration waitingTime;
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
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Station [LATITUDE=" + LATITUDE + ", LONGITUDE=" + LONGITUDE + ", name=" + NAME + ", incident="
				+ incident + ", waitingTime=" + waitingTime + "]";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Station o) {
		if(this.equals(o)) {return 0;}
		return Integer.parseInt(this.waitingTime.minus(o.getWaitingTime()).getSeconds()+"");
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

	public Duration getWaitingTime() {
		return waitingTime;
	}
	
	public int getWaitingTimeInt() {
		return Integer.parseInt(this.waitingTime.getSeconds()+"");
	}

	public void setWaitingTime(Duration waitingTime) {
		this.waitingTime = waitingTime;
	}

	public boolean isIncident() {
		return incident;
	}

	public void setIncident(boolean incident) {
		this.incident = incident;
	}
	
}
