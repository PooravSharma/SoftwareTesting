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

class Description_BB {

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
		
		//TestID Des1
		@Test
		@DisplayName("Valid Description (TestID:Des1)")
		void Des1() throws Exception {   
			// make spy, we can use some of the real methods and mock some of the other methods
			planner = Mockito.spy(Planner.class);
			
			//override main menu with do nothing..we are mocking this ha-ah!
		    Mockito.doNothing().when(planner).mainMenu();
		    	    
			//set inputs as per table
			int month = 1;
			int day = 1;
			int start = 0;
			int end = 12;
			String roomIn = "JO18.330";
			String personIn = "Justin Gardener";
			String complete = "done";
			String description = "Meeting about inflation"; //testing this 
					
			
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
			//Checking to see if it outputs the correct last string
			Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));	
		}
		
		//TestID Des2
		@Test
		@DisplayName("Valid Blank Description (TestID:Des2)")
		void Des2() throws Exception {   
			// make spy, we can use some of the real methods and mock some of the other methods
			planner = Mockito.spy(Planner.class);
			
			//override main menu with do nothing..we are mocking this ha-ah!
		    Mockito.doNothing().when(planner).mainMenu();
		    	    
			//set inputs as per table
			int month = 1;
			int day = 1;
			int start = 0;
			int end = 12;
			String roomIn = "JO18.330";
			String personIn = "Justin Gardener";
			String complete = "done";
			String description = "";//testing this 
					
			
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
			//Checking to see if it outputs the correct last string
			Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));	
		}
		

}
