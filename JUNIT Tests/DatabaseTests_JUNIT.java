package application.backend;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class DatabaseTests_JUNIT {
	Database db;
	
	@Before
	public void dbSetup() {
		db = new Database();
	}
	
	//Initialization Test
	@Test
	public void setUpTest() {
		int expected = 0;
		int actual = db.getAccountsList().size();
		assertEquals("Initialization Test, ArrayList Size:",expected,actual);
	}
	
	//User Doesn't Exist, Empty Database
	@Test
	public void emptyDBUserExistTest() {
		boolean expected = false;
		boolean actual = db.userExists("A Random Username");
		assertEquals("Empty Database User Doesn't Exist Test:",expected,actual); //boolean test
		assertEquals("Empty Database User index",-1,db.getUserIndex("A Random Username")); //index test, expects -1
	}
	
	//User Doesn't Exist, Database is not empty
	@Test
	public void userDoesntExistTest() {
		Account acc = new Account("username","name","password");
		db.addUser(acc, "password");
		boolean expected = false;
		boolean actual = db.userExists("A Random Username");
		assertEquals("Testing Database Empty?:",false,db.getAccountsList().isEmpty()); //Makes sure user was added correctly
		assertEquals("User Doesn't Exist Test:",expected,actual); //boolean test
		assertEquals("User Doesn't Exist Index Test:",-1,db.getUserIndex("A Random Username")); //index test, expects -1
	}
	
	//Add User Test
	@Test
	public void addUserTest() {
		Account acc = new Account("User","Name","Pwrd");
		db.addUser(acc, "Pwrd");
		int index = db.getUserIndex("User");
		assertEquals("Add User Array Size Test:",1,db.getAccountsList().size()); //Array Size
		assertEquals("Add User, User Exists Test:",true,db.userExists("User")); //User Exists
		assertEquals("Add User, User Index Test:",0,index); //User index
		
		//Same Account?
		assertEquals("Add User, Account Name Test:","Name",db.getAccountsList().get(index).getName());
		assertEquals("Add User, Account Username Test:","User",db.getAccountsList().get(index).getUsername());
		assertEquals("Add User, Account Password Test:","Pwrd",db.getAccountsList().get(index).getPassword());
		
	}
	
	//Remove User Test
	@Test
	public void removeUserTest() {
		
		//Add the User to an empty array
		Account acc = new Account("username","name","password"); 
		db.addUser(acc, "password"); //Adds User
		assertEquals("Remove User, Array Size Test:",1,db.getAccountsList().size()); //Array Size, Ensures user was added
		assertEquals("Remove User, User Exists Test:",true,db.userExists("username")); //User Exists
		
		//Remove the User
		db.removeUser("username"); //Removes User
		assertEquals("Remove User, Array Size Test:",0,db.getAccountsList().size());
		assertEquals("Add User, User No longer Exists Test:",false,db.userExists("username")); //User No Longer Exists
		
	}
	
	//Copy List
	@Test
	public void copyTest() {
		Account acc0 = new Account("0","0","0"), acc1 = new Account("1","1","1"), acc2 = new Account("2","2","2");
		db.addUser(acc0, "0");
		db.addUser(acc1, "1");
		db.addUser(acc2, "2");
		assertEquals("Checking Array Size:",3,db.getAccountsList().size()); //Ensures all users were added correctly
		
		Database newDB = new Database(db); //Copies db to a new Database
		
		assertEquals("Coparing copied Database Size:",3,newDB.getAccountsList().size()); //Makes sure ArrayList size is identical
		
		//Checks all three Accounts' usernames and compares them
		assertEquals("Checking index 0:",db.getAccountsList().get(0).getUsername(),newDB.getAccountsList().get(0).getUsername());
		assertEquals("Checking index 1:",db.getAccountsList().get(1).getUsername(),newDB.getAccountsList().get(1).getUsername());
		assertEquals("Checking index 2:",db.getAccountsList().get(2).getUsername(),newDB.getAccountsList().get(2).getUsername());
	}
	
	//Transfer Test
	@Test
	public void transferTest() {
		//Creates two accounts and sets one balance to 500 and the other to 1000
		Account acc0 = new Account("0","0","0"), acc1 = new Account("1","1","1");
		acc0.setBalance(500);
		acc1.setBalance(1000);
		
		//Adds Users to the Database
		db.addUser(acc0, "0");
		db.addUser(acc1, "1");
		assertEquals("Checking Array Size:",2,db.getAccountsList().size()); //Ensures all users were added correctly
		
		//Transfer 250 Funds acc0 -> acc1
		db.transfer("0", "1", 250);
		
		assertEquals("Post Transfer, Checking First Account's Funds:",500-250,db.getAccountsList().get(0).getBalance(),0.000000000001); //Checks acc0
		assertEquals("Post Transfer, Checking Second Account's Funds:",1000+250,db.getAccountsList().get(1).getBalance(),0.000000000001); //Checks acc1
		
		
	}
	
	//Message Test
	@Test
	public void dbMessageTest() {
		Account acc0 = new Account("0","0","0"), acc1 = new Account("1","1","1"); //Two Accounts used to test the message method
		
		//Adds two users to database
		db.addUser(acc0, "0");
		db.addUser(acc1, "1");
		assertEquals("Checking Array Size:",2,db.getAccountsList().size()); //Ensures all users were added correctly
		assertEquals("Checking Reciever Inbox Size",0,db.getAccountsList().get(1).getMessages().size()); //Makes sure second user's inbox is empty
		
		//Message acc0 -> acc1
		db.sendMessage("0", "1", "SuBjEcT", "This is the main content of the message");
		
		assertEquals("Checking Reciever Inbox Size",1,db.getAccountsList().get(1).getMessages().size()); //Makes sure second user's inbox received a message
		
		Message message = db.getAccountsList().get(1).getMessages().get(0); //Variable used to check message contents
		
		//Checks message Contents
		assertEquals("Checking Message Contents, From","0",message.getSender());
		assertEquals("Checking Message Contents, To","1",message.getReciever());
		assertEquals("Checking Message Contents, Subject","SuBjEcT",message.getSubject());
		assertEquals("Checking Message Contents, Content","This is the main content of the message",message.getContent());
	}

	
	
}
