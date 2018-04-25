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

import Metro.Line;
import Metro.Station;

@RunWith(JUnitPlatform.class)
@DisplayName("JSONProcessorTest")
public class LineTest {

	/**
	 * Test of Line's creation with every valid arguments
	 */
	@Test
	void testLigne() {
		new Line("Test1","Test2");
	}
	
	/**
	 * Test of Line's creation with the first name null
	 */
	@Test
	void testName1() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line(null,"Test2");
		});
		assertEquals(e.getMessage(), "At least one argument is null");
	}
	
	/**
	 * Test of Line's creation with the second name null
	 */
	@Test
	void testName2() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("Test1",null);
		});
		assertEquals(e.getMessage(), "At least one argument is null");
	}
	
	/**
	 * Test of Line's creation with the first name blank
	 */
	@Test
	void testName3() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("","Test2");
		});
		assertEquals(e.getMessage(), "Name of the stations can't be null or blank or the same");
	}
	
	/**
	 * Test of Line's creation with the second name blank
	 */
	@Test
	void testName4() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("Test1","");
		});
		assertEquals(e.getMessage(), "Name of the stations can't be null or blank or the same");
	}
	
	/**
	 * Test of Line's creation with twice the same name
	 */
	@Test
	void testName5() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("Test1","Test1");
		});
		assertEquals(e.getMessage(), "Name of the stations can't be null or blank or the same");
	}
	
	/**
	 * Test of Line's creation with a valid duration
	 */
	@Test
	void testDuration1() {
		new Line("Test1","Test2",Duration.ofMinutes(1));
	}
	
	/**
	 * Test of Line's creation with a negative duration
	 */
	@Test
	void testDuration2() {
		Duration duration = Duration.ofMinutes(-1);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("Test1","Test2",duration);
		});
		assertEquals(e.getMessage(), "Invalid duration : "+duration.toString());
	}
	
	/**
	 * Test of Line's creation with a duration that equals zero
	 */
	@Test
	void testDuration3() {
		Duration duration = Duration.ZERO;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("Test1","Test2",duration);
		});
		assertEquals(e.getMessage(), "Invalid duration : "+duration.toString());
	}
	
	/**
	 * Test of Line's creation with a null duration
	 */
	@Test
	void testDuration4() {
		Duration duration = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			new Line("Test1","Test2",duration);
		});
		assertEquals(e.getMessage(), "At least one argument is null");
	}
	
	/**
	 * Test of Line's creation with an incident
	 */
	@Test
	void testIncident1() {
		Line l = new Line("Test1","Test2",true);
		assertTrue(l.isIncident());
	}
	
	/**
	 * Test of Line's creation with an incident
	 */
	@Test
	void testIncident2() {
		Line l = new Line("Test1","Test2",false);
		assertFalse(l.isIncident());
	}
	
	/**
	 * Test of the valid hashcode function
	 */
	@Test
	void testHashCode() {
		Line l = new Line("Test1","Test2");
		assertEquals(l.hashCode(),-1712604254);
	}
	
	/**
	 * Test of equals with valid arguments
	 */
	@Test
	void testEquals1() {
		Line l1 = new Line("Test1","Test2");
		Line l2 = new Line("Test1","Test2");
		assertTrue(l1.equals(l2));
	}
	
	/**
	 * Test of equals with reverted arguments
	 */
	@Test
	void testEquals2() {
		Line l1 = new Line("Test1","Test2");
		Line l2 = new Line("Test2","Test1");
		assertTrue(l1.equals(l2));
	}
	
	/**
	 * Test of equals with wrong arguments
	 */
	@Test
	void testEquals3() {
		Line l1 = new Line("Test1","Test2");
		Line l2 = new Line("Test1","Test3");
		assertFalse(l1.equals(l2));
	}
	
	/**
	 * Test of equals with the same object
	 */
	@Test
	void testEquals4() {
		Line l1 = new Line("Test1","Test2");
		Line l2 = l1;
		assertTrue(l1.equals(l2));
	}
	
	/**
	 * Test of equals with a null object
	 */
	@Test
	void testEquals5() {
		Line l1 = new Line("Test1","Test2");
		Line l2 = null;
		assertFalse(l1.equals(l2));
	}
	
	/**
	 * Test of equals with an object of a different class
	 */
	@Test
	void testEquals6() {
		Line l1 = new Line("Test1","Test2");
		Integer l2 = new Integer(1);
		assertFalse(l1.equals(l2));
	}
	
	/**
	 * Test of the toString method
	 */
	@Test
	void testToString() {
		Line l1 = new Line("Test1","Test2");
		assertEquals(l1.toString(),"Line [STATION1=" + l1.getStation1() 
				+ ", STATION2=" + l1.getStation2() 
				+ ", incident=" + l1.isIncident() 
				+ ", waitingTime=" + l1.getWaitingTime() + "]");
	}
}
