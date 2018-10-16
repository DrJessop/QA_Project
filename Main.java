package frontend;

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
		 * function login: Scanner -> null
		 * Functionality: Isolates the login logic away from the logic when in the actual program
		 * Parameters
		 * 	Scanner scanner: Accepts the user's input
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
				case "selltickets": 
					user.sellTickets(scanner, toTransactionSummaryFile);
					break;
				case "changetickets":
					user.changeTickets(scanner, toTransactionSummaryFile);
					break;
				case "canceltickets":
					user.cancelTickets(scanner, toTransactionSummaryFile);
					break;
				case "logout": 
					user.logout(toTransactionSummaryFile);
					stillLoggedIn = false;
					break;
				default: System.out.print("You have entered an invalid transaction. ");   
			}
		}
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		
		Scanner scanner = new Scanner(System.in);
		String validServices = "validServices.txt";
		User user;
		FileWriter toTransactionSummaryFile;
		while (true) {
			
			char userState = login(scanner);
			
			if (userState == 'a') 
				user = (Agent) new Agent(validServices);
			else
				user = (Planner) new Planner(validServices);
			toTransactionSummaryFile = new FileWriter("transactionSummaryFile.txt");
			acceptTransactions(user, scanner, toTransactionSummaryFile);
			toTransactionSummaryFile.close();
		}
		
	}

}
