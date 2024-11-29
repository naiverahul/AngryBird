package com.badlogic.drop.user;

import java.io.Serializable;
import com.badlogic.drop.screens.game_screen;
import com.badlogic.drop.MyGame;

public class User implements Serializable {
    private String name;
    private int level;
    private int score;
    private GameState gameState;
//    private MyGame game;

    public User(String name) {
        this.name = name;
        this.level = 1;
        this.score = 0;
        this.gameState = new GameState();
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
    public GameState getGameState() {
        return gameState;
    }
    public  void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    public void incrementLevel() {
        level++;
        score += 100;
    }
}
