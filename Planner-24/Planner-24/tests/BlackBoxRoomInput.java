package tests;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import com.github.stefanbirkner.systemlambda.SystemLambda;

import au.edu.sccs.csp3105.NBookingPlanner.Planner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class BlackBoxRoomInput {

	// ------------------------------------------------------------------------------------------- \\
	// ------------------------------------ PREPARE FOR TESTS ------------------------------------ \\
	// ------------------------------------------------------------------------------------------- \\
	
	// Any required beforeEach/afterEach etc. methods to be added here.
	
	
	// ------------------------------------------------------------------------------------------- \\
	// --------------------------------------- BEGIN TESTS --------------------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
	
	/**
	 * Conducts a black box test for the room input functionality within the meeting scheduling 
	 * process. This test verifies the system's response when a specific room is selected, ensuring 
	 * that the output correctly lists available attendees and prompts for additional input.
	 * <p>
	 * The test employs a spy of the Planner class to monitor interactions without altering the 
	 * underlying behavior. User input is simulated using SystemLambda to provide a controlled test 
	 * environment where input sequences can be predefined and the corresponding outputs captured. 
	 * The primary objective is to validate that selecting a room correctly triggers the expected 
	 * sequence of outputs, including displaying available people and prompting for further actions.
	 * <p>
	 * Output is systematically captured and split into lines to allow detailed verification against 
	 * expected results. This allows each output line to be examined for correctness, affirming the 
	 * integrity of the room selection process. The array indices below map to specific lines of the 
	 * expected output array, this exists purely for developer reference:
	 * <pre>
	 * [0] Welcome to the Meeting Scheduling Interface.
	 * [1] 
	 * [2] 1. Schedule a meeting
	 * [3] 2. Book vacation dates
	 * [4] 3. Check room availability
	 * [5] 4. Check person availability
	 * [6] 5. Check agenda for a room
	 * [7] 6. Check agenda for a person
	 * [8] 0. Exit
	 * [9] 
	 * [10] Please enter the number that corresponds to the option that you want to proceed with.
	 * [11] 
	 * [12] Enter the month of the meeting (1-12): 
	 * [13] 
	 * [14] Enter the day of the meeting (1-31): 
	 * [15] 
	 * [16] Enter the starting hour of the meeting (0-23): 
	 * [17] 
	 * [18] Enter the ending hour of the meeting (0-23): 
	 * [19] The rooms open at that time are:
	 * [20] JO18.330
	 * [21] JO7.221
	 * [22] JO15.236
	 * [23] JO1.230
	 * [24] JO34.536
	 * [25] JO19.230
	 * [26] ML5.123
	 * [27] ML18.330
	 * [28] ML21.520
	 * [29] ML13.213
	 * [30] ML21.310
	 * [31] ML13.218
	 * [32] 
	 * [33] Enter the desired room ID, or cancel to cancel: 
	 * [34] The people available to attend at that time are:
	 * [35] Justin Gardener
	 * [36] Ashley Matthews
	 * [37] Mary Jane Cook
	 * [38] Rose Austin
	 * [39] Mike Smith
	 * [40] Helen West
	 * [41] Steven Lewis
	 * [42] Edith Cowan
	 * [43] Mark Colin
	 * [44] Jacquie Martin
	 * [45] Jaci Johnston
	 * [46] Travis Colin
	 * [47] Ashley Martin
	 * [48] 
	 * [49] Enter a person's name, or done if finished: 
	 * [50] 
	 * [51] Enter a person's name, or done if finished: 
	 * [52] 
	 * [53] Enter a description for the meeting: 
	 * [54] 
	 * [55] 1. Schedule a meeting
	 * [56] 2. Book vacation dates
	 * [57] 3. Check room availability
	 * [58] 4. Check person availability
	 * [59] 5. Check agenda for a room
	 * [60] 6. Check agenda for a person
	 * [61] 0. Exit
	 * [62] 
	 * [63] Please enter the number that corresponds to the option that you want to proceed with.
	 * </pre>
	 */
	@Test
	@DisplayName("BB_room_0 valid room input 'JO34.536'")
	public void BB_room_0() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);

	    // Define the specific slice of the expected output array that we want to compare to the output
	    String[] expectedOutputSlice = new String[] {
	        "The people available to attend at that time are:",
	        "Justin Gardener",
	        "Ashley Matthews",
	        "Mary Jane Cook",
	        "Rose Austin",
	        "Mike Smith",
	        "Helen West",
	        "Steven Lewis",
	        "Edith Cowan",
	        "Mark Colin",
	        "Jacquie Martin",
	        "Jaci Johnston",
	        "Travis Colin",
	        "Ashley Martin",
	        "",
	        "Enter a person's name, or done if finished: "
	    };

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	    	SystemLambda.catchSystemExit(() -> {
	    		// Simulate sequential user inputs to drive the menu logic
		        withTextFromSystemIn(
		        	"1",			// Menu --> schedule meeting
		            "2",            // Month
		            "3",            // Day
		            "4",            // Start time
		            "5",            // End Time
		            "JO34.536",     // Room
		            "Travis Colin", // Name
		            "done",         // No more names
		            "desc",         // Description
		            "0"				// Menu --> exit
		        ).execute(() -> planner.mainMenu());
	    	});
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");
	    
	    // Extract the specific part of the actual output using array slicing
	    String[] actualOutputSlice = Arrays.copyOfRange(lines, 34, 50);

	    // Assert that the expected prompt appears in the correct sequence
	    assertArrayEquals(expectedOutputSlice, actualOutputSlice);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------
	
	/**
	 * Tests the 'cancel' input during room selection to ensure the system correctly handles this EP.
	 * This black box test simulates the user entering 'cancel' when prompted
	 * <p>
	 * A spy of the Planner class is employed to monitor interactions without affecting the overall 
	 * behavior. The main menu method is overridden to ensure it does not interfere with the testing 
	 * process, focusing solely on the response to the cancellation command.
	 * <p>
	 * The output is captured and analyzed to confirm that upon entering 'cancel', the system outputs 
	 * the correct prompt without proceeding to any subsequent steps. The test concludes by verifying 
	 * the last line of the output is the prompt for a room ID or cancellation, as the cancellation 
	 * should prevent further actions.
	 * <pre>
	 * [0] Welcome to the Meeting Scheduling Interface.
	 * [1] 
	 * [2] 
	 * [3] 1. Schedule a meeting
	 * [4] 2. Book vacation dates
	 * [5] 3. Check room availability
	 * [6] 4. Check person availability
	 * [7] 5. Check agenda for a room
	 * [8] 6. Check agenda for a person
	 * [9] 0. Exit
	 * [10] 
	 * [11] Please enter the number that corresponds to the option that you want to proceed with.
	 * [12] 
	 * [13] Enter the month (1-12): 
	 * [14] 
	 * [15] Enter the day (1-31), or all to see the whole month: 
	 * [16] Which of the following rooms are you interested in?
	 * [17] JO18.330
	 * [18] JO7.221
	 * [19] JO15.236
	 * [20] JO1.230
	 * [21] JO34.536
	 * [22] JO19.230
	 * [23] ML5.123
	 * [24] ML18.330
	 * [25] ML21.520
	 * [26] ML13.213
	 * [27] ML21.310
	 * [28] ML13.218
	 * [29] Enter a room ID as shown in the list, or cancel to cancel the request: 
	 * [30] 
	 * [31] 
	 * [32] 1. Schedule a meeting
	 * [33] 2. Book vacation dates
	 * [34] 3. Check room availability
	 * [35] 4. Check person availability
	 * [36] 5. Check agenda for a room
	 * [37] 6. Check agenda for a person
	 * [38] 0. Exit
	 * [39] 
	 * [40] Please enter the number that corresponds to the option that you want to proceed with.
	 * <pre>
	 */
	@Test
	@DisplayName("BB_room_1 valid room input 'cancel'")
	public void BB_room_1() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);
	    
	    // Define the specific slice of the expected output array that we want to compare to the output
	    String[] expectedOutputSlice = new String[] {
	    	"",
	    	"",
			"1. Schedule a meeting",
			"2. Book vacation dates",
			"3. Check room availability",
			"4. Check person availability",
			"5. Check agenda for a room",
			"6. Check agenda for a person",
			"0. Exit",
			"",
			"Please enter the number that corresponds to the option that you want to proceed with."
	    };

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	    	SystemLambda.catchSystemExit(() -> {
		        // Simulate sequential user inputs to drive the menu logic
		        withTextFromSystemIn(
		        	"5",			// Menu --> check room agenda	
		            "5",            // Month
		            "6",            // Day
		            "cancel",       // Room --> cancel
		            "0"				// Menu --> exit
		        ).execute(() -> {
		            planner.mainMenu();
		        });
	    	});
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");
	    
	    // Extract the specific part of the actual output using array slicing
	    String[] actualOutputSlice = Arrays.copyOfRange(lines, 30, 41);

	    // Since 'cancel' should return us to the main menu, but Mockito is
	    // overriding that method - the final line of the output stream
	    // should be the prompt for a room code (as the test should essentially
	    // end immediately after)
	    assertArrayEquals(expectedOutputSlice, actualOutputSlice);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------

	/**
	 * Tests the behavior of the system when an invalid room ID ('cat') is entered during the meeting 
	 * scheduling process. This black box test verifies that the system correctly responds with an 
	 * error message and re-prompts the user to enter a valid room ID or cancel.
	 * <p>
	 * The test employs a spy of the Planner class to monitor interactions without modifying the actual 
	 * class behavior. User input is simulated via SystemLambda to provide a controlled test environment.
	 * <p>
	 * Output is systematically captured and split into lines to allow detailed verification against 
	 * expected results. This allows each output line to be examined for correctness, affirming the 
	 * integrity of the room selection process. The array indices below map to specific lines of the 
	 * expected output array, this exists purely for developer reference:
	 * <pre>
	 * [0] 
	 * [1] 
	 * [2] 1. Schedule a meeting
	 * [3] 2. Book vacation dates
	 * [4] 3. Check room availability
	 * [5] 4. Check person availability
	 * [6] 5. Check agenda for a room
	 * [7] 6. Check agenda for a person
	 * [8] 0. Exit
	 * [9] 
	 * [10] Please enter the number that corresponds to the option that you want to proceed with.
	 * [11] 
	 * [12] Enter the month of the meeting (1-12): 
	 * [13] 
	 * [14] Enter the day of the meeting (1-31): 
	 * [15] 
	 * [16] Enter the starting hour of the meeting (0-23): 
	 * [17] 
	 * [18] Enter the ending hour of the meeting (0-23): 
	 * [19] The rooms open at that time are:
	 * [20] JO18.330
	 * [21] JO7.221
	 * [22] JO15.236
	 * [23] JO1.230
	 * [24] JO34.536
	 * [25] JO19.230
	 * [26] ML5.123
	 * [27] ML18.330
	 * [28] ML21.520
	 * [29] ML13.213
	 * [30] ML21.310
	 * [31] ML13.218
	 * [32] 
	 * [33] Enter the desired room ID, or cancel to cancel: 
	 * [34] Requested room does not exist
	 * [35] 
	 * [36] Enter the desired room ID, or cancel to cancel: 
	 * [37] 
	 * [38] 
	 * [39] 1. Schedule a meeting
	 * [40] 2. Book vacation dates
	 * [41] 3. Check room availability
	 * [42] 4. Check person availability
	 * [43] 5. Check agenda for a room
	 * [44] 6. Check agenda for a person
	 * [45] 0. Exit
	 * [46] 
	 * [47] Please enter the number that corresponds to the option that you want to proceed with.
	 * </pre>
	 */
	@Test
	@DisplayName("BB_room_2 invalid room input 'cat'")
	public void BB_room_2() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);
	    
	    // Define the specific slice of the expected output array that we want to compare to the output
	    String[] expectedOutputSlice = new String[] {
	        "Requested room does not exist",
	        "",
	        "Enter the desired room ID, or cancel to cancel: "
	    };

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	        // Catch the system exit to prevent the test from terminating prematurely
	    	SystemLambda.catchSystemExit(() -> {
		        // Simulate sequential user inputs to the schedule meeting method
		        withTextFromSystemIn(
		        	"1",		// Menu --> schedule meeting
		            "2", 		// Month
		            "3",		// Day
		            "4",		// Start time
		            "5",		// End time
		            "cat",		// Room
		            "cancel",	// Room --> cancel
		            "0"			// Menu --> exit
		        ).execute(() -> {
		            planner.mainMenu();
		        });
	    	});
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");
	    
	    // Extract the specific part of the actual output using array slicing
	    String[] actualOutputSlice = Arrays.copyOfRange(lines, 34, 37);

	    // Assert that the expected prompt appears in the correct sequence
	    assertArrayEquals(expectedOutputSlice, actualOutputSlice);
	}

	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------

	/**
	 * Tests the behavior of the system when an invalid room ID ('ML5.12' - missing final character) 
	 * is entered during the meeting scheduling process. This black box test verifies that the system 
	 * correctly responds with an error message and re-prompts the user to enter a valid room ID or 
	 * cancel.
	 * <p>
	 * The test employs a spy of the Planner class to monitor interactions without modifying the actual 
	 * class behavior. User input is simulated via SystemLambda to provide a controlled test environment.
	 * <p>
	 * Output is systematically captured and split into lines to allow detailed verification against 
	 * expected results. This allows each output line to be examined for correctness, affirming the 
	 * integrity of the room selection process. The array indices below map to specific lines of the 
	 * expected output array, this exists purely for developer reference:
	 * <pre>
	 * [0] 
	 * [1] 
	 * [2] 1. Schedule a meeting
	 * [3] 2. Book vacation dates
	 * [4] 3. Check room availability
	 * [5] 4. Check person availability
	 * [6] 5. Check agenda for a room
	 * [7] 6. Check agenda for a person
	 * [8] 0. Exit
	 * [9] 
	 * [10] Please enter the number that corresponds to the option that you want to proceed with.
	 * [11] 
	 * [12] Enter the month of the meeting (1-12): 
	 * [13] 
	 * [14] Enter the day of the meeting (1-31): 
	 * [15] 
	 * [16] Enter the starting hour of the meeting (0-23): 
	 * [17] 
	 * [18] Enter the ending hour of the meeting (0-23): 
	 * [19] The rooms open at that time are:
	 * [20] JO18.330
	 * [21] JO7.221
	 * [22] JO15.236
	 * [23] JO1.230
	 * [24] JO34.536
	 * [25] JO19.230
	 * [26] ML5.123
	 * [27] ML18.330
	 * [28] ML21.520
	 * [29] ML13.213
	 * [30] ML21.310
	 * [31] ML13.218
	 * [32] 
	 * [33] Enter the desired room ID, or cancel to cancel: 
	 * [34] Requested room does not exist
	 * [35] 
	 * [36] Enter the desired room ID, or cancel to cancel: 
	 * [37] 
	 * [38] 
	 * [39] 1. Schedule a meeting
	 * [40] 2. Book vacation dates
	 * [41] 3. Check room availability
	 * [42] 4. Check person availability
	 * [43] 5. Check agenda for a room
	 * [44] 6. Check agenda for a person
	 * [45] 0. Exit
	 * [46] 
	 * [47] Please enter the number that corresponds to the option that you want to proceed with.
	 * </pre>
	 */
	@Test
	@DisplayName("BB_room_3 invalid room input 'ML5.12'")
	public void BB_room_3() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);
	    
	    // Define the specific slice of the expected output array that we want to compare to the output
	    String[] expectedOutputSlice = new String[] {
	        "Requested room does not exist",
	        "",
	        "Enter the desired room ID, or cancel to cancel: "
	    };

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	    	 // Catch the system exit to prevent the test from terminating prematurely
	    	SystemLambda.catchSystemExit(() -> {
		        // Simulate sequential user inputs to the schedule meeting method
		        withTextFromSystemIn(
		        	"1", 		// Menu --> schedule meeting
		            "2", 		// Month
		            "3",		// Day
		            "4",		// Start time
		            "5",		// End time
		            "ML5.12",	// Room
		            "cancel",	// Room --> cancel
		            "0"			// Menu --> exit	
		        ).execute(() -> {
		            planner.mainMenu();
		        });
	    	});
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");
	    
	    // Extract the specific part of the actual output using array slicing
	    String[] actualOutputSlice = Arrays.copyOfRange(lines, 34, 37);

	    // Assert that the expected prompt appears in the correct sequence
	    assertArrayEquals(expectedOutputSlice, actualOutputSlice);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------

	/**
	 * Tests the behavior of the system when an invalid room ID ('ml5.12' - lower case 'M' and 'L') 
	 * is entered during the meeting scheduling process. This black box test verifies that the system 
	 * correctly responds with an error message and re-prompts the user to enter a valid room ID or 
	 * cancel.
	 * <p>
	 * The test employs a spy of the Planner class to monitor interactions without modifying the actual 
	 * class behavior. User input is simulated via SystemLambda to provide a controlled test environment.
	 * <p>
	 * Output is systematically captured and split into lines to allow detailed verification against 
	 * expected results. This allows each output line to be examined for correctness, affirming the 
	 * integrity of the room selection process. The array indices below map to specific lines of the 
	 * expected output array, this exists purely for developer reference:
	 * <pre>
	 * [0] 
	 * [1] 
	 * [2] 1. Schedule a meeting
	 * [3] 2. Book vacation dates
	 * [4] 3. Check room availability
	 * [5] 4. Check person availability
	 * [6] 5. Check agenda for a room
	 * [7] 6. Check agenda for a person
	 * [8] 0. Exit
	 * [9] 
	 * [10] Please enter the number that corresponds to the option that you want to proceed with.
	 * [11] 
	 * [12] Enter the month of the meeting (1-12): 
	 * [13] 
	 * [14] Enter the day of the meeting (1-31): 
	 * [15] 
	 * [16] Enter the starting hour of the meeting (0-23): 
	 * [17] 
	 * [18] Enter the ending hour of the meeting (0-23): 
	 * [19] The rooms open at that time are:
	 * [20] JO18.330
	 * [21] JO7.221
	 * [22] JO15.236
	 * [23] JO1.230
	 * [24] JO34.536
	 * [25] JO19.230
	 * [26] ML5.123
	 * [27] ML18.330
	 * [28] ML21.520
	 * [29] ML13.213
	 * [30] ML21.310
	 * [31] ML13.218
	 * [32] 
	 * [33] Enter the desired room ID, or cancel to cancel: 
	 * [34] Requested room does not exist
	 * [35] 
	 * [36] Enter the desired room ID, or cancel to cancel: 
	 * [37] 
	 * [38] 
	 * [39] 1. Schedule a meeting
	 * [40] 2. Book vacation dates
	 * [41] 3. Check room availability
	 * [42] 4. Check person availability
	 * [43] 5. Check agenda for a room
	 * [44] 6. Check agenda for a person
	 * [45] 0. Exit
	 * [46] 
	 * [47] Please enter the number that corresponds to the option that you want to proceed with.
	 * </pre>
	 */
	@Test
	@DisplayName("BB_room_4 invalid room input 'ml5.12'")
	public void BB_room_4() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);
	    
	    // Define the specific slice of the expected output array that we want to compare to the output
	    String[] expectedOutputSlice = new String[] {
	        "Requested room does not exist",
	        "",
	        "Enter the desired room ID, or cancel to cancel: "
	    };

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	    	 // Catch the system exit to prevent the test from terminating prematurely
	    	SystemLambda.catchSystemExit(() -> {
		        // Simulate sequential user inputs to the schedule meeting method
		        withTextFromSystemIn(
		        	"1", 		// Menu --> schedule meeting
		            "9", 		// Month
		            "13",		// Day
		            "10",		// Start time
		            "15",		// End time
		            "ml5.12",	// Room
		            "cancel",	// Room --> cancel
		            "0"			// Menu --> exit	
		        ).execute(() -> {
		            planner.mainMenu();
		        });
	    	});
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");
	    
	    // Extract the specific part of the actual output using array slicing
	    String[] actualOutputSlice = Arrays.copyOfRange(lines, 34, 37);

	    // Assert that the expected prompt appears in the correct sequence
	    assertArrayEquals(expectedOutputSlice, actualOutputSlice);
	}
	
	// ----------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------

	/**
	 * Tests the behavior of the system when an invalid room ID ('JO7.221 ' - trailing whitespace) 
	 * is entered during the meeting scheduling process. This black box test verifies that the system 
	 * correctly responds with an error message and re-prompts the user to enter a valid room ID or 
	 * cancel.
	 * <p>
	 * The test employs a spy of the Planner class to monitor interactions without modifying the actual 
	 * class behavior. User input is simulated via SystemLambda to provide a controlled test environment.
	 * <p>
	 * Output is systematically captured and split into lines to allow detailed verification against 
	 * expected results. This allows each output line to be examined for correctness, affirming the 
	 * integrity of the room selection process. The array indices below map to specific lines of the 
	 * expected output array, this exists purely for developer reference:
	 * <pre>
	 * [0] 
	 * [1] 
	 * [2] 1. Schedule a meeting
	 * [3] 2. Book vacation dates
	 * [4] 3. Check room availability
	 * [5] 4. Check person availability
	 * [6] 5. Check agenda for a room
	 * [7] 6. Check agenda for a person
	 * [8] 0. Exit
	 * [9] 
	 * [10] Please enter the number that corresponds to the option that you want to proceed with.
	 * [11] 
	 * [12] Enter the month of the meeting (1-12): 
	 * [13] 
	 * [14] Enter the day of the meeting (1-31): 
	 * [15] 
	 * [16] Enter the starting hour of the meeting (0-23): 
	 * [17] 
	 * [18] Enter the ending hour of the meeting (0-23): 
	 * [19] The rooms open at that time are:
	 * [20] JO18.330
	 * [21] JO7.221
	 * [22] JO15.236
	 * [23] JO1.230
	 * [24] JO34.536
	 * [25] JO19.230
	 * [26] ML5.123
	 * [27] ML18.330
	 * [28] ML21.520
	 * [29] ML13.213
	 * [30] ML21.310
	 * [31] ML13.218
	 * [32] 
	 * [33] Enter the desired room ID, or cancel to cancel: 
	 * [34] Requested room does not exist
	 * [35] 
	 * [36] Enter the desired room ID, or cancel to cancel: 
	 * [37] 
	 * [38] 
	 * [39] 1. Schedule a meeting
	 * [40] 2. Book vacation dates
	 * [41] 3. Check room availability
	 * [42] 4. Check person availability
	 * [43] 5. Check agenda for a room
	 * [44] 6. Check agenda for a person
	 * [45] 0. Exit
	 * [46] 
	 * [47] Please enter the number that corresponds to the option that you want to proceed with.
	 * </pre>
	 */
	@Test
	@DisplayName("BB_room_5 invalid room input 'JO7.221 '")
	public void BB_room_5() throws Exception {
	    // Create a spy of the Planner class to monitor interactions
	    Planner planner = Mockito.spy(Planner.class);
	    
	    // Define the specific slice of the expected output array that we want to compare to the output
	    String[] expectedOutputSlice = new String[] {
	        "Requested room does not exist",
	        "",
	        "Enter the desired room ID, or cancel to cancel: "
	    };

	    // Capture the console output during the test
	    String output = SystemLambda.tapSystemOutNormalized(() -> {
	    	// Catch the system exit to prevent the test from terminating prematurely
	        SystemLambda.catchSystemExit(() -> {
	        	// Simulate sequential user inputs to the schedule meeting method
		        withTextFromSystemIn(
		        	"1",		// Menu	--> schedule meeting
		            "2", 		// Month
		            "3",		// Day
		            "4",		// Start time
		            "5",		// End time
		            "JO7.221 ",	// Room
		            "cancel",	// Room --> cancel
		            "0"			// Menu --> exit
		        ).execute(() -> {
		            planner.mainMenu();
		        });
	        });
	    });

	    // Split the captured output into lines for detailed inspection
	    String[] lines = output.split("\n");
	    
	    // Extract the specific part of the actual output using array slicing
	    String[] actualOutputSlice = Arrays.copyOfRange(lines, 34, 37);

	    // Assert that the expected prompt appears in the correct sequence
	    assertArrayEquals(expectedOutputSlice, actualOutputSlice);
	}
}
