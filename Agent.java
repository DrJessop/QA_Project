package frontend;

public class Agent extends User {

	public Agent(String fileName) {
		super(fileName);
	}
	

	@Override
	public int changeTickets(int ticketsAlreadyChanged) {
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
					} else if ((ticketsAlreadyChanged + numOfTickets) > 20) {
						System.out.println("Invalid Input: The agent cannot change more than 20 tickets in a session.");
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
					if ((numOfTickets < 0) || (numOfTickets > 10)) {     // Ensure the ticket amount is within the limit
						System.out.println("Invalid Input: The ticket quantity you have entered is not in between 0 and 10.");
					} else if ((ticketsAlreadyCancelled + numOfTickets) > 20) {
						System.out.println("Invalid Input: The agent cannot cancel more than 20 tickets in a session.");
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
	public void createService() throws TransactionException {
		/*
		 * Function Flow: function getService : String -> String -> String -> TransactionException
		 * Function Name: createService
		 * Functionality: Through a message to tell user that they do not have permission.
		 * Parameters: None
		 * Throws: -----
		 * Returns: void
		*/
		System.out.println("You do not have access for this service.");
	}



	@Override
	public void deleteService() throws TransactionException {
		/*
		 * Function Flow: function deleteService : String -> String -> TransactionException
		 * Function Name: getService
		 * Functionality: Through a message to tell user that they do not have permission.
		 * Parameters: None
		 * Throws: -----
		 * Returns: void
		*/
		System.out.println("You do not have access for this service.");
	}

}
