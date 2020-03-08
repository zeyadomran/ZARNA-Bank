package application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Zeyad Omran
 */

public class Message extends MultiUserInteraction {
	
	private String subject;
	private String content;
	
	/**
	 * Constructor for Messages, initializes a new object of the class
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing the reciever's username
	 * @param sub  -> string representing the subject of the message
	 * @param con -> string representing the content of the message
	 */
	public Message(String fr, String to, String sub, String con) {
		super(fr, to);
		this.subject = sub;
		this.content = con;
	}
	
	public Message(String fr, String to, String sub, String con, String ts) {
		super(fr, to, ts);
		this.subject = sub;
		this.content = con;
	}
	
	// *************************GETTERS*************************
	
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
	
}
