package com.kurs.toikana.toikana;

import java.sql.Connection;

public class Account {
    private String name;
    private String password;
    private String email;
    private String restaurant;
    private String role;

    public  Account() {
    }
    public void setName(String name){

        this.name = name;
    }
    public void setEmail(String email){

        this.email = email;
    }
    public void setRestaurant(String restaurant){

        this.restaurant = restaurant;
    }
    public void setRole(String role) {

        this.role = role;
    }





    public String getName(){

        return (this.name);
    }
    public String getEmail(){

        return (this.email);
    }
    public String getRestaurant(){

        return (this.restaurant);
    }
    public String getRole(){

        return (this.role);
    }
}
