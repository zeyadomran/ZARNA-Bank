package application.backend;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;

public class ApplicationTests_JUNIT {
	Account acc; //Account used as default for tests
	
	//Sets up the default account before every test
	@Before
	public void setUpAccount() {
		acc = new Account("username","First Last","password");
	}
	
	//Constructor Test
	@Test
	public void testConstructor() {
		String expectedUsername = "username";
		String expectedName = "First Last";
		String expectedPassword = "password";
		double expectedBalance = 0.0;
		
		assertEquals("Username constructor test:",expectedUsername,acc.getUsername());
		assertEquals("Name constructor test:",expectedName,acc.getName());
		assertEquals("Password constructor test:",expectedPassword,acc.getPassword());
		assertEquals("Balance constructor test:",expectedBalance,acc.getBalance(),0.0000000001);
	}
	
	
	//Copy Constructor Test
	@Test
	public void testCopyConstructor() {
		Account newAcc = new Account(acc);
		assertEquals("copyConstructor Name Test:",acc.getName(),newAcc.getName());
		assertEquals("copyConstructor Username Test:",acc.getUsername(),newAcc.getUsername());
		assertEquals("copyConstructor Password Test:",acc.getPassword(),newAcc.getPassword());
		assertEquals("copyConstructor Balance Test:",acc.getBalance(),newAcc.getBalance(),0.0000000001);
	}
	
	//setName test
	@Test
	public void setNameTest() {
		acc.setName("New Name");
		String expected = "New Name";
		String actual = acc.getName();
		assertEquals("Set Name Test:",expected,actual);
	}
	
	//set Username Test
	@Test
	public void setUserTest() {
		acc.setUsername("New Username");
		String expected = "New Username";
		String actual = acc.getUsername();
		assertEquals("Set Username Test:",expected,actual);
	}
	
	//set Password Test
	@Test
	public void setPasswordTest() {
		acc.setPassword("New_Password");
		String expected = "New_Password";
		String actual = acc.getPassword();
		assertEquals("Set Password Test:",expected,actual);
	}
	
	//Set Balance Test
	@Test
	public void setBalanceTest() {
		acc.setBalance(1234.56);
		double expected = 1234.56;
		double actual = acc.getBalance();
		assertEquals("Set Balance Test:",expected,actual,0.0000000001);
	}
	
	
	//Deposit Test (Uses the method setBalance)
	@Test
	public void testDeposit() {
		acc.setBalance(100.27);
		double expected = 100.27;
		double actual = acc.getBalance();
		assertEquals("Deposit Test (must pass the 'Set Balance' test to work):",expected,actual,0.0000000000001);
	}
	
	//Deposit a negative Test
	@Test
	public void testNegDeposit() {
		double expected = acc.getBalance();
		acc.deposit(-50.76);
		double actual = acc.getBalance();
		assertEquals("Negative Deposit Test:",expected,actual,0.0000000000001);
	}
	
	//Withdraw Test (Uses the method setBalance)
	@Test
	public void withdrawTest() {
		acc.setBalance(100);
		acc.withdraw(49.5);
		double expected = 100 - 49.5;
		double actual = acc.getBalance();
		assertEquals("Withdraw Test (must pass the 'Set Balance' test to work):",expected,actual,0.00000000000001);
	}
	
	//Withdraw Too Much Test
	@Test
	public void withdrawTooMuchTest() {
		double expected = acc.getBalance();
		acc.withdraw(100000000); //Assumes acc was initialized with the balance of 0.0
		double actual = acc.getBalance();
		assertEquals("Withdraw Too Much Test, Expects no Change:",expected,actual,0.000000000001);
	}
		
	//Withdraw Negative Test (expects the negative to be converted into a positive then withdrawn)
	@Test
	public void withdrawNegTest() {
		acc.setBalance(100);
		double expected = 100-50;
		acc.withdraw(-50);
		double actual = acc.getBalance();
		
		assertEquals("Negative Withdraw Test, Expects argument to be converted to a positive:",expected,actual,0.000000000001);
	}
	
	//Multiple Transactions Test
	@Test
	public void multipleTransactionsTest() {
		double startingBal = acc.getBalance();
		acc.deposit(100.45);
		acc.deposit(400);
		acc.withdraw(205.53);
		acc.deposit(4.55);
		acc.withdraw(36.54);
		double expected = startingBal + 100.45 + 400 - 205.53 + 4.55 - 36.54;
		double actual = acc.getBalance();
		
		assertEquals("Multiple Transactions Test:",expected,actual,0.0000000001);
	}
	
	//Add Message Test
	@Test
	public void addMessageTest() {
		acc.addMessage("fr", "to", "sub", "content of the message"); //Adds Message
		assertEquals("Add Message, Checking Array Size:",1,acc.getMessages().size()); //Ensures the message was added to the ArrayList
		
		ArrayList<Message> messages = acc.getMessages();//Variable used for future tests, represents messageList after adding the message
		
		//Tests message contents
		assertEquals("Add Message, Checking Contents, From:","fr",messages.get(0).getSender());
		assertEquals("Add Message, Checking Contents, To:","to",messages.get(0).getReciever());
		assertEquals("Add Message, Checking Contents, Subject:","sub",messages.get(0).getSubject());
		assertEquals("Add Message, Checking Contents, Content:","content of the message",messages.get(0).getContent());
	}
	
	
}
