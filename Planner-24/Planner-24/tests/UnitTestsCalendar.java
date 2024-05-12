package tests;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.stefanbirkner.systemlambda.SystemLambda;

import au.edu.sccs.csp3105.NBookingPlanner.Calendar;
import au.edu.sccs.csp3105.NBookingPlanner.ConflictsException;
import au.edu.sccs.csp3105.NBookingPlanner.Meeting;
import au.edu.sccs.csp3105.NBookingPlanner.Person;
import au.edu.sccs.csp3105.NBookingPlanner.Planner;
import au.edu.sccs.csp3105.NBookingPlanner.Room;

class UnitTestsCalendar {

	// ------------------------------------------------------------------------------------------- \\
	// ------------------------------------ PREPARE FOR TESTS ------------------------------------ \\
	// ------------------------------------------------------------------------------------------- \\
	
	// Init classes for use in tests
	private Calendar calendar;
	private Meeting meeting;
	private Room room;
	private Person person;

	// Object factories for use ONLY when mocking is unrealistic or infeasible due to class design
	Meeting makeMeeting(int month, int day, int start, int end, ArrayList<Person> attendees, Room room, String description) {
		
		return new Meeting(month, day, start, end, attendees, room, description);
	}
	Room makeRoom(String ID) {
		
		return new Room(ID);
	}
	Person makePerson(String Name) {
		return new Person(Name);
	}
	
	
	// ------------------------------------------------------------------------------------------- \\
	// --------------------------------------- calendar() ---------------------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	/**
	 * Tests the Calendar constructor to ensure that it properly initializes the internal data structure.
	 * The 'occupied' ArrayList is expected to be correctly sized according to the code, which includes 
	 * initializing 14 months, and 33 days per month, and an 'invalid day' placeholder meeting in each
	 * invalid day.
	 *
	 * This test uses Java Reflection to access the private 'occupied' field within the Calendar class, bypassing 
	 * encapsulation purely for testing purposes. It checks the integrity of each month's initialization to confirm
	 * that all days are accounted for and initialized correctly.
	 *
	 * @throws Exception if the reflection fails to access the private field or other unexpected exceptions occur.
	 */
	@Test
	public void calendar01() throws Exception {
        // Create a new Calendar instance
        calendar = new Calendar();

        // Access the private 'occupied' field
        Field occupiedField = Calendar.class.getDeclaredField("occupied");
        occupiedField.setAccessible(true);

        // Retrieve and cast the occupied field
        ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
        		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);
	    // Initialise the expected sizes of 'occupied' and each of its months
	    int expectedSizeOfOccupied = 14;
	    int expectedSizeOfMonth = 33;
	    
	    // Initialise expeted string, as well as list of month/day pairs 
	    // to check if constructor correcltly initialised placeholder meetings
	    // on 'invalid' days
	    String expectedInvalidDayDesc = "Day does not exist";
        List<int[]> invalidDays = new ArrayList<>();
        invalidDays.add(new int[]{2, 29});
        invalidDays.add(new int[]{2, 30});
        invalidDays.add(new int[]{2, 31});
        invalidDays.add(new int[]{4, 31});
        invalidDays.add(new int[]{6, 31});
        invalidDays.add(new int[]{9, 31});
        invalidDays.add(new int[]{11, 30});
        invalidDays.add(new int[]{11, 31});

	    // Assert that the 'occupied' array is not null, meaning it was initialized
	    assertNotNull(occupied);

	    // Assert that the outer list has the correct size (14 months)
	    assertEquals(expectedSizeOfOccupied, occupied.size());

	    // Loop through each month and check if each month contains the correct number of days
	    for (ArrayList<ArrayList<Meeting>> month : occupied) {
	        assertEquals(expectedSizeOfMonth, month.size());
	    }
	    
        // Loop through each specified month/day pair and assert that each placeholder meeting
	    // meeting has been initialised
        for (int[] pair : invalidDays) {
            int current_month = pair[0];
            int current_day = pair[1];
            Meeting invalidDayPlaceholder = occupied.get(current_month).get(current_day).get(0);
            assertEquals(expectedInvalidDayDesc, invalidDayPlaceholder.getDescription());
        }
	}
	
	
	// ------------------------------------------------------------------------------------------- \\
	// --------------------- isBusy(int month, int day, int start, int end)  --------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Tests the isBusy method to ensure it accurately returns False when no meetings are scheduled 
	 * during the specified time frame. This test case specifically checks an empty calendar on March 
	 * 6th, from 14:00 to 17:00, expecting no meetings to be present, so should return false.
	 */
	@Test
	public void isBusy01() throws Exception {
	    // Initialize a fresh calendar
	    Calendar calendar = new Calendar();
	    
	    // Perform the test by checking if the calendar is busy on March 6th, from 14:00 to 17:00
	    // Expect false since no meetings should be scheduled in a new calendar.
	    assertFalse(calendar.isBusy(3, 6, 14, 17));
	}

	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the isBusy method to ensure it accurately returns True when a meeting is scheduled during 
	 * the specified time frame. This test case adds a meeting on March 6th from 13:00 to 18:00 and then 
	 * checks for busyness from 14:00 to 15:00, expecting the calendar to report it as busy since the 
	 * queried time overlaps with the scheduled meeting.
	 */
	@Test
	public void isBusy02() throws Exception {
	    // Initialize a fresh calendar
	    Calendar calendar = new Calendar();
	    
	    // Create a mock Meeting object 
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting and add it to the calendar
	    when(mockMeeting.getMonth()).thenReturn(3); 
	    when(mockMeeting.getDay()).thenReturn(6);  
	    when(mockMeeting.getStartTime()).thenReturn(13);  
	    when(mockMeeting.getEndTime()).thenReturn(18);
	    calendar.addMeeting(mockMeeting);
	    
	    // Check if the calendar is busy on March 6th from 14:00 to 15:00
	    // Expect true since there is an overlap with the added meeting.
	    assertTrue(calendar.isBusy(3, 6, 14, 15));
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the isBusy method to ensure it accurately returns False when no meetings are scheduled during 
	 * the specified time frame. This test case adds a meeting on March 6th from 10:00 to 12:00 and then 
	 * checks for busyness from 13:00 to 18:00, expecting the calendar to report it as not busy since the 
	 * queried time does not overlap with the scheduled meeting.
	 */
	@Test
	public void isBusy03() throws Exception {
	    // Initialize a fresh calendar
	    Calendar calendar = new Calendar();
	    
	    // Create a mock Meeting object 
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting and add it to the calendar
	    when(mockMeeting.getMonth()).thenReturn(3); 
	    when(mockMeeting.getDay()).thenReturn(6);  
	    when(mockMeeting.getStartTime()).thenReturn(10);  
	    when(mockMeeting.getEndTime()).thenReturn(12);
	    calendar.addMeeting(mockMeeting);
	    
	    // Check if the calendar is busy on March 6th from 13:00 to 18:00
	    // Expect false since there is no overlap with the added meeting.
	    assertFalse(calendar.isBusy(3, 6, 13, 18));
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the isBusy method to ensure it accurately returns False when no meetings are scheduled during 
	 * the specified time frame. This test case adds a meeting on March 6th from 08:00 to 09:00 and then 
	 * checks for busyness from 10:00 to 12:00, expecting the calendar to report it as not busy since the 
	 * queried time does not overlap with the scheduled meeting.
	 */
	@Test
	public void isBusy04() throws Exception {
	    // Initialize a fresh calendar 
	    Calendar calendar = new Calendar();

	    // Create a mock Meeting object 
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting and add it to the calendar
	    when(mockMeeting.getMonth()).thenReturn(3); 
	    when(mockMeeting.getDay()).thenReturn(6);  
	    when(mockMeeting.getStartTime()).thenReturn(8);  
	    when(mockMeeting.getEndTime()).thenReturn(9);
	    calendar.addMeeting(mockMeeting);
	    
	    // Check if the calendar is busy on March 6th from 10:00 to 12:00
	    // Expect false since there is no overlap with the added meeting.
	    assertFalse(calendar.isBusy(3, 6, 10, 12));
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the isBusy method to ensure it accurately returns True when a meeting overlaps with 
	 * the specified time frame. This test case adds a meeting on March 6th from 08:00 to 14:00 and then 
	 * checks for busyness from 03:00 to 12:00, expecting the calendar to report it as busy since the 
	 * queried time overlaps with the scheduled meeting.
	 */
	@Test
	public void isBusy05() throws Exception {
	    // Initialize a fresh calendar and insert a new test meeting from 08:00 to 14:00 on March 6th
	    Calendar calendar = new Calendar();
	    
	    // Create a mock Meeting object 
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting and add it to the calendar
	    when(mockMeeting.getMonth()).thenReturn(3); 
	    when(mockMeeting.getDay()).thenReturn(6);  
	    when(mockMeeting.getStartTime()).thenReturn(8);  
	    when(mockMeeting.getEndTime()).thenReturn(14);
	    calendar.addMeeting(mockMeeting);
	    
	    // Check if the calendar is busy on March 6th from 03:00 to 12:00
	    // Expect true since there is an overlap with the added meeting.
	    assertTrue(calendar.isBusy(3, 6, 3, 12));
	}


	// ------------------------------------------------------------------------------------------- \\
	// ----------------- checkTimes(int mMonth, int mDay, int mStart, int mEnd) ------------------ \\
	// ------------------------------------------------------------------------------------------- \\

	/**
	 * Tests the checkTimes method of the Calendar class to ensure it correctly handles valid date 
	 * and time inputs without throwing any exceptions. This test verifies that the method accepts a 
	 * valid month, day, start time, and end time combination. The specific test case checks for
	 * April 6th, from 13:00 to 15:00, expecting no exceptions to be thrown since these are within the 
	 * valid date and time ranges specified by the method's implementation.
	 */
	@Test
	public void checkTimes01() throws Exception {
	    // Execute the checkTimes method with valid parameters and 
	    // assert that no exceptions are thrown
	    assertDoesNotThrow(() -> {
	        Calendar.checkTimes(4, 6, 13, 15);
	    });
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an invalid day is entered. This test case passes a day value of 0 (invalid) to 
	 * check that a ConflictsException is thrown and that the exception message accurately
	 * reflects the expected message.
	 */
	@Test
	public void checkTimes02() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Day does not exist.";
	    
	    // Execute the checkTimes method with invalid parameters (day = 0) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(5, 0, 4, 6);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an excessively high invalid day is entered. This test case passes a day value of 56 
	 * (invalid) to check that a ConflictsException is thrown and that the exception message accurately
	 * reflects the expected message.
	 */
	@Test
	public void checkTimes03() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Day does not exist.";
	    
	    // Execute the checkTimes method with invalid parameters (day = 56) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(4, 56, 3, 7);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an invalid month is entered. This test case passes a month value of -3 
	 * (invalid) to check that a ConflictsException is thrown and that the exception message accurately
	 * reflects the expected message.
	 */
	@Test
	public void checkTimes04() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Month does not exist.";
	    
	    // Execute the checkTimes method with invalid parameters (month = -3) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(-3, 5, 12, 14);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an invalid month is entered. This test case passes a month value of 16 
	 * (invalid) to check that a ConflictsException is thrown and that the exception message accurately
	 * reflects the expected message.
	 */
	@Test
	public void checkTimes05() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Month does not exist.";
	    
	    // Execute the checkTimes method with invalid parameters (month = 16) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(16, 5, 12, 14);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an invalid hour is entered. This test case passes a start time of -4 (invalid) 
	 * to check that a ConflictsException is thrown and that the exception message accurately reflects
	 *  the expected message for an illegal hour.
	 */
	@Test
	public void checkTimes06() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Illegal hour.";
	    
	    // Execute the checkTimes method with invalid parameters (start time = -4) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(8, 5, -4, 12);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an illegal hour is entered. This test case passes an end time of 30 (invalid)
	 * to check that a ConflictsException is thrown and that the exception message accurately reflects 
	 * the expected message for an illegal hour.
	 */
	@Test
	public void checkTimes07() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Illegal hour.";
	    
	    // Execute the checkTimes method with invalid parameters (end time = 30) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(8, 5, 30, 12);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an illegal hour is entered. This test case passes a start time of -2 (invalid) 
	 * to check that a ConflictsException is thrown and that the exception message accurately reflects 
	 * the expected message for an illegal hour.
	 */
	@Test
	public void checkTimes08() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Illegal hour.";
	    
	    // Execute the checkTimes method with invalid parameters (start time = -2) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(8, 5, 10, -2);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an illegal hour is entered. This test case passes an end time of 33 (invalid) 
	 * to check that a ConflictsException is thrown and that the exception message accurately
	 * reflects the expected message for an illegal hour.
	 */
	@Test
	public void checkTimes09() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Illegal hour.";
	    
	    // Execute the checkTimes method with invalid parameters (end time = 33) and 
	    // assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(8, 5, 10, 33);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the checkTimes method to ensure it throws the appropriate exception with the correct 
	 * message when an illegal combination of start/end hour is entered. This test case passes a
	 * start time of 10 and an end time of 8 to check that a ConflictsException is thrown and that 
	 * the exception message accurately reflects the expected message for an illegal combination
	 * of hours where the meeting ends before it starts.
	 */
	@Test
	public void checkTimes10() throws Exception {
	    // Initialise expected error message to be thrown by ConflictsException
	    String expectedMessage = "Meeting starts before it ends.";
	    
	    // Execute the checkTimes method with invalid parameters (end time is before start time) 
	    // and assert that the appropriate exception is thrown
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        Calendar.checkTimes(8, 5, 10, 8);
	    });

	    // Assert that the thrown exception contains the expected message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	
	// ------------------------------------------------------------------------------------------- \\
	// -------------------------------- addMeeting(Meeting toAdd)  ------------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Tests the addMeeting method to see if it adds a new meeting to a calendar on an invalid date,
	 * specifically September 31st. This test checks the functionality of an expected fault edge cases 
	 * where a date does not actually exist in the calendar. It verifies that the new meeting is added 
	 * alongside an existing placeholder indicating the day does not exist.
	 */
	@Test
	public void addMeeting01() throws Exception {
	    // Initialize a fresh calendar and prepare a new meeting for September 31st
	    Calendar calendar = new Calendar();
	   
	    // Create a mock Meeting object 
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting and add it to the calendar
	    when(mockMeeting.getMonth()).thenReturn(9); 
	    when(mockMeeting.getDay()).thenReturn(31);  
	    when(mockMeeting.getStartTime()).thenReturn(1);  
	    when(mockMeeting.getEndTime()).thenReturn(1);
	    calendar.addMeeting(mockMeeting);
	    
	    // Access and cast the private 'occupied' field to check internal states
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	    		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);
	    
	    // Assert that there are two meetings on this day, including the placeholder for the invalid day
	    assertEquals(2, occupied.get(9).get(31).size());

	    // Verify the details of the newly added meeting
	    assertEquals(9, occupied.get(9).get(31).get(1).getMonth());
	    assertEquals(31,occupied.get(9).get(31).get(1).getDay());
	    assertEquals(1, occupied.get(9).get(31).get(1).getStartTime());
	    assertEquals(1, occupied.get(9).get(31).get(1).getEndTime());
	}

	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
		
	/**
	 * Tests the addMeeting method to verify that it accurately handles the addition of new meetings
	 * to specific dates, ensuring correct behavior when meetings do not overlap. This test case
	 * specifically checks that two meetings can be scheduled on the same day without causing conflicts,
	 * as long as their time intervals do not overlap. It confirms that the calendar correctly manages
	 * these entries and reflects the expected number of meetings.
	 */
	@Test
	public void addMeeting02() throws Exception {
	    // Create mock Meeting objects
	    Meeting existingMeeting = Mockito.spy(Meeting.class);
	    Meeting newMeeting = Mockito.spy(Meeting.class);

	    // Set up the existing meeting on March 6 from 13:00 to 15:00
	    when(existingMeeting.getMonth()).thenReturn(3);
	    when(existingMeeting.getDay()).thenReturn(6);
	    when(existingMeeting.getStartTime()).thenReturn(13);
	    when(existingMeeting.getEndTime()).thenReturn(15);
	    when(existingMeeting.getDescription()).thenReturn("Existing Meeting");

	    // Set up a new meeting on the same day from 16:00 to 17:00
	    when(newMeeting.getMonth()).thenReturn(3);
	    when(newMeeting.getDay()).thenReturn(6);
	    when(newMeeting.getStartTime()).thenReturn(16);
	    when(newMeeting.getEndTime()).thenReturn(17);
	    when(newMeeting.getDescription()).thenReturn("New Meeting");

	    // Create a new calendar and add both meetings
	    Calendar calendar = new Calendar();
	    calendar.addMeeting(existingMeeting);
	    calendar.addMeeting(newMeeting);
	    
	    // Access the 'occupied' field via reflection to inspect the internal state of the calendar
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	    		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);

	    // Assert that there are two meetings on this day
	    assertEquals(2, occupied.get(3).get(6).size());

	    // Verify the details of the newly added meeting
	    assertEquals(3, occupied.get(3).get(6).get(1).getMonth());
	    assertEquals(6, occupied.get(3).get(6).get(1).getDay());
	    assertEquals(16, occupied.get(3).get(6).get(1).getStartTime());
	    assertEquals(17, occupied.get(3).get(6).get(1).getEndTime());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the addMeeting method to ensure it correctly throws a ConflictsException when trying to add
	 * a new meeting that overlaps with an existing meeting on both sides. This test checks if adding a 
	 * new meeting that overlaps in time with a previously added meeting correctly identifies the overlap 
	 * and throws an exception with the appropriate message.
	 */
	@Test
	public void addMeeting03() throws Exception {
	    // Create mock Meeting objects using Mockito to simulate existing and new meetings
	    Meeting existingMeeting = Mockito.spy(Meeting.class);
	    Meeting newMeeting = Mockito.spy(Meeting.class);

	    // Configure the existing meeting with specific time details
	    when(existingMeeting.getMonth()).thenReturn(3);
	    when(existingMeeting.getDay()).thenReturn(6);
	    when(existingMeeting.getStartTime()).thenReturn(11);
	    when(existingMeeting.getEndTime()).thenReturn(17);
	    when(existingMeeting.getDescription()).thenReturn("Existing Meeting");

	    // Configure the new meeting to overlap the existing meeting's time - both times overlap
	    when(newMeeting.getMonth()).thenReturn(3);
	    when(newMeeting.getDay()).thenReturn(6);
	    when(newMeeting.getStartTime()).thenReturn(13);
	    when(newMeeting.getEndTime()).thenReturn(15);
	    when(newMeeting.getDescription()).thenReturn("New Meeting");
	    
	    // Initialize a Calendar object and add the existing meeting
	    Calendar calendar = new Calendar();
	    calendar.addMeeting(existingMeeting);
	    
	    // Expected message from the ConflictsException when trying to add an overlapping meeting
	    String expectedMessage = "Overlap with another item - Existing Meeting - scheduled from 11 and 17";
	    
	    // Assert that adding a conflicting meeting throws ConflictsException with the correct message
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        calendar.addMeeting(newMeeting);
	    });
	    
	    // Validate that the exception message matches the expected error message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the addMeeting method to verify if a new meeting that does not overlap with an existing meeting
	 * can be added correctly. It checks the internal state of the calendar to ensure the new meeting is added
	 * as expected and that it does not disrupt existing meetings.
	 */
	@Test
	public void addMeeting04() throws Exception {
	    // Create mock Meeting objects using Mockito to simulate existing and new meetings
	    Meeting existingMeeting = Mockito.spy(Meeting.class);
	    Meeting newMeeting = Mockito.spy(Meeting.class);

	    // Configure the existing meeting with specific time details for March 6th from 10:00 to 12:00
	    when(existingMeeting.getMonth()).thenReturn(3);
	    when(existingMeeting.getDay()).thenReturn(6);
	    when(existingMeeting.getStartTime()).thenReturn(10);
	    when(existingMeeting.getEndTime()).thenReturn(12);
	    when(existingMeeting.getDescription()).thenReturn("Existing Meeting");

	    // Configure the new meeting for the same day from 13:00 to 15:00
	    when(newMeeting.getMonth()).thenReturn(3);
	    when(newMeeting.getDay()).thenReturn(6);
	    when(newMeeting.getStartTime()).thenReturn(13);
	    when(newMeeting.getEndTime()).thenReturn(15);
	    when(newMeeting.getDescription()).thenReturn("New Meeting");
	    
	    // Initialize a new Calendar instance and add both meetings
	    Calendar calendar = new Calendar();
	    calendar.addMeeting(existingMeeting);
	    calendar.addMeeting(newMeeting);
	    
	    // Access the 'occupied' field via reflection to inspect the internal state of the calendar
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	    		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);

	    // Assert that there are two distinct meetings on this day
	    assertEquals(2, occupied.get(3).get(6).size());

	    // Verify the details of the newly added meeting
	    assertEquals(3, occupied.get(3).get(6).get(1).getMonth());
	    assertEquals(6, occupied.get(3).get(6).get(1).getDay());
	    assertEquals(13, occupied.get(3).get(6).get(1).getStartTime());
	    assertEquals(15, occupied.get(3).get(6).get(1).getEndTime());
	    assertEquals("New Meeting", occupied.get(3).get(6).get(1).getDescription());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the addMeeting method to ensure it correctly throws a ConflictsException when trying to add
	 * a new meeting that overlaps with an existing meeting. The test verifies that the exception's message
	 * correctly identifies the conflict, including the details of the existing meeting that causes the overlap.
	 */
	@Test
	public void addMeeting05() throws Exception {
	    // Create mock Meeting objects using Mockito to simulate existing and new meetings
	    Meeting existingMeeting = Mockito.spy(Meeting.class);
	    Meeting newMeeting = Mockito.spy(Meeting.class);

	    // Configure the existing meeting with specific time details for March 6th from 11:00 to 16:00
	    when(existingMeeting.getMonth()).thenReturn(3);
	    when(existingMeeting.getDay()).thenReturn(6);
	    when(existingMeeting.getStartTime()).thenReturn(11);
	    when(existingMeeting.getEndTime()).thenReturn(16);
	    when(existingMeeting.getDescription()).thenReturn("Existing Meeting");

	    // Configure the new meeting for the same day from 13:00 to 15:00, end time overlaps with the existing meeting
	    when(newMeeting.getMonth()).thenReturn(3);
	    when(newMeeting.getDay()).thenReturn(6);
	    when(newMeeting.getStartTime()).thenReturn(13);
	    when(newMeeting.getEndTime()).thenReturn(15);
	    when(newMeeting.getDescription()).thenReturn("New Meeting");
	    
	    // Initialize a new Calendar instance and add the existing meeting
	    Calendar calendar = new Calendar();
	    calendar.addMeeting(existingMeeting);
	    
	    // Define the expected message from the ConflictsException for overlapping meetings
	    String expectedMessage = "Overlap with another item - Existing Meeting - scheduled from 11 and 16";
	    
	    // Assert that adding a conflicting new meeting throws ConflictsException with the correct message
	    ConflictsException thrownError = assertThrows(ConflictsException.class, () -> {
	        calendar.addMeeting(newMeeting);
	    });
	    
	    // Validate that the exception message matches the expected error message
	    assertEquals(expectedMessage, thrownError.getMessage());
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the addMeeting method to ensure it correctly adds a new meeting to a day with no
	 * conflicts and no invalid arguments.
	 */
	@Test
	public void addMeeting06() throws Exception {
	    // Initialize a new Calendar instance 
	    Calendar calendar = new Calendar();
	    
	    // Create mock Meeting object
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting 
	    when(mockMeeting.getMonth()).thenReturn(3);
	    when(mockMeeting.getDay()).thenReturn(6);
	    when(mockMeeting.getStartTime()).thenReturn(12);
	    when(mockMeeting.getEndTime()).thenReturn(13);
	    when(mockMeeting.getDescription()).thenReturn("New Meeting");
	    
	    // Add the mock meeting to the calendar
	    calendar.addMeeting(mockMeeting);
	    
	    // Access the 'occupied' field via reflection to inspect the internal state of the calendar
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	    		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);

	    // Assert that there is now one meeting on this day
	    assertEquals(1, occupied.get(3).get(6).size());

	    // Verify the details of the newly added meeting
	    assertEquals(3, occupied.get(3).get(6).get(0).getMonth());
	    assertEquals(6, occupied.get(3).get(6).get(0).getDay());
	    assertEquals(12, occupied.get(3).get(6).get(0).getStartTime());
	    assertEquals(13, occupied.get(3).get(6).get(0).getEndTime()); 
	}
	
	// ------------------------------------------------------------------------------------------- \\
	// ------------------------------------- clearSchedule()  ------------------------------------ \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Tests the clearSchedule method to verify that it effectively clears all meetings scheduled 
	 * for a specific day. This test adds a meeting to the calendar and then ensures that the 
	 * clearSchedule method removes it, leaving the specified day's schedule empty.
	 */
	@Test
	public void clearSchedule01() throws Exception {
	    // Initialize a new Calendar instance
	    Calendar calendar = new Calendar();
	    
	    // Create a mock Meeting object to simulate an existing meeting
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting 
	    when(mockMeeting.getMonth()).thenReturn(4); 
	    when(mockMeeting.getDay()).thenReturn(5);  
	    when(mockMeeting.getStartTime()).thenReturn(7);  
	    when(mockMeeting.getEndTime()).thenReturn(10);  
	    when(mockMeeting.getDescription()).thenReturn("Mock Meeting");
	    
	    // Access the private 'occupied' field via reflection to check the internal state before clearing
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	    		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);
	    
	    // Add the mock meeting to the calendar and verify it's added
	    calendar.addMeeting(mockMeeting);
	    assertEquals(1, occupied.get(4).get(5).size());
	    
	    // Execute clearSchedule for April 5th and assert the schedule is now empty
	    calendar.clearSchedule(4, 5);
	    assertEquals(0, occupied.get(4).get(5).size());
	}
	
	// ------------------------------------------------------------------------------------------- \\
	// ---------------------------------- printAgenda(int month)  -------------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * This is purely a placeholder to represent a test that might one day be implemented once the
	 * code supports it.
	 */
	@Test
	public void printAgenda01() throws Exception {
		fail();
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	}
	 * This is purely a placeholder to represent a test that might one day be implemented once the
	 * code supports it.
	 */
	@Test
	public void printAgenda02() throws Exception {
		fail();
	}
	
	
	// ------------------------------------------------------------------------------------------- \\
	// ------------------------------ printAgenda(int month, int day)  --------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Tests the printAgenda method for a specific day to ensure it correctly returns a message 
	 * indicating no meetings are booked when there are definitely no meetings on that day.
	 */
	@Test
	public void printAgendaDay01() throws Exception {
	    // Initialize a new Calendar instance
	    Calendar calendar = new Calendar();
	    
	    // Define the expected output
	    String expectedOutput = "No Meetings booked on this date.\n\n";
	    
	    // Verify that the agenda for a day with no meetings returns the expected message
	    assertEquals(expectedOutput, calendar.printAgenda(4, 3));
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the printAgenda method for a specific day by verifying it produces a detailed string
	 * output reflecting the agenda of meetings scheduled for a given day. This test creates a meeting
	 * with a specified room and attendees, adds it to the calendar, and checks if the string returned
	 * by printAgenda matches the expected format and content.
	 */
	@Test
	public void printAgendaDay02() throws Exception {
	    // Initialize a new Calendar instance
	    Calendar calendar = new Calendar();

	    // Create a Room object (impractical to mock, see defect report)
	    Room testRoom = makeRoom("JO7.221");
	    
	    // Create a Person object (impractical to mock, see defect report)
	    Person testPerson = makePerson("Mark Colin");
	    
	    // Set up the list of attendees for the meeting
	    ArrayList<Person> attendees = new ArrayList<>();
	    attendees.add(testPerson);
	    
	    // Create a Meeting object (impractical to mock, see defect report)
	    Meeting testMeeting = makeMeeting(4, 3, 8, 10, attendees, testRoom, "Test Meeting");
	    
	    // Add the configured meeting to the calendar
	    calendar.addMeeting(testMeeting);
	    
	    // Reflectively access the 'occupied' private field of the Calendar class to inspect state
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	        (ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);
	    
	    // Define the expected output string 
	    String expectedOutput = "Agenda for 4/3 are as follows:\n"
	        + "Month is 4, "
	        + "Day is 3, "
	        + "Time slot:8 - 10, "
	        + "Room No:JO7.221: Test Meeting\n"
	        + "Attending: Mark Colin\n";
	    
	    // Assert that the actual printAgenda output matches the expected output
	    assertEquals(expectedOutput, calendar.printAgenda(4, 3)); 
	}

	
	// ------------------------------------------------------------------------------------------- \\
	// ------------------------- getMeeting(int month, int day, int index)  ---------------------- \\
	// ------------------------------------------------------------------------------------------- \\

	/**
	 * Tests the getMeeting method of the Calendar class to ensure it can retrieve a specific meeting 
	 * correctly based on the given date and index. This test involves mocking a meeting, adding it to 
	 * the calendar, and then retrieving it to verify all properties match the expected values.
	 */
	@Test
	public void getMeeting01() throws Exception {
	    // Initialize a new Calendar instance
	    Calendar calendar = new Calendar();
	    
	    // Create a mock Meeting object to simulate an existing meeting
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting 
	    when(mockMeeting.getMonth()).thenReturn(5); 
	    when(mockMeeting.getDay()).thenReturn(6);  
	    when(mockMeeting.getStartTime()).thenReturn(8);  
	    when(mockMeeting.getEndTime()).thenReturn(10);
	    
	    // Access the private 'occupied' field via reflection to check the internal state before clearing
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	    		(ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);
	    
	    // Add the mock meeting to the calendar and verify it's added
	    calendar.addMeeting(mockMeeting);
	    
	    // Verify that all properties of the mock meeting match the expected values
	    assertEquals(5, occupied.get(5).get(6).get(0).getMonth());
	    assertEquals(6, occupied.get(5).get(6).get(0).getDay());
	    assertEquals(8, occupied.get(5).get(6).get(0).getStartTime());
	    assertEquals(10, occupied.get(5).get(6).get(0).getEndTime());
	}

	
	// ------------------------------------------------------------------------------------------- \\
	// ------------------------ removeMeeting(int month, int day, int index)  -------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Tests the removeMeeting method of the Calendar class to ensure it correctly removes a specific 
	 * meeting from the calendar. This test involves creating and adding a meeting, then removing it
	 * using the removeMeeting method, and finally verifying the meeting list is empty after removal.
	 */
	@Test
	public void removeMeeting01() throws Exception {
	    // Initialize a new Calendar instance
	    Calendar calendar = new Calendar();
	    
	    // Create a mock Meeting object 
	    Meeting mockMeeting = Mockito.spy(Meeting.class);

	    // Configure the mock meeting
	    when(mockMeeting.getMonth()).thenReturn(9); 
	    when(mockMeeting.getDay()).thenReturn(6);  
	    when(mockMeeting.getStartTime()).thenReturn(8);  
	    when(mockMeeting.getEndTime()).thenReturn(10);
	    
	    // Add the mock meeting to the calendar
	    calendar.addMeeting(mockMeeting);
	    
	    // Access the private 'occupied' field via reflection to check the internal state before removal
	    Field occupiedField = Calendar.class.getDeclaredField("occupied");
	    occupiedField.setAccessible(true);
	    ArrayList<ArrayList<ArrayList<Meeting>>> occupied = 
	        (ArrayList<ArrayList<ArrayList<Meeting>>>) occupiedField.get(calendar);
	    
	    // Ensure the meeting was added successfully
	    assertEquals(1, occupied.get(9).get(6).size());
	    
	    // Perform the removal of the meeting based on the specified month, day, and index
	    calendar.removeMeeting(9, 6, 0);
	    
	    // Assert the meeting list for the specific day is now empty
	    assertEquals(0, occupied.get(9).get(6).size());
	}

}
