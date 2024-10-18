package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class MyGame extends Game {
    private FirstScreen firstScreen;
    private LoadingScreen loadingScreen;
    private AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();

        // Create instances of your screens
        loadingScreen = new LoadingScreen(this, assetManager);
        firstScreen = new FirstScreen(this);

        setScreen(loadingScreen);
    }

    public void switchToFirstScreen() {
        setScreen(firstScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (loadingScreen != null) loadingScreen.dispose();
        if (firstScreen != null) firstScreen.dispose();
        assetManager.dispose();
    }
}
