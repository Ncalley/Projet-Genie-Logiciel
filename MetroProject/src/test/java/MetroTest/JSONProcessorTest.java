package MetroTest;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	void testFileNotFound() {
		assertThrows(FileNotFoundException.class, () -> JSONProcessor.deserialize("lol.txt"));
	}
	
	/**
	 * Throws an exception if there is no argument given
	 */
	@Test
	void testNullPointer() {
		assertThrows(NullPointerException.class, () -> JSONProcessor.deserialize(null));
	}
	
	@Test
	void testSerialize() {
		String s = "This is a String";
		try {
			JSONProcessor.serialize(s, "src/test/resources/TestSave.txt");
		}catch(IOException e) {
			System.out.println("Test serialize fail");
		}
	}
	
	@Test
	void testDeserialize() {
		String s = "This is a String";
		String res = "";
		try {
			JSONProcessor.serialize(s, "src/test/resources/TestSave.txt");
		}catch(IOException e) {
			System.out.println("Serialization fail");
		}
		try {
			res = (String)JSONProcessor.deserialize("src/test/resources/TestSave.txt");
		}catch(FileNotFoundException e) {
			System.out.println("Deserialization fail");
		}
		assertEquals("This is a String",res);
	}
}
