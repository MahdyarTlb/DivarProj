package com.Divarproject.Register;

public class User {
    String UserName;
    String Password;
    int Score;

    //Constructor
    public User(String UserName, String Password){
        this.UserName = UserName;
        this.Password = Password;
        this.Score = 0;
    }

    //Getters & Setters
    public String getUserName(){
        return UserName;
    }

    public String getPassword(){
        return Password;
    }

    public int getScore(){
        return Score;
    }

    public void setScore(int Score){
        this.Score = Score;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return UserName.equals(user.UserName); // مقایسه بر اساس نام کاربری
    }

    @Override
    public int hashCode() {
        return UserName.hashCode(); // هش بر اساس نام کاربری
    }
}
