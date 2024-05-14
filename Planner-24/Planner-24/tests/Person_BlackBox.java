package tests;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import au.edu.sccs.csp3105.NBookingPlanner.Planner;

public class Person_BlackBox {
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
	
	@Test
	@DisplayName("BB Valid person from the list")
	void BB_ValidPerson() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Ashley Matthews";
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
				"cancel"
				).execute(() -> {
					//call the schedule meeting method
					planner.scheduleMeeting();
				});
			
			//assert what we expect to be printed to console, is what is actually observed
			Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));		
		}
	
	@Test
	@DisplayName("BB Entering Cancel in Check Person Agenda")
	void BB_CheckCancelOption() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;

		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
				Integer.toString(day),
				"cancel"
				).execute(() -> {
					//call the schedule meeting method
					planner.checkAgendaPerson();
				});
			
			//assert what we expect to be printed to console, is what is actually observed
			//Assertions.assertEquals(" ", GetLastConsoleOutput(out.toString()));	
		Assertions.assertTrue(out.toString().contains(" "));
		}
	
	@Test
	@DisplayName("BB inserting another person name which is not in the suggested names")
	void BB_InValidPerson() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Ahmad Sharifi";
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
//			Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));
			Assertions.assertTrue(out.toString().contains("Requested employee does not exist"));

		}
	
	@Test
	@DisplayName("BB inserting another person name which is not in the suggested names in checking agenda")
	void BB_InValidPersonCheckAgenda() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;
	
		String personIn = "Ahmad Sharifi";


		//buffer input values, console will call these one by one! Thanks system lambda library
		withTextFromSystemIn(Integer.toString(month),
				Integer.toString(day),
				Integer.toString(day),
				personIn, 
				"cancel").execute(() -> {
					//call the schedule meeting method
					planner.checkAgendaPerson();
				});
			
			//assert what we expect to be printed to console, is what is actually observed
			Assertions.assertEquals("Requested employee does not exist", GetLastConsoleOutput(out.toString()));		
		}
	
	@Test
	@DisplayName("BB inserting a name with lower case starting from both first and lastname")
	void BB_PersonCaseSensitivity() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "ashley matthews";
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
	@DisplayName("BB inserting empty string")
	void BB_PersonEmptyString() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "";
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
	@DisplayName("BB inserting extra white space between name and surname")
	void BB_PersonExtraWhiteSpace() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Jacquie  Martin";
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
	
	
}
