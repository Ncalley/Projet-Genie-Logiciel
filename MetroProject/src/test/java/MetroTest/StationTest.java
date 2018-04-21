package MetroTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import Metro.Station;

@RunWith(JUnitPlatform.class)
@DisplayName("JSONProcessorTest")
public class StationTest {
	
	/**
	 * Test of Station's creation with all legal arguments
	 */
	@Test
	void testStationName1() {
		new Station(0,0,"Test");
	}
	
	/**
	 *  Test of Station's creation with the name null
	 */
	@Test
	void testStationName2() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,null);
		});
		assertEquals(e.getMessage(), "Name can't be null or blank");
	}
	
	/**
	 * Test of Station's creation with the name blank
	 */
	@Test
	void testStationName3 () {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,"");
		});
		assertEquals(e.getMessage(), "Name can't be null or blank");
	}
	
}
