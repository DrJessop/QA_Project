package backend;

public class ServiceInfo {

	private String serviceNumber;
	private String capacity;
	private String numTicketsSold;
	private String name;
	private String date;
	
	public ServiceInfo(String serviceNumber, String capacity, String numTicketsSold, String name, String date) {
		this.serviceNumber = serviceNumber;
		this.capacity = capacity;
		this.numTicketsSold = numTicketsSold;
		this.name = name;
		this.date = date;
	}
	
	public void setServiceNumber(String newServiceNumber) throws InvalidLineException { 
		/*
		 * method setServiceNumber : String -> void
		 * Functionality: Checks that a given service number was correctly entered
		 * Parameters
		 * 	String newServiceNumber: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		try {
			Integer.parseInt(newServiceNumber);
			if ((newServiceNumber.length() != 5) || (newServiceNumber.charAt(0) == '0')) {
				throw new InvalidLineException("Invalid Input: The service number is not valid.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidLineException("The service number you have entered is not a number.");
		}
		this.serviceNumber = newServiceNumber; 
	}
	
	public void setCapacity(String newCapacity) throws InvalidLineException { 
		/*
		 * method setCapacity : String -> void
		 * Functionality: Checks that number of tickets sold and capacity was correctly entered
		 * Parameters
		 * 	String newCapacity: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(newCapacity);
			if (numOfTickets < 1 || numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("The tickets sold is not within 1 - 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("Tickets entered is not an integer.");
		}
		this.capacity = newCapacity; 
	}
	
	public void setNumTicketsSold(String newNumTicketsSold) throws InvalidLineException { 
		/*
		 * method setNumTicketsSold : String -> void
		 * Functionality: Checks that number of tickets sold and capacity was correctly entered
		 * Parameters
		 * 	String newNumTicketsSolds: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(newNumTicketsSold);
			if (numOfTickets < 1 || numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("The tickets sold is not within 1 - 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("Tickets entered is not an integer.");
		}
		this.numTicketsSold = newNumTicketsSold; 
	}
	
	public void setName(String newName) throws InvalidLineException { 
		/*
		 * method setName : String -> void
		 * Functionality: Checks that a given name was correctly entered
		 * Parameters
		 * 	String newName: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		if ((newName.length() < 3) || (newName.length() > 39) || 
				(newName.charAt(0) == ' ') || (newName.charAt(newName.length() - 1) == ' ')) {
					throw new InvalidLineException("The name is not valid");
		} 
		else {
				for (int i = 0; i < newName.length(); i++) {
					if((!Character.isLetterOrDigit(newName.charAt(i))) && (newName.charAt(i) != ' ')) { 
						throw new InvalidLineException("A non-alphanumeric char detected");
					}
				}
		}
		this.name = newName; 
	}
	
}
