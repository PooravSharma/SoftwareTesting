package tests;

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

import au.edu.sccs.csp3105.NBookingPlanner.Planner;

// A small tutorial testing class
public class ConsoleOutput {

	// save out the console output to a stream, rather than printing to the actual console window
	private final ByteArrayOutputStream out = new ByteArrayOutputStream(); // data can be written to this byte array
	private final PrintStream originalOut = System.out; // write output data in text instead of bytes
	
	// lets make the planner class, so we can use it to test
	private Planner planner;

	// helper method, takes in the console stream, cleans up the text and returns the last line
	private String GetLastConsoleOutput(String input) {
		String output = input;
		output = output.strip();
		String[] lines = output.split("\n"); 
		String lastLine = lines[lines.length - 1];
		return lastLine;
	}
	
	// set up our stream to capture the console output
	@BeforeEach
	public void setStreams() {
	    System.setOut(new PrintStream(out));  // reassigns the output stream, we can store in the out variable
	}

	// reset after the test is done
	@AfterEach
	public void restoreInitialStreams() {
	    System.setOut(originalOut); // reset
	}
	
	// simple test to show you how we can assert a print statement (compare observed with expected)
	@Test
	public void outGood() {
	    System.out.print("test output");
	    assertEquals("test output", out.toString());
	}
	
	// simple test to show you how we can assert a print statement (compare observed with expected)
	@Test
	public void outBad() {
	    System.out.print("test output");
	    assertEquals("test outpu", out.toString());
	}
		
	@Test
	@DisplayName("SM3 Invalid day")
	void sM3InvalidDay() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 6;
		int day = 39;
		int start = 9;
		int end = 10;
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
				
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
							Integer.toString(day),
							Integer.toString(start),
							Integer.toString(end),
							roomIn, 
							personIn, 
							complete, 
							description,
							"cancel").execute(() -> {
			//call the schedule meeting method
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Day does not exist.", GetLastConsoleOutput(out.toString()));	
	}
	
	@Test
	@DisplayName("SM4 Valid day")
	void sM4ValidDay() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 6;
		int day = 10;
		int start = 9;
		int end = 10;
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
				
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
							Integer.toString(day),
							Integer.toString(start),
							Integer.toString(end),
							roomIn, 
							personIn, 
							complete, 
							description,
							"cancel").execute(() -> {
			//call the schedule meeting method	
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));		
	}
	
	@Test
	@DisplayName("SM1 Valid meeting room ID")
	void sM1ValidMeetingRoom() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 6;
		int day = 10;
		int start = 9;
		int end = 10;
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
				
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
				Integer.toString(day),
				Integer.toString(start),
				Integer.toString(end),
				roomIn, 
				personIn, 
				complete, 
				description,
				"cancel").execute(() -> {
			//call the schedule meeting method
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));		
	}

	@Test
	@DisplayName("SM2 Invalid meeting room ID")
	void sM2InvalidMeetingRoom() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 6;
		int day = 10;
		int start = 9;
		int end = 10;
		String badRoomIn = "ML18.33";
		String roomIn = "ML18.330";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";
				
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
				Integer.toString(day),
				Integer.toString(start),
				Integer.toString(end),
				badRoomIn,
				roomIn, 
				personIn, 
				complete, 
				description,
				"cancel").execute(() -> {
			//call the schedule meeting method
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));		
	}
	
	@Test
	@DisplayName("SM2alt Invalid meeting room ID")
	void sM2_alt_InvalidMeetingRoom() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		
		//override main menu with do nothing..we are mocking this ha-ah!
	    Mockito.doNothing().when(planner).mainMenu();
	    	    
		//set inputs as per table
		int month = 6;
		int day = 10;
		int start = 9;
		int end = 10;
		String badRoomIn = "ML18.33";
			
		
		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
				Integer.toString(day),
				Integer.toString(start),
				Integer.toString(end),
				badRoomIn,
				"cancel").execute(() -> {
			//call the schedule meeting method
			planner.scheduleMeeting();
        });
		
		//assert what we expect to be printed to console, is what is actually observed
		Assertions.assertEquals("Enter the desired room ID, or cancel to cancel:", GetLastConsoleOutput(out.toString()));		
	}
}
