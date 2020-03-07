package application;

import java.util.ArrayList;

/**
 * @author Angelina Rochon
 *
 */

public class Database {
	
	//private int id;
	private ArrayList<Account> accounts;
	
	/**
	 * Creates a new database
	 * 
	 * @param id -> ID of the database
	 */
	Database() {
		//this.id = id;
		accounts = new ArrayList<Account>();
	}

	/**
	 * Copy Constructor, sets same id as database passed, and adds a copy of
	 * accounts list
	 * 
	 * @param db -> the database to copy
	 */
	Database(Database db) {
		//this.id = db.id;
		this.accounts = copyAccountsList(db.accounts);
	}

	private ArrayList<Account> copyAccountsList(ArrayList<Account> accounts) {
		ArrayList<Account> copy = new ArrayList<Account>();
		for (Account account : accounts) {
			copy.add(new Account(account));
		}
		return copy;
	}
	
	public ArrayList<Account> getAccountsList() {
		ArrayList<Account> copy = new ArrayList<Account>();
		for (Account account : this.accounts) {
			copy.add(new Account(account));
		}
		return copy;
	}
	
	/**
	 * Searches through accounts to find a specified account's location
	 * 
	 * @param userId -> The ID of the user you are looking for
	 * @return The index of the user within accounts, or -1 if the account is not found
	 */
	private int getUserIndex(String username) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getUsername().equals(username)) {
				return i;
			}
		}
		return -1;
	}
	
	public double deposit(double amount, String uN) {
		return this.accounts.get(getUserIndex(uN)).deposit(amount);
	}
	
	public double withdraw(double amount, String uN) {
		return this.accounts.get(getUserIndex(uN)).withdraw(amount);
	}
	
	public double getBalance(String uN) {
		return this.accounts.get(getUserIndex(uN)).getBalance();
	}
	
	public ArrayList<Transaction> getTransactions(String uN) {
		return new ArrayList<Transaction>(accounts.get(getUserIndex(uN)).getLog());
	}
	
	public ArrayList<Message> getMessages(String uN) {
		return new ArrayList<Message>(accounts.get(getUserIndex(uN)).getMessages());
	}
	
	public void editName(String uN, String nN) {
		accounts.get(getUserIndex(uN)).setName(nN);
	}
	
	public void editUsername(String uN, String nN) {
		accounts.get(getUserIndex(uN)).setUsername(nN);
	}

	
	/**
	 * Finds the index of an account within the accounts list
	 * 
	 * @param username -> account to look for
	 * @return whether the user exists or not
	 */
	public boolean userExists(String username) {
		return !(getUserIndex(username) == -1);
	}

	/**
	 * Removes an account within the database
	 * 
	 * @param username -> the username of the account to delete
	 */
	public void removeUser(String username) {
		accounts.remove(getUserIndex(username));
	}

	/**
	 * Appends given account to the end of accounts list
	 * 
	 * @param account -> account to add
	 * @param password of the account being added
	 */
	public void addUser(Account account, String password) {
		Account copy;
		if (account != null) {
			copy = new Account(account);
			copy.setPassword(password);
			accounts.add(copy);
		}
	}

	/**
	 * Adds a message to a given account
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing reciever's username
	 * @param amount  -> double representing the amount of the transfer
	 * 
	 * @return 1 -> success
	 * @return 0 -> withdraw failed
	 * @return -1 -> amount less than 0
	 * @return -2 -> user does not exist
	 * @return -3 -> sending transfer to yourself
	 */
	public void transfer(String fr, String to, double amount) {
		accounts.get(getUserIndex(fr)).withdraw(amount, to);
		accounts.get(getUserIndex(to)).deposit(amount, fr);
	}

	/**
	 * Checks whether the username is linked to an account in the database, and that
	 * the given password matches the account's password
	 * 
	 * @param username -> the account to login to
	 * @param password -> the account to login to
	 * @return whether access is granted to an account
	 */
	public Account login(String username, String password) {
		if (!userExists(username))
			return null;
		if (accounts.get(getUserIndex(username)).passwordMatch(password)) {
			return this.accounts.get(getUserIndex(username));
		} else {
			return null;
		}
	}

	/**
	 * Adds a message to a given account
	 * 
	 * @param fr  -> string representing the sender's username
	 * @param to  -> string representing reciever's username
	 * @param sub  -> string representing the subject of the message
	 * @param con -> string representing the content of the message
	 * 
	 * @return returns 1 if message is successful, 0 if user is sending message to himself and -1 if the reciever does not exist.
	 */
	public void sendMessage(String fr, String to, String sub, String con) {
		accounts.get(getUserIndex(to)).addMessage(fr, to, sub, con);
	}
}
