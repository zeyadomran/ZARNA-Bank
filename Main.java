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
			selection = keyb.nextLine();
			switch (selection) {
			case "1":
				login();
				break;
			case "2":
				createAccount(db);
				break;
			}
			System.out.println("\n\n\n\n");
		} while (selection != "3");
	}

	/**
     * Account Menu
     * @param acc    ->    object of type Account
     * @param db    ->    object of type Database
     */
    public static void accountMenu(Account acc, Database db) {
        String selection;
        double amount;
        do {
            System.out.println("Hello " + acc.getName());
            System.out.println("\t1. Check Balance\n\t2. Deposit\n\t3. Withdraw\n\t4. Transfer\n\t5. Messages\n\t6. Log out");
            System.out.print("Please make a selection > ");
            selection = keyb.nextLine(); 
            System.out.println("\n");
            switch(selection) {
                case "1":
                    System.out.println("Your current account Balance is: $" + String.valueOf(acc.getBalance()));
                    break;
                case "2":
                    System.out.println("Please enter the amount you wish to deposit:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    acc.deposit(amount);
                    System.out.println("Deposit Successful");
                    break;
                case "3":
                    System.out.println("Please enter the amount you wish to withdraw:");
                    amount = getAmount();
                    keyb.nextLine(); //Clears Scanner
                    acc.withdraw(amount);
                    break;
                case "4":
                    //Transfer
                    break;
                case "5":
                    acc.displayMessages();
                    break;
                case "6":
                    System.out.println("--Logging Out--");
                    break;

            }

        System.out.println("\n\n");
        } while (!selection.equals("6"));

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

	public static void login() {
		Account a = null;
		System.out.println("\nPlease enter your username");
		String uName = keyb.nextLine();
		System.out.println("\nPlease enter your password");
		String pswd = keyb.nextLine();
		a = db.login(uName, pswd);
		if (a == null)
			System.out.println("\nLogin failed");
		else {
			System.out.println("\nLogin Successful!");
			accountMenu(a, db);
		}

	}

	public static void createAccount(Database db) {
		boolean valid;
		String uName, fName, lName, pswd, pswd2;
		do {
			System.out.println("\nPlease create a username");
			uName = keyb.nextLine();
			if (db.userExists(uName))
				System.out.println("\nUsername already exists; please try again");
		} while (db.userExists(uName));
		System.out.println("\nPlease enter your first name");
		fName = keyb.nextLine();
		System.out.println("\nPlease enter you last name");
		lName = keyb.nextLine();
		do {
			System.out.println("\nPlease create a password");
			pswd = keyb.nextLine();
			System.out.println("\nPlease re-type your password");
			pswd2 = keyb.nextLine();
			if (!pswd.equals(pswd2))
				System.out.println("\nPasswords did not match, please try again");
		} while (!pswd.equals(pswd2));
		Account a = new Account(uName, fName, lName, pswd);
		db.addUser(a, pswd);
		System.out.println("\nAccount Created!");
	}

}
