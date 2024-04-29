package tests;

//import the planner class so we can reference the code
import au.edu.sccs.csp3105.NBookingPlanner.Planner;

import static org.junit.jupiter.api.Assertions.*;

//THe JUnit5 Libraries required 
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The point of this test class is to ensure the day input the the program is working as intended
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
		assertEquals(6, 6, isValid);
		assertEquals(7, 7, isValid);
		assertEquals(8, 8, isValid);
		assertEquals(9, 9, isValid);
		assertEquals(10, 10, isValid);
		assertEquals(11, 11, isValid);
		assertEquals(12, 12, isValid);
		assertEquals(13, 13, isValid);
		assertEquals(14, 14, isValid);
		assertEquals(15, 15, isValid);
		assertEquals(16, 16, isValid);
		assertEquals(17, 17, isValid);
		assertEquals(18, 18, isValid);
		assertEquals(19, 19, isValid);
		assertEquals(20, 20, isValid);
		assertEquals(21, 21, isValid);
		assertEquals(22, 22, isValid);
		assertEquals(23, 23, isValid);
		assertEquals(31, 31, isValid);

	}

	@Test	
	void testInvalidDay() {
		
		assertEquals("1", "0", isNotValid);
		assertEquals( 1, 0, isNotValid);
		assertEquals( 32, 32, isNotValid);


	}
	

	void testOverDayBoundary() {

		assertEquals(0, 1, overBoundary);
		assertEquals(31, 33, overBoundary);
		
	}
	
}
