import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	
	static Database db;
	static Scanner keyb = new Scanner(System.in);

	public static void main(String[] args) throws IOException, ParseException {
		db = loadDb();
		mainMenu(db);
	}

	public static void mainMenu(Database db) throws IOException {
		String selection;
		do {
			System.out.println("Please enter a number\n\t1. Login\n\t2. Create Account\n\t3. Exit");
			selection = keyb.nextLine().trim();
			switch (selection) {
			case "1":
				login();
				break;
			case "2":
				createAccount(db);
				break;
			}
			System.out.println("\n\n\n\n");
		} while (!selection.equals("3"));
	}

	/**
     * Account Menu
     * @param acc -> object of type Account
     * @param db -> object of type Database
	 * @throws IOException 
     */
    public static void accountMenu(Account acc, Database db) throws IOException {
        String selection;
        double amount;
        String to;
        String subject;
        String content;
        int state;
        do {
            System.out.println("Hello " + acc.getName());
            System.out.println("\t1. Check Balance\n\t2. Deposit\n\t3. Withdraw\n\t4. Transfer\n\t5. Display Messages\n\t6. Send Message\n\t7. Display Log\n\t8. Edit Info\n\t9. Log out");
            System.out.print("Please make a selection > ");
            selection = keyb.nextLine().trim(); 
            System.out.println("\n");
            switch(selection) {
                case "1":
                    System.out.println("Your current account Balance is: $" + String.valueOf(acc.getBalance()));
                    break;
                case "2":
                    System.out.println("Please enter the amount you wish to deposit:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    state = acc.deposit(amount);
                    if(state == 1) {
                    	System.out.println("Deposit Successful");
                    	updateDb(db);
                    }
                    if(state == 0) System.out.println("\nThe amount must be larger than 0.");
                    break;
                case "3":
                    System.out.println("Please enter the amount you wish to withdraw:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    state = acc.withdraw(amount);
                    if(state == 0) 	System.out.println("Incompatible amount to withdraw.");
                    if(state == 1) 	{
                    	System.out.println("Withdraw successful.");
                    	updateDb(db);
                    }
                    break;
                case "4":
                	System.out.println("Please enter the username of who this transfer is for:");
                	to = keyb.nextLine().trim();
                	System.out.println("Please enter the amount you wish to transfer:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    state = db.transfer(acc.getUsername(), to, amount);
                    if(state == 1) {
                    	System.out.println("\nTransfer successfully sent to " + to + ".");
                    	updateDb(db);
                    }
                    if(state == 0) System.out.println("\nBalance is lower than the amount you wish to trasnfer to " + to + ".");
                    if(state == -1) System.out.println("\nThe amount must be larger than 0.");
                    if(state == -2) System.out.println("\nTransfer was unable to be sent to " + to + ", because the username does not exist.");
                	if(state == -3) System.out.println("\nYou cannot send a transfer to yourself!");
                    break;
                case "5":
                    acc.displayMessages();
                    break;
                case "6":
                	System.out.println("Please enter the username of who this messages is for:");
                	to = keyb.nextLine().trim();
                	System.out.println("\nPlease enter the subject of the message:");
                	subject = keyb.nextLine().trim();
                	System.out.println("\nPlease enter the content of the message:");
                	content = keyb.nextLine().trim();
                	state = db.sendMessage(acc.getUsername(), to, subject, content);
                	if(state == 1) {
                		System.out.println("\nMessage successfully sent to " + to + ".");
                		updateDb(db);
                	}
                	if(state == 0) System.out.println("\nYou cannot send a message to yourself!");
                	if(state == -1) System.out.println("\nMessage was unable to be sent to " + to + ", because the username does not exist.");
                	break;
                case "7":
                    acc.displayLog();
                    break;
                case "8":
                    editMenu(acc, db);
                    break;
                case "9":
                    System.out.println("--Logging Out--");
                    break;
            }
        System.out.println("\n\n");
        } while (!selection.equals("9"));

    }
	
    // ***********************************
    /**
     * Edit info menu Menu
     * @param acc -> object of type Account
     * @param db -> object of type Database
     */
    public static void editMenu(Account acc, Database db) {
    	// Code Here
    }
    // ***********************************


    /**
     * updateDB -> This method converts the current Database into a json format and saves it into a json file
	 * We are using the simple.JSON library to complete this task
     * @param db -> object of type Database
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateDb(Database db) throws IOException {
    	ArrayList <Account> acc = db.getAccountsList(); // get list of accounts
    	JSONObject jo = new JSONObject();
        JSONArray accList = new JSONArray(); // JSON array that holds all the accounts
    	for(int i = 0; i < acc.size(); i++) { // loop through accounts
        	Map accInfo = new LinkedHashMap(7); // save this instance's information
    		Account cAcc = acc.get(i);
    		accInfo.put("username", cAcc.getUsername()); // get information and save it in JSON string
    		accInfo.put("firstName", cAcc.getFirstName());
    		accInfo.put("lastName", cAcc.getLastName());
    		accInfo.put("password", cAcc.getPassword());
    		accInfo.put("balance", cAcc.getBalance());
    		JSONArray msgList = new JSONArray(); // JSON array that holds all the messages for this instance account
    		Map msgInfo = new LinkedHashMap(5);
    		ArrayList <Message> msgs = cAcc.getMessages();
    		for(int j = 0; j < msgs.size(); j++) { // loop through messages
    			Message msg = msgs.get(j);
    			msgInfo.put("from", msg.getSender());
    			msgInfo.put("to", msg.getReciever());
    			msgInfo.put("subject", msg.getSubject());
    			msgInfo.put("content", msg.getContent());
    			msgInfo.put("ts", msg.getTimestamp());
    			msgList.add(msgInfo);
    		}
    		accInfo.put("messages", msgList);
    		JSONArray tranList = new JSONArray(); // JSON array that holds all the transactions for this instance account
    		Map tranInfo = new LinkedHashMap(4);
    		ArrayList <Transaction> trans = cAcc.getLog();
    		for(int j = 0; j < trans.size(); j++) { // loop through transactions
    			Transaction tran = trans.get(j);
    			tranInfo.put("type", tran.getType());
    			tranInfo.put("note", tran.getNote());
    			tranInfo.put("amount", tran.getAmount());
    			tranInfo.put("ts", tran.getTimestamp());
    			tranList.add(tranInfo);
    		}
    		accInfo.put("log", tranList);
        	accList.add(accInfo);
    	}
    	jo.put("accounts", accList);
		FileWriter fw=new FileWriter("Database.json"); // create Database.json file and allows us to overwrite it
    	PrintWriter pw = new PrintWriter(fw); // prints text to file
        pw.write(jo.toJSONString()); // print JSON string to file
        pw.flush(); 
        pw.close(); 
    }
    
    /**
     * loadDB -> This method reads the JSON string from the local Database.json file and converts the JSON string into values that will load into the Database class
	 * We are using the simple.JSON library and org.JSON library to complete this task
     */
    @SuppressWarnings({ "unchecked" })
	public static Database loadDb() throws FileNotFoundException, IOException, ParseException {
    	File tmpDir = new File("Database.json");
    	boolean exists = tmpDir.exists();
    	if(!exists) { // checks if the file does not exist to prevent error and creates it
    		FileWriter fw=new FileWriter("Database.json"); // create Database.json file and allows us to overwrite it
    		PrintWriter pw = new PrintWriter(fw); // prints text to file
    		pw.write("{\"accounts\":[]}"); // print JSON string to file
            pw.flush(); 
            pw.close(); 
    	}
    	Object obj = new JSONParser().parse(new FileReader("Database.json")); // load JSON string into object
    	JSONObject jo = (JSONObject) obj; // create JSON object from object
    	Database db = new Database(); // create DB instance
    	JSONArray accList = (JSONArray) jo.get("accounts"); // get accounts from db
    	Iterator<JSONObject> itr1 = accList.iterator(); // creates an iterator to go through the object
    	while(itr1.hasNext()) { // loops through all accounts
    		JSONObject acc = itr1.next();
    		ArrayList <Message> msgs = new ArrayList<Message>();
    		ArrayList <Transaction> trans = new ArrayList<Transaction>();
    		Account nAcc = new Account((String) acc.get("username"), (String) acc.get("firstName"), (String) acc.get("lastName"), (String) acc.get("password")); // create new account
    		JSONArray msgList = (JSONArray) acc.get("messages");
    		Iterator<JSONObject> itr2 = msgList.iterator();
    		while(itr2.hasNext()) { // loop through messages
    			JSONObject msg = itr2.next();
    			Message nMsg = new Message((String) msg.get("from"), (String) msg.get("to"), (String) msg.get("subject"), (String) msg.get("content"), (String) msg.get("ts")); // creates new message
    			msgs.add(nMsg);
    		}
    		nAcc.setMessages(msgs);
    		JSONArray tranList = (JSONArray) acc.get("log");
    		Iterator<JSONObject> itr3 = tranList.iterator();
    		while(itr3.hasNext()) { // loop through transactions
    			JSONObject tran = itr3.next();
    			Transaction nTran = new Transaction((String) tran.get("type"), (String) tran.get("note"), (double) tran.get("amount"), (String) tran.get("ts")); // create new transaction
    			trans.add(nTran);
    		}
    		nAcc.setTransactions(trans);
    		nAcc.setBalance((double) acc.get("balance"));
    		db.addUser(nAcc, (String) acc.get("password"));
    	}
    	return db; // return db, will always return a valid db
    }
    
    /**
     * Function used to get a double amount, error checking and exception handling within
     * @return a valid double amount entered by user
     */
    public static double getAmount() {
        double d;
        while(true) {
        try {
        System.out.print("$");
        d = keyb.nextDouble();
        if(d >= 0.0) {
            return d;
        }
        else {
            System.out.println("Amount can't be negative");
        }
        }
        catch (Exception e) {
            keyb.nextLine(); //clears scanner
            System.out.println("INVALID INPUT");
        }
        }//Loops until something is returned
    }

	public static void login() throws IOException {
		Account a = null;
		System.out.println("\nPlease enter your username");
		String uName = keyb.nextLine().trim();
		System.out.println("\nPlease enter your password");
		String pswd = keyb.nextLine().trim();
		a = db.login(uName, pswd);
		if (a == null)
			System.out.println("\nLogin failed");
		else {
			System.out.println("\nLogin Successful!");
			accountMenu(a, db);
		}

	}

	public static void createAccount(Database db) throws IOException {
		String uName, fName, lName, pswd, pswd2;
		do {
			System.out.println("\nPlease create a username");
			uName = keyb.nextLine().trim();
			if (db.userExists(uName))
				System.out.println("\nUsername already exists; please try again");
		} while (db.userExists(uName));
		System.out.println("\nPlease enter your first name");
		fName = keyb.nextLine().trim();
		System.out.println("\nPlease enter you last name");
		lName = keyb.nextLine().trim();
		do {
			System.out.println("\nPlease create a password");
			pswd = keyb.nextLine().trim();
			System.out.println("\nPlease re-type your password");
			pswd2 = keyb.nextLine().trim();
			if (!pswd.equals(pswd2))
				System.out.println("\nPasswords did not match, please try again");
		} while (!pswd.equals(pswd2));
		Account a = new Account(uName, fName, lName, pswd);
		db.addUser(a, pswd);
		updateDb(db);
		System.out.println("\nAccount Created!");
	}

}
