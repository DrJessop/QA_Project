package frontend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
	
	public static char login(Scanner scanner) {
		/*
		 * function login: Scanner -> null
		 * Functionality: Isolates the login logic away from the logic when in the actual program
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
		 */
		boolean loggedIn = false;
		String input;
		char userState = '\0';
		while (!loggedIn) {
			System.out.println("Please enter agent or planner: ");
			input = scanner.nextLine();
			if (input.equals("agent")) {
				userState = 'a';
				loggedIn = true;
			}
			else if (input.equals("planner")) {
				userState = 'p';
				loggedIn = true;
			}
			else 
				System.out.println("Invalid input");
		}
		return userState;
	}
	
	public static void acceptTransactions(User user, Scanner scanner, FileWriter toTransactionSummaryFile) throws IOException {
		/*
		 * function acceptTransactions: User -> Scanner -> FileWriter -> null
		 * Functionality: The logic that occurs when the user is successfully logged into the program
		 * Parameters
		 * 	User user: At the point that this runs, the user has been cast to either a Planner or an Agent object
		 * 	Scanner scanner: Accepts the user's input
		 * 	FileWriter toTransactionSummaryFile: Adds the transaction to the transaction summary file
		 */
		String transaction;
		boolean stillLoggedIn = true;
		while (stillLoggedIn) {
			System.out.println("Please enter a valid transaction: ");
			transaction = scanner.nextLine();
			switch (transaction) {
				case "createservice": 
					user.createService(scanner, toTransactionSummaryFile);
					break;
				case "deleteservice": 
					user.deleteService(scanner, toTransactionSummaryFile);
					break;
				case "sellticket": 
					user.sellTickets(scanner, toTransactionSummaryFile);
					break;
				case "changeticket":
					user.changeTickets(scanner, toTransactionSummaryFile);
					break;
				case "cancelticket":
					user.cancelTickets(scanner, toTransactionSummaryFile);
					break;
				case "logout": 
					user.logout(toTransactionSummaryFile); //Writes the final EOS line to the transaction summary file and closes the file reader
					stillLoggedIn = false;
					break;
				default: System.out.print("You have entered an invalid transaction. ");   
			}
		}
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		
		/*
		 * Overall program intention:
		 * 	To serve as an interface for transactions involving transport tickets
		 * Input and output files:
		 * 	input file: 
		 * 		'validServices.txt'
		 * 			each line in this file contains a five-digit number
		 * 			no line begins with a 0, except the final line
		 * 			the final line in this file is 00000
		 * 	output file:
		 * 		'transactionSummaryFile.txt'
		 * 			this program creates this file if it is not currently in the directory, else it overwrites it 
		 * 			the exact format of the output is in the specifications for this project
		 * How the program is intended to be run:
		 * 	This program should be run in a Java v8 IDE
		 * 	The 'validServices.txt' file needs to be within the scope of this Java project
		 *	The project must contain the classes Agent, Planner, User, and Main
		 * function main: String[] args -> null
		 * Functionality: Control flow of the program 
		 * Parameters
		 * 	String[] args: Command-line arguments
		 */
		
		String validServices = args[0];
		if (!(new File(validServices).exists())) {
			System.out.println("'validServices.txt' is not in the current directory");
			return;
		}
		User user;
		FileWriter toTransactionSummaryFile;
		Scanner scanner = new Scanner(System.in);
		char userState;
		
		while (true) {
			
			userState = login(scanner);
			
			if (userState == 'a') 
				user = (Agent) new Agent(validServices);
			else
				user = (Planner) new Planner(validServices);
			toTransactionSummaryFile = new FileWriter(args[1]);
			acceptTransactions(user, scanner, toTransactionSummaryFile); //file reader gets closed in this method
		}
		
	}

}
