package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class MyGame extends Game {
    private FirstScreen firstScreen;
    private LoadingScreen loadingScreen;
    private AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();

        loadingScreen = new LoadingScreen(this, assetManager);
        firstScreen = new FirstScreen(this);

        setScreen(loadingScreen);
    }

    public void switchToFirstScreen() {
        // Switch to FirstScreen when ready
        setScreen(firstScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
    }
}
