package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class InvalidLineException extends Exception {
	public InvalidLineException(String message) {
		super(message);
		System.exit(0);
	}
}


public class FileChecker {
	
	private FileChecker() {} 
	
	/************************************************************************
	 * checkServiceNumber() does not check if the service number actually exists
	 **************************************************/
	
	private static void checkServiceNumber(String input) throws InvalidLineException { 
		/*
		 * method checkServiceNumber : String -> void
		 * Functionality: Checks that a given service number was correctly entered
		 * Parameters
		 * 	String input: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		try {
			Integer.parseInt(input);
			if ((input.length() != 5) || (input.charAt(0) == '0')) {
				throw new InvalidLineException("Invalid Input: The service number is not valid.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidLineException("The service number you have entered is not a number.");
		}
	}

	private static void checkNumberTicketsSoldAndCapacity(String input) throws InvalidLineException { 
		/*
		 * method checkNumberTicketsSold : String -> void
		 * Functionality: Checks that number of tickets sold and capacity was correctly entered
		 * Parameters
		 * 	String input: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		
		try {								// Check for valid input and kill transaction if input is not valid
			int numOfTickets = Integer.parseInt(input);
			if (numOfTickets < 1 || numOfTickets > 1000) {     	// Ensure the ticket amount is within the limit
				throw new InvalidLineException("The tickets sold is not within 1 - 1000");
			} 
		} catch (NumberFormatException e) {
			throw new InvalidLineException("Tickets entered is not an integer.");
		}	
	}
	
	private static void checkServiceName(String input) throws InvalidLineException { 
		/*
		 * method checkServiceName : String -> void
		 * Functionality: Checks that a given name was correctly entered
		 * Parameters
		 * 	String input: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		if ((input.length() < 3) || (input.length() > 39) || 
			(input.charAt(0) == ' ') || (input.charAt(input.length() - 1) == ' ')) {
				throw new InvalidLineException("The name is not valid");
			} else {
				for (int i = 0; i < input.length(); i++) {
					if((!Character.isLetterOrDigit(input.charAt(i))) && (input.charAt(i) != ' ')) { 
						throw new InvalidLineException("A non-alphanumeric char detected");
					}
				}
			}
	}
	
	private static void checkServiceDate(String input) throws InvalidLineException {	
		/*
		 * method checkServiceDate : String -> void
		 * Functionality: Checks that a given date was correctly entered
		 * Parameters
		 * 	String input: User input that will be validated based on specifications
		 * Throws: InvalidLineException when wrong input is detected
		*/
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new InvalidLineException("The date you entered contains non-numeric characters.");
		}
		if (input.length() != 8) {
			throw new InvalidLineException("The date you entered is not of length 8.");
		} else if ((Integer.parseInt(input.substring(0,4)) < 1980) || (Integer.parseInt(input.substring(0,4)) > 2999)) {
			throw new InvalidLineException("The year is invalid.");
		} else if ((Integer.parseInt(input.substring(4,6)) < 1) || (Integer.parseInt(input.substring(4,6)) > 12)) {
			throw new InvalidLineException("The month is invalid.");
		} else if ((Integer.parseInt(input.substring(6,8)) < 1) || (Integer.parseInt(input.substring(6,8)) > 31)) {
			throw new InvalidLineException("The day is invalid.");
		}
	}

	//Fill Functions above
	private static ServiceInfo parseServiceLine(String serviceLine) throws InvalidLineException {
		/*
		 * method checkServiceDate : String -> serviceInfo
		 * Functionality: Parse the string and put the contents into an object
		 * Parameters
		 * 	String input: A single line from a service line
		 * Throws: InvalidLineException when wrong input is detected from service line
		*/	
		int thereBetterBeASpaceHere = serviceLine.length() - 9;
		if (serviceLine.charAt(5) != ' ' || serviceLine.charAt(thereBetterBeASpaceHere) != ' ' || serviceLine.charAt(thereBetterBeASpaceHere - 1) == ' ') 
			throw new InvalidLineException("Invalid service line");
		int countSpaces = 0;    // Count the number of spaces before reaching the name
		String serviceNumber = serviceLine.substring(0, 5);				// Used to instantiate object
		String capacity = "";
		String numTicketsSold = "";
		String date = serviceLine.substring(serviceLine.length() - 8, serviceLine.length());
		System.out.println(serviceNumber);
		serviceLine = serviceLine.substring(6, serviceLine.length() - 8);
		System.out.println(date);
		int counter = 0;
		while (countSpaces < 2) {
			if (serviceLine.charAt(counter) == ' ') {
				if (serviceLine.charAt(counter + 1) == ' ') throw new InvalidLineException("Invalid service line");
				countSpaces++;
			}
			else {
				if (countSpaces == 0) 
					capacity = capacity + serviceLine.charAt(counter);
				if (countSpaces == 1)
					numTicketsSold = numTicketsSold + serviceLine.charAt(counter);
			}
			counter++;	
		}
		//if (serviceLine.charAt(serviceLine.length() - 1) == ' ') throw new InvalidLineException("Invalid service line");
		String name = serviceLine.substring(counter, serviceLine.length());
		System.out.println(capacity);
		System.out.println(numTicketsSold);
		System.out.println(name);
		return new ServiceInfo(serviceNumber, capacity, numTicketsSold, name, date);
	}
	
	private static void checkTSF(String serviceLine) throws InvalidLineException {
		
		// To do: Fill in the function, check TSF File is valid
	
	}
	
	public static HashMap<String, String[]> isCentralServicesValid(String centralServicesFile) throws InvalidLineException {
		HashMap<String, String[]> serviceToDataMapping = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(centralServicesFile))) {
			String line;
			String value[] = new String[4];
			ArrayList<Character> properValue = new ArrayList<>();
			int counter = 0;
			while (!((line = br.readLine()) == null)) { 
				checkValidService(line);
				for (int i = 0; i < line.length(); i++) {
					if (counter == 2) { //We reached the name
						
					}
					else if (line.charAt(i) == ' ') {
						value[counter++] = properValue.toString();
						properValue.clear();
					}
					else {
						properValue.add(line.charAt(i));
					}
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serviceToDataMapping;
	}
	
	public static boolean isTSFValid(String tsf) throws InvalidLineException {
		try (BufferedReader br = new BufferedReader(new FileReader(tsf))) {
			String line;
			while (!((line = br.readLine()) == null)) checkTSF(line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
