package backend;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws IOException, InvalidLineException {
		String centralServicesFile = "centralServicesFile.txt";
		String mergedTSF = "mergedTransactionSummaryFile.txt";
		HashMap<String, ServiceInfo> serviceToDataMapping = FileChecker.isCentralServicesValid(centralServicesFile);
		FileChecker.modifyCentralServicesObject(mergedTSF, serviceToDataMapping);
		String newCentralServicesFile = "newCentralServicesFile.txt";
		String newValidServicesFile = "newValidServicesFile.txt";
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
				date = centralServiceObject.getDate();
				bw.write(String.format("%s %s %s %s %s\n", serviceNumber, capacity, ticketsSold, name, date));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(newValidServicesFile))) {
			for (String key: serviceToDataMapping.keySet()) {
				bw.write(String.format("%s\n", key));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
