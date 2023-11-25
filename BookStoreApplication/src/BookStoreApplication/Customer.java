/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStoreApplication;

public class Customer {
    public String username;
    public String password;
    
    public int points;
    
    public CustomerStatus status;
    
    public Customer(String password, String username, int points){
        this.password = password;
        this.username = username;
        this.points = points;
        status = new Silver();
        }
    
    public int getPoints(){
        return this.points;
    }
    
    public String getUsername(){
        
        return this.username;
    }
    public String getPassword(){
        
        return this.password;
    }
    public void setPoints(int x){
        this.points = x;
    }
    
    public void statusUpdate(){
        
        status.statusUpdate(this);
    }
    
    @Override
    public String toString(){
        return ("Username: " + this.username + " Password: " + this.password + " Points: " + this.points);
        
    }
}
// Customer x = new Customer()
//x.getPoitns()