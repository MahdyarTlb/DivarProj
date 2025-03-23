package com.Divarproject.Ads;

import com.Divarproject.Register.User;

public abstract class Ad {
    private String Name;
    private String Description;
    private int Price;
    private String Contact;
    private User Owner;
    private String ImagePath;
    private AdType Type;

    //Constructors
    public Ad(String Name, String Description, int Price, String Contact, User Owner, String ImagePath, AdType Type){
        this.Name = Name;
        this.Description = Description;
        this.Price = Price;
        this.Contact = Contact;
        this.Owner = Owner;
        this.ImagePath = ImagePath;
        this.Type = Type;
    }

    //Getters & Setters
    public String getName(){return Name;}
    public String getDescription(){return Description;}
    public int getPrice(){return Price;}
    public String getContact(){return Contact;}
    public User getOwner(){return Owner;}
    public String getImagePath(){return ImagePath;}
    public AdType getType(){return Type;}

    public void setName(String Name){this.Name = Name;}
    public void setDescription(String Description){this.Description = Description;}
    public void setPrice(int Price){this.Price = Price;}
    public void setImagePath(String ImagePath){this.ImagePath = ImagePath;}
    //enum for type
    public enum AdType{
        CAR;
    }

    //ShowDetails
    public abstract void displayDetails();



}
