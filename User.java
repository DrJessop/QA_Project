package frontend;

import java.util.HashMap;
import java.util.Scanner; 

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
		/*
		 * Function Flow: function getService : String -> this.map.get(serviceNumber)
		 * Function Name: getService
		 * Functionality: Retrieve the Service information.
		 * Parameters: serviceNumber (The identifier of a service)
		 * Throws: TransactionException (Raised when service number is not found)
		 * Returns: this.map.get(serviceNumber) (Hashmap element)
		*/
		if (!this.map.containsKey(serviceNumber)) throw new TransactionException("This service does not exist\n");
		return this.map.get(serviceNumber);
	}
	
	protected String getUserInput(String message) {
		/*
		 * Function Flow: function getUserInput : String -> scan.next()
		 * Function Name: getUserInput
		 * Functionality: Get the user Input.
		 * Parameters: message (Display what the user is to see)
		 * Throws: -----
		 * Returns: scan.next()
		*/
		Scanner scan = new Scanner(System.in);
		System.out.println(message);
		return scan.next();
	}
	
	protected void sellTickets() {
		
		/*
		 * Function Flow: function sellTickets : String -> String -> null
		 * Function Name: sellTickets
		 * Functionality: Allow the user to sell tickets from a specified service.
		 * Parameters: None
		 * Throws: TransactionException (Raised when incorrect input is used)
		 * Returns: void
		*/
		String message = "";					// Store the user input
		ServiceDetails sd = null;				// Used to change the Service Detail
		
		message = getUserInput("Please enter a valid service number:");
		try {									// See if the transaction code exists and handle invalid input
			 sd = getService(message);
			 message = getUserInput("Please enter a number of tickets to sell: ");
				try {								// Check for valid input and kill transaction if input is not valid
					int ticketNumber = 0;			
					ticketNumber = Integer.parseInt(message);
					if (ticketNumber < 0) {
						System.out.println("Invalid Input: The ticket quantity you have entered is less than 0.");
					}
					try {
						sd.addTickets(message);
						System.out.println("The transaction was successful");			// Notify user of successful transaction.
					} catch (TransactionException e) {
						System.out.println("Unable to sell tickets");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
				}
		} catch (TransactionException e){
		    System.out.print("The service does not exist.");
		}		
	}
	
	public abstract int cancelTickets() throws TransactionException;
	public abstract int changeTickets() throws TransactionException;
	public abstract void createService() throws TransactionException;
	public abstract void deleteService() throws TransactionException;
	

}

