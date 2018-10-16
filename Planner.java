package frontend;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Planner extends User {

	public Planner(String validServices) {
		/* class Planner
		 * Functionality: This class has privileged planner transaction methods, and does not have as many constraints as the Agent class
		 * Parameters
		 * 	String validServices: The string with the name of the valid services file including the '.txt' extension
		 */
		super(validServices);
	}
	
	private int checkDate(String userInput) {
		/*
		 * method checkDate : String -> int
		 * Functionality: Checks that a given date was correctly entered
		 * Parameters
		 * 	String userInput: User input that should be a valid date
		   Returns -1 if in invalid, else 1
		*/
		try {
			Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			System.out.println("The date you entered contains non-number variables.");
			return -1;
		}
		if (userInput.length() != 8) {
			System.out.println("The date you entered is not of length 8.");
			return -1;
		} else if ((Integer.parseInt(userInput.substring(0,4)) < 1980) || (Integer.parseInt(userInput.substring(0,4)) > 2999)) {
			System.out.println("The year is invalid.");
			return -1;
		} else if ((Integer.parseInt(userInput.substring(5,6)) < 1) || (Integer.parseInt(userInput.substring(5,6)) > 12)) {
			System.out.println("The month is invalid.");
			return -1;
		} else if ((Integer.parseInt(userInput.substring(7,8)) < 1) || (Integer.parseInt(userInput.substring(7,8)) > 31)) {
			System.out.println("The day is invalid.");
			return -1;
		}
		return 1;
	}
	
	protected void createService(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method createService : Scanner -> FileWriter -> null
		 * Functionality: Creates a service
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		*/
							
		String date ;
		String name;
		boolean isGood = true;					// Make sure there are no non-alphanumeric numbers
		String serviceNumber = getUserInput("Please enter a valid service number: ", scanner); // Store the user input
		
		if ((serviceNumber.length() != 5) || (serviceNumber.charAt(0) == '0')) {
			System.out.println("Invalid Input: The service number is not valid.");
			return;
		} else if (this.validService(serviceNumber)) {
			System.out.println("Service number already exists");
			return;
		} 
		try {
			Integer.parseInt(serviceNumber);
			date = getUserInput("Please enter a date: ", scanner);	
			if (checkDate(date) == -1) return;
			name = getUserInput("Please enter a name: ", scanner);     // Check to make sure the name is valid
			if ((name.length() < 3) || (name.length() > 39) || 
				(name.charAt(0) == ' ') || (name.charAt(name.length() - 1) == ' ')) {
				for (int i = 0; i < name.length(); i++) {
					if(!Character.isLetterOrDigit(name.charAt(i))) 
						isGood = false;
				}
			}
			if (isGood) {			// Add the service to the map
				writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("CRE %s 0000 00000 %s %s\n", serviceNumber, name, date));
				System.out.println("The service was added to the registry");
				return;
			} else 
				System.out.println("The entry for the name is not valid.");
		} catch (NumberFormatException e) {
			System.out.println("The service number you have entered is not a number.");
		}
	}		
	
	protected void deleteService(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method deleteService : Scanner -> FileWriter 
		 * Functionality: Deletes a service
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		*/
		String serviceNumber;					// Store the user input
		String name;
		serviceNumber = getUserInput("Please enter a valid service number: ", scanner);
		if (!this.validService(serviceNumber)) {
			System.out.println("This service is not in the valid services file");
			return;
		}
		name = getUserInput("Please enter the name of the service: ", scanner);
		writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("DEL %s 0 00000 %s 0\n", serviceNumber, name));
		System.out.println("The service was successfully removed.");
	}

	@Override
	protected void cancelTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method cancelTickets : Scanner -> FileWriter -> null
		 * Functionality: Cancels tickets for a given service
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		 */				
		String serviceNumber = getUserInput("Please enter a valid service number:", scanner); // Store the user input
										// See if the transaction code exists and handle invalid input
		if (!this.validService(serviceNumber)) {
			System.out.println("This service number is not in the valid services file");
			return;
		}
		String numTicketsToCancel = getUserInput("Please enter the number of tickets to cancel: ", scanner);
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(numTicketsToCancel);
			if (numOfTickets < 0 || numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				System.out.println("Invalid Input: The ticket quantity you have entered is not valid.");
				return;
			} 
			writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("CAN %s %s 00000 **** 0\n", serviceNumber, numTicketsToCancel));
			System.out.println("The transaction was successful");			// Notify user of successful transaction.
			return;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
		}			
		return;				// If Program gets here, then the transaction failed
	}

	@Override
	protected void changeTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method getService : Scanner -> FileWriter
		 * Functionality: Changes the tickets between the service
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		*/						
		
		String serviceNumber = getUserInput("Please enter a valid service number:", scanner); // Store the user input
		if (!this.validService(serviceNumber)) {
			System.out.println("This service is not in the valid services file");
			return;
		}
		String serviceNumber2;
		String numTicketsToCancel;
		serviceNumber2 = getUserInput("Please enter another service valid number: ", scanner);
		if (!this.validService(serviceNumber2)) {
			System.out.println("This service is not in the valid services file");
			return;
		}
		numTicketsToCancel = getUserInput("Please enter the number of tickets to cancel: ", scanner);
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = 0;			
			numOfTickets = Integer.parseInt(numTicketsToCancel);
			if (numOfTickets < 1 || numOfTickets > 1000) {    // Ensure the ticket amount is within the limit
				System.out.println("Invalid Input: The ticket quantity you have entered is not valid.");
				return;
			}
			writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("CHG %s %s %s **** 0\n", serviceNumber, numTicketsToCancel, serviceNumber2));
			System.out.println("The transaction was successful");			// Notify user of successful transaction.
			return;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
		}			
		return;				// If Program gets here, then the transaction failed
	}

}
