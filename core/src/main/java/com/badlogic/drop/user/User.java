package com.badlogic.drop.user;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int level;
    private int score;
    public User(String name) {
        this.name = name;
        this.level = 1;
        this.score = 0;
    }
    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public int getScore() {
        return score;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void incrementLevel() {
        level++;
    }

}
