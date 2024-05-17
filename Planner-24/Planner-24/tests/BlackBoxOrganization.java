package tests;

import au.edu.sccs.csp3105.NBookingPlanner.Organization;
import au.edu.sccs.csp3105.NBookingPlanner.Person;
import au.edu.sccs.csp3105.NBookingPlanner.Room;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

//Create a Organization class constructor to initialise and store a mockup of data from the file

public class BlackBoxOrganization {

	private Organization organization;
	
	private ArrayList<Person> employees;
	private ArrayList<Room> rooms;
	
	public void Organization(){
	//Create an array of strings with the names of employees. these strings will be the expected input and output
	employees = new ArrayList<Person>();
	employees.add(new Person("Justin Gardener"));	
	
	rooms = new ArrayList<Room>();
	rooms.add(new Room("JO18.330"));
			
	};

	

	//We define variables which will be called depending on their respective variable names
	String isRoom = "This ID matches an existing room"; 
	String isNotRoom = "This ID does not match an existing room"; 
	
	String isEmployee = "This person is an employee"; 
	String isNotEmployee = "This person is NOT an employee"; 
	
	
	
	/*
	 *  each test returns a room if the room itself exists. 
	 *  Exceptions are there for if the room does not exist
	 */
	@Test
	@DisplayName("BB_org_0 valid room")
	public void TestRoom(String ID) throws Exception {
		//Create a spy of the Organization class using Mockito to monitor interactions
		organization = Mockito.spy(Organization.class);
		
		String actualoutput = "JO18.330";
		
		for (Room tocheck : rooms) {
			assertEquals(tocheck.getID(), actualoutput);
			System.out.print(isRoom);
			}
				
	}
	
	@Test
	@DisplayName("BB_org_1 invalid room")
	public void testRoomNotTrue(String ID) throws Exception {
		
		organization = Mockito.spy(Organization.class);

		String actualoutput = "JO18.33";
		
		for (Room tocheck : rooms) {
			assertEquals(tocheck.getID(), actualoutput);
			System.out.print(isNotRoom);

			}
		
	}

	//continuing this, each employee is tested for a string
	
	@Test
	@DisplayName("BB_org_2 valid employee")
	public void testEmployeeTrue(String name) throws Exception {
		
		organization = Mockito.spy(Organization.class);

		String actualoutput = "Justin Gardener";
		for (Person tocheck : employees) {
			assertEquals(tocheck.getName(), actualoutput);
			System.out.print(isEmployee);
		}
		
	}
	
	@Test
	@DisplayName("BB_org_3 invalid employee")
	public void testEmployeeNotTrue(String name) throws Exception {
		
		organization = Mockito.spy(Organization.class);

		String actualoutput = "Just Garden";
		
		for (Person tocheck : employees) {
			assertEquals(tocheck.getName(), actualoutput);
			System.out.print(isNotEmployee);
		}
	}
	
	

}
