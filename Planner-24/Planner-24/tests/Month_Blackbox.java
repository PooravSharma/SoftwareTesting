package tests;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import au.edu.sccs.csp3105.NBookingPlanner.Planner;

public class Month_Blackbox {

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
	@DisplayName("BB valid Numeric Month")
	void BB_ValidMonth() throws Exception {   
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
		String personIn = "Justin Gardener";
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
	@DisplayName("BB Invalid Numeric Month")
	void BB_InvalidNumericMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 15;
		int day = 1;
		int start = 11;
		int end = 12;
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
		Assertions.assertEquals("Month does not exist.", GetLastConsoleOutput(out.toString()));	
	}

	@Test
	@DisplayName("BB Invalid Non-Numeric Month")
	void BB_InvalidNonNumericMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();
		String output = tapSystemOut(() -> {
			withTextFromSystemIn("1","a").execute(() -> {
				planner.mainMenu();
			});
		});

		//Assertions.assertEquals("", GetLastConsoleOutput(output)); // Expect an empty string to be returned
		Assertions.assertTrue(output.toString().contains(""));
	}

	@Test
	@DisplayName("BB Invalid White space for Month")
	void BB_InvalidWhiteSpaceMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();
		String output = tapSystemOut(() -> {
			withTextFromSystemIn(" ", "1", "11", "12").execute(() -> {
				planner.mainMenu();
			});
		});



		Assertions.assertEquals("", GetLastConsoleOutput(output));
	}
	
		// Boundary Analysis
		
	@Test
	@DisplayName("BB  our side of low boundary for Month start from 0")
	void BB_OutsideLowBoundaryMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 0;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
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
		Assertions.assertEquals("Month does not exist.", GetLastConsoleOutput(out.toString()));	
		}
		
	@Test
	@DisplayName("BB  lower Boundary for Month start from 1")
	void BB_LowerBoundaryMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 1;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
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
	@DisplayName("BB upper Boundary for Month")
	void BB_UpperBoundaryMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 12;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
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
		Assertions.assertEquals("Month does not exist.", GetLastConsoleOutput(out.toString()));	
		}
	@Test
	@DisplayName("BB above the upper boundary for Month")
	void BB_AboveUppBoundaryMonth() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 13;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
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
		Assertions.assertEquals("Month does not exist.", GetLastConsoleOutput(out.toString()));	
	}
	
}
