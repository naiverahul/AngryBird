package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class MyGame extends Game {
    // protected FirstScreen firstScreen;
    protected LoadingScreen loadingScreen;
    protected level_screen level_screen;
    protected level_screen pause_screen;
    protected SettingsScreen settingsScreen;
    private AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();

        // Create instances of your screens
        loadingScreen = new LoadingScreen(this, assetManager);
        // firstScreen = new FirstScreen(this);
        level_screen = new level_screen(this, assetManager);
        settingsScreen = new SettingsScreen();
        pause_screen = level_screen;
        // setScreen(loadingScreen);
        // setScreen(level_screen);
        switch_screen(loadingScreen);
    }

    public void switch_screen(Screen target_screen) {
        // setScreen(firstScreen);
        // setScreen(level_screen);
        setScreen(target_screen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (loadingScreen != null) loadingScreen.dispose();
        // if (firstScreen != null) firstScreen.dispose();
        if (level_screen != null) level_screen.dispose();
        if(settingsScreen != null) settingsScreen.dispose();
        assetManager.dispose();
    }
}
