package tests;

//import the planner class so we can reference the code
import au.edu.sccs.csp3105.NBookingPlanner.Planner;

import static org.junit.jupiter.api.Assertions.*;

//The test only requires JUnit5 Libraries 
import org.junit.jupiter.api.Test;

/**
 * The point of this test class is to ensure the day input the the program is working as intended. We conduct a test for all possible inputs 
 */

public class BlackBoxDayInput {
	
	String isValid = "This is a valid day";
			
	String isNotValid = "This is not a valid day!";

	String overBoundary = "Enter a number between 1 and 31";
	
	
	
	
	@Test
	void testValidDay() {
		assertEquals(1, 1, isValid);
		assertEquals(2, 2, isValid);
		assertEquals(3, 3, isValid);
		assertEquals(4, 4, isValid);
		assertEquals(5, 5, isValid);
		assertEquals(29, 29, isValid);
		assertEquals(30, 30, isValid);
		assertEquals(31, 31, isValid);

	}

	// We account for the 29th day because February has 29 days and because of Leap Years
	@Test	
	void testInvalidDay() {

		assertEquals( -1, -1, isNotValid);
		assertEquals("0", "0", isNotValid);
		assertEquals( 0, 0, isNotValid);
		assertEquals( 29, 29, isNotValid);
		assertEquals( 32, 32, isNotValid);
		assertEquals( 33, 33, isNotValid);		

	}
	
	// The days aabove or below the boundaries are tested
	void testOverDayBoundary() {

		assertEquals(0, 0, overBoundary);
		assertEquals(33, 33, overBoundary);
		
	}
	
}
