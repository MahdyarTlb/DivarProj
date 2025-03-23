package com.Divarproject.Ads;

import com.Divarproject.Register.User;

public class CarAd extends Ad{

    private int Productionage = -1; //نماد خالی بودن
    private int Mileage = -1;
    private boolean HasAccident;

    //Constructor
    public CarAd(String Name, String Description, int Price, String Contact, User Owner, String ImagePath,
                 int Productionage, int Mileage, boolean HasAccident){
        super(Name,Description,Price,Contact,Owner,ImagePath,AdType.CAR);
        this.Productionage = Productionage;
        this.Mileage = Mileage;
        this.HasAccident = HasAccident;
    }

    @Override
    public void displayDetails() {
        System.out.println("Car Ad");
        System.out.println("Name: " + getName() + "\n" + "Description: " + getDescription()
                +  "\n" + "Price: " + getPrice() + "\n" + "Contact: " + getContact()
                + "\n" + "Year produce: " + Productionage + "\n" + "Mileage: "
                + Mileage + "\n" + "Has accident: " + ((HasAccident) ?  "Yes" : "No"));
    }

    //Getters & Setters
    public int getProductionage() {return Productionage;}
    public int getMileage() {return Mileage;}
    public boolean isHasAccident() {return HasAccident;}
    public void  setProductionage(int Productionage) {this.Productionage = Productionage;}
    public void setMileage(int Mileage) {this.Mileage = Mileage;}
    public void setHasAccident(boolean HasAccident) {this.HasAccident = HasAccident;}
}
