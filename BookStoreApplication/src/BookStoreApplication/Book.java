/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStoreApplication;

import javafx.scene.control.CheckBox;

public class Book {
    public String booktitle;
    public double bookprice;
    public CheckBox select;
    
    public Book(String booktitle, double bookprice){
        this.bookprice = bookprice;
        this.booktitle = booktitle;
        this.select = new CheckBox();
    }
    
    public String getBooktitle(){
        return this.booktitle;
    }
    
    public double getBookprice(){
        return this.bookprice;
    }
    public CheckBox getSelect(){
        return this.select;
        
    }
    @Override
    public String toString(){
        return ("Book Title: " + this.booktitle + " Price: " + String.format("%.2f", this.bookprice) +" $");
        
    }
    
}
