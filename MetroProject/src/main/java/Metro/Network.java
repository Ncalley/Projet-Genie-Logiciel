package Metro;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * This class implements a subway network in Paris
 * it contains a HashMap to symbolize the links between stations and ensure that there can't be doubles.
 * There are two Arraylist of Station an Line that contains the elements of the network.
 * I chose ArrayList over LinkedList because we are going to set the items in the network once and access them often.
 * In terms of access time ArrayList is better than LinkedList.
 * SubwayLines represents all the lines of the network, it'll be used when finding a way where we have the less line
 * changes.
 * @author Nicolas
 *
 */
public class Network{
	private HashMap<String, String> Map = new HashMap<String,String>();
	private ArrayList<Station> Stations = new ArrayList<Station>();
	private ArrayList<Line> Lines = new ArrayList<Line>();
	private ArrayList<Station[]> SubwayLines = new  ArrayList<Station[]>();
	
	//Constructors
	
	//Methods
	
	/**
	 * Add a station to the Network if it's not already in
	 * @param s
	 * 		The station to add
	 * @return
	 * 		Returns true if the station as been successfully added to the network
	 * 		Returns false otherwise
	 */
	public boolean addStation(Station s) {
		if(DataChecker.checkNull(s)) {
			throw new IllegalArgumentException("The Station can't be null");
		}
		if(!Stations.contains(s)) {
			Stations.add(s);
			return true;
		}
		return false;
	}
	
	/**
	 * Add a line to the network only if this line is valid.
	 * A valid line links two stations of the network and isn't already in the network
	 * @param l
	 * 		The lin to add
	 * @return
	 * 		Returns true if the line as been successfully added to the network
	 * 		Returns false otherwise
	 */
	public boolean addLine(Line l) {
		if(DataChecker.checkNull(l)) {
			throw new IllegalArgumentException("The Line can't be null");
		}
		if(Stations.contains(new Station(0,0,l.getStation1())) 
				&& Stations.contains(new Station(0,0,l.getStation2()))
				&& !isInMap(l)
				&& !Lines.contains(l)
				) {
			Lines.add(l);
			Map.put(l.getStation1(), l.getStation2());
			Map.put(l.getStation2(), l.getStation1());
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the line given is in the map
	 * @param l
	 * 		The line we want to check
	 * @return
	 * 		- True if the line is in the Map
	 * 		- False if not
	 */
	private boolean isInMap(Line l) {
		if(Map.containsKey(l.getStation1())) {
			if(Map.get(l.getStation1()).equals(l.getStation2())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates a new Line in the network with the given arguments
	 * @param s1
	 * 		The first Station
	 * @param s2
	 * 		The second Station
	 * @param duration
	 * 		The Duration of the line
	 * @param incident
	 * 		Is there an incident on the Line
	 * @return
	 * 		Returns true if the line as been successfully added to the network
	 * 		Returns false otherwise
	 */
	public boolean createLine(Station s1, Station s2, Duration duration, boolean incident) {
		return addLine(new Line(s1.getName(),s2.getName(),duration,incident));
	}
	
	/**
	 * Returns the contents of the network as a String
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("");
		for(Station elt: Stations) {
			s.append(elt+"\n");
		}
		StringBuffer l = new StringBuffer();
		l.append("");
		for(Line elt: Lines) {
			l.append(elt+"\n");
		}
		return "Network [Map=" + Map.toString() 
			+ ",\n Stations=" + s.toString() 
			+ ", Lines=" + l.toString()
				+ "]";
	}

	//Getters & Setters
	public HashMap<String, String> getMap() {
		return Map;
	}

	public ArrayList<Station> getStations() {
		return Stations;
	}

	public ArrayList<Line> getLines() {
		return Lines;
	}

	public ArrayList<Station[]> getSubwayLines() {
		return SubwayLines;
	}
}
