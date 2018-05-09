package MetroTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

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
	void testStation() {
		new Station(0,0,"Test");
	}
	
	/**
	 *  Test of Station's creation with the name null
	 */
	@Test
	void testName1() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,null);
		});
		assertEquals(e.getMessage(), "At least one argument is null");
	}
	
	/**
	 * Test of Station's creation with the name blank
	 */
	@Test
	void testName2 () {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,"");
		});
		assertEquals(e.getMessage(), "Name can't be null or blank");
	}
	
	/**
	 * Test of Station's creation with a negative invalid latitude
	 */
	@Test
	void testCoordinates1() {
		float latitude = -91;
		float longitude = 0;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(latitude,longitude,"Test");
		});
		assertEquals(e.getMessage(), "Invalid coordinate's format\nLatitude = "
				+ latitude
				+ "\nLongitude= "
				+ longitude
				+ "\nCoordinates must be between -90 and 90 included");
	}
	
	/**
	 * Test of Station's creation with a positive invalid latitude
	 */
	@Test
	void testCoordinates2() {
		float latitude = 91;
		float longitude = 0;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(latitude,longitude,"Test");
		});
		assertEquals(e.getMessage(), "Invalid coordinate's format\nLatitude = "
				+ latitude
				+ "\nLongitude= "
				+ longitude
				+ "\nCoordinates must be between -90 and 90 included");
	}
	
	/**
	 * Test of Station's creation with a negative invalid longitude
	 */
	@Test
	void testCoordinates3() {
		float latitude = 0;
		float longitude = -91;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(latitude,longitude,"Test");
		});
		assertEquals(e.getMessage(), "Invalid coordinate's format\nLatitude = "
				+ latitude
				+ "\nLongitude= "
				+ longitude
				+ "\nCoordinates must be between -90 and 90 included");
	}
	
	/**
	 * Test of Station's creation with a positive invalid longitude
	 */
	@Test
	void testCoordinates4() {
		float latitude = 0;
		float longitude = 91;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(latitude,longitude,"Test");
		});
		assertEquals(e.getMessage(), "Invalid coordinate's format\nLatitude = "
				+ latitude
				+ "\nLongitude= "
				+ longitude
				+ "\nCoordinates must be between -90 and 90 included");
	}
	
	/**
	 * Test of Station's creation with a valid duration
	 */
	@Test
	void testDuration1(){
		Duration duration = Duration.ofMinutes(1).plus(Duration.ofSeconds(30));
		new Station(0,0,"Test",duration);
	}
	
	/**
	 * Test of Station's creation with a null duration
	 */
	@Test
	void testDuration2(){
		Duration duration = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,"Test",duration);
		});
		assertEquals(e.getMessage(), "At least one argument is null");
		
	}
	
	/**
	 * Test of Station's creation with a negative duration
	 */
	@Test
	void testDuration3(){
		Duration duration = Duration.ofMinutes(-42);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,"Test",duration);
		});
		assertEquals(e.getMessage(), "Invalid duration : " + duration.toString());
	}
	
	/**
	 * Test of Station's creation with a duration with a value that equals zero
	 */
	@Test
	void testDuration4(){
		Duration duration = Duration.ZERO;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Station(0,0,"Test",duration);
		});
		assertEquals(e.getMessage(), "Invalid duration : " + duration.toString());
	}
	
	/**
	 * Test of Station's creation with an incident
	 */
	@Test
	void testIncident1() {
		new Station(0,0,"Test",Duration.ofMinutes(1).plus(Duration.ofSeconds(30)),true);
	}
	
	/**
	 * Test of Station's creation without incident
	 */
	@Test
	void testIncident2() {
		new Station(0,0,"Test",Duration.ofMinutes(1).plus(Duration.ofSeconds(30)),false);
	}
	
	/**
	 * Test of Station's creation with an incident
	 */
	@Test
	void testIncident3() {
		new Station(0,0,"Test",true);
	}
	
	/**
	 * Test of Station's creation without incident
	 */
	@Test
	void testIncident4() {
		new Station(0,0,"Test",false);
	}
	
	/**
	 * Test of getWaitingTime method
	 */
	@Test
	void testGetWaitingTime() {
		Duration duration = Duration.ofSeconds(42);
		Station s = new Station(0,0,"Test",duration);
		assertEquals(s.getWaitingTime(),duration);
	}
	
	/**
	 * Test of getLatitude method
	 */
	@Test
	void testGetLatitude() {
		float latitude = 5;
		Station s = new Station(latitude,0,"Test");
		assertEquals(s.getLatitude(),latitude);
	}
	
	/**
	 * Test of getLongitude method
	 */
	@Test
	void testGetLongitude() {
		float longitude = 5;
		Station s = new Station(0,longitude,"Test");
		assertEquals(s.getLongitude(),longitude);
	}
	
	/**
	 * Test of getName method
	 */
	@Test
	void testGetName() {
		String name = "Test";
		Station s = new Station(0,0,name);
		assertEquals(s.getName(),name);
	}
	
	/**
	 * Test of setWaitingTime method with a valid duration
	 */
	@Test
	void testSetWaitingTime1() {
		Duration duration = Duration.ofSeconds(42);
		Station s = new Station(0,0,"Test");
		s.setWaitingTime(duration);
		assertEquals(duration,s.getWaitingTime());
	}
	
	/**
	 * Test of setWaitingTime method with a negative duration
	 */
	@Test
	void testSetWaitingTime2() {
		Duration duration = Duration.ofSeconds(-42);
		Station s = new Station(0,0,"Test");
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			s.setWaitingTime(duration);
		});
		assertEquals(e.getMessage(), "Invalid duration : " + duration.toString());
	}
	
	/**
	 * Test of setWaitingTime method with a duration that equals zero
	 */
	@Test
	void testSetWaitingTime3() {
		Duration duration = Duration.ZERO;
		Station s = new Station(0,0,"Test");
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			s.setWaitingTime(duration);
		});
		assertEquals(e.getMessage(), "Invalid duration : " + duration.toString());
	}
	
	/**
	 * Test of setWaitingTime method with a null duration
	 */
	@Test
	void testSetWaitingTime4() {
		Duration duration = null;
		Station s = new Station(0,0,"Test");
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			s.setWaitingTime(duration);
		});
		assertEquals(e.getMessage(), "At least one argument is null");
	}
	
	/**
	 * Test of the valid hashcode function
	 */
	@Test
	void testHashCode() {
		Station s = new Station(0,0,"Test");
		assertEquals(s.hashCode(),2603217);
	}
	
	/**
	 * Test of equals with valid arguments
	 */
	@Test
	void testEquals1() {
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test");
		assertTrue(s1.equals(s2));
	}
	
	/**
	 * Test of equals with a null
	 */
	@Test
	void testEquals2() {
		Station s1 = new Station(0,0,"Test");
		Station s2 = null;
		assertFalse(s1.equals(s2));
	}
	
	/**
	 * Test of equals with an object wrong
	 */
	@Test
	void testEquals3() {
		Station s1 = new Station(0,0,"Test");
		Integer s2 = new Integer(1);
		assertFalse(s1.equals(s2));
	}
	
	/**
	 * Test of equals with two differen names
	 */
	@Test
	void testEquals4() {
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,0,"Test1");
		assertFalse(s1.equals(s2));
	}
	
	/**
	 * Test of equals with the same name and two different positions
	 */
	@Test
	void testEquals5() {
		Station s1 = new Station(0,0,"Test");
		Station s2 = new Station(0,1,"Test");
		assertTrue(s1.equals(s2));
	}
	
	/**
	 * Test of equals with the same object
	 */
	@Test
	void testEquals6() {
		Station s1 = new Station(0,0,"Test");
		Station s2 = s1;
		assertTrue(s1.equals(s2));
	}
	
	/**
	 * Test of ToString
	 */
	@Test
	void testToString() {
		Station s1 = new Station(0,0,"Test");
		assertEquals(s1.toString(),"Station [LATITUDE=" + s1.getLatitude() 
				+ ", LONGITUDE=" + s1.getLongitude() 
				+ ", name=" + s1.getName() 
				+ ", waitingTime=" + s1.getWaitingTime() + "]");
	}
}
