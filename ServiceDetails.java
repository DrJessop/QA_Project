package frontend;

class TransactionException extends Exception {
	/*
	 * Class Name: TransactionException
	 * Functionality: Used to throw exceptions.
	 * Throws: None
	*/
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
	
	public ServiceDetails(String serviceNumber, String numTickets,
							  String destinationNumber, String serviceName, String serviceDate,
							  boolean inTransactionSummaryFile) {
		/*
		 * Class Name: ServiceDetails
		 * Functionality: Stores all the information for a service.
		 * Parameters: serviceNumber (The service number or identifier),
		 * numTickets (The number of tickets available),
		 * destinationNumber (Where the service will be heading),
		 * serviceName (The name of the service),
		 * serviceDate (When the service is created),
		 * inTransactionSummaryFile (Indicator of the services presence in the transaction summary file)
		 * Throws: None
		*/
		this.serviceNumber = serviceNumber;
		this.numTickets = numTickets;
		this.destinationNumber = destinationNumber;
		this.serviceName = serviceName;
		this.serviceDate = serviceDate;
		this.inTransactionSummaryFile = inTransactionSummaryFile;
	}
	
	protected void removeTickets(String numTicketsToTakeAway) throws TransactionException {
		/*
		 * Function Flow: function removeTickets : String -> void
		 * Function Name: removeTickets
		 * Functionality: Remove tickets from a service.
		 * Parameters: numTicketsToTakeAway (Number of tickets to remove)
		 * Throws: TransactionException (Raised when service number is not found)
		 * Returns: None
		*/
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
		/*
		 * Function Flow: function addTickets : String -> void
		 * Function Name: addTickets
		 * Functionality: Add tickets to a service.
		 * Parameters: numTicketsToAdd (Number of tickets to add)
		 * Throws: TransactionException (Raised when service number is not found)
		 * Returns: None
		*/
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
	
	protected String getNumTickets() { return this.numTickets; }        // Accessor
	protected String getServiceName() { return this.serviceName; }		// Accessor
}

