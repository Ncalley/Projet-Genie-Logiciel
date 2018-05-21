package GeoDataSourceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import GeoDataSource.DistanceCalculator;

@RunWith(JUnitPlatform.class)
@DisplayName("DistanceCalculatorTest")
public class DistanceCalculatorTest {
	
	@Test
	void distanceTest1() {
		assertEquals(DistanceCalculator.distance(0, 0, 0, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest2() {
		assertEquals(DistanceCalculator.distance(1, 0, 1, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest3() {
		assertEquals(DistanceCalculator.distance(0, 1, 0, 1, "K"),0.0);
	}
	
	@Test
	void distanceTest4() {
		assertEquals(DistanceCalculator.distance(0, 0, 0, 0, "N"),0.0);
	}
	
	@Test
	void distanceTest5() {
		assertEquals(DistanceCalculator.distance(0, 0, 0, 0, "M"),0.0);
	}
	
	@Test
	void distanceTest6() {
		assertNotEquals(DistanceCalculator.distance(1, 0, 0, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest7() {
		assertNotEquals(DistanceCalculator.distance(0, 1, 0, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest8() {
		assertNotEquals(DistanceCalculator.distance(0, 0, 1, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest9() {
		assertNotEquals(DistanceCalculator.distance(0, 0, 0, 1, "K"),0.0);
	}
	
	@Test
	void distanceTest10() {
		assertNotEquals(DistanceCalculator.distance(1, 1, 0, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest11() {
		assertNotEquals(DistanceCalculator.distance(-1, -1, 0, 0, "K"),0.0);
	}
	
	@Test
	void distanceTest12() {
		assertEquals(DistanceCalculator.distance(-1, 0, -1, 0, "K"),0.0);
	}
}
