package application;

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
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import application.backend.Account;
import application.backend.Database;
import application.backend.Message;
import application.backend.Transaction;

import application.components.TransactionPane;
import application.components.MessagesPane;

public class Controller {
	
	//  Instance Variables
	private Database db;
	private Account acc = null;
	private String scene = "login";
	
	// loginScene elements
	@FXML private TextField userLogin;
	@FXML private PasswordField passLogin;
	
	// signUpScene elements
	@FXML private TextField userSignUp;
	@FXML private TextField nameSignUp;
	@FXML private PasswordField passSignUp;
	@FXML private PasswordField cPassSignUp;
	
	// mainMenuScene Elements
	@FXML private Label welcomeMessageMenu;
	@FXML private Label balance;
	
	// transferScene Elements
	@FXML private TextField fromTransfer;
	@FXML private TextField toTransfer;
	@FXML private TextField amountTransfer;
	
	// transactionScene Elements
	@FXML private Pane transactionPane;
	
	// viewMessagesScene Elements
	@FXML private Pane messagesPane;
	
	// transferScene Elements
	@FXML private TextField fromMessage;
	@FXML private TextField toMessage;
	@FXML private TextField subjectMessage;
	@FXML private TextField contentMessage;

	// Controller Constructor, loads the DB
	public Controller() throws FileNotFoundException, IOException, ParseException {
		this.db = loadDb();
	}
	
	// Funtction to initialize the controller and to manage scene data
	public void initData(Account ac, String sc) {
		this.acc = ac;
		this.scene = sc;
		switch(scene) {
			case "login":
				break;
			case "signUp":
				break;
			case "mainMenu":
				balance.setText("Balance: $" + String.format("%.2f", this.db.getBalance(this.acc.getUsername())));
				welcomeMessageMenu.setText("Welcome " + acc.getName() + "!");
				break;
			case "transfer":
				fromTransfer.setText("From: " + this.acc.getUsername());
				break;
			case "transactions":
				this.loadTransactions();
				break;
			case "viewMessages":
				this.loadMessages();
				break;
			case "sendMessages":
				fromMessage.setText("From: " + this.acc.getUsername());
				break;
		}
	}
	
	// function to load transactions into scroll pane
	public void loadTransactions() {
		ArrayList<Transaction> trans = this.db.getTransactions(this.acc.getUsername());
		if(trans.size() < 1) {
			Pane transPane = new Pane();
			transPane.getStyleClass().add("scrollPaneELement");
			transPane.setLayoutY(0);
			transPane.setLayoutX(0);
			Label userMsg = new Label("No transactions!");
			userMsg.getStyleClass().add("txt");
			userMsg.getStyleClass().add("txt3");
			userMsg.setLayoutY(40);
			userMsg.setLayoutX(40);
			transPane.getChildren().add(userMsg);
			transactionPane.getChildren().add(transPane);
		} else {
			for(int i = 0; i < trans.size(); i++) {
				TransactionPane transPane = new TransactionPane(trans.get(i).getType(), trans.get(i).getNote(), trans.get(i).getAmount(), trans.get(i).getTimestamp());
				transactionPane.getStyleClass().add("scrollPaneElement2");
				transPane.getStyleClass().add("scrollPaneElement");
				transPane.setLayoutY(100 * i);
				transPane.setLayoutX(0);
				transactionPane.getChildren().add(transPane);
			}
			transactionPane.setPrefHeight(100 * trans.size());
		}
	}
	
	// function to load messages to scroll pane
	public void loadMessages() {
		ArrayList<Message> msgs = this.db.getMessages(this.acc.getUsername());
		if(msgs.size() < 1) {
			Pane msgPane = new Pane();
			msgPane.getStyleClass().add("scrollPaneELement");
			msgPane.setLayoutY(0);
			msgPane.setLayoutX(0);
			Label userMsg = new Label("No Messages!");
			userMsg.getStyleClass().add("txt");
			userMsg.getStyleClass().add("txt3");
			userMsg.setLayoutY(40);
			userMsg.setLayoutX(40);
			msgPane.getChildren().add(userMsg);
			messagesPane.getChildren().add(msgPane);
		} else {
			for(int i = 0; i < msgs.size(); i++) {
				MessagesPane msgPane = new MessagesPane(msgs.get(i).getSender(), msgs.get(i).getSubject(), msgs.get(i).getContent(), msgs.get(i).getTimestamp());
				messagesPane.getStyleClass().add("scrollPaneElement2");
				msgPane.getStyleClass().add("scrollPaneElement");
				msgPane.setLayoutY(100 * i);
				msgPane.setLayoutX(0);
				messagesPane.getChildren().add(msgPane);
			}
			messagesPane.setPrefHeight(100 * msgs.size());
		}
	}

	// function to login
	public void login(ActionEvent event) throws IOException {
		this.acc = this.db.login(userLogin.getText(), passLogin.getText());
		if(this.acc == null) {
			Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Username / Password is incorrect!"); 
            a.setHeaderText("Login Failed!");
            a.showAndWait(); 
			userLogin.getStyleClass().add("inputFieldInvalid");
			passLogin.getStyleClass().add("inputFieldInvalid");
		} else {
			this.mainMenuScene(event);
		}
	}
	
	// function to create account
	public void createAccount(ActionEvent event) throws IOException {
		String username = userSignUp.getText();
		String name = nameSignUp.getText();
		String password = passSignUp.getText();
		String cpassword = cPassSignUp.getText();
		if(!password.equals(cpassword) || password.equals("")) {
			Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Passwords do not match!"); 
            a.setHeaderText("Sign Up Failed!");
            a.showAndWait(); 
            passSignUp.getStyleClass().add("inputFieldInvalid");
            cPassSignUp.getStyleClass().add("inputFieldInvalid");
		} else if(db.userExists(username)) {
			Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Username already exists!"); 
            a.setHeaderText("Try again with a different username");
            a.showAndWait(); 
            userSignUp.getStyleClass().add("inputFieldInvalid");
		} else {
			this.acc = new Account(username, name, password);
			this.db.addUser(this.acc, password);
			Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Sign Up Successful!");
            a.setContentText("Account successfully created!"); 
            a.showAndWait();
            this.updateDb(this.db);
            this.mainMenuScene(event);
		}
	}
	
	// function to logout
	public void logout(ActionEvent event) throws IOException {
		this.acc = null;
		this.loginScene(event);
	}
	
	//  function to deposit
	public void deposit() throws IOException {
		TextInputDialog td = new TextInputDialog("$ ");
		td.setHeaderText("Please enter the amount you wish to deposit.");
		td.setTitle("Deposit");
		double amount = -1;
		boolean canceled = false;
			do {
				try {
					Optional<String> result = td.showAndWait();
					String am = "";
					if (result.isPresent()) { 
						am = result.get();
						am = am.replace("$", "").trim();
						amount = Double.parseDouble(am);
						if(amount <= 0) {
							Alert a = new Alert(AlertType.ERROR);
							a.setContentText("Amount must be greater than 0!"); 
							a.setHeaderText("Please enter a number greater than 0!");
							a.showAndWait(); 
						} else {
							double bl = this.db.deposit(amount, this.acc.getUsername());
							balance.setText("Balance: $" + String.format("%.2f", bl));
							this.updateDb(this.db);
						}
					} else {
						canceled = true;
					}
				} catch(Exception e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Amount must be a number!"); 
					a.setHeaderText("Please enter a number!");
					a.showAndWait();
				}
			} while(amount <= 0 && !canceled);
	}
	
	// function to withdraw
	public void withdraw() throws IOException {
		TextInputDialog td = new TextInputDialog("$ ");
		td.setHeaderText("Please enter the amount you wish to withdraw.");
		td.setTitle("Withdraw");
		double amount = -1;
		boolean canceled = false;
		do {
			try {
				Optional<String> result = td.showAndWait();
				String am = "";
				if (result.isPresent()) { 
					am = result.get();
					am = am.replace("$", "").trim();
					amount = Double.parseDouble(am);
					if(amount <= 0) {
						Alert a = new Alert(AlertType.ERROR);
						a.setContentText("Amount must be greater than 0!"); 
						a.setHeaderText("Please enter a number greater than 0!");
						a.showAndWait(); 
					} else {
						if(amount > this.db.getBalance(this.acc.getUsername())) {
							Alert a = new Alert(AlertType.ERROR);
							a.setHeaderText("Insufficient Funds!"); 
							a.setContentText("Amount you wish to withdraw is greater than your balance!");
							a.showAndWait();
							amount = -1;
						} else {
							double bl = this.db.withdraw(amount, this.acc.getUsername());
							balance.setText("Balance: $" + String.format("%.2f", bl));
							this.updateDb(this.db);
						}
					}
				} else {
					canceled = true;
				}
			} catch(Exception e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Amount must be a number!"); 
				a.setHeaderText("Please enter a number!");
				a.showAndWait();
			}
		} while(amount < 0 && !canceled);
	}
	
	// function to edit name
	public void editName() throws IOException {
		TextInputDialog td = new TextInputDialog("$ ");
		td.setHeaderText("Please enter the name you wish to change to.");
		td.setTitle("Change Name");
		boolean canceled = false;
		boolean changedName = false;
		do {
			Optional<String> result = td.showAndWait();
			String am = "";
			if (result.isPresent()) { 
				am = result.get();
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setHeaderText("Change Name!"); 
				a.setContentText("Are you sure you want to change your name to " + am + "?");
				Optional<ButtonType> bt = a.showAndWait();
				if(bt.get() == ButtonType.OK) {
					changedName = true;
					this.db.editName(this.acc.getUsername(), am);
					this.acc.setName(am);
					this.updateDb(this.db);
					Alert a1 = new Alert(AlertType.INFORMATION);
		            a1.setHeaderText("Changed Name Successful!");
		            a1.setContentText("Change name to " + am + "!"); 
		            a1.showAndWait();
				}
			} else {
				canceled = true;
			}
		} while(!canceled && !changedName);
	}

	// function to edit username
	public void editUsername() throws IOException {
		TextInputDialog td = new TextInputDialog("$ ");
		td.setHeaderText("Please enter the username you wish to change to.");
		td.setTitle("Change Name");
		boolean canceled = false;
		boolean validUser = false;
		do {
			Optional<String> result = td.showAndWait();
			String am = "";
			if (result.isPresent()) { 
				am = result.get();
				if(this.db.userExists(am)) {
					Alert a1 = new Alert(AlertType.ERROR);
	            	a1.setHeaderText("Username already exists!");
	            	a1.setContentText("The username " + am + " already exists, please try again!"); 
	            	a1.showAndWait();
				} else {
					Alert a = new Alert(AlertType.CONFIRMATION);
					a.setHeaderText("Change Username!"); 
					a.setContentText("Are you sure you want to change your username to " + am + "?");
					Optional<ButtonType> bt = a.showAndWait();
					if(bt.get() == ButtonType.OK) {
						validUser = true;
						this.db.editUsername(this.acc.getUsername(), am);
						this.acc.setUsername(am);
						this.updateDb(this.db);
						Alert a1 = new Alert(AlertType.INFORMATION);
		            	a1.setHeaderText("Changed username Successful!");
		            	a1.setContentText("Change usename to " + am + "!"); 
		            	a1.showAndWait();
					}
				}
			} else {
				canceled = true;
			}
		} while(!canceled && !validUser);
	}	
	
	// function to transfer money
	public void sendTransfer(ActionEvent event) {
		String to = toTransfer.getText().trim();
		String am = amountTransfer.getText().trim();
		double amount = -1;
		try {
			amount = Double.parseDouble(am);
			if(!db.userExists(to)) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(to + " does not exist!"); 
				a.setHeaderText("Please enter a valid username!");
				a.showAndWait(); 
			} else if(this.acc.getUsername().equals(to)) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Invalid Transfer!"); 
				a.setHeaderText("You cannot transfer to yourself!");
				a.showAndWait(); 
			} else {
				if(amount <= 0) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Amount must be greater than 0!"); 
					a.setHeaderText("Please enter a number greater than 0!");
					a.showAndWait(); 
				} else {
					if(amount > this.db.getBalance(this.acc.getUsername())) {
						Alert a = new Alert(AlertType.ERROR);
						a.setHeaderText("Insufficient Funds!"); 
						a.setContentText("Amount you wish to withdraw is greater than your balance!");
						a.showAndWait();
						amount = -1;
					} else {
						Alert a = new Alert(AlertType.INFORMATION);
						this.db.transfer(this.acc.getUsername(), to, amount);
						this.updateDb(this.db);
						a.setHeaderText("Transfer Sent!"); 
						a.setContentText("$" + amount + " was sent to " + to + "!");
						a.showAndWait();
						this.mainMenuScene(event);
					}
				}
			}
		} catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Amount must be a number!"); 
			a.setHeaderText("Please enter a number!");
			a.showAndWait();
		}
	}
	
	// function to send messages
	public void sendMessage(ActionEvent event) throws IOException {
		String to = toMessage.getText().trim();
		String sb = subjectMessage.getText().trim();
		String con = contentMessage.getText().trim();
		if(!db.userExists(to)) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText(to + " does not exist!"); 
			a.setHeaderText("Please enter a valid username!");
			a.showAndWait(); 
		} else if(this.acc.getUsername().equals(to)) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Invalid Message!"); 
			a.setContentText("You cannot send a message to yourself!");
			a.showAndWait(); 
		} else {
			Alert a = new Alert(AlertType.INFORMATION);
			this.db.sendMessage(this.acc.getUsername(), to, sb, con);
			this.updateDb(this.db);
			a.setHeaderText("Message Sent!"); 
			a.setContentText("The message was sent to " + to + "!");
			a.showAndWait();
			this.mainMenuScene(event);
		}
	}
	
	// Scene Management
	// loads the signup scene
	public void signUpScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "signUp";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
		
	}
	
	// function to load the main menu scene
	public void mainMenuScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenuScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "mainMenu";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	// function to load the login scene
	public void loginScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "login";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	// function to load the transfer scene
	public void transferScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("transferScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "transfer";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	// function to load the transactions scene
	public void transactionsScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "transactions";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	// function to load the view messages scene
	public void viewMessagesScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("viewMessagesScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "viewMessages";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	// function to load the send messages scene
	public void sendMessagesScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("sendMessagesScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "sendMessages";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	public void editInfoScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("editInfoScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "editInfo";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	/**
     * updateDB -> This method converts the current Database into a json format and saves it into a json file
	 * We are using the simple.JSON library to complete this task
     * @param db -> object of type Database
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateDb(Database db) throws IOException {
    	ArrayList <Account> acc = db.getAccountsList(); // get list of accounts
    	JSONObject jo = new JSONObject();
        JSONArray accList = new JSONArray(); // JSON array that holds all the accounts
    	for(int i = 0; i < acc.size(); i++) { // loop through accounts
        	Map accInfo = new LinkedHashMap(7); // save this instance's information
    		Account cAcc = acc.get(i);
    		accInfo.put("username", this.encrypt(cAcc.getUsername())); // get information and save it in JSON string
    		accInfo.put("name", this.encrypt(cAcc.getName()));
    		accInfo.put("password", this.encrypt(cAcc.getPassword()));
    		accInfo.put("balance", this.encrypt("" + cAcc.getBalance()));
    		JSONArray msgList = new JSONArray(); // JSON array that holds all the messages for this instance account
    		ArrayList <Message> msgs = cAcc.getMessages();
    		for(Message msg : msgs) { // loop through messages
        		Map msgInfo = new LinkedHashMap(5);
    			msgInfo.put("from", this.encrypt(msg.getSender()));
    			msgInfo.put("to", this.encrypt(msg.getReciever()));
    			msgInfo.put("subject", this.encrypt(msg.getSubject()));
    			msgInfo.put("content", this.encrypt(msg.getContent()));
    			msgInfo.put("ts", this.encrypt(msg.getTimestamp()));
    			msgList.add(msgInfo);
    		}
    		accInfo.put("messages", msgList);
    		JSONArray tranList = new JSONArray(); // JSON array that holds all the transactions for this instance account
    		ArrayList <Transaction> trans = cAcc.getLog();
    		for(Transaction tran : trans) { // loop through transactions
        		Map tranInfo = new LinkedHashMap(4);
    			tranInfo.put("type", this.encrypt(tran.getType()));
    			tranInfo.put("note", this.encrypt(tran.getNote()));
    			tranInfo.put("amount", this.encrypt("" + tran.getAmount()));
    			tranInfo.put("ts", this.encrypt(tran.getTimestamp()));
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
	private static Database loadDb() throws FileNotFoundException, IOException, ParseException {
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
    		String username = (String) acc.get("username"), 
    					name = (String) acc.get("name"), 
    					password = (String) acc.get("password");
    		ArrayList <Message> msgs = new ArrayList<Message>();
    		ArrayList <Transaction> trans = new ArrayList<Transaction>();
    		Account nAcc = new Account(decrypt(username), decrypt(name), decrypt(password)); // create new account
    		JSONArray msgList = (JSONArray) acc.get("messages");
    		Iterator<JSONObject> itr2 = msgList.iterator();
    		while(itr2.hasNext()) { // loop through messages
    			JSONObject msg = itr2.next();
    			String from = (String) msg.get("from"), 
    						to = (String) msg.get("to"), 
    						subject = (String) msg.get("subject"), 
    						content = (String) msg.get("content"), 
    						ts = (String) msg.get("ts");
    			Message nMsg = new Message(decrypt(from), decrypt(to), decrypt(subject), decrypt(content), decrypt(ts)); // creates new message
    			msgs.add(nMsg);
    		}
    		nAcc.setMessages(msgs);
    		JSONArray tranList = (JSONArray) acc.get("log");
    		Iterator<JSONObject> itr3 = tranList.iterator();
    		while(itr3.hasNext()) { // loop through transactions
    			JSONObject tran = itr3.next();
    			double amount = Double.parseDouble((String) tran.get("amount"));
    			Transaction nTran = new Transaction( decrypt((String) tran.get("type")),  decrypt((String) tran.get("note")), Double.parseDouble(decrypt("" + amount)), decrypt((String) tran.get("ts"))); // create new transaction
    			trans.add(nTran);
    		}
    		nAcc.setTransactions(trans);
    		double balance = Double.parseDouble((String) acc.get("balance"));
    		nAcc.setBalance(Double.parseDouble(decrypt("" + balance)));
    		db.addUser(nAcc, decrypt((String) acc.get("password")));
    	}
    	return db; // return db, will always return a valid db
    }
    
    public String encrypt(String s) {
		char [] upperCase = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char []	lowerCase = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		char [] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		int encryptBy = s.length();
		String encrypted = "";
		
		for(int i = 0; i < s.length(); i++) {
			boolean success = false;
			for(int j = 0; j < upperCase.length; j++) {
				if(s.charAt(i) == upperCase[j]) {
					encrypted += upperCase [(j + encryptBy) % 26];
					success = true;
				}
			}
			for(int h = 0; h < lowerCase.length; h++) {
				if(s.charAt(i) == lowerCase[h]) {
					encrypted += lowerCase [(h + encryptBy) % 26];
					success = true;
				}
			}
			for(int j = 0; j < numbers.length; j++) {
				if(s.charAt(i) == numbers[j]) {
					encrypted += numbers [(j + encryptBy) % 10];
					success = true;
				}
			}
			if(!success) {
				encrypted += s.charAt(i);
			}
		}
		return encrypted;
	}
	
	public static String decrypt(String s) {
		char [] upperCase = {'Z' , 'Y', 'X', 'W', 'V', 'U', 'T', 'S', 'R', 'Q', 'P', 'O', 'N', 'M', 'L', 'K', 'J', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
		char []	lowerCase = {'z' , 'y', 'x', 'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'o', 'n', 'm', 'l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
		char [] numbers = { '9', '8', '7', '6', '5', '4', '3', '2', '1', '0'};
		int decryptBy = s.length();
		String decrypted = "";
		
		for(int i = 0; i < s.length(); i++) {
			boolean success = false;
			for(int j = 0; j < upperCase.length; j++) {
				if(s.charAt(i) == upperCase[j]) {
					decrypted += upperCase [(j + decryptBy) % 26];
					success = true;
				}
			}
			for(int h = 0; h < lowerCase.length; h++) {
				if(s.charAt(i) == lowerCase[h]) {
					decrypted += lowerCase [(h + decryptBy) % 26];
					success = true;
				}
			}
			for(int j = 0; j < numbers.length; j++) {
				if(s.charAt(i) == numbers[j]) {
					decrypted += numbers [(j + decryptBy) % 10];
					success = true;
				}
			}
			if(!success) {
				decrypted += s.charAt(i);
			}
		}
		return decrypted;
	}
}
