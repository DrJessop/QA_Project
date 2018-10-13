package frontend;

import java.util.HashMap;

public abstract class User {
	
	private HashMap<String, ServiceDetails> map;
	
	public User(String fileName) {
		this.map = new HashMap<>();
		/*
		 * Code here should parse the transaction summary file to come up easily accessible TransactionSummary objects,
		 * should set the flag inTransactionSummaryFile to true since these transactions belong to older sessions
		 */
		
	}
	
	protected ServiceDetails getService(String serviceNumber) throws TransactionException {
		if (!this.map.containsKey(serviceNumber)) throw new TransactionException("This service does not exist\n");
		return this.map.get(serviceNumber);
	}
	
	protected void sellTickets(String serviceNumber, String numTicketsToAdd) {
		
		/*
		 * Function Flow: function sellTickets : String -> String -> null
		 * Function Name: sellTickets
		 * Functionality: Allow the user to sell tickets from a specified service.
		 * Parameters: serviceNumber (The identifier of a service),
		 * numTickets (The number of tickets that are required to be sold)
		 * Throws: ---------------------
		 * Returns: --------------------
		*/
		
		try {
			int ticketNumber = 0;
			try {
				ticketNumber = Integer.parseInt(numTicketsToAdd);
			} catch (NumberFormatException e) {
				throw new TransactionException("Invalid Input: The number of tickets you have entered is not a string.");
			}
			if (ticketNumber < 0) {
				throw new TransactionException("Invalid Input: The ticket quantity you have entered is less than 0.");
			} 
			ServiceDetails sd = this.getService(serviceNumber);
			sd.addTickets(numTicketsToAdd);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void cancelTickets(String serviceNumber, String numTickets) throws TransactionException;
	public abstract void changeTickets(String serviceNumber1, String serviceNumber2, String numTickets) throws TransactionException;
	public abstract void createService(String serviceNumber, String date, String serviceName) throws TransactionException;
	public abstract void deleteService(String serviceNumber, String serviceName) throws TransactionException;
	////////////////////////////Where do I put this!!!!!!!!!
	private int getTicketNumber(String userInput) throws TransactionException{
		int ticketNumber = 0;
		try {
			ticketNumber = Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			throw new TransactionException("Invalid Input: The number of tickets you have entered is not a string.");
		}
		if (ticketNumber < 0) {
			throw new TransactionException("Invalid Input: The ticket quantity you have entered is less than 0.");
		} else if (ticketNumber > 1000) {
			throw new TransactionException("Invalid Input: The ticket quantity you have entered is greater than 1000.");
		}
		return ticketNumber;
	}

	public void cancelTickets() {
		// TODO Auto-generated method stub
		
	}

}
