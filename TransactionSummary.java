package frontend;

class TransactionException extends Exception {
	public TransactionException(String errorMessage) {
		super(errorMessage);
	}
}

public class TransactionSummary {
	
	private String transactionCode;
	private String serviceNumber;
	private String numTickets;
	private String destinationNumber;
	private String serviceName;
	private String serviceDate;
	private boolean inTransactionSummaryFile;
	
	public TransactionSummary(String transactionCode, String serviceNumber, String numTickets,
							  String destinationNumber, String serviceName, String serviceDate,
							  boolean inTransactionSummaryFile) {
		this.transactionCode = transactionCode;
		this.serviceNumber = serviceNumber;
		this.numTickets = numTickets;
		this.destinationNumber = destinationNumber;
		this.serviceName = serviceName;
		this.serviceDate = serviceDate;
		this.inTransactionSummaryFile = inTransactionSummaryFile;
	}
	
	protected void removeTickets(String numTicketsToTakeAway) throws TransactionException {
		int numTickets = Integer.parseInt(numTicketsToTakeAway);
		int numTicketsCurrently = Integer.parseInt(this.numTickets);
		if (numTickets > numTicketsCurrently) {
			throw new TransactionException("Not enough tickets for this service available. "
					+ "The number of tickets currently available is: "
					+ this.numTickets + '\n');
		}
		else 
			this.numTickets = Integer.toString(numTicketsCurrently - numTickets);
	}
	
}
