/*modified code from University of South Carolina: package MeetingPlanner */
package au.edu.sccs.csp3105.NBookingPlanner;

public class Room {
	private String ID;
	private Calendar calendar;
	
	/**
	 * Default constructor
	 */
	public Room(){
		ID="";
		calendar=  new Calendar();
	}
	
	/**
	 * Constructor, initializes calendar and sets name.
	 */
	public Room(String ID){
		this.ID=ID;
		calendar=  new Calendar();
	}

	public String getID() {
		return ID;
	}
	
	/**
	 * Add a meeting to a calendar.
	 * @see Calendar#addMeeting(Meeting)
	 */
	public void addMeeting(Meeting meeting) throws ConflictsException{
		try{
			calendar.addMeeting(meeting);
		}catch(ConflictsException e){
			throw new ConflictsException("Conflict for room "+ID+":\n"+e.getMessage());
		}
	}
	
	/**
	 * Prints the agenda for a month.
	 * @see Calendar#printAgenda(int)
	 */
	public String printAgenda(int month){
		return calendar.printAgenda(month);
	}
	
	/**
	 * Prints the agenda for a given month and day.
	 * @see Calendar#printAgenda(int, int)
	 */
	public String printAgenda(int month, int day){
		
		    //System.out.println("I am here"); 
		  
		return calendar.printAgenda(month, day);
	}
	
	/**
	 * Checks whether a meeting is scheduled during a timeframe.
	 * @see Calendar#isBusy(int, int, int, int)
	 */
	public boolean isBusy(int month, int day, int start, int end) throws ConflictsException{
		return calendar.isBusy(month,day,start,end);
	}
	
	/**
	 * Gets a particular meeting.
	 * @see Calendar#getMeeting(int, int, int)
	 */
	public Meeting getMeeting(int month, int day, int index){
		return calendar.getMeeting(month, day, index);
	}
	
	/**
	 * Removes a particular meeting.
	 * @see Calendar#removeMeeting(int, int, int)
	 */
    public void removeMeeting(int month, int day, int index){
    	calendar.removeMeeting(month,day,index);
	}
}
