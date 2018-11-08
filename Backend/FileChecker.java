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
		//System.out.println(serviceNumber);
		serviceLine = serviceLine.substring(6, serviceLine.length() - 8);
		//System.out.println(date);
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
		if (serviceLine.charAt(serviceLine.length() - 1) == ' ') throw new InvalidLineException("Invalid service line");
		String name = serviceLine.substring(counter, serviceLine.length());
		//System.out.println(capacity);
		//System.out.println(numTicketsSold);
		//System.out.println(name);
		return new ServiceInfo(serviceNumber, capacity, numTicketsSold, name, date);
	}
	
	private static void checkTSF(String serviceLine) throws InvalidLineException {
		
		String tranCode = serviceLine.substring(0, 3);
		String date = serviceLine.substring(serviceLine.length() - 8, serviceLine.length());
		serviceLine = serviceLine.substring(4, serviceLine.length() - 8);
		String serviceNumber = serviceLine.substring(0, 5);
		serviceLine = serviceLine.substring(5, serviceLine.length());
	
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
