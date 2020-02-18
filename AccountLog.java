import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Zeyad Omran
 */

public class AccountLog {
	
	private String type;
	private String note;
	private double amount;
	private Timestamp ts;
	
	/**
	 * Constructor for AccountLog, initializes a new object of the class
	 * 
	 * @param type  -> string representing the type of the transaction
	 * @param note  -> string representing the transaction's description
	 * @param amount  -> double representing the amount
	 */
	public AccountLog(String type, String note, double amount) {
		this.type = type;
		this.note = note;
		this.amount = amount;
		this.ts = new Timestamp(System.currentTimeMillis());
	}
	
	// *************************GETTERS*************************
	
		/**
		 * Get method for instance from
		 * 
		 * @return a String representing the type of the transaction
		 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Get method for instance from
	 * 
	 * @return a String representing the description of the transaction
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * Get method for instance from
	 * 
	 * @return a double representing the amount of the transaction
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Get method for instance from
	 * 
	 * @return a String representing the timestamp of the transaction
	 */
	public String getTimestamp() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.ts);
	}
}
