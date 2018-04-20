package MetroTest;



import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import Metro.JSONProcessor;

@RunWith(JUnitPlatform.class)
@DisplayName("JSONProcessorTest")
public class JSONProcessorTest {
	
	/**
	 * Throws an exception if it doesn't find a file
	 */
	@Test
	void FileNotFound() {
		Throwable exception = assertThrows(FileNotFoundException.class, () -> {
			JSONProcessor.deserialize("lol.txt");
		});
	}
	
	/**
	 * Throws an exception if there is no argument given
	 */
	@Test
	void NullPointer() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
			JSONProcessor.deserialize(null);
		});
	}
}
