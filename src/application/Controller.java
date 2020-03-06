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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class Controller {
	
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
	
	public Controller() throws FileNotFoundException, IOException, ParseException {
		this.db = loadDb();
	}
	
	public void initData(Account ac, String sc) {
		this.acc = ac;
		this.scene = sc;
		switch(scene) {
		case "login":
			break;
		case "signUp":
			break;
		case "mainMenu":
			balance.setText("Balance: $ " + this.db.getBalance(this.acc.getUsername()));
			welcomeMessageMenu.setText("Welcome " + acc.getName() + "!");
			break;	
		}
	}

	public void login(ActionEvent event) throws IOException {
		this.acc = this.db.login(userLogin.getText(), passLogin.getText());
		if(this.acc == null) {
			Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Username / Password is incorrect!"); 
            a.setHeaderText("Login Failed!");
            a.show(); 
			userLogin.getStyleClass().add("inputFieldInvalid");
			passLogin.getStyleClass().add("inputFieldInvalid");
		} else {
			this.mainMenuScene(event);
		}
	}
	
	public void createAccount(ActionEvent event) throws IOException {
		String username = userSignUp.getText();
		String name = nameSignUp.getText();
		String password = passSignUp.getText();
		String cpassword = cPassSignUp.getText();
		if(!password.equals(cpassword)) {
			Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Passwords do not match!"); 
            a.setHeaderText("Sign Up Failed!");
            a.show(); 
            passSignUp.getStyleClass().add("inputFieldInvalid");
            cPassSignUp.getStyleClass().add("inputFieldInvalid");
		} else {
			this.acc = new Account(username, name, password);
			this.db.addUser(this.acc, password);
			Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Sign Up Successful!");
            a.setContentText("Account successfully created!"); 
            a.show();
            this.updateDb(this.db);
            this.mainMenuScene(event);
		}
	}
	
	public void logout(ActionEvent event) throws IOException {
		this.acc = null;
		this.loginScene(event);
	}
	
	public void deposit() throws IOException {
		TextInputDialog td = new TextInputDialog("$ ");
		td.setHeaderText("Please enter the amount you wish to deposit.");
		td.setTitle("Deposit");
		double amount = -1;
		do {
		Optional<String> result = td.showAndWait();
		String am = "";
		try {
			if (result.isPresent()) { 
		    	am = result.get();
		    	am = am.replace("$", "").trim();
		    	amount = Double.parseDouble(am);
			}
		    if(amount <= 0) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Amount must be greater than 0!"); 
				a.setHeaderText("Please enter a number greater than 0!");
				a.showAndWait(); 
			}
		} catch(Exception e) {
		    	Alert a = new Alert(AlertType.ERROR);
	            a.setContentText("Amount must be a number!"); 
	            a.setHeaderText("Please enter a number!");
	            a.showAndWait();
		  }
		} while(amount <= 0);
		double bl = this.db.deposit(amount, this.acc.getUsername());
		balance.setText("Balance: $ " +bl);
		this.updateDb(this.db);
	}
	
	public void withdraw() throws IOException {
		TextInputDialog td = new TextInputDialog("$ ");
		td.setHeaderText("Please enter the amount you wish to withdraw.");
		td.setTitle("Withdraw");
		double amount = -1;
		do {
		Optional<String> result = td.showAndWait();
		String am = "";
		try {
			if (result.isPresent()) { 
		    	am = result.get();
		    	am = am.replace("$", "").trim();
		    	amount = Double.parseDouble(am);
			}
		    if(amount <= 0) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Amount must be greater than 0!"); 
				a.setHeaderText("Please enter a number greater than 0!");
				a.showAndWait(); 
			}
		} catch(Exception e) {
		    	Alert a = new Alert(AlertType.ERROR);
	            a.setContentText("Amount must be a number!"); 
	            a.setHeaderText("Please enter a number!");
	            a.showAndWait();
		  }
		} while(amount <= 0);
		double bl = this.db.withdraw(amount, this.acc.getUsername());
		balance.setText("Balance: $ " + bl);
		this.updateDb(this.db);
	}
	
	
	
	// Scene Management
	public void signUpScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "signUp";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
		
	}
	
	public void mainMenuScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenuScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "mainMenu";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	public void loginScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScene.fxml"));
		Scene scene = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = "login";
		Controller cr = loader.getController();
		cr.initData(this.acc, this.scene);
		stage.setScene(scene);
	}
	
	public void transferScene(ActionEvent event) {
		
	}
	
	public void transactionsScene(ActionEvent event) {
		
	}
	

	public void viewMessagesScene(ActionEvent event) {
		
	}
	
	public void editInfoScene(ActionEvent event) {
		
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
    		accInfo.put("username", cAcc.getUsername()); // get information and save it in JSON string
    		accInfo.put("name", cAcc.getName());
    		accInfo.put("password", cAcc.getPassword());
    		accInfo.put("balance", cAcc.getBalance());
    		JSONArray msgList = new JSONArray(); // JSON array that holds all the messages for this instance account
    		ArrayList <Message> msgs = cAcc.getMessages();
    		for(Message msg : msgs) { // loop through messages
        		Map msgInfo = new LinkedHashMap(5);
    			msgInfo.put("from", msg.getSender());
    			msgInfo.put("to", msg.getReciever());
    			msgInfo.put("subject", msg.getSubject());
    			msgInfo.put("content", msg.getContent());
    			msgInfo.put("ts", msg.getTimestamp());
    			msgList.add(msgInfo);
    		}
    		accInfo.put("messages", msgList);
    		JSONArray tranList = new JSONArray(); // JSON array that holds all the transactions for this instance account
    		ArrayList <Transaction> trans = cAcc.getLog();
    		for(Transaction tran : trans) { // loop through transactions
        		Map tranInfo = new LinkedHashMap(4);
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
	private static Database loadDb() throws FileNotFoundException, IOException, ParseException {
    	File tmpDir = new File("Database.json");
    	boolean exists = tmpDir.exists();
    	if(!exists) { // checks if the file does not exist to prevent error and creates it
    		FileWriter fw=new FileWriter("Database.json"); // create Database.json file and allows us to overwrite it
    		PrintWriter pw = new PrintWriter(fw); // prints text to file
    		pw.write("{\"loggedIn\":{},\"accounts\":[]}"); // print JSON string to file
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
    		Account nAcc = new Account((String) acc.get("username"), (String) acc.get("name"), (String) acc.get("password")); // create new account
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
}
