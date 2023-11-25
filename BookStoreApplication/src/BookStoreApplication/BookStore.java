/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStoreApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
**/

public class BookStore implements Initializable {
//the following variable declarations are for importing the objects (buttons, text feilds, etc) into the class
    
    //the following imports are for the login_screen.fxml file
    
    @FXML private Label dl_login_screen; //dl stands for "debug label"
    @FXML private Button login_button;
    @FXML private TextField username_textfield;
    @FXML private TextField password_textfield;
    
    //the following imports are for the owner_start_screen.fxml file
    
     @FXML private Text dl_owner_start_screen; //dl stands for "debug label"
     @FXML private Button logout_button_oss; //oss stands for "owner start screen"
     @FXML private Button customers_button;
     @FXML private Button books_button;
     
     //the following imports are for the owner_books_screen.fxml file
     
     @FXML private Label dl_owner_books_screen;
     @FXML private TextField book_name_textfield;
     @FXML private TextField book_price_textfield;
     @FXML private Button back_button_OBS;
     @FXML private Button add_button_OBS;
     @FXML private Button delete_button_OBS;
     @FXML private TableView<Book> table_of_books = new TableView<>();
     
     //the following imports are for the owner_customers_screen.fxml file
     
     @FXML private Label dl_owner_customers_screen;
     @FXML private TextField addusername_textfield;
     @FXML private TextField addpassword_textfield;
     @FXML private Button back_button_OCS;
     @FXML private Button add_button_OCS;
     @FXML private Button delete_button_OCS;
     @FXML private TableView<Customer> table_of_users = new TableView<>();

     
     //the following imports are for the customer_start_screen.fxml file
     
     @FXML private Label title_message;
     @FXML private Button buy_button;
     @FXML private Button logout_button_css;
     @FXML private Button rpab_button;
     @FXML private TableView<Book> table_of_books_userend = new TableView<>();
     //the following imports are for the customer_cost_screen.fxml file
     
     @FXML private Text total_cost;
     @FXML private Text points_and_status;
     
    
     
     //the following variables are needed to switch scenes, not over important to understand
     
     private Parent root;
     private Stage stage;
     private Scene scene;
     
     //actual stuff
     
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Book> checked_books = new ArrayList<Book>();
    int cnt;
    double total_sum;
    private Customer current_user;
    final ObservableList<Customer> customers_ob = FXCollections.observableArrayList(
            
//            new Customer("debug_password1","debug_username1",100), 
//            new Customer("debug_password2","debug_username2",150)
    
    );
     final ObservableList<Book> books_ob = FXCollections.observableArrayList(
            
//            new Book("debug_booktitle1",1500.0), 
//            new Book("debug_booktitle2",750.0)
    
    );
    
//    
    @FXML//NOTE: whenever a method changes screens, must invode IOException
    private void handleLoginButton(ActionEvent event) throws IOException {
        System.out.println("Login Button Pressed...");
        dl_login_screen.setText("Login Button Pressed");
        boolean x = true;
        if("admin".equals(username_textfield.getText()) && "admin".equals(password_textfield.getText())){
            System.out.println("Admin accound accepted...");
            System.out.println("--------------\nLaunching to owner_start_screen.fxml");
            x = false;
            switchToOSS(event);  
        }
        for(Customer obj: customers){
        if(obj.username.equals(username_textfield.getText()) && obj.password.equals(password_textfield.getText())){
            System.out.println("User accound accepted...");
            x = false;
            writeFileCU(obj);
            switchToCSS(event);
            
        }
        }
        if(x){
           System.out.println("Account rejected...");
           dl_login_screen.setText("Account rejected");
           password_textfield.clear();
        }
        
        
    }
    
    //the following method(s) are for the owner_start_screen.fxml file
    
    @FXML
    private void handleBooksButton(ActionEvent event) throws IOException{
     System.out.println("Books button pressed");
     switchToOBS(event);
    }
    
    @FXML
    private void handleCustomersButton(ActionEvent event) throws IOException{
     System.out.println("Customers button pressed");
    switchToOCS(event);
    
    }
    
    
    //the following method(s) are for the owner_books_screen.fxml file
    
    @FXML private void handleBooksAddButton(ActionEvent event){
         System.out.println("Add button pressed (Owner Books Screen)");
         Book x = new Book(book_name_textfield.getText(), Double.valueOf(book_price_textfield.getText()));
         books.add(x);
         books_ob.add(x);
         if (books != null){
         for(Book temp_b: books){
             System.out.println(temp_b.toString());
         }
         }
         writeFile();
    }
    
    @FXML private void handleBooksDeleteButton(ActionEvent event){
         System.out.println("Delete button pressed (Owner Books Screen)");
         books.remove(table_of_books.getSelectionModel().getSelectedItem());
         table_of_books.getItems().removeAll(table_of_books.getSelectionModel().getSelectedItem());
         writeFile();
    
    }
    
    @FXML private void handleBooksBackButton(ActionEvent event) throws IOException{
           System.out.println("Launcing Owner's Start Screen");
           switchToOSS(event);
    }
    
    //the following method(s) are for the owner_customers_screen.fxml file
    
    @FXML private void handleUserAddButton(ActionEvent event){
         System.out.println("Add button pressed (Owner Customers Screen)");
         if(!(addpassword_textfield.getText().isEmpty() || addusername_textfield.getText().isEmpty())){
         Customer x = new Customer(addpassword_textfield.getText(), addusername_textfield.getText(), 0);
         customers.add(x);
         customers_ob.add(x);
         addpassword_textfield.clear();
         addusername_textfield.clear();
         if (customers != null){
         for(Customer temp_c: customers){
             System.out.println(temp_c.toString());
         }
         }
         }
         writeFile();
    }
    
    @FXML private void handleUserDeleteButton(ActionEvent event){
         System.out.println("Delete button pressed (Owner Customers Screen)");
         customers.remove(table_of_users.getSelectionModel().getSelectedItem());
         table_of_users.getItems().removeAll(table_of_users.getSelectionModel().getSelectedItem());
         writeFile();
    
    }
    
    @FXML private void handleUserBackButton(ActionEvent event) throws IOException{
           System.out.println("Launcing Owner's Start Screen");
           switchToOSS(event);
    }
    
    //the following method(s) are for the owner_customers_screen.fxml file
    
    @FXML private void handleBuyButton(ActionEvent event) throws IOException{
        System.out.println("Buy button pressed");
        boolean x = false;
        for(Book temp_ob: books_ob){
            if(temp_ob.select.isSelected()){
                checked_books.add(temp_ob);
                System.out.println(temp_ob.booktitle);
                books.remove(temp_ob);
                x = true;
            }
            
            
        }
        if(x){
            
            
            
        double sum = 0;
         for(Book temp_ob: checked_books){
            sum = sum + temp_ob.bookprice;
        }
         
         for(Customer temp_customer: customers){
             if(temp_customer.username.equals(current_user.username) && temp_customer.password.equals(current_user.password)){
                 temp_customer.setPoints(temp_customer.points + (int)sum*10); 
                 temp_customer.statusUpdate();
             }
             
         }
         current_user.setPoints(current_user.points + (int)sum*10);
         writeFileCU(current_user);
         writeFile();
         writeFileSUM(sum);
         System.out.println(sum);
         switchToCCS(event);
        }
    }
    
    @FXML private void handleRPABButton(ActionEvent event) throws IOException{
        System.out.println("Redeem Points and Buy button pressed");
        boolean x = false;
        for(Book temp_ob: books_ob){
            if(temp_ob.select.isSelected()){
                checked_books.add(temp_ob);
                x = true;
                books.remove(temp_ob);
                System.out.println(temp_ob.booktitle);
            }
            
            
        }
        if(x){
        
         double sum = 0;
         for(Book temp_ob: checked_books){
            sum = sum + temp_ob.bookprice;
        }
        double convertedPoints = ((double) current_user.points)/100;
         if(convertedPoints <= sum){
             sum = sum - convertedPoints;
             current_user.setPoints((int)sum*10);
         }
         if (convertedPoints > sum){
             current_user.setPoints((int)((convertedPoints - sum)*100));
             sum = 0;
         }
         for(Customer temp_customer: customers){
             if(temp_customer.username.equals(current_user.username) && temp_customer.password.equals(current_user.password)){
                 temp_customer.setPoints(current_user.points); 
                 temp_customer.statusUpdate();
             }
             
         }
         
         writeFileCU(current_user);
         writeFileSUM(sum);
         writeFile();
        
         System.out.println(sum);
        switchToCCS(event);
        
        
        }
    }
   
    //the remaining methods are for the computation of our application
    
    public void customersReadfile(String csvFile){       
        String line;
try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer obj = new Customer(data[0], data[1], Integer.valueOf(data[2])); 
                customers.add(obj);
                customers_ob.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void booksReadfile(String csvFile){       
        String line;
try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Book obj = new Book(data[0], Double.valueOf(data[1])); 
                books.add(obj);
                books_ob.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeFile(){

    String csvFile = "customers.txt";
   try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false))) {
     for (Customer obj : customers) {
         bw.write(obj.password + "," + obj.username + "," + obj.points);
         bw.newLine();
     }
 } catch (IOException e) {
     e.printStackTrace();
}
   csvFile = "books.txt";
   try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false))) {
     for (Book obj : books) {
         bw.write(obj.booktitle + "," + obj.bookprice);
         bw.newLine();
     }
 } catch (IOException e) {
     e.printStackTrace();
}
}
    
    public void writeFileCU(Customer obj){
        String csvFile = "current_user.txt";
   try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false))) {
     
         bw.write(obj.password + "," + obj.username + "," + obj.points);
         bw.newLine();
     
 } catch (IOException e) {
     e.printStackTrace();
}
    }
    
     public void ReadfileCU(){     
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader("current_user.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer obj = new Customer(data[0], data[1], Integer.valueOf(data[2]));  
                current_user = obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public void writeFileSUM(double x){
        String csvFile = "sum_of_purchase.txt";
   try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false))) {
        
         bw.write(x + ",");
         bw.newLine();
     
 } catch (IOException e) {
     e.printStackTrace();
}
    }
      public void ReadfileSUM(){     
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader("sum_of_purchase.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                double sum = Double.valueOf(data[0]);
                //Customer obj = new Customer(data[0], data[1], Integer.valueOf(data[2]));  
               total_sum = sum;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void switchToOSS(ActionEvent event) throws IOException{
        //this code can be copy and pasted always to switch screens... of course... file name must change
        root = FXMLLoader.load(getClass().getResource("owner_start_screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 606, 403);
        stage.setScene(scene);
        stage.setTitle("Owner Start Screen");
        stage.show();
    }
    
    private void logout(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login_screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 606, 403);
        stage.setScene(scene);
        stage.setTitle("BookStore Application");
        stage.show();   
    }
    
    private void switchToOBS(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("owner_books_screen2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 606, 403);
        stage.setScene(scene);
        stage.setTitle("Owner Books Screen");
        stage.show();   
    }
    
    
    
    private void switchToOCS(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("owner_customers_screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 606, 403);
        stage.setScene(scene);
       
        stage.setTitle("Owner Customers Screen");
        
        stage.show(); 
    }
    
    private void switchToCSS(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("customer_start_screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 606, 403);
        stage.setScene(scene);
        stage.setTitle("Customer Start Screen");
        stage.show();   
    }
    
    private void switchToCCS(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("customer_cost_screen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 606, 403);
        stage.setScene(scene);
        stage.setTitle("Customer Payment Screen");
        stage.show();   
    }
    
    @FXML//universal logout button
    private void handleLogoutButton(ActionEvent event) throws IOException{
     System.out.println("Logout button pressed");
     logout(event);
    }
    
    public void initalizeCustomers(){
    
    TableColumn username = new TableColumn("Username");
    TableColumn password = new TableColumn("Password");
    TableColumn points = new TableColumn("Points");
    table_of_users.getColumns().addAll(username,password,points); 
    username.setCellValueFactory(new PropertyValueFactory<>("username"));
    username.prefWidthProperty().bind(table_of_users.widthProperty().multiply(0.4));
    password.setCellValueFactory(new PropertyValueFactory<>("password"));
    password.prefWidthProperty().bind(table_of_users.widthProperty().multiply(0.4));
    points.setCellValueFactory(new PropertyValueFactory<>("points"));
    points.prefWidthProperty().bind(table_of_users.widthProperty().multiply(0.2));
    table_of_users.setItems(customers_ob);     
    
    }
         
    public void initalizeBooks(){
  
    TableColumn booktitle = new TableColumn("Book Title");
    TableColumn bookprice = new TableColumn("Price");
    table_of_books.getColumns().addAll(booktitle,bookprice); 
    booktitle.setCellValueFactory(new PropertyValueFactory<>("booktitle"));
    booktitle.prefWidthProperty().bind(table_of_books.widthProperty().multiply(0.5));
    bookprice.setCellValueFactory(new PropertyValueFactory<>("bookprice"));
    bookprice.prefWidthProperty().bind(table_of_books.widthProperty().multiply(0.5));
    table_of_books.setItems(books_ob);     
    }
    
    public void initializeUSERENDBooks(){
     TableColumn booktitle = new TableColumn("Book Title");
     TableColumn bookprice = new TableColumn("Price");
     TableColumn select = new TableColumn("Select");
     table_of_books_userend.getColumns().addAll(booktitle,bookprice, select); 
      booktitle.setCellValueFactory(new PropertyValueFactory<>("booktitle"));
    booktitle.prefWidthProperty().bind(table_of_books_userend.widthProperty().multiply(0.4));
    bookprice.setCellValueFactory(new PropertyValueFactory<>("bookprice"));
    bookprice.prefWidthProperty().bind(table_of_books_userend.widthProperty().multiply(0.4));
    select.setCellValueFactory(new PropertyValueFactory<>("select"));
    select.prefWidthProperty().bind(table_of_books_userend.widthProperty().multiply(0.2));
    table_of_books_userend.setItems(books_ob);
    
    }
    public void initializeCustomerTitle(){
        ReadfileCU();
//        current_user.setPoints(1500);
        current_user.statusUpdate();
        if(current_user.status instanceof Silver){
        title_message.setText("Welcome " + current_user.username + ". You have " + current_user.points + " points. Your current status is Silver");
        }else if(current_user.status instanceof Gold){
        title_message.setText("Welcome " + current_user.username + ". You have " + current_user.points + " points. Your current status is Gold");
    }
//if()
        
    }
    
    public void initalizeCustomerCostScreen(){
        
        current_user.statusUpdate();
        if(current_user.status instanceof Silver){
        total_cost.setText("Total cost: $" + String.format("%.2f", total_sum) + "\n\n Points: " + current_user.points + "\n\n Status: Silver" );
        }else if(current_user.status instanceof Gold){
            total_cost.setText("Total cost: $" + String.format("%.2f", total_sum) + "\n\n Points: " + current_user.points + "\n\n Status: Gold");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println("150");
        customersReadfile("customers.txt");
        booksReadfile("books.txt");
        ReadfileSUM();
        ReadfileCU();
        initalizeBooks();
        initalizeCustomers();
        initializeUSERENDBooks();
        initializeCustomerTitle();
        initalizeCustomerCostScreen();
        
        for(Customer temp_customer: customers){
            temp_customer.statusUpdate();}
    }    
    
}
//for(int i = 0; i < arr.length(); i++)
//      arr[i] = arr[i] + 1;

//for(int temp_arr: arr)
//  temp_arr = temp_arr + 1;