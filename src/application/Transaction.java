package application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Zeyad Omran
 */

public class Transaction {
	
	private String type;
	private String note;
	private double amount;
	private String ts;
	
	/**
	 * Constructor for Transaction, initializes a new object of the class
	 * 
	 * @param type  -> string representing the type of the transaction
	 * @param note  -> string representing the transaction's description
	 * @param amount  -> double representing the amount
	 */
	public Transaction(String type, String note, double amount) {
		this.type = type;
		this.note = note;
		this.amount = amount;
		this.ts = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
	}
	
	public Transaction(String type, String note, double amount, String ts) {
		this.type = type;
		this.note = note;
		this.amount = amount;
		this.ts =ts;
	}
	
	// *************************GETTERS*************************
	
		/**
		 * Get method for instance type
		 * 
		 * @return a String representing the type of the transaction
		 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Get method for instance note
	 * 
	 * @return a String representing the description of the transaction
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * Get method for instance amount
	 * 
	 * @return a double representing the amount of the transaction
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Get method for instance timestamp
	 * 
	 * @return a String representing the timestamp of the transaction
	 */
	
	public String getTimestamp() {
		return new String(this.ts);
	}
}
