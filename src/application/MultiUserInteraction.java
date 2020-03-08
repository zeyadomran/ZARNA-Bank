package application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MultiUserInteraction {
	
	private String from;
	private String to;
	private String ts;
	
	/**
	 * Constructor for Messages, initializes a new object of the class
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing the reciever's username
	 * @param sub  -> string representing the subject of the message
	 * @param con -> string representing the content of the message
	 */
	public MultiUserInteraction(String fr, String to) {
		this.from = fr;
		this.to = to;
		this.ts = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
	}
	
	public MultiUserInteraction(String fr, String to, String ts) {
		this.from = fr;
		this.to = to;
		this.ts = ts;
	}
	
	/**
	 * Get method for instance from
	 * 
	 * @return a String representing the message's instance sender's username
	 */
	public String getSender() {
		return this.from;
	}
	
	/**
	 * Get method for instance to
	 * 
	 * @return a String representing the message's instance reciever's username
	 */
	public String getReciever() {
		return this.to;
	}
	
	/**
	 * Get method for instance ts
	 * 
	 * @return a String representing the message's instance timestamp
	 */
	public String getTimestamp() {
		return new String(this.ts);
	}
}
