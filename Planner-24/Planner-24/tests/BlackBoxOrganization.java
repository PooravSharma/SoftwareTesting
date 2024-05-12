package tests;

import au.edu.sccs.csp3105.NBookingPlanner.Organization;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BlackBoxOrganization {

	String isRoom = "This ID matches an existing room"; 
	String isNotRoom = "This ID does not match an existing room"; 
	
	String isEmployee = "This person is an employee"; 
	String isNotEmployee = "This person is NOT an employee"; 
	

	
	@Test
	void testRoomTrue(String ID) {
		assertEquals(Organization.getRoom(ID), isRoom);
	}
	
	void testRoomNotTrue(String ID) {
		assertEquals(Organization.getRoom(ID), isNotRoom);
	}

	void testEmployeeTrue(String name) {
		assertEquals(Organization.getEmployee();
	}
	
	void testEmployeeNotTrue(String name) {
		assertEquals(Organization.getEmployee(isNotEmployee));
	}
	
	

}
