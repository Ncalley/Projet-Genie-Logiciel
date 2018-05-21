package MetroTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import Metro.CSVParser;

@RunWith(JUnitPlatform.class)
@DisplayName("CSVParserTest")
public class CSVParserTest {
	CSVParser p;

	@Test
	@BeforeEach
	void testCSVParser() {
		p = new CSVParser();
	}
	
	@Test
	void testParseStations1() {
		assertThrows(NullPointerException.class, () -> p.parseStations(null));
	}
	
	@Test
	void testParseStations2() {
		p.parseStations("lol.csv");
	}
	
	@Test
	void testParseStations3() {
		p.parseStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		assertNotEquals(0,p.getNames().size());
	}
	
	@Test
	void testParseStations4() {
		assertThrows(IllegalArgumentException.class, () -> p.parseStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.txt"));
	}
	
	@Test
	void testParseLines1() {
		assertThrows(NullPointerException.class, () -> p.parseLines(null));
	}
	
	@Test
	void testParseLines2() {
		p.parseLines("lol.csv");
	}
	
	@Test
	void testParseLines3() {
		p.parseLines("src/test/resources/lignes.csv");
		assertNotEquals(0,p.getLines().size());
	}
	
	@Test
	void testParseLines4() {
		assertThrows(IllegalArgumentException.class, () -> p.parseLines("src/test/resources/lignes.txt"));
	}
	
	@Test
	void testGetLatitudes() {
		p.getLatitudes();
	}
	
	@Test
	void testGetLongitudes() {
		p.getLongitudes();
	}
}
