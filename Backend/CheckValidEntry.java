package backend;

public class CheckValidEntry {
	
	private CheckValidEntry() {}
	
	public static boolean isValidServiceNumber(String serviceNumber) throws InvalidLineException {
		if (serviceNumber == null) throw new InvalidLineException("Null values are not allowed");
		try {
			Integer.parseInt(serviceNumber);
			if ((serviceNumber.length() != 5) || (serviceNumber.charAt(0) == '0')) {
				throw new InvalidLineException("Invalid Input: The service number is not valid.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidLineException("The service number you have entered is not a number.");
		}
		return true;
	}
	
	public static boolean isValidCapacity(String capacity) throws InvalidLineException {
		if (capacity == null) throw new InvalidLineException("Null values are not allowed");
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(capacity);
			if (numOfTickets < 1 || numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("The tickets sold is not within 1 - 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("Tickets entered is not an integer.");
		}
		return true;
	}
	
	public static boolean isValidNumTicketsSold(String numTicketsSold) throws InvalidLineException {
		if (numTicketsSold == null) throw new InvalidLineException("Null values are not allowed");
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(numTicketsSold);
			if (numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("The number of tickets sold is greater than 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("Tickets entered is not an integer.");
		}
		return true;
	}
	
	public static boolean isValidName(String name) throws InvalidLineException {
		if (name == null) throw new InvalidLineException("Null values are not allowed");
		if ((name.length() < 3) || (name.length() > 39) || 
				(name.charAt(0) == ' ') || (name.charAt(name.length() - 1) == ' ')) {
					throw new InvalidLineException("The name is not valid");
		} 
		else {
				for (int i = 0; i < name.length(); i++) {
					if((!Character.isLetterOrDigit(name.charAt(i))) && (name.charAt(i) != ' ')) { 
						throw new InvalidLineException("A non-alphanumeric char detected");
					}
				}
		}
		return true;
	}
	
	public static boolean isValidDate(String date) throws InvalidLineException {
		if (date == null) throw new InvalidLineException("Null values are not allowed");
		try {
			Integer.parseInt(date);
		} catch (NumberFormatException e) {
			throw new InvalidLineException("The date you entered contains non-numeric characters.");
		}
		if (date.length() != 8) {
			throw new InvalidLineException("The date you entered is not of length 8.");
		} else if ((Integer.parseInt(date.substring(0,4)) < 1980) || (Integer.parseInt(date.substring(0,4)) > 2999)) {
			throw new InvalidLineException("The year is invalid.");
		} else if ((Integer.parseInt(date.substring(4,6)) < 1) || (Integer.parseInt(date.substring(4,6)) > 12)) {
			throw new InvalidLineException("The month is invalid.");
		} else if ((Integer.parseInt(date.substring(6,8)) < 1) || (Integer.parseInt(date.substring(6,8)) > 31)) {
			throw new InvalidLineException("The day is invalid.");
		}
		return true;
	}
}
