package frontend;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static char login(Scanner scanner) {
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
	
	public static void writeToTransactionSummaryFile(User user) {
	}
	
	public static void acceptTransactions(User user, Scanner scanner) {
		String transaction;
		boolean stillLoggedIn = true;
		while (stillLoggedIn) {
			System.out.println("Please enter a valid transaction: ");
			transaction = scanner.nextLine();
			switch (transaction) {
				case "createservice": 
					user.createService();
					break;
				case "deleteservice": 
					user.deleteService();
					break;
				case "selltickets": 
					user.sellTickets();
					break;
				case "changetickets":
					user.changeTickets();
					break;
				case "canceltickets":
					user.cancelTickets();
					break;
				case "logout": 
					writeToTransactionSummaryFile(user);
					stillLoggedIn = false;
					break;
				default: System.out.print("You have entered an invalid transaction. ");   
		}
	}
	
	public static void main(String[] args) throws ParseException {
		
		ArrayList<String> transactionMessages = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String fileName = "transactionSummaryFile.txt";
		User user;
		while (true) {
			
			char userState = login(scanner);
			
			if (userState == 'a') 
				user = (Agent) new Agent(fileName);
			else
				user = (Planner) new Planner(fileName);
			
			while (true) {
				
			}
		}
		
	}

}
