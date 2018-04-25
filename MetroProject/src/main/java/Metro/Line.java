package Metro;

import java.time.Duration;

/**
 * This class is a Subway Line that serves as a link between two subway stations.
 * Has the two stations' name as identifier and arguments.
 * @author Nicolas
 *
 */
public class Line extends NetworkObject{
	private final String STATION1;
	private final String STATION2;
	
	//Constructors
	
	public Line(final String station1, final String station2, final Duration duration, final boolean incident) {
		Object[] o = {station1,station2,duration,incident};
		if(DataChecker.checkNulls(o)) {
			throw new IllegalArgumentException("At least one argument is null");
		}
		if(!DataChecker.checkString(station1) || !DataChecker.checkString(station2) || station1.equals(station2)) {
			throw new IllegalArgumentException("Name of the stations can't be null or blank or the same");
		}
		if(!DataChecker.checkDuration(duration)) {
			throw new IllegalArgumentException("Invalid duration : " + duration.toString());
		}
		STATION1 = station1;
		STATION2 = station2;
		waitingTime = duration;
		this.incident = incident;
	}
	
	public Line(final String station1, final String station2, final Duration duration) {
		this(station1,station2,duration,false);
	}
	
	public Line(final String station1, final String station2, final boolean incident) {
		this(station1,station2,Duration.ofMinutes(1).plus(Duration.ofSeconds(30)),incident);
	}
	
	public Line(final String station1, final String station2) {
		this(station1,station2,Duration.ofMinutes(1).plus(Duration.ofSeconds(30)),false);
	}
	
	//Methods
	
	/**
	 * Automatically generated method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((STATION1 == null) ? 0 : STATION1.hashCode());
		result = prime * result + ((STATION2 == null) ? 0 : STATION2.hashCode());
		return result;
	}

	/**
	 * Two lines are equals if they have the same couple of stations, independently of their order
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		return checkBothSide(other.STATION1,other.STATION2) || checkBothSide(other.STATION2,other.STATION1);
	}
	
	/**
	 * Compares the arguments of two Lines, we use it twice in the equal method to test without considering 
	 * the order of station1 and station2
	 * @param station1
	 * 		The first station to test
	 * @param station2
	 * 		The second station to test
	 * @return
	 * 		- True if both stations are the same than the current Line
	 * 		- False otherwise 
	 */
	private boolean checkBothSide(String station1, String station2) {
		if (!STATION1.equals(station1))
			return false;
		if (!STATION2.equals(station2))
			return false;
		return true;
	}
	
	/**
	 * Automatically generated toString
	 */
	@Override
	public String toString() {
		return "Line [STATION1=" + STATION1 + ", STATION2=" + STATION2 + ", incident=" + incident + ", waitingTime="
				+ waitingTime + "]";
	}

	//Getters & Setters
	public String getStation1() {
		return STATION1;
	}

	public String getStation2() {
		return STATION2;
	}
}
