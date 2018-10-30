package frontend;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Agent extends User {
	
	private int numTicketsAlreadyChanged;
	private int numTicketsAlreadyCanceled;
	private HashMap<String, Integer> numTicketsCanceledForService;

	public Agent(String fileName) {
		super(fileName);
		this.numTicketsAlreadyChanged = 0;
		this.numTicketsAlreadyCanceled = 0;
		this.numTicketsCanceledForService = new HashMap<>();
		Iterator<String> iter = this.validServiceSet.iterator();
		while (iter.hasNext())
			this.numTicketsCanceledForService.put(iter.next(), 0);
	}

	@Override
	public void changeTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method changeTickets: Scanner -> FileWriter -> null
		 * Functionality: Allows the user to change a certain amount of tickets to a different type of service. Agents cannot change more than 20 tickets
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		 */
		String serviceNumber;					// Store the user input
		String serviceNumber2;
		String numTicketsToChange;
		serviceNumber = this.getUserInput("Please enter a valid service number:", scanner);		// See if the transaction code exists and handle invalid input
		if (!this.validService(serviceNumber)) {
			System.out.println("This is not a valid service number");
			return;
		}
		serviceNumber2 = this.getUserInput("Please enter another service valid number: ", scanner);
		if (!this.validService(serviceNumber2)) {
			System.out.println("This is not a valid service number");
			return;
		}
		numTicketsToChange = getUserInput("Please enter the number of tickets to cancel: ", scanner);
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(numTicketsToChange);
			if (numOfTickets < 1) {        // Ensure the ticket amount is within the limit
				System.out.println("Invalid Input: The ticket quantity you have entered is not greater than 0.");
				return;
			} else if ((this.numTicketsAlreadyChanged + numOfTickets) > 20) {
				System.out.println("Invalid Input: The agent cannot change more than 20 tickets in a session.");
				return;
			}
			writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("CHG %s %s %s **** 0\n", serviceNumber, numTicketsToChange, serviceNumber2));
			this.numTicketsAlreadyChanged += numOfTickets;
			System.out.println("The transaction was successful");			// Notify user of successful transaction.
			return;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
		}			
		return;
	}

	@Override
	public void cancelTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method cancelTickets: Scanner -> FileWriter -> null
		 * Functionality: Allows the user to cancel a certain amount of tickets for a specific service
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		 */
		String serviceNumber;					
		String numTicketsCanceled;
		
		serviceNumber = getUserInput("Please enter a valid service number:", scanner);
		if (!this.validService(serviceNumber))	{
			System.out.println("Service number not in valid services file");
			return;
		}
		numTicketsCanceled = getUserInput("Please enter the number of tickets to cancel: ", scanner);
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(numTicketsCanceled);
			if ((numOfTickets < 1) || (numOfTickets > 10)) {     // Ensure the ticket amount is within the limit
				System.out.println("Invalid Input: The ticket quantity you have entered is not in between 1 and 10.");
				return;
			} else if ((this.numTicketsAlreadyCanceled + numOfTickets) > 20) {
				System.out.println("Invalid Input: The agent cannot cancel more than 20 tickets in a session.");
				return;
			}
			if (this.numTicketsCanceledForService.get(serviceNumber) + numOfTickets > 10) {
				System.out.println("Cannot cancel more than 10 tickets for a particular service");
				return;
			}
		
			this.numTicketsCanceledForService.replace(serviceNumber, this.numTicketsCanceledForService.get(serviceNumber) + numOfTickets);
			writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("CAN %s %s 00000 **** 0\n", serviceNumber, numTicketsCanceled));
			System.out.println("The transaction was successful");			// Notify user of successful transaction.
			return;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
		}			
		return;				// If Program gets here, then the transaction failed
	}


	@Override
	public void createService(Scanner scanner, FileWriter toTransactionSummaryFile) {
		/*
		 * method createService: Scanner -> FileWriter -> null
		 * Functionality: Prints out permission denied since agents cannot create services
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		 */
		System.out.println("Permission denied. You cannot create a service");
	}



	@Override
	public void deleteService(Scanner scanner, FileWriter toTransactionSummaryFile) {
		/*
		 * method deleteService: Scanner -> FileWriter -> null
		 * Functionality: Prints out permission denied since agents cannot delete services
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		 */
		System.out.println("Permission denied. You cannot delete a service");
	}

}
