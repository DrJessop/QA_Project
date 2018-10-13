package frontend;

import java.util.HashMap;

public class Planner extends User {
	
	private HashMap<String, ServiceDetails> map;

	public Planner(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
	
	//DO USER INPUTS INSTEAD OF ACCEPTING ATTRIBUTES
	public void createService(String serviceNumber, String date, String serviceName) throws TransactionException {
		
		/* 
		 * Finish date checker and name checker
		 */
		if (serviceNumber.length() != 5) {
			throw new TransactionException("Invalid Input: The service number is not of length five.");
		} else if (serviceNumber.charAt(0) == '0') {
			throw new TransactionException("Invalid Input: The first number in the service number cannot equal 0.");
		}
		if (this.map.containsKey(serviceNumber))
			throw new TransactionException("Service number already exists");
		try {
			Integer.parseInt(serviceNumber);
		} catch (NumberFormatException e) {
			throw new TransactionException("The service number you entered is not actually a number");
		}
		
	}
	
	public void deleteService(String serviceNumber, String serviceName) throws TransactionException {
		ServiceDetails sd = this.getService(serviceNumber);
		if (!sd.getServiceName().equals(serviceName))
			throw new TransactionException(String.format("The service name you supplied does not"
					+ " correspond to the actual service name: %s", sd.getServiceName()));
		this.map.remove(serviceNumber);
	}

	@Override
	public void cancelTickets(String serviceNumber, String numTickets) throws TransactionException {
		ServiceDetails sd = this.getService(serviceNumber);
		sd.removeTickets(numTickets);
	}

	@Override
	public void changeTickets(String serviceNumber1, String serviceNumber2, String numTickets) throws TransactionException {
		ServiceDetails sd1 = this.getService(serviceNumber1);
		ServiceDetails sd2 = this.getService(serviceNumber2);
		int numTicketsForFirstService = Integer.parseInt(sd1.getNumTickets());
		int numTicketsForSecondService = Integer.parseInt(sd2.getNumTickets());
		int ticketsToMove = Integer.parseInt(numTickets);
		if ((ticketsToMove > numTicketsForFirstService) || (ticketsToMove + numTicketsForSecondService > 1000)) {
			throw new TransactionException(String.format("Unable to move %s from the first service,"
					+ " containing %d, into the second service, containing %d", numTickets, numTicketsForFirstService,
																				numTicketsForSecondService));
		}
		sd1.removeTickets(numTickets);
		sd2.addTickets(numTickets);
	}

}
