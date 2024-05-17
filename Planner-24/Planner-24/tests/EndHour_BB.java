
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
public class EndHour_BB {

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
	
	//TestID EndH1
	@Test
	@DisplayName("Valid End Hour (TestID:EndH1)")
	void EndH1() throws Exception {   
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
		String description = "Meeting";
				
		
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
	
	//TestID EndH2
		@Test
		@DisplayName("Invalid End Hour (TestID:EndH2)")
		void EndH2() throws Exception {   
			// make spy, we can use some of the real methods and mock some of the other methods
			planner = Mockito.spy(Planner.class);
			
			//override main menu with do nothing..we are mocking this ha-ah!
		    Mockito.doNothing().when(planner).mainMenu();
		    	    
			//set inputs as per table
			int month = 1;
			int day = 1;
			int start = 0;
			int end = -12;
			String roomIn = "JO18.330";
			String personIn = "Justin Gardener";
			String complete = "done";
			String description = "Meeting";
					
			
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
			Assertions.assertEquals("Illegal hour.", GetLastConsoleOutput(out.toString()));	
		}
		
		//TestID EndH3
		@Test
		@DisplayName("Invalid End Hour (TestID:EndH3)")
		void EndH3() throws Exception {   
			// make spy, we can use some of the real methods and mock some of the other methods
			planner = Mockito.spy(Planner.class);
			
			//override main menu with do nothing..we are mocking this ha-ah!
		    Mockito.doNothing().when(planner).mainMenu();
		    	    
			//set inputs as per table
			int month = 1;
			int day = 1;
			int start = 0;
			int end = 35;
			String roomIn = "JO18.330";
			String personIn = "Justin Gardener";
			String complete = "done";
			String description = "Meeting";
					
			
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
			Assertions.assertEquals("Illegal hour.", GetLastConsoleOutput(out.toString()));	
		}
		
		
		//TestID EndH4 for Upper BVA
		@Test
		@DisplayName("Valid End Hour (TestID:EndH4)")
		void EndH4() throws Exception {   
			// make spy, we can use some of the real methods and mock some of the other methods
			planner = Mockito.spy(Planner.class);
			
			//override main menu with do nothing..we are mocking this ha-ah!
		    Mockito.doNothing().when(planner).mainMenu();
		    	    
			//set inputs as per table
			int month = 1;
			int day = 1;
			int start = 0;
			int end = 22;
			String roomIn = "JO18.330";
			String personIn = "Justin Gardener";
			String complete = "done";
			String description = "Meeting";
					
			
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
		
		//TestID EndH5 for Upper BVA
		@Test
		@DisplayName("Valid End Hour (TestID:EndH5)")
		void EndH5() throws Exception {   
			// make spy, we can use some of the real methods and mock some of the other methods
			planner = Mockito.spy(Planner.class);
			
			//override main menu with do nothing..we are mocking this ha-ah!
		    Mockito.doNothing().when(planner).mainMenu();
		    	    
			//set inputs as per table
			int month = 1;
			int day = 1;
			int start = 0;
			int end = 23;
			String roomIn = "JO18.330";
			String personIn = "Justin Gardener";
			String complete = "done";
			String description = "Meeting";
					
			
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

		//TestID EndH6 for Upper BVA
				@Test
				@DisplayName("Invalid End Hour (TestID:EndH6)")
				void EndH6() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 0;
					int end = 35;
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
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
					Assertions.assertEquals("Illegal hour.", GetLastConsoleOutput(out.toString()));	
				}
				
				
				//TestID EndH7 for lower BVA
				@Test
				@DisplayName("Invalid End Hour (TestID:EndH7)")
				void EndH7() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 0;
					int end = -1;
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
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
					Assertions.assertEquals("Illegal hour.", GetLastConsoleOutput(out.toString()));	
				}
				
				//TestID EndH8 for Lower BVA
				@Test
				@DisplayName("Valid End Hour (TestID:EndH8)")
				void EndH8() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 0;
					int end = 0;
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
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
				
				//TestID EndH9 for Lower BVA
				@Test
				@DisplayName("Valid End Hour (TestID:EndH9)")
				void EndH9() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 0;
					int end = 1;
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
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
				
				//TestID EndH10 
				@Test
				@DisplayName("Invalid End Hour (TestID:EndH10)")
				void EndH10() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 10;
					int end = 5;
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
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
					Assertions.assertEquals("Meeting starts before it ends.", GetLastConsoleOutput(out.toString()));	
				}
				
				//TestID EndH11 
				@Test
				@DisplayName("Invalid End Hour (TestID:EndH11)")
				void EndH11() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 0;
					String end = "a";
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
					//buffer input values, console will call these one by one! Thanks system lambda library
					withTextFromSystemIn(Integer.toString(month),
										Integer.toString(day),
										Integer.toString(start),
										end,
										roomIn, 
										personIn, 
										complete, 
										description,
										"cancel").execute(() -> {
						//call the schedule meeting method
						planner.mainMenu();
			        });
					
					//assert what we expect to be printed to console, is what is actually observed
					Assertions.assertEquals("", GetLastConsoleOutput(out.toString()));	
				}
				
				//TestID EndH12
				@Test
				@DisplayName("Invalid End Hour (TestID:EndH12)")
				void EndH12() throws Exception {   
					// make spy, we can use some of the real methods and mock some of the other methods
					planner = Mockito.spy(Planner.class);
					
					//override main menu with do nothing..we are mocking this ha-ah!
				    Mockito.doNothing().when(planner).mainMenu();
				    	    
					//set inputs as per table
					int month = 1;
					int day = 1;
					int start = 0;
					String end = "";
					String roomIn = "JO18.330";
					String personIn = "Justin Gardener";
					String complete = "done";
					String description = "Meeting";
							
					
					//buffer input values, console will call these one by one! Thanks system lambda library
					withTextFromSystemIn(Integer.toString(month),
										Integer.toString(day),
										Integer.toString(start),
										end,
										roomIn, 
										personIn, 
										complete, 
										description,
										"cancel").execute(() -> {
						//call the schedule meeting method
						planner.mainMenu();
			        });
					

				
					//assert what we expect to be printed to console, is what is actually observed
					Assertions.assertEquals("", GetLastConsoleOutput(out.toString()));	
				}
}
