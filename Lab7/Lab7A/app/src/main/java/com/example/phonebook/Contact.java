package com.example.phonebook;
public class Contact {

    /*Utility class used to store information on contacts in an object*/
    private int id;
    private String num;
    private String firstName;
    private String lastName;
    public Contact(){}
    public Contact( String f, String l, String n){
        firstName = f;
        lastName = l;
        num = n;
    }

    public Contact(int id, String f, String l, String n){
        firstName = f;
        lastName = l;
        num = n;
        this.id = id;
    }
    // Setters and Getters
    public void setId(int i) {id = i;}
    public void setFirstName(String f){
        firstName = f;
    }
    public void setLastName(String l){
        lastName = l;
    }
    public void setNum(String n){
        num = n;
    }
    public int getId(){return id;}
    public String getNum(){
        return num;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
}
