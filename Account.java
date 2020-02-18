import java.util.ArrayList;

/**
 * @author Austin Shawaga
 *
 */

public class Account {
	private String username;
	private String firstName;
	private String lastName;
	private String password = null;
	private double balance;
	private ArrayList<Message> messages;

	/**
	 * Constructor for Account, initializes a new object of the class
	 * 
	 * @param uN  -> string representing username
	 * @param fN  -> string representing first name
	 * @param lN  -> string representing last name
	 * @param pwd -> string representing password
	 * 
	 * 
	 */
	public Account(String uN, String fN, String lN, String pwd) {
		this.setUsername(uN);
		this.setName(fN, lN);
		this.setPassword(pwd);
		this.balance = 0.0;
		this.messages = new ArrayList<Message>();
	}

	/**
	 * Copy Constructor
	 *
	 * @param copyFrom -> the object of Account being copied
	 */
	public Account(Account copyFrom) {
		this.setUsername(copyFrom.getUsername());
		this.setName(copyFrom.getFirstName(), copyFrom.getLastName());
		this.password = null;
		this.setBalance(copyFrom.getBalance());
		this.messages = copyFrom.getMessages();

	}

	// *************************SETTERS*************************

	/**
	 * Sets username of an object with argument passed
	 * 
	 * @param uN -> String representing username
	 * 
	 */
	public void setUsername(String uN) {
		this.username = uN;
	}

	/**
	 * Sets first and last name of an object with the arguments passed
	 * 
	 * @param fN -> String representing a user's first name,
	 * @param lN -> String representing a user's last name
	 */
	public void setName(String fN, String lN) {
		this.firstName = fN;
		this.lastName = lN;
	}

	/**
	 * Sets an objects password Error checking within, only changes password if the
	 * current objects password is null. That way passwords can't be publicly be
	 * overridden
	 * 
	 * @param pwd -> String representing the password being set, only works if the
	 *            current objects password == null.
	 */
	public void setPassword(String pwd) {

		// ensures that only a new password can be set, and not overridden
		if (this.password == null) {
			this.password = pwd;
		}
	}

	/**
	 * Sets object's balance, private. Error check within, argument can't be
	 * negative. If an error is detected, balance of the object is set to 0
	 * 
	 * @param bal -> the amount that the object's balance should be set to
	 */
	private void setBalance(double bal) {
		if (bal >= 0.0) {
			this.balance = bal;
		} else {
			this.balance = 0.0;
		}
	}

	// *************************GETTERS*************************

	/**
	 * Get method for instance username
	 * 
	 * @return a String representing the object's instance username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get method for instance balance
	 * 
	 * @return a double representing the object's instance balance
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * Get method for instance firstName
	 * 
	 * @return a String representing the object's instance firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Get method for instance lastName
	 * 
	 * @return a String representing the object's instance lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Method used to return formatted first + last name as one String
	 * 
	 * @return a String representing the concatenated name (First + Last)
	 */
	public String getName() {
		return this.getFirstName() + " " + this.getLastName();
	}

	/**
	 * Get method for ArrayList messages, encapsulation within to prevent privacy
	 * leaks
	 * 
	 * @return a copy of the object's instance 'messages', completely encapsulated.
	 */
	public ArrayList<Message> getMessages() {
		return new ArrayList<Message>(this.messages);
	}

	// *************************Other_Methods*************************

	/**
	 * Function used to deposit a value into the object's balance. Error checking
	 * within to ensure the argument is greater then zero. If an argument is
	 * invalid, nothing is added to the object's balance
	 * 
	 * @param amount -> a double representing the amount to deposit within the
	 *               object's balance
	 */
	public void deposit(double amount) {
		if (amount > 0) {
			this.balance += amount;
		}
	}

	/**
	 * Function used to subtract a value from the object's balance. Error checking
	 * within to ensure the argument is able to be subtracted from the balance. If
	 * the argument is invalid and error message is displayed to console, and the
	 * balance remains unchanged.
	 * 
	 * @param amount -> a double representing the amount to withdraw from an
	 *               object's balance
	 */
	public void withdraw(double amount) {
		if (amount > this.getBalance()) {
			System.out.println("INCOMPATIBLE AMOUNT TO WITHDRAW");
		} else {
			this.balance -= amount;
		}
	}

	/**
	 * Function used to access if a String matches the object's password
	 * 
	 * @param guess -> A String to be compared with the object's password
	 * @return A Boolean value. True: if the argument matches the object's password.
	 *         False: if the argument does not match the object's password
	 */
	public boolean passwordMatch(String guess) {
		return (this.password.equals(guess));
	}

	/**
	 * Function allowing for a String to be deposited within an object's message
	 * ArrayList
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing reciever's username
	 * @param sub  -> string representingthe subject of the message
	 * @param con -> string representing the content of the message
	 */
	public void addMessage(String fr, String to, String sub, String con ) {
		this.messages.add(new Message(fr, to, sub, con));
	}

	
	public void displayMessages() {
		ArrayList<Message> m = this.getMessages();

		System.out.println("****************************START_OF_MESSAGES****************************");

		if (m.size() == 0) {
			System.out.println("You have no messages!");
		} else {
			for (int i = 0; i < m.size(); i++) {
				System.out.println(String.valueOf(i + 1) + ") \t");
				System.out.println("From: \t" + m.get(i).getSender() + "\nSubject: \t" + m.get(i).getSubject() + "\nContent: \t" + m.get(i).getContent() + "\nTime: \t" + m.get(i).getTimestamp() + "\n");

			}
		}

		System.out.println("*****************************END_OF_MESSAGES*****************************");
	}

}
