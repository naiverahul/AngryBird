package com.badlogic.drop.user;

import java.io.Serializable;
import com.badlogic.drop.screens.game_screen;
public class User implements Serializable {
    private String name;
    private int level;
    private int score;
    private game_screen game_screen;
    public User(String name) {
        this.name = name;
        this.level = 1;
        this.score = 0;
        this.game_screen = null;
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
    public  void setGame_screen(game_screen game_screen){
        this.game_screen = game_screen;
    }
    public game_screen getGame_screen(){
        return game_screen;
    }
    public void incrementLevel() {
        level++;
        score += 100;
    }

}
