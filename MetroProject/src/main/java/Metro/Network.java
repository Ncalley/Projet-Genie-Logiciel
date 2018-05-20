package Metro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import jwetherell.AStar;
import jwetherell.Graph;
import jwetherell.Graph.Edge;
import jwetherell.Graph.TYPE;
import jwetherell.Graph.Vertex;
import GeoDataSource.DistanceCalculator;


/**
 * The Network is an object designed to represent a subway network, it contains some objects to help it.
 * graph : It's jwetherell's implementation of a graph, a clean directed graph which can be used with the Astar algorithm.
 * Astar : It's jwetherell's implementation of the Astar algorithm to search through the whole graph to find the
 * fastest path.
 * linesWithIncidents : regroup every lines with incidents of the graph. As the lines are no longer represented by
 * their own object but rather by graph's edges it's the only direct way to stock them.
 * Lines : represents every "great line" of the network (a great line is the type of line where you don't have to get out
 * of the subway at the station to go on another one).
 * @author Nicolas
 *
 */
public class Network{
	private Graph<Station> graph = new Graph<Station>(TYPE.DIRECTED);
	private final AStar<Station> ASTAR = new AStar<Station>();
	private List<Edge<Station>> linesWithIncidents = new ArrayList<Edge<Station>>();
	private ArrayList<ArrayList<String>> Lines = new ArrayList<ArrayList<String>>();
	
	//Constructors
	public Network() {}
	
	public Network(Graph<Station> graph) {
		if(DataChecker.checkNull(graph)) {
			throw new IllegalArgumentException("The Graph can't be null");
		}
		if(!graph.getType().equals(TYPE.DIRECTED)) {
			throw new IllegalArgumentException("The Graph must be directed");
		}
		this.graph = graph;
	}
	
	//Methods
	
	/**
	 * Add a selected station to the network
	 * @param s
	 * 		The station we want to add (can't be null)
	 * @return
	 * 		- True if the station have been successfully added
	 * 		- False otherwise
	 */
	public boolean addStation(Station s) {
		if(DataChecker.checkNull(s)) {
			throw new IllegalArgumentException("The Station can't be null");
		}
		Vertex<Station> vertex =new Vertex<Station>(s,s.getWaitingTimeInt());
		if(!graph.getVertices().contains(vertex)) {
			graph.getVertices().add(vertex);
			return true;
		}
		return false;
	}
	
	

	/**
	 * Add a Line in the network between two station
	 * By default there is no incident on it
	 * @param e
	 * 		The Edge that we need to add
	 * @return
	 * 		- True if the Line has been successfully added
	 * 		- False otherwise
	 */
	public boolean addLine(Edge<Station> e) {
		if(DataChecker.checkNull(e)) {
			throw new IllegalArgumentException("The Line can't be null");
		}
		if(graph.getVertices().contains(e.getFromVertex()) 
				&& graph.getVertices().contains(e.getToVertex())
				&& !graph.getEdges().contains(e)
				) {
			graph.addEdge(e);
			if(!getVertex(e.getFromVertex().getValue()).getEdges().contains(e)){
				getVertex(e.getFromVertex().getValue()).addEdge(e);
			}
			if(!getVertex(e.getToVertex().getValue()).getEdges().contains(e)){
				getVertex(e.getToVertex().getValue()).addEdge(e);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Create a custom Line between two stations of the network, the line must not be in the network
	 * As it is an undirected graph, we can switch the values of s1 and s2
	 * @param s1
	 * 		The first station
	 * @param s2
	 * 		The second station
	 * @param duration
	 * 		The duration required to travel between the two stations
	 * 			can't be negative
	 * @return
	 * 		- True if the station has been successfully added to the network
	 * 		- False otherwise
	 */
	public boolean createLine(Station s1, Station s2, Duration duration) {
		Object[] o = {s1,s2,duration};
		if(DataChecker.checkNulls(o)) {
			throw new IllegalArgumentException("At least one argument is null");
		}
		Vertex<Station> vertex1 =new Vertex<Station>(s1,s1.getWaitingTimeInt());
		Vertex<Station> vertex2 =new Vertex<Station>(s2,s2.getWaitingTimeInt());
		if(!DataChecker.checkDuration(duration)) {
			throw new IllegalArgumentException("The Duration is invalid : "+duration.toString());
		}
		return addLine(new Edge<Station>(Integer.parseInt(duration.getSeconds()+""),vertex1,vertex2));
	}
	
	/**
	 * Create and insert a station from the given arguments to the graph
	 * @param latitude
	 * 		The latitude of the station
	 * @param longitude
	 * 		The longitude of the station
	 * @param name
	 * 		The name of the station (two stations with the same name are considered the same)
	 * @param waitingTime
	 * 		The time required to leave the station
	 * @param incident
	 * 		Is there an incident on the station
	 * @return
	 * 		- True is the Station has been successfully created and added to the graph
	 * 		- False otherwise
	 */
	public boolean createStation(float latitude, float longitude, String name, Duration waitingTime) {
		return addStation(new Station(latitude,longitude,name,waitingTime,false));
	}
	
	/**
	 * Uses the A* algorithm to find the fastest way from one point to another in the graph
	 * @param graph
	 * 		The graph or subgraph we want to use for the algorithm
	 * @param start
	 * 		The starting station of the Graph
	 * @param goal
	 * 		The station where we want to go
	 * @return
	 * 		An ordered list of the edges that connects our start and our goal
	 */
	public List<Edge<Station>> aStar(Graph<Station> graph, Vertex<Station> start, Vertex<Station> goal) {
		if(DataChecker.checkNull(start)) {
			throw new IllegalArgumentException("The Start point can't be null");
		}
		if(DataChecker.checkNull(goal)) {
			throw new IllegalArgumentException("The Goal point can't be null");
		}
		if(DataChecker.checkNull(graph)) {
			throw new IllegalArgumentException("The Graph can't be null");
		}
		return ASTAR.aStar(buildSubGraph(graph), start, goal);
	}
	
	/**
	 * This function finds the fastest way to go from the starting point to the goal through an ordered list of vertices. 
	 * @param graph
	 * 		The whole graph, usually it will be the this object but another graph can also be used
	 * @param list
	 * 		This is the list of vertices you want to pass by in your travel, there are some restrictions on it.
	 * 			- It must Contain at least two vertices
	 * 			- The first vertex must be your starting point
	 * 			- The last vertex must be your goal
	 * @return
	 * 		A list of Edges that links every points of the list.
	 */
	public List<Edge<Station>> multipleBreakPoints(Graph<Station> graph, List<Vertex<Station>> list){
		if(DataChecker.checkNull(list)) {
			throw new IllegalArgumentException("The list of points can't be null");
		}
		switch (list.size()){
			case 0:
			case 1:
				throw new IllegalArgumentException("The list of points must contain at least two values");
			case 2:
				return ASTAR.aStar(buildSubGraph(graph), list.get(0), list.get(1));
			default:
				ArrayList<Graph.Edge<Station>> res = new ArrayList<Graph.Edge<Station>>();
				Graph<Station> subgraph = buildSubGraph(graph);
				for(int i=0;i<list.size()-2;i++) {
					List<Edge<Station>> e = ASTAR.aStar(subgraph, list.get(i), list.get(i+1));
					/*for(Edge<Station> elt : e) {
						System.out.println(elt.getFromVertex().getValue().getName() + " ; " + elt.getToVertex().getValue().getName());
					}*/
					if(e != null && !e.isEmpty()) {
						res.addAll(e);
					}
				}
				return res;
		}
	}
	
	/**
	 * Builds a sub graph containing only Stations without incidents and lines (Edges) without incidents
	 * @param g
	 * 		The main graph from where we want to extract the valid Stations and Lines
	 * @return
	 * 		A subgraph that contains only Stations and Lines without incidents
	 */
	public Graph<Station> buildSubGraph(Graph<Station> g){
		if(DataChecker.checkNull(g)) {
			throw new IllegalArgumentException("The Graph can't be null");
		}
		Graph<Station> res = new Graph<Station>();
		
		for (Vertex<Station> v : g.getVertices())
			if(!v.getValue().isIncident()) {
				res.getVertices().add(new Vertex<Station>(v.getValue(),v.getWeight()));
			}

        for (Vertex<Station> v : g.getVertices()) {
            for (Edge<Station> e : v.getEdges()) {
            	if(!linesWithIncidents.contains(e)) {
            		res.getEdges().add(e);
            	}
            }
        }
		return res;
	}
	
	/**
	 * Create an incident on the given Line 
	 * @param e
	 * 		The Line on which we want to create an incident
	 * @return
	 * 		- True if the incident has been successfully created
	 * 		- False otherwise
	 */
	public boolean createIncident(Edge<Station> e) {
		if(DataChecker.checkNull(e)) {
			throw new IllegalArgumentException("The Line can't be null");
		}
		if(graph.getEdges().contains(e)) {
			linesWithIncidents.add(e);
			return true;
		}
		return false;
	}
	
	/**
	 * Create an incident on the given Station 
	 * @param s
	 * 		The Station on which we want to create an incident
	 * @return
	 * 		- True if the incident has been successfully created
	 * 		- False otherwise
	 */
	public boolean createIncident(Station s) {
		if(DataChecker.checkNull(s)) {
			throw new IllegalArgumentException("The Station can't be null");
		}
		Vertex<Station> vertex =new Vertex<Station>(s,s.getWaitingTimeInt());
		if(graph.getVertices().contains(vertex)) {
			getVertex(s).getValue().setIncident(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Solve an incident on the given Line 
	 * @param e
	 * 		The Line on which we want to solve an incident
	 * @return
	 * 		- True if the incident has been successfully created
	 * 		- False otherwise
	 */
	public boolean solveIncident(Edge<Station> e) {
		if(DataChecker.checkNull(e)) {
			throw new IllegalArgumentException("The Line can't be null");
		}
		if(linesWithIncidents.contains(e)) {
			linesWithIncidents.remove(e);
			return true;
		}
		return false;
	}
	
	/**
	 * Solve an incident on the given Station 
	 * @param s
	 * 		The Station on which we want to solve an incident
	 * @return
	 * 		- True if the incident has been successfully created
	 * 		- False otherwise
	 */
	public boolean solveIncident(Station s) {
		if(DataChecker.checkNull(s)) {
			throw new IllegalArgumentException("The Station can't be null");
		}
		Vertex<Station> vertex =new Vertex<Station>(s,s.getWaitingTimeInt());
		if(graph.getVertices().contains(vertex)) {
			getVertex(s).getValue().setIncident(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Solves every incident on the network, be it on a Line or on a Station
	 */
	public void solveAllIncidents() {
		linesWithIncidents.clear();
		for(Vertex<Station> elt : graph.getVertices()) {
			if(elt.getValue().isIncident()) {
				elt.getValue().setIncident(false);
			}
		}
	}
	
	/**
	 * This methods runs through every Station of the graph and returns the nearest one to the given parameters
	 * @param latitude
	 * 			A gps coordinate, the latitude must be between -90 and 90 included
	 * @param longitude
	 * 			A gps coordinate, the longitude must be between -90 and 90 included
	 * @return
	 * 			The nearest Station coordinates wise
	 */
	public Vertex<Station> findNearestStation(Float latitude, Float longitude){
		Object[] o = {latitude,longitude};
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
		Vertex<Station> res = new Vertex<Station>(new Station(0,0,"Test"));
		if(graph.getVertices().size() == 0 ) {
			throw new IllegalArgumentException("The graph does not contain any Station");
		}
		double distanceMin = Double.MAX_VALUE;
		for(Vertex<Station> elt : graph.getVertices()) {
			double testDistance = DistanceCalculator.distance(elt.getValue().getLatitude(), 
															elt.getValue().getLongitude(), 
															latitude, 
															longitude, 
															"K");
			if(distanceMin > testDistance) {
				distanceMin = testDistance;
				res = elt;
			}
		}
		return res;
	}
	
	/**
	 * Clears the Network and delete all of it's contents (use with caution)
	 */
	public void clear() {
		graph = new Graph<Station>();
		linesWithIncidents.clear();
	}
	
	/**
	 * Saves the Network on a file with the given name
	 * It requires a bit more time to develop.
	 * @param filename
	 * 		The given filename (with extension like "toto.txt")
	 */
	/*public boolean save(String filename){
		try {
			JSONProcessor.serialize(this, filename);
		}catch(IOException e) {
			return false;
		}return true;
	}*/
	
	/**
	 * Constructs every station based on the correct .csv file and insert them into the Network
	 * @param fileName
	 * 		the given fileName
	 */
	public void loadStations(String fileName) {
		CSVParser parser = new CSVParser();
		parser.parseStations(fileName);
		int pos = 0;
		for(String elt : parser.getNames()) {
			createStation(parser.getLatitudes().get(pos), 
					parser.getLongitudes().get(pos), 
					elt, 
					Duration.ofMinutes(1).plus(Duration.ofSeconds(30)));
			pos++;
		}
	}
	
	/**
	 * Load the lines from a given file then insert them on the graph
	 * Must be used only after all the vertices are in the graph
	 * The complexity of this one is pretty hard and it may be time consuming to run it, use with caution
	 * @param fileName
	 * 		The name and relative path of the file you want to extract the data from
	 */
	public void loadLines(String fileName) {
		CSVParser parser = new CSVParser();
		parser.parseLines(fileName);
		this.Lines = parser.getLines();
		Duration duration = Duration.ofMinutes(1).plus(Duration.ofSeconds(30));
		for(ArrayList<String> array : Lines) {
			for(int i=0;i<array.size()-2;i++) {
				Vertex<Station> v1 = getVertex(array.get(i));
				Vertex<Station> v2 = getVertex(array.get(i+1));
				if(v1!=null && v2 != null) {
					addLine(new Edge<Station>(Integer.parseInt(duration.getSeconds()+""),v1,v2));
				}
			}
		}
	}
	
	/**
	 * Search the whole graph to find if it contains the given Station
	 * @param s
	 * 		The given Station
	 * @return
	 * 		- True if this station is in the graph
	 * 		- False otherwise
	 */
	public boolean contains(Station s) {
		for(Vertex<Station> v : graph.getVertices()) {
			if(v.getValue().equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Loads a Network from a corresponding file using a given filename
	 * Doesn't work properly because the class is a bit too complicated and JSONProcessor needs a TypeAdapter
	 * @param filename
	 * 		the name of the file we needs to extract data from
	 * @return
	 * 		A Network object corresponding to the object previously serialized 
	 */
	/*public static Object load(String filename) {
		Object res = new Network();
		if(!DataChecker.checkString(filename)) {
			throw new IllegalArgumentException("The given name is invalid");
		}
		try {
			res = JSONProcessor.deserialize(filename);
		}catch(FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}*/

	//Getters & Setters
	public Graph<Station> getGraph() {
		return graph;
	}

	public void setGraph(Graph<Station> graph) {
		if(DataChecker.checkNull(graph)) {
			throw new IllegalArgumentException("The Graph can't be null");
		}
		if(!graph.getType().equals(TYPE.DIRECTED)) {
			throw new IllegalArgumentException("The Graph must be directed");
		}
		this.graph = graph;
	}
	
	/**
	 * Search our vertices for a vertex with the given station
	 * @param s
	 * 		The station we want to find in the graph
	 * @return
	 * 		- A vertex of the graph that contains the station
	 * 		- null if there is nothing in the graph or if the station isn't in the graph
	 */
	public Vertex<Station>  getVertex(Station s){
		if(graph.getVertices().isEmpty()) {return null;}
		for(Vertex<Station> elt : graph.getVertices()) {
			if(elt.getValue().equals(s)) {
				return elt;
			}
		}
		return null;
	}
	
	/**
	 * Overload of getVertex with a string
	 * @param s
	 * 		The station we want to find in the graph
	 * @return
	 * 		- A vertex of the graph that contains the station
	 * 		- null if there is nothing in the graph or if the station isn't in the graph
	 */
	public Vertex<Station>  getVertex(String s){
		return getVertex(new Station(0,0,s));
	}

	public List<Edge<Station>> getLinesWithIncidents() {
		return linesWithIncidents;
	}

	public ArrayList<ArrayList<String>> getLines() {
		return Lines;
	}
}
