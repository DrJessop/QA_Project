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
	
	public static void logout(User user) {
		
	}
	
	public static void acceptTransactions(User user, Scanner scanner) {
		String input;
		while (true) {
			System.out.println("Please enter a transaction: ");
			input = scanner.nextLine();
			switch (input) {
				case "createservice":
					user.createService();
				case "deleteservice":
					user.deleteService();
				case "changeservice":
					user.changeservice();
				case "canceltickets":
					user.cancelTickets();
				case "selltickets":
					user.sellTickets();
				case "logout":
					logout(user);
				default:
					System.out.println("You have entered in an invalid transaction");
			}
		}
	}
	
	public static void main(String[] args) throws ParseException {
		
		ArrayList<String> transactionMessages = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String fileName = "transactionSummaryFile.txt";
		User user;
		char userState;
		while (true) { //Here so that if they log out, they are brought back to the log in screen
			
			userState = login(scanner);
			
			if (userState == 'a') 
				user = (Agent) new Agent(fileName);
			else
				user = (Planner) new Planner(fileName);
			
			acceptTransactions(user, scanner);
		}
		
	}

}
