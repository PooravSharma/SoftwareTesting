package tests;

//import the planner class so we can reference the code
import au.edu.sccs.csp3105.NBookingPlanner.Planner;

import static org.junit.jupiter.api.Assertions.*;

//THe JUnit5 Libraries required 
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The point of this test class is to ensure the start hour the the program is working as intended
 */
public class BlackBoxStartHourInput {

	String isValidStart = "This is a valid start time!"; // if the 
	
    String isNotValidStart = "This is not a valid start time";
	
	String overBoundary = "Enter a number between 0 and 23";
	
	
	@Test
	void testValidStartTime() {
		
		assertEquals(0, 0, isValidStart);
		assertEquals(1, 1, isValidStart);
		assertEquals(2, 2, isValidStart);
		assertEquals(3, 3, isValidStart);
		assertEquals(4, 4, isValidStart);
		assertEquals(5, 5, isValidStart);
		assertEquals(6, 6, isValidStart);
		assertEquals(7, 7, isValidStart);
		assertEquals(8, 8, isValidStart);
		assertEquals(9, 9, isValidStart);
		assertEquals(10, 10, isValidStart);
		assertEquals(11, 11, isValidStart);
		assertEquals(12, 12, isValidStart);
		assertEquals(13, 13, isValidStart);
		assertEquals(14, 14, isValidStart);
		assertEquals(15, 15, isValidStart);
		assertEquals(16, 16, isValidStart);
		assertEquals(17, 17, isValidStart);
		assertEquals(18, 18, isValidStart);
		assertEquals(19, 19, isValidStart);
		assertEquals(20, 20, isValidStart);
		assertEquals(21, 21, isValidStart);
		assertEquals(22, 22, isValidStart);
		assertEquals(23, 23, isValidStart);

	}
	
	@Test
	void testInvalidStartTime() {
		
		assertEquals("zero", "zero", isNotValidStart);
		assertEquals(-1, -1, isNotValidStart);
		assertEquals(" ", " ", isNotValidStart);
		assertEquals("one", "one", isNotValidStart);


	}
	
	@Test
	void testOverStartBoundary() {
		
		assertEquals(0, -1, overBoundary);
		assertEquals(23, 24, overBoundary);

	}
	
}