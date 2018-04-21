package Metro;

import java.time.Duration;

/**
 * Class made to check different values to ensure that the format of the data is usable for it's purpose
 * @author Nicolas
 *
 */
public class DataChecker {

	/**
	 * Check if an object is null
	 * @param o
	 * 		The object we want to test
	 * @return
	 * 		- True if the object is null
	 * 		- False if not
	 */
	public static boolean checkNull(final Object o) {
		if (o == null) {return true;}
		return false;
	}
	
	/**
	 * Check if two objects have the same class
	 * None of them shall be Null
	 * @param o1
	 * 		the first object to test
	 * @param o2
	 * 		the second object to test
	 * @return
	 * 		- True is they have the same class and none of them is null
	 * 		- False otherwise
	 */
	public static boolean checkClass(final Object o1, final Object o2) {
		if(!checkNull(o1) && !checkNull(o2) && o1.getClass().equals(o2.getClass())) {return true;}
		return false;
	}
	
	/**
	 * Check if the given float may be a coordinate.
	 * By definition a coordinate has always a value between 90° and -90° included
	 * @param coordinate
	 * 		The given float we need to test
	 * @return
	 * 		- True if the float has an eligible value (between -90 and 90)
	 * 		- False if not or if it's null
	 */
	public static boolean checkCoordinate(final double coordinate) {
		if (checkNull(coordinate)) {return false;}
		if (coordinate <= 90 && coordinate >= -90) {return true;}
		return false;
	}
	
	/**
	 * Check if the duration is valid.
	 * A valid duration is strictly positive
	 * @param duration
	 * 		The given duration we need to test
	 * @return
	 * 		- True if the duration is strictly positive and not null
	 * 		- False otherwise
	 */
	public static boolean checkDuration(final Duration duration) {
		if (!checkClass(duration, Duration.ofSeconds(1))) {return false;}
		if (duration.isNegative()) {return false;}
		if (duration.isZero()) {return false;}
		return true;
	}
	
	/**
	 * Check if the entered string has a value and isn't blank
	 * @param string
	 * 		The given string we need to test
	 * @return
	 * 		- False if null or ""
	 * 		- True otherwise
	 */
	public static boolean checkString(final String string) {
		if(checkNull(string)) {return false;}
		if(string.equals("")) {return false;}
		return true;
	}
}
