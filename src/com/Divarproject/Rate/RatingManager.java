package com.Divarproject.Rate;

import com.Divarproject.Register.User;
import com.Divarproject.Register.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RatingManager {
    private static final String FILE_PATH = "Ratings.txt";

    // ذخیره امتیازات
    public static void saveRating(List<Rating> ratings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Rating rating : ratings) {
                writer.write(rating.getGiver().getUserName() + "," +
                        rating.getReceiver().getUserName() + "," +
                        rating.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("خطا در ذخیره امتیازات: " + e.getMessage());
        }
    }

    // بارگذاری امتیازات
    public static List<Rating> loadRatings(List<User> users) {
        List<Rating> ratings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    User giver = UserManager.FindUserByUserName(users, parts[0]);
                    User receiver = UserManager.FindUserByUserName(users, parts[1]);
                    int score = Integer.parseInt(parts[2]);

                    if (giver != null && receiver != null) {
                        ratings.add(new Rating(giver, receiver, score));
                    } else {
                        System.err.println("کاربر یافت نشد: " + parts[0] + " یا " + parts[1]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("خطا در بارگذاری امتیازات: " + e.getMessage());
        }
        return ratings;
    }

    // محاسبه میانگین امتیاز یک کاربر
    public static double getAverageRating(User user, List<Rating> ratings) {
        double total = 0;
        int count = 0;
        for (Rating rating : ratings) {
            if (rating.getReceiver().equals(user)) {
                total += rating.getScore();
                count++;
            }
        }

        return (count == 0) ? 0 : (total / count);
    }
}