import java.util.Scanner;

public class Main {
	
	static Database db = new Database(1);
	static Scanner keyb = new Scanner(System.in);

	public static void main(String[] args) {
		mainMenu(db);
	}

	public static void mainMenu(Database db) {
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
     */
    public static void accountMenu(Account acc, Database db) {
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
                    if(state == 1) System.out.println("Deposit Successful");
                    if(state == 0) System.out.println("\nThe amount must be larger than 0.");
                    break;
                case "3":
                    System.out.println("Please enter the amount you wish to withdraw:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    state = acc.withdraw(amount);
                    if(state == 0) 	System.out.println("Incompatible amount to withdraw.");
                    if(state == 1) 	System.out.println("Withdraw successful.");
                    break;
                case "4":
                	System.out.println("Please enter the username of who this transfer is for:");
                	to = keyb.nextLine().trim();
                	System.out.println("Please enter the amount you wish to transfer:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    state = db.transfer(acc.getUsername(), to, amount);
                    if(state == 1) System.out.println("\nTransfer successfully sent to " + to + ".");
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
                	if(state == 1) System.out.println("\nMessage successfully sent to " + to + ".");
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
    	//Start to code the menu, this method is used for changing some user's informations.
    	String selection;
    	String newUser;//new username that user want to change.
    	String newfN;  //new firstname that user want to change.
    	String newlN;  //new lastname that user want to change.
    	String selectionf;//selection for change firstname.
    	String selectionl;//selection for change lastname.
    	do {
    		System.out.println("\n\nHello!How can I help you today? "+"'"+ acc.getName()+"'");
    		System.out.println("If you want change your Username, press 1 and type Enter.\n");
    		System.out.println("If you want change your firstname, press 2 and type Enter.\n");
    		System.out.println("If you want change your lastname, press 3 and type Enter.\n");
    		System.out.println("If you want to return to the main menu, press 4 and type Enter.\n");
    		selection=keyb.nextLine().trim();
    		System.out.println("\n\n");
    		switch(selection) {
    		case "1":
    			System.out.println("Your current Username is "+"'"+acc.getUsername()+"'");
    			System.out.println("Please enter the new Username you want to change:\n(note:You won't be able to change your new Username if it already used by others)");
    			newUser=keyb.nextLine();
    			while (db.userExists(newUser)) {
    				System.out.println("\nI'm sorry,that Username already exists; How about try another one?");
    				System.out.println("Another one:");
    				newUser=keyb.nextLine();
    			}
    			acc.setUsername(newUser);
    			System.out.println("\nYou have changed your Username successfully,your new Username is: "+acc.getUsername()+"\n");
    			
    		     break;
    		case "2":
    			System.out.println("Your current firstname is "+"'"+acc.getFirstName()+"'");
    			System.out.println("Please enter the new firstname you want to change: ");
    			newfN=keyb.nextLine();
    			System.out.println("\nI'm going to change your firstname from "+"'"+acc.getFirstName()+"'"+"to "+"'"+newfN+"'");
    			System.out.println("\nAre you sure you want to make this change?\nPress 'yes' to confirm.\notherwise, type anything other than 'yes'.");
    			selectionf=keyb.nextLine();
    			if (selectionf.equals("yes")) {
    				acc.setFirstName(newfN);
    				System.out.println("\nYou have changed your firstname successfully,your new firstname is: "+acc.getFirstName()+"\n");
    				break;
    			}
    			else
    				System.out.println("\n\n");
    				break;
    		case "3":
    			System.out.println("Your current lastname is "+"'"+acc.getLastName()+"'");
    			System.out.println("Please enter the new lastname you want to change: ");
    			newlN=keyb.nextLine();
    			System.out.println("\nI'm going to change your lastname from "+"'"+acc.getLastName()+"'"+"to "+"'"+newlN+"'");
    			System.out.println("\nAre you sure you want to make this change?\nPress 'yes' to confirm.\notherwise, type anything other than 'yes'.");
    			selectionl=keyb.nextLine();
    			if (selectionl.equals("yes")) {
    				acc.setLastName(newlN);
    				System.out.println("\nYou have changed your lastname successfully,your new lastname is: "+acc.getLastName()+"\n");
    				break;
    			}
    			else
    				System.out.println("\n\n");
    				break;
    		case "4":
    			System.out.println("--Renturn to main menu--");
    			break;
    		}	
    		}while(!selection.equals("4"));
    	
    	}
    	
    	
    	
    	
    	
    	
    
    // ***********************************

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

	public static void login() {
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

	public static void createAccount(Database db) {
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
		System.out.println("\nAccount Created!");
	}

}
