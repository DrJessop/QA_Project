package frontend;

import java.util.HashMap;

public abstract class User {
	
	private char state;
	private HashMap<String, ServiceDetails> map;
	
	public void sellTickets(String serviceNumber, String numTickets) {
		try {
			ServiceDetails ts = this.map.get(serviceNumber);
			ts.removeTickets(numTickets);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void cancelTickets();
	public abstract void changeTickets();
	
	public User(String fileName) {
		this.map = new HashMap<>();
		/*
		 * Code here should parse the transaction summary file to come up easily accessible TransactionSummary objects,
		 * should set the flag inTransactionSummaryFile to true since these transactions belong to older sessions
		 */
		
	}

}
