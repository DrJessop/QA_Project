package frontend;

import java.util.HashMap;

public abstract class User {
	
	private HashMap<String, ServiceDetails> map;    // Stores the details of the available service.
	
	public void sellTickets(String serviceNumber, String numTickets) {
		
		/*
		 * Function Flow: function sellTickets : String -> String -> null
		 * Function Name: sellTickets
		 * Functionality: Allow the user to sell tickets from a specified service.
		 * Parameters: serviceNumber (The identifier of a service),
		 * numTickets (The number of tickets that are required to be sold)
		 * Throws: ---------------------
		 * Returns: --------------------
		*/
		
		try {
			ServiceDetails ts = this.map.get(serviceNumber);
			ts.removeTickets(numTickets);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void cancelTickets();
		/*
		 * Function Flow: function cancelTickets : ---------------------
		 * Function Name: cancelTickets
		 * Functionality: Allow the user to cancel tickets from a specified service.
		 * 
		 * Throws: ---------------------
		 * Returns: --------------------
		*/
	public abstract void changeTickets();
		/*
		 * * Function Flow: function changeTickets : ---------------------
		 * Function Name: changeTickets
		 * Functionality: Allow the user to change tickets from a specified service.
		 * 
		 * Throws: ---------------------
		 * Returns: --------------------
		*/
	
	public User(String fileName) {
		
		/*
		 * Class Name: User
		 * Class Functionality: The class represents the set of possible actions that can be performed by the user.
		 * Class Constructors: 
		*/
		
		this.map = new HashMap<>();
		/*
		 * Code here should parse the transaction summary file to come up easily accessible TransactionSummary objects,
		 * should set the flag inTransactionSummaryFile to true since these transactions belong to older sessions
		 */
		
	}

}
