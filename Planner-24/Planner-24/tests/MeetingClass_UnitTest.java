package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import au.edu.sccs.csp3105.NBookingPlanner.*;
import java.util.ArrayList;

class MeetingClass_UnitTest {

	private Meeting meeting;
	private ArrayList<Person> attendees;
	private Room room;

	int month = 1;
	int day = 1;
	int start = 0;
	int end = 12;
	String description = "Meeting about inflation";

	@BeforeEach
	public void setUp() {
		attendees = new ArrayList<>();
		attendees.add(new Person("Justin Gardener"));
		room = new Room("JO18.330");
	}

	// TestID IMeet1
	@Test
	@DisplayName("Valid initialising Meeting (TestID:IMeet1)")
	void initialiseMeet1() {
		meeting = new Meeting();
		Assertions.assertNotNull(meeting);
	}

	// TestID IMeet2
	@Test
	@DisplayName("Valid initialising Meeting (TestID:IMeet2)")
	void initialiseMeet2() {
		meeting = new Meeting(month, day);
		Assertions.assertNotNull(meeting);
		assertEquals(month, meeting.getMonth());
		assertEquals(day, meeting.getDay());
		assertEquals(0, meeting.getStartTime());
		assertEquals(23, meeting.getEndTime());
	}

	// TestID IMeet3
	@Test
	@DisplayName("Valid initialising Meeting (TestID:IMeet3)")
	void initialiseMeet3() {
		meeting = new Meeting(month, day, description);
		Assertions.assertNotNull(meeting);
		Assertions.assertEquals(month, meeting.getMonth());
		Assertions.assertEquals(day, meeting.getDay());
		Assertions.assertEquals(0, meeting.getStartTime());
		Assertions.assertEquals(23, meeting.getEndTime());
		Assertions.assertEquals(description, meeting.getDescription());
	}

	// TestID IMeet4
	@Test
	@DisplayName("Valid initialising Meeting (TestID:IMeet4)")
	void initialiseMeet4() {
		meeting = new Meeting(month, day, start, end);
		Assertions.assertNotNull(meeting);
		Assertions.assertEquals(month, meeting.getMonth());
		Assertions.assertEquals(day, meeting.getDay());
		Assertions.assertEquals(start, meeting.getStartTime());
		Assertions.assertEquals(end, meeting.getEndTime());
	}

	// TestID IMeet5
	@Test
	@DisplayName("Valid initialising Meeting (TestID:IMeet5)")
	void initialiseMeet5() {
		meeting = new Meeting(month, day, start, end, attendees, room, description);
		Assertions.assertNotNull(meeting);
		Assertions.assertEquals(month, meeting.getMonth());
		Assertions.assertEquals(day, meeting.getDay());
		Assertions.assertEquals(start, meeting.getStartTime());
		Assertions.assertEquals(end, meeting.getEndTime());
		Assertions.assertEquals(attendees, meeting.getAttendees());
		Assertions.assertEquals(room, meeting.getRoom());
		Assertions.assertEquals(description, meeting.getDescription());
	}

	// TestID MeetAdd
	@Test
	@DisplayName("Add person to Meeting (TestID:MeetAdd)")
	void meetingAdd() {
		meeting = new Meeting(month, day, start, end, attendees, room, description);
		Person person1 = new Person("Harold");
		meeting.addAttendee(person1);
		Assertions.assertTrue(meeting.getAttendees().contains(person1));

	}

	// TestID MeetRemove
	@Test
	@DisplayName("Remove person from Meeting (TestID:MeetRemove)")
	void meetingRemove() {
		meeting = new Meeting(month, day, start, end, attendees, room, description);
		Person person = new Person("Harold");
		meeting.addAttendee(person);
		Assertions.assertTrue(meeting.getAttendees().contains(person));
		meeting.removeAttendee(person);
		Assertions.assertFalse(meeting.getAttendees().contains(person));

	}

	// TestID MeetTString
	@Test
	@DisplayName("Print Meeting detail (TestID:MeetTString)")
	void meetingtoString() {
		meeting = new Meeting(month, day, start, end, attendees, room, description);
		Person person = new Person("Harold");
		meeting.addAttendee(person);
		String output = "Month is 1, Day is 1, Time slot:0 - 12, Room No:JO18.330: Meeting about inflation\nAttending: Justin Gardener,Harold";
		Assertions.assertEquals(output, meeting.toString());

	}

	// TestID MeetTString2
	@Test
	@DisplayName("Print Meeting detail2 (TestID:MeetTString2)")
	void meetingtoString2() {
		meeting = new Meeting(month, day, start, end, attendees, room, description);
		String output = "Month is 1, Day is 1, Time slot:0 - 12, Room No:JO18.330: Meeting about inflation\nAttending: Justin Gardener";
		Assertions.assertEquals(output, meeting.toString());

	}

	// TestID MeetGS1 & MeetGS2
	@Test
	@DisplayName("Setting and getting Month (TestID:MeetGS1 & MeetGS2)")
	void meetGSMonth() {
		meeting = new Meeting(month, day);
		meeting.setMonth(6);
		assertEquals(6, meeting.getMonth());
	}

	// TestID MeetGS3 & MeetGS4
	@Test
	@DisplayName("Setting and getting Day (TestID:MeetGS3 & MeetGS4)")
	void meetGSDay() {
		meeting = new Meeting(month, day);
		meeting.setDay(12);
		assertEquals(12, meeting.getDay());
	}

	// TestID MeetGS5 & MeetGS6
	@Test
	@DisplayName("Setting and getting Start Hour (TestID:MeetGS5 & MeetGS6)")
	public void meetGSStartTime() {
		meeting = new Meeting(month, day, start, end);
		meeting.setStartTime(5);
		assertEquals(5, meeting.getStartTime());
	}

	// TestID MeetGS7 & MeetGS8
	@Test
	@DisplayName("Setting and getting End Hour (TestID:MeetGS7 & MeetGS8)")
	void meetGSSEndTime() {
		meeting = new Meeting(month, day, start, end);
		meeting.setEndTime(20);
		assertEquals(20, meeting.getEndTime());
	}
	
	// TestID MeetGS9
		@Test
		@DisplayName("Getting Attendees (TestID:MeetGS9)")
		void meetGSAttendees() {
			meeting = new Meeting(month, day, start, end, attendees, room, description);
			Person person = new Person("Harold");
			meeting.addAttendee(person);
			
			assertEquals(attendees, meeting.getAttendees());
		}
	

	// TestID MeetGS10 & MeetGS11
	@Test
	@DisplayName("Setting and getting Room (TestID:MeetGS10 & MeetGS11)")
	public void meetGSRoom() {
		meeting = new Meeting(month, day);
		Room newRoom = new Room("JO18.330");
		meeting.setRoom(newRoom);
		assertEquals(newRoom, meeting.getRoom());
	}

	// TestID MeetGS12 & MeetGS13
	@Test
	@DisplayName("Setting and getting Description (TestID:MeetGS12 & MeetGS13)")
	void meetGSDescription() {
		meeting = new Meeting(11, 7);
		meeting.setDescription("New Description");
		assertEquals("New Description", meeting.getDescription());
	}

}
