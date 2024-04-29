/*modified code from University of South Carolina: package MeetingPlanner */
package au.edu.sccs.csp3105.NBookingPlanner;


@SuppressWarnings("serial")
public class ConflictsException extends Exception {
	public ConflictsException() {
		// TODO Auto-generated constructor stub
	}

	public ConflictsException(String message) {
		super(message);
		
	}

	public ConflictsException(Throwable cause) {
		super(cause);
	}

	public ConflictsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictsException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
}
