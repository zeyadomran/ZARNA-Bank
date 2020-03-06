package application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Zeyad Omran
 */

public class Message {
	
	private String from;
	private String to;
	private String subject;
	private String content;
	private String ts;
	
	/**
	 * Constructor for Messages, initializes a new object of the class
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing the reciever's username
	 * @param sub  -> string representing the subject of the message
	 * @param con -> string representing the content of the message
	 */
	public Message(String fr, String to, String sub, String con) {
		this.from = fr;
		this.to = to;
		this.subject = sub;
		this.content = con;
		this.ts = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
	}
	
	public Message(String fr, String to, String sub, String con, String ts) {
		this.from = fr;
		this.to = to;
		this.subject = sub;
		this.content = con;
		this.ts = ts;
	}
	
	// *************************GETTERS*************************
	
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
	 * Get method for instance subject
	 * 
	 * @return a String representing the message's instance subject
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Get method for instance content
	 * 
	 * @return a String representing the message's instance content
	 */
	public String getContent() {
		return this.content;
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
