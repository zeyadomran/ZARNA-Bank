import java.sql.Timestamp;

/**
 * @author Zeyad Omran
 */

public class Message {
	
	private String from;
	private String to;
	private String subject;
	private String content;
	private Timestamp ts;
	
	/**
	 * Constructor for Messages, initializes a new object of the class
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing reciever's username
	 * @param sub  -> string representingthe subject of the message
	 * @param con -> string representing the content of the message
	 */
	
	public Message(String fr, String to, String sub, String con) {
		this.from = fr;
		this.to = to;
		this.subject = sub;
		this.content = con;
		this.ts = new Timestamp(System.currentTimeMillis());
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
		return this.ts.toString();
	}
	
}
