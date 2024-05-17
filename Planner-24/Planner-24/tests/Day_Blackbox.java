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

import com.github.stefanbirkner.systemlambda.SystemLambda;

import au.edu.sccs.csp3105.NBookingPlanner.Planner;


public class Day_Blackbox {

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
   
    // ID: BB_DAY_1
	@Test
	@DisplayName("BB valid Day")
	void BB_ValidDay() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);

		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();

		//set inputs as per table
		int month = 10;
		int day = 5;
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
	
	// ID: BB_DAY_2
	@Test
	@DisplayName("BB Invalid 29 of Feb")
	void BB_InvalidDayFebruary() throws Exception {   

		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();
		
		int month = 2;
		int day = 29;
		int start = 11;
		int end = 12;
		String roomIn = "JO15.236";
		String personIn = "Justin Gardener";
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

		Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));		
		}
		
	// ID: BB_DAY_3
	@Test
	@DisplayName("Enter a alphabetic chracter")
    void BB_InvalidDay() throws Exception {
      
        planner = Mockito.spy(Planner.class);
        Mockito.doNothing().when(planner).mainMenu();
        
        int month = 10;
        String day = "a";  // Invalid input for day
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
                        Integer.toString(month),
                        day,
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

        String expected = "For input string: \"a\"";
        String actual = exception.getMessage();
        Assertions.assertTrue(actual.contains(expected));
    }
	
	// ID: BB_DAY_8
	@Test
	@DisplayName("End the vacation date before the start date")
    void BB_InValidBookVacation() throws Exception {
	
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		int smonth = 4;
		int sday = 1; 
		int emonth = 3;
		int eday = 11;
		String personIn = "Justin Gardener";
	
		withTextFromSystemIn(Integer.toString(smonth),
				Integer.toString(sday),
				Integer.toString(emonth),
				Integer.toString(eday), 
				personIn
				).execute(() -> {
					//call the schedule meeting method
					planner.scheduleVacation();
				});
			
			Assertions.assertEquals("Day does not exist.", GetLastConsoleOutput(out.toString()));		
			}

	
//  Boundary Analysis Schedule Meeting
		
	// ID: BB_DAY_4
	@Test
	@DisplayName("BB  out side of low boundary for Day start from 0")
	void BB_OutsideLowBoundaryDay() throws Exception {   
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		int month = 10;
		int day = 0;
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
				planner.scheduleMeeting();
				});
		
		Assertions.assertEquals("Day does not exist.", GetLastConsoleOutput(out.toString()));	
		}
	
	// ID: BB_DAY_5
	@Test
	@DisplayName("BB  lower Boundary for Day start from 1")
	void BB_LowerBoundaryDay() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
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
			
		Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));	
		}
	
	// ID: BB_DAY_6
    @Test
	@DisplayName("BB upper Boundary for Day")
	void BB_UpperBoundaryDay() throws Exception {   
		// make spy, we can use some of the real methods and mock some of the other methods
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		int month = 11;
		int day = 31;
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
			
		Assertions.assertEquals("Enter a description for the meeting:", GetLastConsoleOutput(out.toString()));	
		}
    
 // ID: BB_DAY_7
	@Test
	@DisplayName("BB above the upper boundary day in schedule meeting")
	void BB_AboveUppBoundaryDay() throws Exception {   
		
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		int month = 11;
		int day = 32;
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
	
		Assertions.assertEquals("Day does not exist.", GetLastConsoleOutput(out.toString()));	
	}
	
	
//		Boundary Analysis Schedule Vacation
	
	// ID: BB_DAY_9
	@Test
	@DisplayName("BB  out side of low boundary for Day start from 0 in vacation")
	void BBVacation_OutsideLowBoundaryDay() throws Exception {

		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();

		int smonth = 4;
		int sday = 0; 
		int emonth = 5;
		int eday = 11;
		String personIn = "Justin Gardener";

		withTextFromSystemIn(Integer.toString(smonth),
				Integer.toString(sday),
				Integer.toString(emonth),
				Integer.toString(eday), 
				personIn
				).execute(() -> {
					//call the schedule meeting method
					planner.scheduleVacation();
				});

			Assertions.assertEquals("Day does not exist.", GetLastConsoleOutput(out.toString()));				
}

	// ID: BB_DAY_10
	@Test
	@DisplayName("BB  lower Boundary for Day start from 1 in vacation")
	void BBVacationLowBoundaryDay() throws Exception {
		
		planner = Mockito.spy(Planner.class);
	
		//override main menu with do nothing
		Mockito.doNothing().when(planner).mainMenu();
	
		//set inputs as per table
		int smonth = 4;
		int sday = 1; 
		int emonth = 5;
		int eday = 11;
		String personIn = "Justin Gardener";
	
		withTextFromSystemIn(Integer.toString(smonth),
				Integer.toString(sday),
				Integer.toString(emonth),
				Integer.toString(eday), 
				personIn
				).execute(() -> {
					//call the schedule meeting method
					planner.scheduleVacation();
				});
			
			Assertions.assertEquals("Enter a person's name, or cancel to cancel the request", GetLastConsoleOutput(out.toString()));
		}
	
	// ID: BB_DAY_11
	@Test
	@DisplayName("BB upper Boundary for Day in vacation")
	void BBVacationUpBoundaryDay() throws Exception {   
		planner = Mockito.spy(Planner.class);
	
		Mockito.doNothing().when(planner).mainMenu();
	
		int smonth = 7;
		int sday = 31; 
		int emonth = 8;
		int eday = 11;
		String personIn = "Justin Gardener";
	
		withTextFromSystemIn(Integer.toString(smonth),
				Integer.toString(sday),
				Integer.toString(emonth),
				Integer.toString(eday), 
				personIn
				).execute(() -> {
				
					planner.scheduleVacation();
				});
			
			Assertions.assertEquals("Enter a person's name, or cancel to cancel the request", GetLastConsoleOutput(out.toString()));
		}	
	
	// ID: BB_DAY_12
	@Test
	@DisplayName("BB above the upper boundary for day in vacation")
	void BBVacation_AboveUppBoundaryDay() throws Exception {   
		
		planner = Mockito.spy(Planner.class);
		Mockito.doNothing().when(planner).mainMenu();
	
		int smonth = 4;
		int sday = 32; 
		int emonth = 5;
		int eday = 11;
		String personIn = "Justin Gardener";
	
		withTextFromSystemIn(Integer.toString(smonth),
				Integer.toString(sday),
				Integer.toString(emonth),
				Integer.toString(eday), 
				personIn
				).execute(() -> {
					
					planner.scheduleVacation();
				});
			
			Assertions.assertEquals("Day doe not exist", GetLastConsoleOutput(out.toString()));
			
	}

}


