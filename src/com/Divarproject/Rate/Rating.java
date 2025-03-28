package com.Divarproject.Rate;

import com.Divarproject.Register.User;

public class Rating {
    private User giver;
    private User receiver;
    private int score;

    public Rating(User giver, User receiver, int score) {
        this.giver = giver;
        this.receiver = receiver;
        setScore(score); // اعتبارسنجی امتیاز
    }

    // Getter و Setter
    public User getGiver() {
        return giver;
    }

    public User getReceiver() {
        return receiver;
    }

    public int getScore() {
        return score;
    }

    public void setGiver(User giver) {
        this.giver = giver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setScore(int score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("امتیاز باید بین ۱ تا ۵ باشد!");
        }
        this.score = score;
    }
}