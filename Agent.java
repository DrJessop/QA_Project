package frontend;

public class Agent extends User {
	
	private int numTicketsSoldAlready;

	public Agent(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void cancelTickets() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void changeTickets(String serviceNumber1, String serviceNumber2, String numTickets) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void cancelTickets(String serviceNumber, String numTickets) throws TransactionException {
		// TODO Auto-generated method stub
	}



	@Override
	public void createService(String serviceNumber, String date, String serviceName) throws TransactionException {
		throw new TransactionException("Permission denied");
	}



	@Override
	public void deleteService(String serviceNumber, String serviceName) throws TransactionException {
		throw new TransactionException("Permission denied");	
	}

}
