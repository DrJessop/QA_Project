package backend;

public class ServiceInfo {

	private String serviceNumber;
	private String capacity;
	private String numTicketsSold;
	private String name;
	private String date;
	
	public ServiceInfo(String serviceNumber, String capacity, String numTicketsSold, String name, String date) throws InvalidLineException {
		setServiceNumber(serviceNumber);
		setCapacity(capacity);
		setNumTicketsSold(numTicketsSold);
		if (Integer.parseInt(numTicketsSold) > Integer.parseInt(capacity)) throw new InvalidLineException("Invalid service line");
		setName(name);
		setDate(date);
	}
	
	/*public ServiceInfo(String serviceNumber, String numTicketsSold, String serviceNumber, String name, String date) throws InvalidLineException {
		setServiceNumber(serviceNumber);
		setCapacity(capacity);
		setNumTicketsSold(numTicketsSold);
		if (Integer.parseInt(numTicketsSold) > Integer.parseInt(capacity)) throw new InvalidLineException("Invalid service line");
		setName(name);
		setDate(date);
	}*/
	
	public void setServiceNumber(String newServiceNumber) throws InvalidLineException{ 
		/*
		 * method setServiceNumber : String -> void
		 * Functionality: Checks that a given service number was correctly entered
		 * Parameters
		 * 	String newServiceNumber: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		if (CheckValidEntry.isValidServiceNumber(newServiceNumber))
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
		if (CheckValidEntry.isValidCapacity(newCapacity))
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
		if (CheckValidEntry.isValidNumTicketsSold(newNumTicketsSold))
			this.numTicketsSold = newNumTicketsSold; 
	}
	
	public void setName(String newName) throws InvalidLineException{ 
		/*
		 * method setName : String -> void
		 * Functionality: Checks that a given name was correctly entered
		 * Parameters
		 * 	String newName: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		if (CheckValidEntry.isValidName(newName))
			this.name = newName; 
	}
	
	private void setDate(String newDate) throws InvalidLineException {	
		/*
		 * method checkServiceDate : String -> void
		 * Functionality: Checks that a given date was correctly entered
		 * Parameters
		 * 	String input: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		if (CheckValidEntry.isValidDate(newDate))
			this.date = newDate;
	}
	
	public String getServiceNumber() { return this.serviceNumber; }
	
	public String getCapacity() { return this.capacity; }
	
	public String getNumTicketsSold() { return this.numTicketsSold; }
	
	public String getName() { return this.name; }
	
	public String getDate() { return this.date; }
	
}
