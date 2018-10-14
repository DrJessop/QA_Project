package frontend;

import java.util.HashMap;

public class Planner extends User {
	
	private HashMap<String, ServiceDetails> map;

	public Planner(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
	
	private void checkDate(String userInput) throws TransactionException {
		/*
		 * Function Flow: function checkDate : String -> void
		 * Function Name: checkDate
		 * Functionality: Create a service.
		 * Parameters: None
		 * Throws: userInput (User Input that should be a date)
		 * Returns: None
		*/
		try {
			Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			throw new TransactionException("The date you entered contains non-number variables.");
		}
		if (userInput.length() != 8) {
			throw new TransactionException("The date you entered is not of length 8.");
		} else if ((Integer.parseInt(userInput.substring(0,4)) < 1980) || (Integer.parseInt(userInput.substring(0,4)) > 2999)) {
			throw new TransactionException("The year is invalid.");
		} else if ((Integer.parseInt(userInput.substring(5,6)) < 1) || (Integer.parseInt(userInput.substring(5,6)) > 12)) {
			throw new TransactionException("The month is invalid.");
		} else if ((Integer.parseInt(userInput.substring(7,8)) < 1) || (Integer.parseInt(userInput.substring(7,8)) > 31)) {
			throw new TransactionException("The day is invalid.");
		}
	}
	
	public void createService() throws TransactionException {
		
		/* 
		 * Finish date checker and name checker
		 */
		if (serviceNumber.length() != 5) {
			throw new TransactionException("Invalid Input: The service number is not of length five.");
		} else if (serviceNumber.charAt(0) == '0') {
			throw new TransactionException("Invalid Input: The first number in the service number cannot equal 0.");
		}
		if (this.map.containsKey(serviceNumber))
			throw new TransactionException("Service number already exists");
		try {
			Integer.parseInt(serviceNumber);
		} catch (NumberFormatException e) {
			throw new TransactionException("The service number you entered is not actually a number");
		}
		
	}
	
	public void deleteService() throws TransactionException {
		/*
		 * Function Flow: function deleteService : void -> void
		 * Function Name: deleteService
		 * Functionality: Delete a service.
		 * Parameters: None
		 * Throws: TransactionException (Raised when service number is not found)
		 * Returns: None
		*/
		String message = "";					// Store the user input
		ServiceDetails sd = null;				// Used to change the Service Detail
		
		message = getUserInput("Please enter a valid service number: ");
		try {									// See if the transaction code exists and handle invalid input
			sd = this.getService(message);
			message = getUserInput("Please enter the name of the service: ");
			if (!sd.getServiceName().equals(message))
				System.out.println(String.format("The service name you supplied does not" + 
						" correspond to the " + "actual service name: %s", sd.getServiceName()));
			else {
				this.map.remove(message);
				System.out.println("The service was successfully removed.");
			}
		} catch (TransactionException e) {
		    System.out.print("The service does not exist.");
		}
	}

	@Override
	public int cancelTickets(int ticketsAlreadyCancelled) throws TransactionException {
		/*
		 * Function Flow: function cancelTickets : int -> int
		 * Function Name: cancelTickets
		 * Functionality: Cancel tickets within a service.
		 * Parameters: ticketsAlreadyCancelled (Tickets already cancelled in the session)
		 * Throws: TransactionException (Raised when service number is not found)
		 * Returns: ticketsCancelled (Number of tickets that were cancelled in the transaction)
		*/
		String message = "";					// Store the user input
		ServiceDetails sd = null;				// Used to change the Service Detail
		
		message = getUserInput("Please enter a valid service number:");
		try {									// See if the transaction code exists and handle invalid input
			 sd = getService(message);
			 message = getUserInput("Please enter the number of tickets to cancel: ");
				try {								// Check for valid input and kill transaction if input is not valid
					int numOfTickets = 0;			
					numOfTickets = Integer.parseInt(message);
					if (numOfTickets < 0) {     	// Ensure the ticket amount is within the limit
						System.out.println("Invalid Input: The ticket quantity you have entered is not greater than 0.");
					} 
					try {
						sd.removeTickets(message);
						System.out.println("The transaction was successful");			// Notify user of successful transaction.
						return numOfTickets;
					} catch (TransactionException e) {
						System.out.println("Unable to sell tickets");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
				}
		} catch (TransactionException e) {
		    System.out.print("The service does not exist.");
		}				
		return -1;				// If Program gets here, then the transaction failed
	}

	@Override
	public int changeTickets(int ticketsAlreadyChanged) throws TransactionException {
		/*
		 * Function Flow: function getService : int -> int
		 * Function Name: changeTickets
		 * Functionality: Change the tickets between the service.
		 * Parameters: None
		 * Throws: TransactionException (Raised when service number is not found)
		 * Returns: ticketsChanged (Number of tickets that were changed)
		*/	
		String message = "";					// Store the user input
		ServiceDetails sd1 = null;				// Used to change the Service Detail
		ServiceDetails sd2 = null;				// Used to change the Service Detail
		
		message = getUserInput("Please enter a valid service number:");
		try {									// See if the transaction code exists and handle invalid input
			 sd1 = getService(message);
			 message = getUserInput("Please enter another service valid number: ");
			 sd2 = getService(message);
			 message = getUserInput("Please enter the number of tickets to cancel: ");
				try {								// Check for valid input and kill transaction if input is not valid
					int numOfTickets = 0;			
					numOfTickets = Integer.parseInt(message);
					if (numOfTickets < 0) {        // Ensure the ticket amount is within the limit
						System.out.println("Invalid Input: The ticket quantity you have entered is not greater than 0.");
					} 
					try {
						int numTicketsForFirstService = Integer.parseInt(sd1.getNumTickets());
						int numTicketsForSecondService = Integer.parseInt(sd2.getNumTickets());
						if ((numOfTickets > numTicketsForFirstService) || (numOfTickets + numTicketsForSecondService > 1000)) {
							System.out.println(String.format("Unable to move from the first service,"+ " containing %d, into the second "
									+ "service, containing %d", numOfTickets, numTicketsForFirstService, numTicketsForSecondService));
						}
						sd1.removeTickets(Integer.toString(numOfTickets));
						sd2.addTickets(Integer.toString(numOfTickets));
						System.out.println("The transaction was successful");			// Notify user of successful transaction.
						return numOfTickets;
					} catch (TransactionException e) {
						System.out.println("Unable to sell tickets");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
				}
		} catch (TransactionException e) {
		    System.out.print("The service does not exist.");
		}				
		return -1;				// If Program gets here, then the transaction failed
	}

}
