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
	
	// Fill in these functions with the appropriate checks
	
	private static void checkServiceNumber(String input) throws InvalidLineException { }
	private static void checkServiceCapacity(String input) throws InvalidLineException { }
	private static void checkNumberTicketsSold(String input) throws InvalidLineException { }
	private static void checkServiceName(String input) throws InvalidLineException { }
	private static void checkServiceDate(String input) throws InvalidLineException { }

	//Fill Functions above
	private static void checkValidService(String serviceLine) throws InvalidLineException {
		
		// To do: Fill in the function, check each service is valid
	
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
