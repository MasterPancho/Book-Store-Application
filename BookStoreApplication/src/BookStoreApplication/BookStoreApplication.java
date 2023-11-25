/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStoreApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//This class acts as the "main" file for the code. Below is mainly defult operations to get JavaFX running.
//This file runs where the "Run Project" button is pressed
public class BookStoreApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_screen.fxml"));
        Scene scene = new Scene(root, 606, 403);
        int x = ((int)(Math.random()*50));
        switch(x%4){
            case 0:
                stage.setTitle("BookStore Application - Revolutionary!");
                break;
            case 1: 
                stage.setTitle("BookStore Application - Ground Breaking!");
                break;
            case 2:
                stage.setTitle("BookStore Application - Get Your Books!");
                break;
            case 3:
                stage.setTitle("BookStore Application - Excelsior!");
                break;
        }
        System.out.println("Title Switch Case: " + x%4);
        System.out.println("Launching login_screen.fxml...");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
