package frontend;

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
	
	public static void main(String[] args) {
		
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
			
			
		}
		
	}

}
