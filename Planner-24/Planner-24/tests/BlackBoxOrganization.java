package tests;

import au.edu.sccs.csp3105.NBookingPlanner.Organization;
import au.edu.sccs.csp3105.NBookingPlanner.Person;
import au.edu.sccs.csp3105.NBookingPlanner.Room;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

public class BlackBoxOrganization {

	//Create a spy of the Organization class to 
	Organization planner = Mockito.spy(Organization.class);


	String[] expectedPersonOutput = new String[] {
			"Justin Gardener",
	        "Ashley Matthews",
	        "Mary Jane Cook",
	        "Rose Austin",
	        "Mike Smith",
	        "Helen West",
	        "Steven Lewis",
	        "Edith Cowan",
	        "Mark Colin",
	        "Jacquie Martin",
	        "Jaci Johnston",
	        "Travis Colin",
	        "Ashley Martin",
			
	};
	
	String[] expectedRoomOutput = new String[] {
			"JO18.330",
			"JO7.221",
			"JO15.236",
			"JO1.230",
			"JO34.536",
			"JO19.230",
			"ML5.123",
			"ML18.330",
			"ML21.520",
			"ML13.213",
			"ML21.310",
			"ML13.218",
			
	};
	
	//We define variables which will be called depending on their respective variable names
	String isRoom = "This ID matches an existing room"; 
	String isNotRoom = "This ID does not match an existing room"; 
	
	String isEmployee = "This person is an employee"; 
	String isNotEmployee = "This person is NOT an employee"; 
	
	
	
	
	// we can capture
	@Test
	void testRoomTrue(String ID) throws Exception {
		assertEquals(expectedRoomOutput, isRoom);
	}
	
	void testRoomNotTrue(String ID) {
		assertEquals(expectedRoomOutput, isNotRoom);
	}

	//continuing this, each employee
	void testEmployeeTrue(String name) {
		assertEquals(expectedPersonOutput, isEmployee);
	}
	
	void testEmployeeNotTrue(String name) {
		assertEquals(Organization.getEmployee(employees), isNotEmployee);
	}
	
	

}
