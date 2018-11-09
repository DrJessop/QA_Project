package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * Class Name: InvalidLineException
 * Functionality: Used has a custom extension class that allows us to throw detailed messages.
 * Extends: Exception
 * Parameters
 *  String message (The exception message to be thrown)
 */
class InvalidLineException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLineException(String message) {
		super(message);
		//System.exit(0);
	}
}
/*
 * Class Name: FileChecker
 * Functionality: The file checker enables us to parse the central service file and transaction summary file.
 * 	The central services file is parsed into a hashmap. The hashmap contains the service objects which will be 
 * 	manipulated based on the contents of the transaction summary file. If an error occurs in the process of 
 * 	parsing the data, an exception is thrown with a cusomisable message.
 * Extends: None
 * Parameters
 *  None
 */
public class FileChecker {
	
	private FileChecker() {}
	
	public static ServiceInfo parseServiceLine(String serviceLine) throws InvalidLineException {
		/*
		 * method parseServiceLine : String -> ServiceInfo
		 * Functionality: Parse the string and put the contents into an object.
		 * Parameters
		 * 	String input: A single line from the central summary file.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: ServiceInfo (The contents from a single line in the central service file)
		*/	
		int thereBetterBeASpaceHere = serviceLine.length() - 9;
		if (serviceLine.charAt(5) != ' ' || serviceLine.charAt(thereBetterBeASpaceHere) != ' ' || serviceLine.charAt(thereBetterBeASpaceHere - 1) == ' ') 
			throw new InvalidLineException("19. Space issues");
		int countSpaces = 0;    // Count the number of spaces before reaching the name
		String serviceNumber = serviceLine.substring(0, 5);				// Used to instantiate object
		String capacity = "";
		String numTicketsSold = "";
		String date = serviceLine.substring(serviceLine.length() - 8, serviceLine.length());
		serviceLine = serviceLine.substring(6, serviceLine.length() - 9);
		int counter = 0;
		while (countSpaces < 2 && counter < serviceLine.length()) {
			if (serviceLine.charAt(counter) == ' ') {
				if (serviceLine.charAt(counter + 1) == ' ') throw new InvalidLineException("20. Invalid service line");
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
		if (serviceLine.charAt(serviceLine.length() - 1) == ' ') throw new InvalidLineException("21. Invalid service line");
		String name = serviceLine.substring(counter, serviceLine.length());
		return new ServiceInfo(serviceNumber, capacity, numTicketsSold, name, date);
	}
	
	private static String[] checkValidEOS(String serviceLine) throws InvalidLineException {
		/*
		 * method checkValidEOS : String -> String[]
		 * Functionality: Ensure the Transaction Summary File Line starting with EOS is valid.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: tokens (The parsed contents of the input.)
		*/	
		if (!(serviceLine.equals("EOS 00000 0 00000 **** 0"))) throw new InvalidLineException("EOS line not correct");
		String[] tokens = {"EOS", "00000", "0", "00000", "****", "0"};
		return tokens;
	}
	
	private static String[] checkValidCRE(String serviceLine) throws InvalidLineException {
		/*
		 * method checkValidCRE : String -> String[]
		 * Functionality: Ensure the Transaction Summary File Line starting with CRE is valid.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: tokens (The parsed contents of the input.)
		*/
		String[] tokens = new String[6];
		String serviceNumber = serviceLine.substring(4, 9);
		if (!(CheckValidEntry.isValidServiceNumber(serviceNumber))) 
			throw new InvalidLineException("22. Invalid service number");
		tokens[0] = "CRE";
		if (serviceLine.charAt(3) != ' ') 
			throw new InvalidLineException("23. Invalid service line, no space");
		tokens[1] = serviceNumber;
		if ((serviceLine.charAt(9) != ' ') || (serviceLine.charAt(10) != '0') || (serviceLine.charAt(11) != ' '))
			throw new InvalidLineException("24. Invalid service line, no space");
		tokens[2] = "0";
		if (serviceLine.substring(13, 18) != "00000" || serviceLine.charAt(18) != ' ') 
			throw new InvalidLineException("25. Invalid service line");
		tokens[3] = "00000";
		tokens[4] = serviceLine.substring(19, serviceLine.length() - 9);
		if (!(CheckValidEntry.isValidName(tokens[4])) || serviceLine.charAt(serviceLine.length() - 9) != ' ') throw new InvalidLineException("Invalid name");
		String date = serviceLine.substring(serviceLine.length() - 8, serviceLine.length());
		if (!(CheckValidEntry.isValidDate(date))) throw new InvalidLineException("26. Invalid date");
		tokens[5] = date;
		return tokens;
	}
	
	private static String[] checkValidDEL(String serviceLine) throws InvalidLineException {
		/*
		 * method checkValidDEL : String -> String[]
		 * Functionality: Ensure the Transaction Summary File Line starting with DEL is valid.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: tokens (The parsed contents of the input.)
		*/
		String[] tokens = new String[6];
		String serviceNumber = serviceLine.substring(4, 9);
		if (!(CheckValidEntry.isValidServiceNumber(serviceNumber))) 
			throw new InvalidLineException("27. Invalid service number");
		tokens[0] = "DEL";
		if (serviceLine.charAt(3) != ' ') 
			throw new InvalidLineException("28. Invalid service line, no space");
		tokens[1] = serviceNumber;
		if ((serviceLine.charAt(10) != ' ') || (serviceLine.charAt(11) != '0') || (serviceLine.charAt(12) != ' '))
			throw new InvalidLineException("29. Invalid service line, no space");
		tokens[2] = "0";
		if (serviceLine.substring(13, 18) != "00000" || serviceLine.charAt(18) != ' ') 
			throw new InvalidLineException("30. Invalid service line");
		tokens[3] = "00000";
		tokens[4] = serviceLine.substring(19, serviceLine.length() - 1);
		if (!(CheckValidEntry.isValidName(tokens[4])) || serviceLine.charAt(serviceLine.length() - 2) != ' ' || serviceLine.charAt(serviceLine.length() - 1) != '0') 
			throw new InvalidLineException("31. Invalid line");
		tokens[5] = "0";
		return tokens;
	}
	
	private static String[] checkValidSEL(String serviceLine) throws InvalidLineException {
		/*
		 * method checkValidSEL : String -> String[]
		 * Functionality: Ensure the Transaction Summary File Line starting with SEL is valid.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: tokens (The parsed contents of the input.)
		*/
		String[] tokens = new String[6];
		String serviceNumber = serviceLine.substring(4, 9);
		if (!(CheckValidEntry.isValidServiceNumber(serviceNumber))) 
			throw new InvalidLineException("32. Invalid service number");
		tokens[0] = "SEL";
		if (serviceLine.charAt(3) != ' ' || serviceLine.charAt(9) != ' ') 
			throw new InvalidLineException("33. Invalid service line, no space");
		tokens[1] = serviceNumber;
		int counter = 10;
		tokens[2] = "";
		while (serviceLine.charAt(counter) != ' ' && counter < 68) {
			tokens[2] += serviceLine.charAt(counter);
			counter++;
		}
		serviceLine = serviceLine.substring(counter + 1, serviceLine.length());
		if (!serviceLine.equals("00000 **** 0")) 
			throw new InvalidLineException("34. Invalid service line");
		tokens[3] = "00000";
		tokens[4] = "****";
		tokens[5] = "0";
		return tokens;
	}
	
	private static String[] checkValidCAN(String serviceLine) throws InvalidLineException {
		/*
		 * method checkValidCAN : String -> String[]
		 * Functionality: Ensure the Transaction Summary File Line starting with CAN is valid.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: tokens (The parsed contents of the input.)
		*/
		String[] tokens = new String[6];
		String serviceNumber = serviceLine.substring(4, 9);
		if (!(CheckValidEntry.isValidServiceNumber(serviceNumber))) 
			throw new InvalidLineException("35. Invalid service number");
		tokens[0] = "CAN";
		if (serviceLine.charAt(3) != ' ' || serviceLine.charAt(9) != ' ') 
			throw new InvalidLineException("36. Invalid service line, no space");
		tokens[1] = serviceNumber;
		int counter = 10;
		tokens[2] = "";
		while (serviceLine.charAt(counter) != ' ' && counter < 68) {
			tokens[2] += serviceLine.charAt(counter);
			counter++;
		}
		serviceLine = serviceLine.substring(counter + 1, serviceLine.length());
		if (!serviceLine.equals("00000 **** 0")) 
			throw new InvalidLineException("37. Invalid service line");
		tokens[3] = "00000";
		tokens[4] = "****";
		tokens[5] = "0";
		return tokens;
	}
	
	private static String[] checkValidCHG(String serviceLine) throws InvalidLineException {
		/*
		 * method checkValidCHG : String -> String[]
		 * Functionality: Ensure the Transaction Summary File Line starting with CHG is valid.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: tokens (The parsed contents of the input.)
		*/
		String[] tokens = new String[6];
		String serviceNumber = serviceLine.substring(4, 9);
		if (!(CheckValidEntry.isValidServiceNumber(serviceNumber))) 
			throw new InvalidLineException("38. Invalid service number");
		tokens[0] = "CHG";
		if (serviceLine.charAt(3) != ' ' || serviceLine.charAt(9) != ' ') 
			throw new InvalidLineException("39. Invalid service line, no space");
		tokens[1] = serviceNumber;
		int counter = 10;
		tokens[2] = "";
		while (serviceLine.charAt(counter) != ' ' && counter < 68) {
			tokens[2] += serviceLine.charAt(counter);
			counter++;
		}
		serviceLine = serviceLine.substring(counter + 1, serviceLine.length());
		tokens[3] = serviceLine.substring(0, 5);
		if (!serviceLine.substring(5, serviceLine.length()).equals(" **** 0"));
		tokens[4] = "****";
		tokens[5] = "0";
		return tokens;
	}
	private static void processTransaction(String serviceLine, HashMap<String, ServiceInfo> centralServicesMapping) throws InvalidLineException {
		/*
		 * method processTransaction : String -> HashMap-> void
		 * Functionality: Reads a line from the transaction summary file and conducts the
		 * 	appropriate changes in the HashMap. The HashMap stores the central services details.
		 * Parameters
		 * 	String serviceLine: A single line from the Transaction Summary File.
		 *  HashMap centralServicesMapping: The haspmap containing the central services and its features.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: void
		*/
		String transactionType = serviceLine.substring(0, 3);
		String[] tokens;
		ServiceInfo centralServiceObject;
		ServiceInfo centralServiceDestination;
		int numTicketsSold;
		int numTicketsCanceled;
		int capacity;
		int destinationCapacity;
		int currNumTicketsSold;
		int destinationNumTicketsSold;
		switch (transactionType) {
			case "EOS":
				tokens = checkValidEOS(serviceLine);
				System.exit(0);
				break;
			case "CRE":
				tokens = checkValidCRE(serviceLine);
				if (centralServicesMapping.containsKey(tokens[1])) throw new InvalidLineException("40. Duplicate service");
				centralServicesMapping.put(tokens[1], new ServiceInfo(tokens[1], tokens[2], "30", tokens[4], tokens[5]));
				break;
			case "DEL":
				tokens = checkValidDEL(serviceLine);
				if (!centralServicesMapping.containsKey(tokens[1])) throw new InvalidLineException("41. Service does not exist");
				if (!centralServicesMapping.get(tokens[1]).getName().equals(tokens[4])) throw new InvalidLineException("Service names don't match");
				centralServicesMapping.remove(tokens[1]);
				break;
			case "SEL":
				tokens = checkValidSEL(serviceLine);
				if (!centralServicesMapping.containsKey(tokens[1])) throw new InvalidLineException("42. Service does not exist");
				centralServiceObject = centralServicesMapping.get(tokens[1]);
				numTicketsSold = Integer.parseInt(tokens[2]);
				capacity = Integer.parseInt(centralServiceObject.getCapacity());
				if (numTicketsSold > capacity) throw new InvalidLineException("43. Num tickets sold greater than capacity");
				centralServiceObject.setNumTicketsSold(Integer.toString(Integer.parseInt(centralServiceObject.getNumTicketsSold()) + numTicketsSold));
				break;
			case "CAN":
				tokens = checkValidCAN(serviceLine);
				if (!centralServicesMapping.containsKey(tokens[1])) throw new InvalidLineException("44. Service does not exist");
				centralServiceObject = centralServicesMapping.get(tokens[1]);
				numTicketsCanceled = Integer.parseInt(tokens[2]);
				currNumTicketsSold = Integer.parseInt(centralServiceObject.getNumTicketsSold());
				if (numTicketsCanceled > currNumTicketsSold) throw new InvalidLineException("45. Num tickets canceled greater than current number of tickets sold");
				centralServiceObject.setNumTicketsSold(Integer.toString(currNumTicketsSold - numTicketsCanceled));
				break;
			case "CHG":
				tokens = checkValidCHG(serviceLine);
				if (!centralServicesMapping.containsKey(tokens[1]) || !centralServicesMapping.containsKey(tokens[3])) throw new InvalidLineException("46. Service does not exist");
				centralServiceObject = centralServicesMapping.get(tokens[1]);
				centralServiceDestination = centralServicesMapping.get(tokens[3]);
				currNumTicketsSold = Integer.parseInt(centralServiceObject.getNumTicketsSold());
				destinationCapacity = Integer.parseInt(centralServiceDestination.getCapacity());
				destinationNumTicketsSold = Integer.parseInt(centralServiceDestination.getNumTicketsSold());
				if (currNumTicketsSold - Integer.parseInt(tokens[2]) < 0 || destinationNumTicketsSold + Integer.parseInt(tokens[2]) > destinationCapacity)
					throw new InvalidLineException("47. Invalid change");
				destinationNumTicketsSold += Integer.parseInt(tokens[2]);
				currNumTicketsSold -= Integer.parseInt(tokens[2]);
				centralServiceObject.setNumTicketsSold(Integer.toString(currNumTicketsSold));
				centralServiceDestination.setNumTicketsSold(Integer.toString(destinationNumTicketsSold));
				break;
			default:
				throw new InvalidLineException("48. How the hell did you manage to break our frontend????????");
		}
	}
	
	public static HashMap<String, ServiceInfo> isCentralServicesValid(String centralServicesFile) throws InvalidLineException, FileNotFoundException, IOException {
		/*
		 * method isCentralServicesValid : String -> HashMap
		 * Functionality: Read the central services file and read it into a hashmap.
		 * Parameters
		 * 	String centralServicesFile: The name of the central services file.
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: HashMap centralServicesMapping: The haspmap containing the central services and its features.
		*/
		HashMap<String, ServiceInfo> serviceToDataMapping = new HashMap<>();
		String line;
		String key;
		try (BufferedReader br = new BufferedReader(new FileReader(centralServicesFile))) {
			while (!((line = br.readLine()) == null)) {
				key = line.substring(0, 5);
				if (serviceToDataMapping.containsKey(key)) throw new InvalidLineException("49. Invalid service");
				serviceToDataMapping.put(key, FileChecker.parseServiceLine(line));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return serviceToDataMapping;
	}
	
	public static void modifyCentralServicesObject(String tsf, HashMap<String, ServiceInfo> centralServicesMapping) throws InvalidLineException {
		/*
		 * method modifyCentralServicesObject : String -> HashMap -> String[]
		 * Functionality: The Transaction Summary File is parsed while changes
		 *  are ServiceInfo objects depending on the transaction read into the program.
		 * Parameters
		 * 	String tsf: The Transaction Summary File.
		 *  HashMap centralServicesMapping: The HashMap of central services
		 * Throws: InvalidLineException when wrong input is detected from the line.
		 * Returns: void
		*/
		try (BufferedReader br = new BufferedReader(new FileReader(tsf))) {
			String serviceLine;
			while (!((serviceLine = br.readLine()) == null)) 
				processTransaction(serviceLine, centralServicesMapping);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
