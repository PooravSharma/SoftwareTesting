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

import com.github.stefanbirkner.systemlambda.SystemLambda;

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
	
	
	// ID: M_BB_1
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
	
	// ID: M_BB_2
	@Test
	@DisplayName("BB Invalid Numeric Month")
	void BB_InvalidNumericMonth() throws Exception {   
		
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
	
	// ID: M_BB_3
	@Test
	@DisplayName("BB Invalid Non-Numeric Month")
	void BB_InvalidNonNumericMonth() throws Exception {   
		
        planner = Mockito.spy(Planner.class);

        // Override the main menu method to do nothing
        Mockito.doNothing().when(planner).mainMenu();

        // Set inputs as per table
        String month = "a";
        int day = 1;  
        int start = 11;
        int end = 12;
        String roomIn = "JO15.236";
        String personIn = "Justin Gardener";
        String complete = "done";
        String description = "Desc";

        // Use System Lambda to capture and test for exceptions
        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> {
            SystemLambda.tapSystemOut(() -> {
           
                SystemLambda.withTextFromSystemIn(
                        month,
                        Integer.toString(day),
                        Integer.toString(start),
                        Integer.toString(end),
                        roomIn,
                        personIn,
                        complete,
                        description,
                        "cancel"
                ).execute(() -> {
                   
                    planner.scheduleMeeting();
                });
            });
        });

    
        String expected = "For input string: \"a\"";
        String actual = exception.getMessage();
        Assertions.assertTrue(actual.contains(expected));
	}
	
	// ID: M_BB_4
	@Test
	@DisplayName("BB Invalid White space for Month")
	void BB_InvalidWhiteSpaceMonth() throws Exception {   
		 planner = Mockito.spy(Planner.class);

	        Mockito.doNothing().when(planner).mainMenu();

	        String month = "";
	        int day = 1;  
	        int start = 11;
	        int end = 12;
	        String roomIn = "JO15.236";
	        String personIn = "Justin Gardener";
	        String complete = "done";
	        String description = "Desc";

	      
	        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> {
	            SystemLambda.tapSystemOut(() -> {
	           
	                SystemLambda.withTextFromSystemIn(
	                        month,
	                        Integer.toString(day),
	                        Integer.toString(start),
	                        Integer.toString(end),
	                        roomIn,
	                        personIn,
	                        complete,
	                        description,
	                        "cancel"
	                ).execute(() -> {
	                    // Call the schedule meeting method
	                    planner.scheduleMeeting();
	                });
	            });
	        });

	        // Optionally check the exception message if needed
	        String expected = "For input string: \"\"";
	        String actual = exception.getMessage();
	        Assertions.assertTrue(actual.contains(expected));
	}
	
		// Boundary Analysis
		
	// ID: M_BB_5
	@Test
	@DisplayName("BB  our side of low boundary for Month start from 0")
	void BB_OutsideLowBoundaryMonth() throws Exception {   
		
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
	
	// ID: M_BB_6	
	@Test
	@DisplayName("BB  lower Boundary for Month start from 1")
	void BB_LowerBoundaryMonth() throws Exception {   
	
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		
		int month = 1;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";

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
	
	// ID: M_BB_7	
	@Test
	@DisplayName("BB upper Boundary for Month")
	void BB_UpperBoundaryMonth() throws Exception {   

		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();


		int month = 12;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";

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
			
	
		Assertions.assertEquals("Month does not exist.", GetLastConsoleOutput(out.toString()));	
		}
	
	// ID: M_BB_8
	@Test
	@DisplayName("BB above the upper boundary for Month")
	void BB_AboveUppBoundaryMonth() throws Exception {   
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		
		int month = 13;
		int day = 1;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Mark Colin";
		String complete = "done";
		String description = "Desc";

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

	
		Assertions.assertEquals("Month does not exist.", GetLastConsoleOutput(out.toString()));	
	}
	
}
