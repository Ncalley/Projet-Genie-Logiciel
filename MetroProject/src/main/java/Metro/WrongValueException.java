package Metro;

/**
 * Exception thrown when a program finds a wrong value
 * @author Nicolas
 *
 */
public class WrongValueException extends Exception{
	

	/**
	 * Serial ID of the exception, automatically generated
	 */
	private static final long serialVersionUID = 3155687534811337747L;

	//constructor
	public WrongValueException(String message) {
		super(message);
	}
}
