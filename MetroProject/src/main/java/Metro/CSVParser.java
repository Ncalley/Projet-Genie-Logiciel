package Metro;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a parser for our specific .csv file containing all informations for our stations
 * latitudes contains the latitude of each station
 * Longitudes contains the longitude of each station
 * names contains every names of every station on the network
 * Lines contains all the lines of the network, it's a list of lists of station's names and between each of these stations
 * there is a corresponding line.
 * example : there is a list with the names "A","B" and "C", if these 3 are stations of the graph, we will insert the
 * following lines : "A-B" and "B-C"
 * @author Nicolas
 *
 */
public class CSVParser {
	//latitude -> longitude
	private List<Float> latitudes = new ArrayList<Float>();
	private List<Float> longitudes = new ArrayList<Float>();
	private List<String> names = new ArrayList<String>();
	private ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
	
	/**
	 * Parse the file defined by it's given filename and enters the extracted data on coordinates and names
	 * @param fileName
	 * 		the name and relative path to the file, ex: "src/main/resources/toto.csv"
	 */
	public void parseStations(String fileName){
		if(!fileName.contains(".csv")) {
			throw new IllegalArgumentException("The given filename is invalid, it must be a .csv");
		}
		
		try{
            // On lit le fichier
            InputStream ips= new FileInputStream(fileName); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            try (BufferedReader br = new BufferedReader(ipsr)) {
                String ligne;
                String[] splitLine;
                boolean firstLine = true;
                while ((ligne=br.readLine())!=null){ 
                	if(!firstLine) {
	                    splitLine = ligne.split(";");
	                    this.names.add(splitLine[1]);
	                    this.latitudes.add(Float.parseFloat(splitLine[4]));
	                    this.longitudes.add(Float.parseFloat(splitLine[5]));
                	}else {
                		firstLine=false;
                	}
                    
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
		}		
		catch (FileNotFoundException e){
			System.out.println("This file wasn't found : " + fileName);
        }
	}

	/**
	 * Parse the file
	 * @param fileName
	 */
	public void parseLines(String fileName) {
		if(!fileName.contains(".csv")) {
			throw new IllegalArgumentException("The given filename is invalid, it must be a .csv");
		}
		
		try{
            // On lit le fichier
            InputStream ips= new FileInputStream(fileName); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            try (BufferedReader br = new BufferedReader(ipsr)) {
                String ligne;
                String[] splitLine;
                while ((ligne=br.readLine())!=null){
                	ArrayList<String> res = new ArrayList<String>();
	                splitLine = ligne.split(";");
	                for(String elt : splitLine) {
	                	res.add(elt);
	                }
                    lines.add(res);
                }
	        }catch (Exception e){
	            e.printStackTrace();
	        }
		}
        catch (FileNotFoundException e){
        	System.out.println("This file wasn't found : " + fileName);
        }
	}		
	

	public List<String> getNames() {
		return names;
	}


	public List<Float> getLatitudes() {
		return latitudes;
	}


	public List<Float> getLongitudes() {
		return longitudes;
	}

	public ArrayList<ArrayList<String>> getLines() {
		return lines;
	}
}
