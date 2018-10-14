package frontend;

class TransactionException extends Exception {
	public TransactionException(String errorMessage) {
		super(errorMessage);
	}
}

public class ServiceDetails {
	
	private String serviceNumber;
	private String numTickets;
	private String destinationNumber;
	private String serviceName;
	private String serviceDate;
	private boolean inTransactionSummaryFile;
	
	public ServiceDetails(String transactionCode, String serviceNumber, String numTickets,
							  String destinationNumber, String serviceName, String serviceDate,
							  boolean inTransactionSummaryFile) {
		this.serviceNumber = serviceNumber;
		this.numTickets = numTickets;
		this.destinationNumber = destinationNumber;
		this.serviceName = serviceName;
		this.serviceDate = serviceDate;
		this.inTransactionSummaryFile = inTransactionSummaryFile;
	}
	
	protected void removeTickets(String numTicketsToTakeAway) throws TransactionException {
		int numTicketsCurrently = Integer.parseInt(this.numTickets);
		int intNumTicketsToTakeAway = Integer.parseInt(numTicketsToTakeAway);
		if (intNumTicketsToTakeAway > numTicketsCurrently) {
			throw new TransactionException("Not enough tickets for this service available. "
					+ "The number of tickets currently available is: "
					+ this.numTickets + '\n');
		}
		else 
			this.numTickets = Integer.toString(numTicketsCurrently - intNumTicketsToTakeAway);
	}

	protected void addTickets(String numTicketsToAdd) throws TransactionException {
		int numTicketsCurrently = Integer.parseInt(this.numTickets);
		int intNumTicketsToAdd = Integer.parseInt(numTicketsToAdd);
		if ((intNumTicketsToAdd + numTicketsCurrently) > 1000) {
			throw new TransactionException("The number of tickets cannot exceed 1000 per service. "
					+ "The number of tickets currently available is: "
					+ this.numTickets + '\n');
		}
		else 
			this.numTickets = Integer.toString(numTicketsCurrently + intNumTicketsToAdd);	
	}
	
	protected String getNumTickets() { return this.numTickets; }
	protected String getServiceName() { return this.serviceName; }
}

