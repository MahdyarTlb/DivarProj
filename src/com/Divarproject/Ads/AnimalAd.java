package com.Divarproject.Ads;

import com.Divarproject.Register.User;

public class AnimalAd extends Ad {

    private String AnimalType;
    private int Age;
    private boolean isVaccinated;

    //constructor
    public AnimalAd(String Name, String Description,
                    int Price, String Contact, User Owner,
                    String ImagePath, String AnimalType, int Age, boolean isVaccinated) {
        super(Name, Description, Price, Contact, Owner, ImagePath, AdType.ANIMAL);
        this.AnimalType = AnimalType;
        this.Age = Age;
        this.isVaccinated = isVaccinated;
    }

    //Getter & Setter
    public String getAnimalType() {return AnimalType;}
    public int getAge() {return Age;}
    public boolean getIsVaccinated() {return isVaccinated;}

    public void setAnimalType(String AnimalType) {this.AnimalType = AnimalType;}
    public void setAge(int Age) {this.Age = Age;}
    public void setVaccinated(boolean isVaccinated) {this.isVaccinated = isVaccinated;}


    @Override
    public void displayDetails() {
        System.out.println("Animal Type: " + AnimalType);
        System.out.println("Age: " + Age);
        System.out.println("isVaccinated: " + ((isVaccinated) ?  "Yes" : "No"));
    }
}
