package tests;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import au.edu.sccs.csp3105.NBookingPlanner.Planner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BlackBoxMenuInput {
	
	// ------------------------------------------------------------------------------------------- \\
	// ------------------------------------ PREPARE FOR TESTS ------------------------------------ \\
	// ------------------------------------------------------------------------------------------- \\
	
	// Any required beforeEach/afterEach etc. methods to be added here.
	
	
	// ------------------------------------------------------------------------------------------- \\
	// --------------------------------------- BEGIN TESTS --------------------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Tests that the menu correctly handles valid input '0' by checking for the expected system 
	 * exit behavior. This test initializes a spy of the Planner class, simulates user input using 
	 * SystemLambda, and confirms that the `System.exit` method is invoked, which indicates the 
	 * program attempts to terminate as expected.
	 * <p>
	 * The output from the system is captured in a normalized format to ensure any 
	 * environment-specific newline characters do not affect the test. This method intercepts and 
	 * captures the system exit call to prevent actual termination of the JVM, allowing us to verify 
	 * correct application behavior upon receiving the exit command.
	 */
	@Test
	@DisplayName("BB_menu_0 valid menu input '0'")
	public void BB_menu_0() throws Exception {
	    // Mock the Planner class to assess the behavior of the mainMenu function
	    Planner planner = Mockito.spy(Planner.class);

	    // Capture the console output in a string
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Intercept calls to System.exit to confirm that the exit command is processed without 
	    	// terminating the JVM
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate user input '0' which should trigger a system termination (exit) via 
	        	// System.in
	            SystemLambda.withTextFromSystemIn("0").execute(() -> planner.mainMenu());
	        });
	    });

	    // Verify that the output confirms a call to System.exit, indicating a successful exit 
	    // command processing
	    assertTrue(output.contains("Exit"));
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '1' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '1'.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to 
	 * verify the order and content of the messages displayed to the user, ensuring they appear 
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * this exisst purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month of the meeting (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_1 valid menu input '1'")
	public void BB_menu_1() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month of the meeting (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "1",        	// Menu --> schedule meeting
	                "2",        	// Month
	                "2",        	// Day
	                "4",			// Start time
	                "5",			// End Time
	                "JO34.536", 	// Room
	                "Travis Colin",	// Name
	                "done",			// No more names
	                "desc",			// Description
	                "0"         	// Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '2' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '2' for booking 
	 * vacation dates.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month that the vacation starts (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_2 valid menu input '2'")
	public void BB_menu_2() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month that the vacation starts (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "2",            // Menu --> schedule vacation
	                "7",            // Start month
	                "10",           // Start day
	                "7",            // End month
	                "15",           // End day
	                "Rose Austin",  // Employee name
	                "0"             // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}

	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '3' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '3' for checking 
	 * room availability.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month of the meeting (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_3 valid menu input '3'")
	public void BB_menu_3() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month of the meeting (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "3",    // Menu --> check room availability
	                "1",    // Month
	                "2",    // Day
	                "3",    // Start time
	                "4",    // End time
	                "0"     // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}

	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '4' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '4' for checking 
	 * employee availability.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month of the meeting (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_4 valid menu input '4'")
	public void BB_menu_4() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month of the meeting (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "4",    // Menu --> check employee availability
	                "1",    // Month
	                "2",    // Day
	                "3",    // Start time
	                "4",    // End time
	                "0"     // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}

	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '5' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '5' for checking 
	 * the agenda of a room.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_5 valid menu input '5'")
	public void BB_menu_5() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "5",        // Menu --> check agenda for a room
	                "1",        // Month
	                "5",        // Day (optional, can be used if detailed input)
	                "JO7.221",  // Room ID
	                "0"         // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}

	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '6' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '6' for checking 
	 * the agenda of a person.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month (1-12): "
	 * </pre>
	 */
	@Test
	
	@DisplayName("BB_menu_6 valid menu input '6'")
	public void BB_menu_6() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "6",            // Menu --> check agenda for a person
	                "5",            // Month
	                "6",            // Day (optional, can be used if detailed input)
	                "Steven Lewis", // Person name
	                "0"             // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}

	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles an invalid input '-5' by checking for the expected error 
	 * message. This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing an invalid menu input.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the error messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] "Please enter a number from 0 - 6"
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_7 invalid menu input '-5'")
	public void BB_menu_7() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct error handling
	    String expectedOutput = "Please enter a number from 0 - 6";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "-5",   // Invalid menu input
	                "0"     // Exit the menu to end the test
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected error message appears in the correct sequence
	    assertEquals(expectedOutput, lines[11]);
	}

	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles an invalid input '12' by checking for the expected error 
	 * message. This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing an input beyond the valid 
	 * range.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the error messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] "Please enter a number from 0 - 6"
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_8 invalid menu input '12'")
	public void BB_menu_8() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct error handling
	    String expectedOutput = "Please enter a number from 0 - 6";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "12",   // Invalid menu input outside the valid range
	                "0"     // Exit the menu to end the test
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected error message appears in the correct sequence
	    assertEquals(expectedOutput, lines[11]);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles an invalid input '-1' by checking for the expected error 
	 * message. This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing an invalid menu input below 
	 * the valid range.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the error messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] "Please enter a number from 0 - 6"
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_9 invalid lower boundary '-1'")
	public void BB_menu_9() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct error handling
	    String expectedOutput = "Please enter a number from 0 - 6";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "-1",   // Invalid menu input below the valid range
	                "0"     // Exit the menu to end the test
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected error message appears in the correct sequence
	    assertEquals(expectedOutput, lines[11]);
	}

	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles valid lower boundary value of '0' by checking for the 
	 * expected system exit behavior. This test initializes a spy of the Planner class, simulates 
	 * user input using SystemLambda, and confirms that the `System.exit` method is invoked, which 
	 * indicates the program attempts to terminate as expected.
	 * <p>
	 * The output from the system is captured in a normalized format to ensure any 
	 * environment-specific newline characters do not affect the test. This method intercepts and 
	 * captures the system exit call to prevent actual termination of the JVM, allowing us to verify 
	 * correct application behavior upon receiving the exit command.
	 */
	@Test
	@DisplayName("BB_menu_10 valid lower boundary '0'")
	public void BB_menu_10() throws Exception {
	    // Mock the Planner class to assess the behavior of the mainMenu function
	    Planner planner = Mockito.spy(Planner.class);

	    // Capture the console output in a string
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Intercept calls to System.exit to confirm that the exit command is processed without 
	    	// terminating the JVM
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate user input '0' which should trigger a system termination (exit) via 
	        	// System.in
	            SystemLambda.withTextFromSystemIn("0").execute(() -> planner.mainMenu());
	        });
	    });

	    // Verify that the output confirms a call to System.exit, indicating a successful exit 
	    // command processing
	    assertTrue(output.contains("Exit"));
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles the valid lower boundary '1' by checking for the expected 
	 * prompt. This test initializes a spy of the Planner class, simulates user input using 
	 * SystemLambda,and asserts that the output matches expected results after processing input '1'.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to 
	 * verify the order and content of the messages displayed to the user, ensuring they appear 
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * this exisst purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month of the meeting (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_11 valid lower boundary '1'")
	public void BB_menu_11() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month of the meeting (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "1",        	// Menu --> schedule meeting
	                "2",        	// Month
	                "2",        	// Day
	                "4",			// Start time
	                "5",			// End Time
	                "JO34.536", 	// Room
	                "Travis Colin",	// Name
	                "done",			// No more names
	                "desc",			// Description
	                "0"         	// Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '5' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '5' for checking 
	 * the agenda of a room.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month (1-12): "
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_12 valid upper boundary '5'")
	public void BB_menu_12() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "5",        // Menu --> check agenda for a room
	                "1",        // Month
	                "5",        // Day (optional, can be used if detailed input)
	                "JO7.221",  // Room ID
	                "0"         // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles input '6' by checking for the expected prompt.
	 * This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing input '6' for checking 
	 * the agenda of a person.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] 
	 * [12] "Enter the month (1-12): "
	 * </pre>
	 */
	@Test
	
	@DisplayName("BB_menu_13 valid upper boundary '6'")
	public void BB_menu_13() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct method behavior
	    String expectedOutput = "Enter the month (1-12): ";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "6",            // Menu --> check agenda for a person
	                "5",            // Month
	                "6",            // Day (optional, can be used if detailed input)
	                "Steven Lewis", // Person name
	                "0"             // Menu --> exit
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected prompt appears in the correct sequence
	    assertEquals(expectedOutput, lines[12]);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests that the menu correctly handles an invalid input '7' by checking for the expected error 
	 * message. This test initializes a spy of the Planner class, simulates user input using SystemLambda,
	 * and asserts that the output matches expected results after processing an input just outside the 
	 * valid range.
	 * <p>
	 * The output is captured and split into lines based on newline characters. This allows us to
	 * verify the order and content of the error messages displayed to the user, ensuring they appear
	 * as expected. The array indices below map to specific parts of the expected output array,
	 * existing purely for developer reference:
	 * <pre>
	 * [0]  
	 * [1]  
	 * [2]  1. "Schedule a meeting"
	 * [3]  2. "Book vacation dates"
	 * [4]  3. "Check room availability"
	 * [5]  4. "Check person availability"
	 * [6]  5. "Check agenda for a room"
	 * [7]  6. "Check agenda for a person"
	 * [8]  0. "Exit"
	 * [9]  
	 * [10] "Please enter the number that corresponds to the option that you want to proceed with."
	 * [11] "Please enter a number from 0 - 6"
	 * </pre>
	 */
	@Test
	@DisplayName("BB_menu_14 invalid menu input '7'")
	public void BB_menu_14() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the expected output to verify correct error handling
	    String expectedOutput = "Please enter a number from 0 - 6";

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	            // Simulate sequential user inputs to drive the menu logic
	            withTextFromSystemIn(
	                "7",    // Invalid menu input just above the valid range
	                "0"     // Exit the menu to end the test
	            ).execute(() -> {
	                planner.mainMenu();
	            });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");

	    // Assert that the expected error message appears in the correct sequence
	    assertEquals(expectedOutput, lines[11]);
	}

}
