package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import au.edu.sccs.csp3105.NBookingPlanner.ConflictsException;

class UnitTestsConflictsException {
	
	// ------------------------------------------------------------------------------------------- \\
	// ---------------------------------- ConflictsException() ----------------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
    /**
     * Tests the default constructor of ConflictsException to ensure that it initializes
     * an exception object with no message and no cause.
     */
    @Test 
    public void conflictsException01() {
        ConflictsException exception = new ConflictsException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
    
    
	// ------------------------------------------------------------------------------------------- \\
	// --------------------------- ConflictsException(String message) ---------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
    /**
     * Tests the constructor that accepts a message. This test ensures that the exception
     * is initialized with the specified message and no cause.
     */
    @Test
    public void conflictsExceptionMessage01() {
        String message = "Test Message";
        ConflictsException exception = new ConflictsException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }
    
    
	// ------------------------------------------------------------------------------------------- \\
	// --------------------------- ConflictsException(Throwable cause) --------------------------- \\
	// ------------------------------------------------------------------------------------------- \\
    /**
     * Tests the constructor that accepts a cause. This test ensures that the exception
     * is initialized with the specified cause and the message is derived from the cause.
     */
    @Test
    public void conflictsExceptionCause01() {
        Throwable cause = new RuntimeException("Test Cause");
        ConflictsException exception = new ConflictsException(cause);
        assertEquals(cause.toString(), exception.getMessage());
        assertSame(cause, exception.getCause());
    }
    
    
	// ------------------------------------------------------------------------------------------- \\
	// ----------------- ConflictsException(String message, Throwable Cause) --------------------- \\
	// ------------------------------------------------------------------------------------------- \\
    /**
     * Tests the constructor that accepts both a message and a cause. This test checks that
     * the exception is initialized with the specified message and cause.
     */
    @Test
    public void conflictsExceptionMessageCause01() {
        String message = "Test Message";
        Throwable cause = new RuntimeException("Test Cause");
        ConflictsException exception = new ConflictsException(message, cause);
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }
    
    
	// ------------------------------------------------------------------------------------------- \\
	// ----- ConflictsException(String message, Throwable cause,boolean enableSuppression, ------- \\
    //			boolean writableStackTrace) 
	// ------------------------------------------------------------------------------------------- \\
    /**
     * Tests the constructor that accepts a message, a cause, and true booleans for suppression 
     * and writable stack trace. This test checks that the exception is properly initialized with 
     * all provided options and contains the appropriate message and cause.
     */
    @Test
    public void conflictsExceptionAllArguments01() {
        String message = "Test Error";
        Throwable cause = new RuntimeException("Test Cause");
        boolean enableSuppression = true;
        boolean writableStackTrace = false;
        ConflictsException exception = new ConflictsException(message, cause, enableSuppression, writableStackTrace);
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
