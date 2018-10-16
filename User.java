package frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public abstract class User {
	
	protected HashSet<String> validServiceSet;
	
	public User(String validServices) {
		/*
		 * abstract class User
		 * Functionality: This class acts as a driver for the agent and planner classes. 
		 * Attributes
		 * 	HashSet<String> validServiceSet: The string with the name of the valid services file including the '.txt' extension
		 */
		this.validServiceSet = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(validServices))) {
			String line;
			while (!(line = br.readLine()).equals("00000")) 
				this.validServiceSet.add(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	protected static void writeToTransactionSummaryFile(FileWriter writer, String transaction) throws IOException {
		/*
		 * function writeToTransactionSummaryFile: ArrayList<String> -> String -> null
		 * Functionality: Writes out all of the transactions to the transaction summary file
		 * Parameters
		 * 	FileWriter writer: Adds the transaction to the transaction summary file
		 * 	String transaction: The transaction that is being written to the transaction summary file
		 */
		writer.write(transaction);
	}
	
	protected boolean validService(String serviceNumber) {
		/*
		 * method validService: String -> boolean
		 * Functionality
		 * 	Allows the front end to know whether a service that is being mentioned already exists in the valid services file
		 * Parameters
		 * 	String serviceNumber: The service number we would like to check exists in the valid services file
		 * Returns
		 * 	Whether the service number corresponds to a valid service or not
		 */
		if (!this.validServiceSet.contains(serviceNumber)) return false;
		return true;
	}
	
	protected String getUserInput(String message, Scanner scanner) {
		/*
		 * method getUserInput : String -> String
		 * Functionality: Get the user input
		 * Parameters
		 * 	String message: Display what the user is to see
		 * 	Scanner scanner: Accepts the user's input 
		 * Returns user input
		*/
		System.out.println(message);
		return scanner.nextLine();
	}
	
	
	
	protected void logout(FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method logout :  FileWriter -> null
		 * Functionality: Allows the user to logout and puts in the EOS transaction into the transaction summary file
		 * Parameters
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		*/		
		writeToTransactionSummaryFile(toTransactionSummaryFile, "EOS 00000 0 00000 **** 0\n");
		toTransactionSummaryFile.close();
	}
	
	protected void sellTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * method sellTickets : Scanner -> ArrayList<String> -> null
		 * Functionality: Allow the user to sell tickets from a specified service.
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		*/					
		String serviceNumber = getUserInput("Please enter a valid service number:", scanner); // Store the user input
		
		if (!this.validService(serviceNumber)) {
			System.out.println("This service number does not exist");
			return;
		}
		String numTicketsToSell = getUserInput("Please enter a number of tickets to sell: ", scanner);
		try { 
			//If the ticket number the user enters is a number
			int ticketNumber = Integer.parseInt(numTicketsToSell);
			if (ticketNumber < 1) 
				System.out.println("Invalid Input: The ticket quantity you have entered is less than 1.");
			writeToTransactionSummaryFile(toTransactionSummaryFile, String.format("SEL %s %s 00000 **** 0\n", serviceNumber, numTicketsToSell));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input: The number of tickets you have entered is not a string.");
		}
	}
	
	
	/*
	 * abstract method cancelTickets: Scanner -> ArrayList<String> -> null
	 * Functionality: Allows the user to cancel a certain amount of tickets for a service
	 * Parameters
	 * 	Scanner scanner: Accepts the user's input
	 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
	 */
	protected abstract void cancelTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException;
	
	/*
	 * abstract method changeTickets: Scanner -> ArrayList<String> -> null
	 * Functionality: Allows the user to change a certain amount of tickets to a different type of service
	 * Parameters
	 * 	Scanner scanner: Accepts the user's input
	 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
	 */
	protected abstract void changeTickets(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException;
	
	/*
	 * abstract method createService: Scanner -> ArrayList<String> -> null
	 * Functionality: Allows the user to create a service in planner mode, will print out permission denied for agents
	 * Parameters
	 * 	Scanner scanner: Accepts the user's input
	 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
	 */
	protected abstract void createService(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException;
	
	/*
	 * abstract method deleteService: Scanner -> ArrayList<String> -> null
	 * Functionality: Allows planners to delete service, will print out permission denied for agents
	 * Parameters
	 * 	Scanner scanner: Accepts the user's input
	 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file for 
	 */
	protected abstract void deleteService(Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException;
	

}

