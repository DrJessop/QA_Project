package backend;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws IOException, InvalidLineException {
		/*
		 * Functionality: The main class takes the file names, converts the central service file
		 * 	into a HashMap, makes the changes to the HashMap based on the content in the 
		 * 	transaction summary file and writes the final changes in two new files: the central services
		 * 	file and valid services file. This is not currently a command line program to make our testing
		 *  easier, so you will need to have centralServicesFile.txt and mergedTransactionSummaryFile.txt in your
		 *  current directory in order to run. 
		 */
		String centralServicesFile = args[0];
		String mergedTSF = args[1];
		HashMap<String, ServiceInfo> serviceToDataMapping = FileChecker.isCentralServicesValid(centralServicesFile);
		FileChecker.modifyCentralServicesObject(mergedTSF, serviceToDataMapping);
		String newCentralServicesFile = args[2];
		String newValidServicesFile = args[3];
		ServiceInfo centralServiceObject;
		String serviceNumber;
		String capacity;
		String ticketsSold;
		String name;
		String date;
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(newCentralServicesFile))) {
			for (String key: serviceToDataMapping.keySet()) {
				centralServiceObject = serviceToDataMapping.get(key);
				serviceNumber = key;
				capacity = centralServiceObject.getCapacity();
				ticketsSold = centralServiceObject.getNumTicketsSold();
				name = centralServiceObject.getName();
				date = centralServiceObject.getDate(); System.out.println(String.format("%s %s %s %s %s\n", serviceNumber, capacity, ticketsSold, name, date));
				bw.write(String.format("%s %s %s %s %s\n", serviceNumber, capacity, ticketsSold, name, date));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(newValidServicesFile))) {
			for (String key: serviceToDataMapping.keySet()) 
				bw.write(String.format("%s\n", key));
			bw.write("00000");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
