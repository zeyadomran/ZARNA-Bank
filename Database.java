import java.util.ArrayList;

public class Database {
	private int id;
	private ArrayList<Account> accounts;

	/**
	 * Creates a new database
	 * 
	 * @param id ID of the database
	 */
	Database(int id) {
		this.id = id;
		accounts = new ArrayList<Account>();
	}

	/**
	 * Copy Constructor, sets same id as database passed, and adds a copy of
	 * accounts list
	 * 
	 * @param db the database to copy
	 */
	Database(Database db) {
		this.id = db.id;
		this.accounts = copyAccountsList(db.accounts);
	}

	private ArrayList<Account> copyAccountsList(ArrayList<Account> accounts) {
		ArrayList<Account> copy = new ArrayList<Account>();
		for (Account account : accounts) {
			copy.add(new Account(account));
		}
		return copy;
	}

	/**
	 * Searches through accounts to find a specified account's location
	 * 
	 * @param userId The ID of the user you are looking for
	 * @return The index of the user within accounts, or -1 if the account is not
	 *         found
	 */
	private int getUserIndex(String username) {

		for (int i = 0; i < accounts.size(); i++)
			if (accounts.get(i).getUsername().equals(username))
				return i;

		return -1;
	}

	/**
	 * Finds the index of an account within the accounts list
	 * 
	 * @param username account to look for
	 * @return
	 */
	public boolean userExists(String username) {
		return !(getUserIndex(username) == -1);
	}

	/**
	 * Removes an account within the database
	 * 
	 * @param username the username of the account to delete
	 */
	public void removeUser(String username) {
		accounts.remove(getUserIndex(username));
	}

	/**
	 * Appends given account to the end of accounts list
	 * 
	 * @param account  account to add
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

	public void transfer() {
		
	}

	/**
	 * Checks whether the username is linked to an account in the database, and that
	 * the given password matches the account's password
	 * 
	 * @param username of the account to login to
	 * @param password of the account to login to
	 * @return whether access is granted to an account
	 */
	public Account login(String username, String password) {
		if (!userExists(username))
			return null;
		if (accounts.get(getUserIndex(username)).passwordMatch(password)) {
			return accounts.get(getUserIndex(username));
		} else {
			return null;
		}
	}

	/**
	 * Adds a message to a given account
	 * 
	 * @param username of the account to send message to
	 * @param msg      message to send
	 */
	public void sendMessage(String username, String msg) {
		accounts.get(getUserIndex(username)).addMessage(msg + accounts.get(getUserIndex(username)).getName());
	}

}
