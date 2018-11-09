package backend;

/*
 * Class Name: CheckValidEntry
 * Functionality: The class contains multiple helper functions that provides checks for user inputs.
 * 	The checks are used by both the ServiceInfo class and the Transaction Summary File checks.
 * Extends: None
 * Parameters
 *  None
 */

public class CheckValidEntry {
	
	private CheckValidEntry() {}
	
	public static boolean isValidServiceNumber(String serviceNumber) throws InvalidLineException {
		/*
		 * method isValidServiceNumber : String -> boolean
		 * Functionality: Checks that a given service number was correctly entered
		 * Parameters
		 * 	String serviceNumber: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		 * Returns: true (If value is a valid entry)
		*/
		if (serviceNumber == null) throw new InvalidLineException("1. Null values are not allowed");
		try {
			Integer.parseInt(serviceNumber);
			if ((serviceNumber.length() != 5) || (serviceNumber.charAt(0) == '0')) {
				throw new InvalidLineException("2. Invalid Input: The service number is not valid.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidLineException("3. The service number you have entered is not a number.");
		}
		return true;
	}
	
	public static boolean isValidCapacity(String capacity) throws InvalidLineException {
		/*
		 * method isValidCapacity : String -> boolean
		 * Functionality: Checks that a given capacity number was correctly entered
		 * Parameters
		 * 	String capacity: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		 * Returns: true (If value is a valid entry)
		*/
		if (capacity == null) throw new InvalidLineException("4. Null values are not allowed");
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(capacity);
			if (numOfTickets < 1 || numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("5. The tickets sold is not within 1 - 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("6. Tickets entered is not an integer.");
		}
		return true;
	}
	
	public static boolean isValidNumTicketsSold(String numTicketsSold) throws InvalidLineException {
		/*
		 * method isValidNumTicketsSold : String -> boolean
		 * Functionality: Checks that a given service number was correctly entered
		 * Parameters
		 * 	String newServiceNumber: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		 * Returns: true (If value is a valid entry)
		*/
		if (numTicketsSold == null) throw new InvalidLineException("7. Null values are not allowed");
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(numTicketsSold);
			if (numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("8. The number of tickets sold is greater than 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("9. Tickets entered is not an integer.");
		}
		return true;
	}
	
	public static boolean isValidName(String name) throws InvalidLineException {
		/*
		 * method isValidName : String -> boolean
		 * Functionality: Checks that a given name was correctly entered
		 * Parameters
		 * 	String name: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		 * Returns: true (If value is a valid entry)
		*/
		if (name == null) throw new InvalidLineException("10. Null values are not allowed");
		if ((name.length() < 3) || (name.length() > 39) || 
				(name.charAt(0) == ' ') || (name.charAt(name.length() - 1) == ' ')) {
					throw new InvalidLineException("11. The name is not valid");
		} 
		else {
				for (int i = 0; i < name.length(); i++) {
					if((!Character.isLetterOrDigit(name.charAt(i))) && (name.charAt(i) != ' ')) { 
						throw new InvalidLineException("12. A non-alphanumeric char detected");
					}
				}
		}
		return true;
	}
	
	public static boolean isValidDate(String date) throws InvalidLineException {
		/*
		 * method isValidDate : String -> boolean
		 * Functionality: Checks that a given date was correctly entered
		 * Parameters
		 * 	String date: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		 * Returns: true (If value is a valid entry)
		*/
		if (date == null) throw new InvalidLineException("13. Null values are not allowed");
		try {
			Integer.parseInt(date);
		} catch (NumberFormatException e) {
			throw new InvalidLineException("14. The date you entered contains non-numeric characters.");
		}
		if (date.length() != 8) {
			throw new InvalidLineException("15. The date you entered is not of length 8.");
		} else if ((Integer.parseInt(date.substring(0,4)) < 1980) || (Integer.parseInt(date.substring(0,4)) > 2999)) {
			throw new InvalidLineException("16. The year is invalid.");
		} else if ((Integer.parseInt(date.substring(4,6)) < 1) || (Integer.parseInt(date.substring(4,6)) > 12)) {
			throw new InvalidLineException("17. The month is invalid.");
		} else if ((Integer.parseInt(date.substring(6,8)) < 1) || (Integer.parseInt(date.substring(6,8)) > 31)) {
			throw new InvalidLineException("18. The day is invalid.");
		}
		return true;
	}
}
