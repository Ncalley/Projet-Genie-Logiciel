package Metro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import jwetherell.AStar;
import jwetherell.Graph;
import jwetherell.Graph.Edge;
import jwetherell.Graph.Vertex;




public class Network{
	private Graph<Station> graph = new Graph<Station>();
	private final AStar<Station> ASTAR = new AStar<Station>();
	private List<Edge<Station>> linesWithIncidents = new ArrayList<Edge<Station>>();
	
	//Constructors
	public Network() {}
	
	public Network(Graph<Station> graph) {
		if(DataChecker.checkNull(graph)) {
			throw new IllegalArgumentException("The Graph can't be null");
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
			graph.getEdges().add(e);
			if(!e.getFromVertex().getEdges().contains(e)){
				e.getFromVertex().addEdge(e);
			}
			if(!e.getToVertex().getEdges().contains(e)){
				e.getToVertex().addEdge(e);
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
	 * @return
	 * 		- True if the station has been successfully added to the network
	 * 		- False otherwise
	 */
	public boolean createLine(Station s1, Station s2, Duration duration) {
		Vertex<Station> vertex1 =new Vertex<Station>(s1,s1.getWaitingTimeInt());
		Vertex<Station> vertex2 =new Vertex<Station>(s2,s2.getWaitingTimeInt());
		if(DataChecker.checkDuration(duration)) {
			throw new IllegalArgumentException("The Duration is invalid");
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
	public boolean createStation(float latitude, float longitude, String name, Duration waitingTime, boolean incident) {
		return addStation(new Station(latitude,longitude,name,waitingTime,incident));
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
	public List<Graph.Edge<Station>> aStar(Graph<Station> graph, Graph.Vertex<Station> start, Graph.Vertex<Station> goal) {
		return ASTAR.aStar(buildSubGraph(graph), start, goal);
	}
	
	/**
	 * Builds a sub graph containing only Stations without incidents and lines (Edges) without incidents
	 * @param g
	 * 		The main graph from where we want to extract the valid Stations and Lines
	 * @return
	 * 		A subgraph that contains only Stations and Lines without incidents
	 */
	public Graph<Station> buildSubGraph(Graph<Station> g){
		Graph<Station> res = new Graph<Station>();
		
		for (Vertex<Station> v : g.getVertices())
			if(!v.getValue().isIncident()) {
				res.getVertices().add(new Vertex<Station>(v));
			}

        for (Vertex<Station> v : res.getVertices()) {
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
			graph.getVertices().get(graph.getVertices().indexOf(vertex)).getValue().setIncident(true);
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
			graph.getVertices().get(graph.getVertices().indexOf(vertex)).getValue().setIncident(false);
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
	 * Clears the Network and delete all of it's contents (use with caution)
	 */
	public void clear() {
		graph = new Graph<Station>();
		linesWithIncidents.clear();
	}
	
	/**
	 * Saves the Network on a file with the given name
	 * @param filename
	 * 		The given filename (with extension like "toto.txt")
	 * @throws IOException
	 */
	public void save(String filename) throws IOException {
		JSONProcessor.serialize(this, filename);
	}
	
	/**
	 * Loads a Network from a corresponding file using a given filename
	 * @param filename
	 * 		the name of the file we needs to extract data from
	 * @return
	 * 		A Network object corresponding to the object previously serialized 
	 */
	public static Network load(String filename) {
		Network res = new Network();
		if(DataChecker.checkString(filename)) {
			throw new IllegalArgumentException("The given name is invalid");
		}
		try {
			res = (Network)JSONProcessor.deserialize(filename);
		}catch(FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}

	//Getters & Setters
	public Graph<Station> getGraph() {
		return graph;
	}

	public void setGraph(Graph<Station> graph) {
		if(DataChecker.checkNull(graph)) {
			throw new IllegalArgumentException("The Graph can't be null");
		}
		this.graph = graph;
	}
}
