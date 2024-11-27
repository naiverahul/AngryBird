package com.badlogic.drop.user;

import com.badlogic.drop.screens.game_screen;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    // private static final String DATA_FILE = "C:\\Users\\thera\\OneDrive\\Desktop\\2023418_2023610\\AngryBird\\core\\src\\main\\data.ser";
    // private static final String DATA_FILE = "./data.ser";
    private static final String DATA_FILE = "C:\\Users\\thera\\OneDrive\\Desktop\\New folder\\AngryBird\\data.ser";
    private static DataManager instance;
    private Map<String, User> userMap;

    private DataManager() {
        userMap = loadData();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private Map<String, User> loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new HashMap<>(); // Return empty data if file doesn't exist
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>(); // Return empty data in case of error
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String name) {
        return userMap.containsKey(name);
    }

    public void addUser(String name) {
        userMap.put(name, new User(name));
        saveData();
    }

    public User getUser(String name) {
        return userMap.get(name);
    }
    public void customexit(){
        saveData();
        Gdx.app.exit();
    }
}
