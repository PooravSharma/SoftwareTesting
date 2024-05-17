package tests;

import au.edu.sccs.csp3105.NBookingPlanner.Organization;
import au.edu.sccs.csp3105.NBookingPlanner.Person;
import au.edu.sccs.csp3105.NBookingPlanner.Room;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

//Create a Organization class constructor to initialise and store a mockup of data from the file

public class BlackBoxOrganization {


	private ArrayList<Person> employees;
	private ArrayList<Room> rooms;
	
	public void Organization(){
	//Create an array of strings with the names of employees. these strings will be the expected input asnd output
	employees = new ArrayList<Person>();
	employees.add(new Person("Justin Gardener"));
	employees.add(new Person("Ashley Matthews"));
	employees.add(new Person("Mary Jane Cook"));
	employees.add(new Person("Rose Austin"));
	employees.add(new Person("Mike Smith"));
	employees.add(new Person("Helen West"));
	employees.add(new Person("Steven Lewis"));
	employees.add(new Person("Edith Cowan"));
	employees.add(new Person("Mark Colin"));
	employees.add(new Person("Jacquie Martin"));
	employees.add(new Person("Jaci Johnston"));
	employees.add(new Person("Travis Colin"));
	employees.add(new Person("Ashley Martin"));
			
	
	
	rooms = new ArrayList<Room>();
	rooms.add(new Room("JO18.330"));
	rooms.add(new Room("JO7.221"));
	rooms.add(new Room("JO15.236"));
	rooms.add(new Room("JO1.230"));
	rooms.add(new Room("JO34.536"));
	rooms.add(new Room("JO19.230"));
	rooms.add(new Room("ML5.123"));
	rooms.add(new Room("ML18.330"));
	rooms.add(new Room("ML21.520"));
	rooms.add(new Room("ML13.213"));
	rooms.add(new Room("ML21.310"));
	rooms.add(new Room("ML13.218"));
			
	};
	
	public ArrayList<Person> getEmployees() {
		return employees;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}
	

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
		Organization organization = Mockito.spy(Organization.class);
		
		Room[] actualoutput = new Room [] {"JO18.330"};
		
		for (Room[] tocheck : rooms) {
			if(tocheck.getID().equals(ID)) {
				actualoutput = tocheck;
				assertEquals(rooms, actualoutput);
			}
		}
		
	}
	
	@Test
	@DisplayName("BB_org_1 invalid room")
	public void testRoomNotTrue(String ID) throws Exception {
		
		Organization organization = Mockito.spy(Organization.class);

		Room actualoutput = null;
		for (Room tocheck : rooms) {
			if(tocheck.getID().equals(ID)) {
				actualoutput = tocheck;
				assertEquals(rooms, actualoutput);
			}
		}
	}

	//continuing this, each employee
	
	@Test
	@DisplayName("BB_org_2 valid employee")
	public void testEmployeeTrue(String name) throws Exception {
		
		Organization organization = Mockito.spy(Organization.class);

		Person actualoutput = null;
		
		assertEquals(employees, actualoutput);
	}
	
	@Test
	@DisplayName("BB_org_3 invalid employee")
	public void testEmployeeNotTrue(String name) throws Exception {
		
		Organization organization = Mockito.spy(Organization.class);

		Person actualoutput = null;
		
		assertEquals(employees, actualoutput);
	}
	
	

}
