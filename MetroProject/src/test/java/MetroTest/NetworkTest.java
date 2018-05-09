package MetroTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


import Metro.Network;
import Metro.Station;

@RunWith(JUnitPlatform.class)
@DisplayName("JSONProcessorTest")
public class NetworkTest {
	//Initialization of the Network
	Network n = new Network();
	
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
		assertThrows(IllegalArgumentException.class, () -> {
			n.addStation(station);
		});
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
	
	
	
	/*@Test
	void testToString() {
		StringBuffer s = new StringBuffer();
		s.append("");
		for(Station elt: n.getStations()) {
			s.append(elt+"\n");
		}
		StringBuffer l = new StringBuffer();
		l.append("");
		for(Line elt: n.getLines()) {
			l.append(elt+"\n");
		}
		String res = "Network [Map=" + n.getMap().toString() 
				+ ",\n Stations=" + s.toString() 
				+ ", Lines=" + l.toString()
				+ "]";
		assertEquals(n.toString(),res);
	}*/
}

