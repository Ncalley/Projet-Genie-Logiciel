package MetroTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import Metro.DataChecker;
import Metro.Station;

@RunWith(JUnitPlatform.class)
@DisplayName("JSONProcessorTest")
public class DataCheckerTest {

	/**
	 * Test of checkNull with a not null object
	 */
	@Test
	void testCheckNull1() {
		assertFalse(DataChecker.checkNull(new Integer(42)));
	}
	
	/**
	 * Test of checkNull with a null entry
	 */
	@Test
	void testCheckNull2() {
		assertTrue(DataChecker.checkNull(null));
	}
	
	/**
	 * Test of checkNulls with a null entry
	 */
	@Test
	void testCheckNulls1() {
		assertTrue(DataChecker.checkNulls(null));
	}
	
	/**
	 * Test of checkNulls with at least 1 null element in the list
	 */
	@Test
	void testCheckNulls2() {
		Object[] o = {2,5f,"Test",null,"Test2"};
		assertTrue(DataChecker.checkNulls(o));
	}
	
	/**
	 * Test of checkNulls with a list full of null elements
	 */
	@Test
	void testCheckNulls3() {
		Object[] o = {null,null,null,null,null};
		assertTrue(DataChecker.checkNulls(o));
	}
	
	/**
	 * Test of checkNulls with a list without null elements
	 */
	@Test
	void testCheckNulls4() {
		Object[] o = {2,5f,"Test",Duration.ZERO,new Station(0,0,"Test")};
		assertFalse(DataChecker.checkNulls(o));
	}
	
	/**
	 * Test of checkNull with null casted as an object
	 */
	@Test
	void testCheckNull3() {
		assertTrue(DataChecker.checkNull((Object)null));
	}
	
	/**
	 * Test of checkClass with twice the same object
	 */
	@Test
	void testCheckClass1() {
		assertTrue(DataChecker.checkClass(new Integer(1), new Integer(1)));
	}
	
	/**
	 * Test of checkClass with two different objects of the same class
	 */
	@Test
	void testCheckClass2() {
		assertTrue(DataChecker.checkClass(new Integer(1), new Integer(15)));
	}
	
	/**
	 * Test of checkClass with an object and null
	 */
	@Test
	void testCheckClass3() {
		assertFalse(DataChecker.checkClass(new Integer(1), null));
	}
	
	/**
	 * Test of checkClass with two null
	 */
	@Test
	void testCheckClass4() {
		assertFalse(DataChecker.checkClass(null, null));
	}
	
	/**
	 * Test of checkClass with two objects of different classes
	 */
	@Test
	void testCheckClass5() {
		assertFalse(DataChecker.checkClass(new Integer(1), "Test"));
	}
	
	/**
	 * Test of checkClass's reflexivity
	 */
	@Test
	void testCheckClass6() {
		assertFalse(DataChecker.checkClass("Test" , new Integer(1)));
	}
	
	/**
	 * Test of checkClass with a primitive type and it's object counterpart
	 * The primitive type is automatically wrapped as an object when passed as an argument
	 * thus leading to the function to consider them from the same class.
	 */
	@Test
	void testCheckClass7() {
		assertTrue(DataChecker.checkClass(1 , new Integer(1)));
	}
	
	/**
	 * Test of checkCoordinates with a valid positive number
	 */
	@Test
	void testCheckCoordinates1() {
		assertTrue(DataChecker.checkCoordinate(1));
	}
	
	/**
	 * Test of checkCoordinates with a valid negative number
	 */
	@Test
	void testCheckCoordinates2() {
		assertTrue(DataChecker.checkCoordinate(-1));
	}
	
	/**
	 * Test of checkCoordinates with a 0
	 */
	@Test
	void testCheckCoordinates3() {
		assertTrue(DataChecker.checkCoordinate(0));
	}
	
	/**
	 * Test of checkCoordinates with a negative invalid value
	 */
	@Test
	void testCheckCoordinates4() {
		assertFalse(DataChecker.checkCoordinate(-100));
	}
	
	/**
	 * Test of checkCoordinates with a positive invalid value
	 */
	@Test
	void testCheckCoordinates5() {
		assertFalse(DataChecker.checkCoordinate(100));
	}
	
	/**
	 * Test of checkCoordinates with the positive edge of the valid values (included)
	 */
	@Test
	void testCheckCoordinates6() {
		assertTrue(DataChecker.checkCoordinate(90));
	}
	
	/**
	 * Test of checkCoordinates with the negative edge of the valid values (included)
	 */
	@Test
	void testCheckCoordinates7() {
		assertTrue(DataChecker.checkCoordinate(-90));
	}
	
	/**
	 * Test of checkCoordinates with a valid Integer
	 */
	@Test
	void testCheckCoordinates8() {
		assertTrue(DataChecker.checkCoordinate(new Integer(5)));
	}
	
	/**
	 * Test of checkCoordinates with something weird
	 * in theory we cannot pass something null to this function and it should be handled anyway 
	 * but for some reason it's not.
	 * this function doesn't throws exception, it's thrown directly by Integer.getValue() itself.
	 * the function needs some arrangement on this point.
	 
	@Test
	void testCheckCoordinates9() {
		assertThrows(NullPointerException.class, () -> {
			coordinate = (Double)null;
			DataChecker.checkCoordinate(coordinate);
		});
	}*/
	
	/**
	 * Test of checkDuration with a valid duration
	 */
	@Test
	void testCheckDuration1() {
		assertTrue(DataChecker.checkDuration(Duration.ofMinutes(1)));
	}
	
	/**
	 * Test of checkDuration with a negative duration
	 */
	@Test
	void testCheckDuration2() {
		assertFalse(DataChecker.checkDuration(Duration.ofMinutes(-11)));
	}
	
	/**
	 * Test of checkDuration with a null duration
	 */
	@Test
	void testCheckDuration3() {
		assertFalse(DataChecker.checkDuration(Duration.ZERO));
	}
	
	/**
	 * Test of checkDuration with a null argument
	 */
	@Test
	void testCheckDuration4() {
		assertFalse(DataChecker.checkDuration(null));
	}
	
	/**
	 * Test of checkString with a valid argument
	 */
	@Test
	void testCheckString1() {
		assertTrue(DataChecker.checkString("Test"));
	}
	
	/**
	 *  Test of checkString with a blank string (invalid)
	 */
	@Test
	void testCheckString2() {
		assertFalse(DataChecker.checkString(""));
	}
	
	/**
	 *  Test of checkString with a null argument
	 */
	@Test
	void testCheckString3() {
		assertFalse(DataChecker.checkString(null));
	}
	
	/**
	 *  Test of checkString with a null argument wrapped as a String
	 */
	@Test
	void testCheckString4() {
		assertFalse(DataChecker.checkString((String)null));
	}
}
