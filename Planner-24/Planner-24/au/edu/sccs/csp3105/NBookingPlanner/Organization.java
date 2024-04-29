/*modified code from University of South Carolina: package MeetingPlanner */
package au.edu.sccs.csp3105.NBookingPlanner;


import java.util.ArrayList;

public class Organization {
	/** This class is used to initialize and store a list of employees
	 * and rooms available for meetings. 
	 * 
	 *  It gets a bunch of initialization code out of the PlannerInterface.
	 */
	
	private ArrayList<Person> employees;
	private ArrayList<Room> rooms;

	/**
	 * Default constructor - sets up some rooms and people.
	 */
	public Organization(){
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
	}
	
	public ArrayList<Person> getEmployees() {
		return employees;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * Search for and retrieve a room.
	 * @param ID - ID of the room to retrieve.
	 * @return Room - The requested Room object.
	 * @throws Exception - If the room does not exist.
	 */
	public Room getRoom(String ID) throws Exception{
		Room room = null;
		for(Room toCheck : rooms){
			if(toCheck.getID().equals(ID)){
				room = toCheck;
				return room;
			}
		}
		
		throw new Exception("Requested room does not exist");
	}
	
	/**
	 * Search for and retrieve a person.
	 * @param name - The name of the person to retrieve.
	 * @return Person - The requested Person object.
	 * @throws Exception - If the person does not exist.
	 */
	public Person getEmployee(String name) throws Exception{
		Person employee = null;
		for(Person toCheck : employees){
			if(toCheck.getName().equals(name)){
				employee = toCheck;
				return employee;
			}
		}
		
		throw new Exception("Requested employee does not exist");
	}
}
