package MetroTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


import Metro.Network;
import Metro.Station;
import jwetherell.Graph;
import jwetherell.Graph.Edge;
import jwetherell.Graph.TYPE;
import jwetherell.Graph.Vertex;

@RunWith(JUnitPlatform.class)
@DisplayName("NetworkTest")
public class NetworkTest {
	//Initialization of the Network
	Network n;
	
	@Test
	@BeforeEach
	void testNetwork() {
		n = new Network();
	}
	
	@Test
	void testNetwork1() {
		assertThrows(IllegalArgumentException.class, () -> n = new Network(null));
	}
	
	@Test
	void testNetwork2() {
		assertThrows(IllegalArgumentException.class, () -> n = new Network(new Graph<Station>()));
	}
	
	@Test
	void testNetwork3() {
		n = new Network(new Graph<Station>(TYPE.DIRECTED));
	}
	
	@Test
	void testAddLines1() {
		assertThrows(IllegalArgumentException.class, () -> n.addLine(null));
	}
	
	@Test
	void testAddLines2() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		Vertex<Station> from = n.getVertex(s1);
		Vertex<Station> to = n.getVertex(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		assertTrue(n.addLine(e));
	}
	
	@Test
	void testAddLines3() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		Vertex<Station> from = n.getVertex(s1);
		Vertex<Station> to = new Vertex<Station>(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		assertFalse(n.addLine(e));
	}
	
	@Test
	void testAddLines4() {
		int num = 0;
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s2);
		Vertex<Station> from = new Vertex<Station>(s2);
		Vertex<Station> to = n.getVertex(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		assertFalse(n.addLine(e));
	}
	
	@Test
	void testAddLines5() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		Vertex<Station> from = n.getVertex(s1);
		Vertex<Station> to = n.getVertex(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		n.addLine(e);
		assertFalse(n.addLine(e));
	}
	
	@Test
	void testAddLines6() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		Vertex<Station> from = n.getVertex(s1);
		Vertex<Station> to = n.getVertex(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		from.addEdge(e);
		n.addLine(e);
		assertFalse(n.addLine(e));
	}
	
	@Test
	void testAddLines7() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		Vertex<Station> from = n.getVertex(s1);
		Vertex<Station> to = n.getVertex(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		to.addEdge(e);
		n.addLine(e);
		assertFalse(n.addLine(e));
	}
	
	@Test
	void testCreateLine() {
		assertThrows(IllegalArgumentException.class,() -> n.createLine(null,null,null));
	}
	
	@Test
	void testCreateLine1() {
		Station s1 = new Station(0,0,"Test");
		assertThrows(IllegalArgumentException.class,() -> n.createLine(s1,null,null));
	}
	
	@Test
	void testCreateLine2() {
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		assertThrows(IllegalArgumentException.class,() -> n.createLine(s1,s2,null));
	}
	
	@Test
	void testCreateLine3() {
		Station s2 = new Station(0,0,"Test1");
		assertThrows(IllegalArgumentException.class,() -> n.createLine(null,s2,null));
	}
	
	@Test
	void testCreateLine4() {
		Duration duration = Duration.ofMinutes(1).plus(Duration.ofSeconds(30));
		Station s2 = new Station(0,0,"Test1");
		assertThrows(IllegalArgumentException.class,() -> n.createLine(null,s2,duration));
	}
	
	@Test
	void testCreateLine5() {
		Duration duration = Duration.ofMinutes(1).plus(Duration.ofSeconds(30));
		Station s1 = new Station(0,0,"Test");
		assertThrows(IllegalArgumentException.class,() -> n.createLine(s1,null,duration));
	}
	
	@Test
	void testCreateLine6() {
		Duration duration = Duration.ofMinutes(1).plus(Duration.ofSeconds(30));
		assertThrows(IllegalArgumentException.class,() -> n.createLine(null,null,duration));
	}
	
	@Test
	void testCreateLine7() {
		Duration duration = Duration.ZERO;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		assertThrows(IllegalArgumentException.class,() -> n.createLine(s1,s2,duration));
	}
	
	@Test
	void testCreateLine8() {
		Duration duration = Duration.ofMinutes(-1);
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		assertThrows(IllegalArgumentException.class,() -> n.createLine(s1,s2,duration));
	}
	
	@Test
	void testCreateLine9() {
		Duration duration = Duration.ofMinutes(5);
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		assertTrue(n.createLine(s1,s2,duration));
	}
	
	@Test
	void testCreateLine10() {
		Duration duration = Duration.ofSeconds(30);
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		n.addStation(s1);
		n.addStation(s2);
		assertTrue(n.createLine(s1,s2,duration));
	}
	
	/**
	 * Test adding a station with a valid station
	 */
	@Test
	void testAddStation1() {
		Station station = new Station(0,0,"Test");
		assertTrue(n.addStation(station));
	}
	
	/**
	 * Test adding a station with a null invalid station
	 */
	@Test
	void testAddStation2() {
		Station station = null;
		assertThrows(IllegalArgumentException.class, () -> n.addStation(station));
	}
	
	/**
	 * Test adding a station with a valid station twice
	 */
	@Test
	void testAddStation3() {
		Station station = new Station(0,0,"Test");
		n.addStation(station);
		assertFalse(n.addStation(station));
	}
	
	@Test
	void testCreateStation() {
		float latitude = 0.0f;
		float longitude = 0.0f;
		String name = "Test";
		Duration d = Duration.ofMinutes(1);
		n.createStation(latitude, longitude, name, d);
	}
	
	@Test
	void testAStar() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		assertNotNull(n.aStar(n.getGraph(), n.getVertex(n.getLines().get(0).get(0)), n.getVertex(n.getLines().get(0).get(4))));
	}
	
	@Test
	void testAStar1() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		assertThrows(IllegalArgumentException.class, () ->n.aStar(null, n.getGraph().getVertices().get(0), n.getGraph().getVertices().get(26)));
	}
	
	@Test
	void testAStar2() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		assertThrows(IllegalArgumentException.class, () ->n.aStar(n.getGraph(), null, n.getGraph().getVertices().get(26)));
	}
	
	@Test
	void testAStar3() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		assertThrows(IllegalArgumentException.class, () ->n.aStar(n.getGraph(), n.getGraph().getVertices().get(0), null));
	}
	
	@Test
	void testMultipleBreakPoint() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		List<Vertex<Station>> list = new ArrayList<Vertex<Station>>();
		for(int i=0;i<7;i++) {
			list.add(n.getGraph().getVertices().get(i));
		}
		n.multipleBreakPoints(n.getGraph(), list);
	}
	
	@Test
	void testMultipleBreakPoint1() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		List<Vertex<Station>> list = new ArrayList<Vertex<Station>>();
		for(String v : n.getLines().get(0)) {
			list.add(n.getVertex(v));
		}
		n.multipleBreakPoints(n.getGraph(), list);
	}
	
	@Test
	void testMultipleBreakPoint2() {
		assertThrows(IllegalArgumentException.class, () ->n.multipleBreakPoints(n.getGraph(), null));
	}
	
	@Test
	void testMultipleBreakPoint3() {
		List<Vertex<Station>> list = new ArrayList<Vertex<Station>>();
		assertThrows(IllegalArgumentException.class, () ->n.multipleBreakPoints(n.getGraph(), list));
	}
	
	@Test
	void testMultipleBreakPoint4() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		List<Vertex<Station>> list = new ArrayList<Vertex<Station>>();
		list.add(n.getGraph().getVertices().get(0));
		assertThrows(IllegalArgumentException.class, () ->n.multipleBreakPoints(n.getGraph(), list));
	}
	
	@Test
	void testMultipleBreakPoint5() {
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		List<Vertex<Station>> list = new ArrayList<Vertex<Station>>();
		list.add(n.getGraph().getVertices().get(0));
		list.add(n.getGraph().getVertices().get(3));
		n.multipleBreakPoints(n.getGraph(), list);
	}
	
	@Test 
	void testBuildSubGraph(){
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		Graph<Station> graph = n.buildSubGraph(n.getGraph());
		assertNotNull(graph);
		assertNotNull(graph.getEdges());
		assertNotNull(graph.getVertices());
	}
	
	@Test 
	void testBuildSubGraph1(){
		n.addStation(new Station(0,0,"Test"));
		n.addStation(new Station(0,0,"Test1",true));
		Graph<Station> graph = n.buildSubGraph(n.getGraph());
		assertEquals(1,graph.getVertices().size());
	}
	
	@Test 
	void testBuildSubGraph2(){
		assertThrows(IllegalArgumentException.class, () -> n.buildSubGraph(null));
	}
	
	@Test 
	void testBuildSubGraph3(){
		n.addStation(new Station(0,0,"Test"));
		n.addStation(new Station(0,0,"Test1"));
		n.createLine(new Station(0,0,"Test"), new Station(0,0,"Test1"), Duration.ofMinutes(5));
		n.createIncident(n.getVertex("Test").getEdges().get(0));
		Graph<Station> graph = n.buildSubGraph(n.getGraph());
		assertEquals(0,graph.getVertices().get(0).getEdges().size());
	}
	
	@Test
	void testCreateIncident1() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		Vertex<Station> from = new Vertex<Station>(s1);
		Vertex<Station> to = new Vertex<Station>(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		assertFalse(n.createIncident(e));
	}
	
	@Test
	void testCreateIncident2() {
		n.addStation(new Station(0,0,"Test"));
		n.addStation(new Station(0,0,"Test1"));
		n.createLine(new Station(0,0,"Test"), new Station(0,0,"Test1"), Duration.ofMinutes(5));
		assertTrue(n.createIncident(n.getVertex("Test").getEdges().get(0)));
	}
	
	@Test
	void testCreateIncident3() {
		Edge<Station> e = null;
		assertThrows(IllegalArgumentException.class, () ->n.createIncident(e));
	}
	
	@Test
	void testCreateIncident4() {
		Station e = null;
		assertThrows(IllegalArgumentException.class, () ->n.createIncident(e));
	}
	
	@Test
	void testCreateIncident5() {
		Station s1 = new Station(0,0,"Test");
		n.addStation(s1);
		assertTrue(n.createIncident(s1));
	}
	
	@Test
	void testCreateIncident6() {
		Station s1 = new Station(0,0,"Test");
		assertFalse(n.createIncident(s1));
	}
	
	@Test
	void testSolveIncident() {
		Edge<Station> e = null;
		assertThrows(IllegalArgumentException.class, () ->n.solveIncident(e));
	}
	
	@Test
	void testSolveIncident1() {
		Station e = null;
		assertThrows(IllegalArgumentException.class, () ->n.solveIncident(e));
	}
	
	@Test
	void testSolveIncident2() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		Vertex<Station> from = new Vertex<Station>(s1);
		Vertex<Station> to = new Vertex<Station>(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		assertFalse(n.solveIncident(e));
	}
	
	@Test
	void testSolveIncident3() {
		int num = 0;
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		Vertex<Station> from = new Vertex<Station>(s1);
		Vertex<Station> to = new Vertex<Station>(s2);
		Edge<Station> e = new Edge<Station>(num,from,to);
		n.addLine(e);
		assertFalse(n.solveIncident(e));
	}
	
	@Test
	void testSolveIncident4() {
		Station s1 = new Station(0,0,"Test");
		n.addStation(s1);
		n.createIncident(s1);
		assertTrue(n.solveIncident(s1));
	}
	
	@Test
	void testSolveIncident5() {
		Station s1 = new Station(0,0,"Test");
		assertFalse(n.solveIncident(s1));
	}
	
	@Test
	void testSolveIncident6() {
		n.addStation(new Station(0,0,"Test"));
		n.addStation(new Station(0,0,"Test1"));
		n.createLine(new Station(0,0,"Test"), new Station(0,0,"Test1"), Duration.ofMinutes(5));
		n.createIncident(n.getVertex("Test").getEdges().get(0));
		assertTrue(n.solveIncident(n.getVertex("Test").getEdges().get(0)));
	}
	
	@Test
	void testSolveAllIncidents() {
		n.solveAllIncidents();
	}
	
	@Test
	void testSolveAllIncidents1() {
		Station s1 = new Station(0,0,"Test");
		n.addStation(s1);
		n.solveAllIncidents();
	}
	
	@Test
	void testSolveAllIncidents2() {
		Station s1 = new Station(0,0,"Test",true);
		n.addStation(s1);
		n.solveAllIncidents();
		assertFalse(n.getVertex(s1).getValue().isIncident());
	}
	
	@Test
	void testFindNearestStation() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(null, null));
	}
	
	@Test
	void testFindNearestStation1() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(1.0f, null));
	}
	
	@Test
	void testFindNearestStation3() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(null, 1.0f));
	}
	
	@Test
	void testFindNearestStation4() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(1.0f, 1.0f));
	}
	
	@Test
	void testFindNearestStation5() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(92.0f, 1.0f));
	}
	
	@Test
	void testFindNearestStation6() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(92.0f, 92.0f));
	}
	
	@Test
	void testFindNearestStation7() {
		assertThrows(IllegalArgumentException.class, () ->n.findNearestStation(1.0f, 92.0f));
	}
	
	@Test
	void testFindNearestStation8() {
		Station s1 = new Station(0,0,"Test",true);
		n.addStation(s1);
		Station s2 = new Station(25,25,"Test1",true);
		n.addStation(s2);
		n.findNearestStation(1.0f, 1.0f);
	}
	
	@Test
	void testClear() {
		Station s1 = new Station(0,0,"Test",true);
		n.addStation(s1);
		Station s2 = new Station(25,25,"Test1",true);
		n.addStation(s2);
		assertNotEquals(0,n.getGraph().getVertices().size());
		n.clear();
		assertEquals(0,n.getGraph().getVertices().size());
	}
	
	/*@Test
	void testSave() {
		n.save("src/test/resources/testSaveNetwork.csv");
	}*/
	
	@Test
	void testContains() {
		Station s1 = new Station(0,0,"Test",true);
		n.addStation(s1);
		assertTrue(n.contains(s1));
	}
	
	@Test
	void testContains1() {
		assertFalse(n.contains(new Station(0,0,"Test",true)));
	}
	
	@Test
	void testContains2() {
		Station s1 = new Station(0,0,"Test",true);
		n.addStation(s1);
		Station s2 = new Station(0,0,"Test1",true);
		n.addStation(s2);
		assertTrue(n.contains(s1));
	}
	
	/*@Test
	void testLoad() {
		n.save("src/test/resources/testSaveNetwork.csv");
		Network.load("src/test/resources/testSaveNetwork.csv");
	}
	
	@Test
	void testLoad1() {
		assertThrows(IllegalArgumentException.class, () ->Network.load(""));
	}
	
	@Test
	void testLoad2() {
		Network.load("ezcfezfz.txt");
	}*/
	
	@Test
	void testSetGraph1() {
		assertThrows(IllegalArgumentException.class, () -> n.setGraph(null));
	}
	
	@Test
	void testSetGraph2() {
		assertThrows(IllegalArgumentException.class, () -> n.setGraph(new Graph<Station>()));
	}
	
	@Test
	void testSetGraph3() {
		n.setGraph(new Graph<Station>(TYPE.DIRECTED));
	}
	
	@Test
	void testGetVertex() {
		n.getVertex("Test");
	}
	
	@Test
	void testGetVertex1() {
		Station s1 = new Station(0,0,"Test",true);
		n.addStation(s1);
		Station s2 = new Station(0,0,"Test1",true);
		n.addStation(s2);
		n.getVertex("Test2");
	}
	
	@Test
	void testGetLinesWithIncidents() {
		n.getLinesWithIncidents();
	}
}

