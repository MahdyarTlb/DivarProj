package com.Divarproject.Data;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Rate.Rating;
import com.Divarproject.Register.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static DataManager instance = new DataManager();

    private List<User> users = new ArrayList<>();
    private List<Ad> ads = new ArrayList<>();
    private List<Rating> ratings = new ArrayList<>();

    // Private constructor for Singleton
    private DataManager() {
    }

    public static DataManager getInstance() {
        return instance;
    }

    // Getters and Setters
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
