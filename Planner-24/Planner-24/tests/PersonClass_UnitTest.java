package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import au.edu.sccs.csp3105.NBookingPlanner.ConflictsException;
import au.edu.sccs.csp3105.NBookingPlanner.Meeting;
import au.edu.sccs.csp3105.NBookingPlanner.Person;
import au.edu.sccs.csp3105.NBookingPlanner.Room;
import java.util.ArrayList;

public class PersonClass_UnitTest {
	
	@Test
	// Test ID 1
	// This test ensures the getName works as it intended.
    void PersonConstructorWithName() {
    	
        Person person = new Person("Ahmad Sharifi");
        assertEquals("Ahmad Sharifi", person.getName());
    }
	@Test
	// Test ID 2
	// This test is a path where a person added to a meeting without an Error
	void AddMeetingNoException() {
		
	    Person person = new Person("Ahmad Sharifi");
	    ArrayList<Person> attendees = new ArrayList<>();
	    attendees.add(person);  
	    Room room = new Room(); 
	    Meeting meeting = new Meeting(5, 15, 9, 11, attendees, room, "desc");

	    assertDoesNotThrow(() -> person.addMeeting(meeting));
	}
	
	@Test
	// Test ID 3
	// This test throws exception due to invalid month
	void AddMeetingException() {
		
	    Person person = new Person("Ahmad Sharifi");
	    ArrayList<Person> attendees = new ArrayList<>();
	    attendees.add(person);  
	    Room room = new Room(); 
	    Meeting meeting = new Meeting(15, 15, 9, 11, attendees, room, "desc");

	    assertThrows(ConflictsException.class, () -> person.addMeeting(meeting));
	}


    // Test ID 4
	@Test
	// The test print the agenda for selected month
	void PrintAgendaWithMonth() {
		
	    Person person = new Person("Ahmad Sharifi");
	    Room room = new Room("JO1.230"); 
	    ArrayList<Person> attendees = new ArrayList<>();
	    attendees.add(person); 
	    
	    Meeting meeting = new Meeting(5, 20, 9, 11, attendees, room, "desc");
	    assertDoesNotThrow(() -> person.addMeeting(meeting));
	    
	    String agenda = person.printAgenda(5);
	    
	    assertTrue(agenda.contains("Agenda for 5:"));
	}
	
	// Test ID 5
	@Test
	// The test print the agenda for selected month and day
	void PrintAgendaWithMonthDay() {
		
	    Person person = new Person("Ahmad Sharifi");
	    Room room = new Room("JO1.230"); 
	    room.getID(); 

	    ArrayList<Person> attendees = new ArrayList<>();
	    attendees.add(person); // Make sure attendees are added

	    Meeting meeting = new Meeting(5, 20, 9, 11, attendees, room, "desc");
	    assertDoesNotThrow(() -> person.addMeeting(meeting),
	        "Meeting should be added without conflicts");

	    String agenda = person.printAgenda(5, 20);
	    assertTrue(agenda.contains("Agenda for 5/20 are as follows:"));
	}
	
	// Test ID 6, Test ID 7
	// The test first creates a meeting and then checks if the person in the meeting is busy that time
	 @Test
	    void IsBusyNoException() throws ConflictsException {
	        
	        Person person = new Person("Ahmad Sharifi");
	        Room room = new Room("JO1.230"); 
	        ArrayList<Person> attendees = new ArrayList<>();
	        attendees.add(person); 
	        Meeting meeting = new Meeting(5, 15, 10, 11, attendees, room, "desc");
	        person.addMeeting(meeting);
	        
	        assertFalse(person.isBusy(5, 15, 8, 9));
	        assertTrue(person.isBusy(5, 15, 10, 11));
	 }
	 
	 // Test ID 8
	 @Test
	 // The test throws exception due to invalid hours input
	    void IsBusyNoWithException() throws ConflictsException {
	        
	        Person person = new Person("Ahmad Sharifi");
	        Room room = new Room("JO1.230"); 
	        ArrayList<Person> attendees = new ArrayList<>();
	        attendees.add(person); 
	        Meeting meeting = new Meeting(5, 15, 10, 11, attendees, room, "desc");
	        person.addMeeting(meeting);
	        
	        assertThrows(ConflictsException.class, () -> person.isBusy(5, 15, 23, 24));
	 }
	 
	 // Test ID 9

	 @Test
	 // The test check whether getMeeting method works correctly when there is meeting in the meeting array.
	    void getMeeting() throws ConflictsException {
	        // Setup
	        Person person = new Person("Ahmad Sharifi");
	        Room room = new Room("JO1.230");
	        ArrayList<Person> attendees = new ArrayList<>();
	        attendees.add(person);
	        int month = 5;
	        int day = 15;
	        int start = 10;
	        int end = 11;
	        String desc = "desc";

	
	        Meeting meeting = new Meeting(month, day, start, end, attendees, room, desc);
	        person.addMeeting(meeting);

	       
	        String expected = "Month is " + month + ", Day is " + day + ", Time slot:" + start + " - " + end +
	                          ", Room No:" + room.getID() + ": " + desc + "\nAttending: ";
	        for (Person attendee : attendees) {
	            expected += attendee.getName() + ",";
	        }
	        expected = expected.substring(0, expected.length() - 1);  // Remove the last comma

	        // Assert that the meeting's toString output matches expected output
	        assertEquals(expected, person.getMeeting(month, day, 0).toString());
	    }
	 
	 // Test ID 10
	 @Test
	 // The test checks if the removeMeeting works correctly by removing a meeting.
	    void removeMeeting() throws ConflictsException {
	        // Setup
	        Person person = new Person("Ahmad Sharifi");
	        Room room = new Room("JO1.230");
	        ArrayList<Person> attendees = new ArrayList<>();
	        attendees.add(person);
	        int month = 5;
	        int day = 15;
	        int start = 10;
	        int end = 11;
	        String desc = "desc";

	        // Create the meeting and add it
	        Meeting meeting = new Meeting(month, day, start, end, attendees, room, desc);
	        person.addMeeting(meeting);

	        // Check if meeting is added and exists
	        assertNotNull(person.getMeeting(month, day, 0));

	        // Now remove the meeting
	        person.removeMeeting(month, day, 0);

	        // Check if meeting is removed
	        assertThrows(IndexOutOfBoundsException.class, () -> person.getMeeting(month, day, 0));
	    }

	
}
