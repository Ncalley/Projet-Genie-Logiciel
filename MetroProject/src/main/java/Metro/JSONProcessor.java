package Metro;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class converts objects into json files to save them
 * Can also retrieve objects from json files
 * @author Nicolas
 *
 */
public class JSONProcessor {
	
	/**
	 * Create a json file to save an object
	 * @param obj
	 * 		The object we want to save
	 * @param filename
	 * 		The name of the file we want to write on
	 * @throws IOException
	 */
	public static void serialize(Object obj, String filename) throws IOException{
		Writer out = new FileWriter(filename);
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		out.write(gson.toJson(obj));
		out.close();
	}
	
	/**
	 * Take a filename of a json file containing an object and returns the object
	 * @param filename
	 * 		Name of the designed file
	 * @return Object
	 * 		The object that was previously saved in the file
	 * @throws FileNotFoundException
	 */
	public static Object deserialize(String filename) throws FileNotFoundException{
		Reader in = new BufferedReader(new FileReader(filename));
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.fromJson(in, Object.class);
	}
}
