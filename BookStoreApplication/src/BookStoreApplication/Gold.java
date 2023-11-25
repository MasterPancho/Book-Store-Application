/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStoreApplication;

public class Gold extends CustomerStatus {
    
    @Override
    public void statusUpdate(Customer c){
        if (c.points >= 1000){
        }else
            c.status = new Silver();
    }
        
    }
    

