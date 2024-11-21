package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {
    private MyGame myGame;
    public static final String title = "Udja Kale Kaua";

    @Override
    public void create() {
            Gdx.app.log(title, ": create()");
            myGame = new MyGame();

        // Call the create method on MyGame to set up screens
        myGame.create();
    }

    @Override
    public void render() {
//        Gdx.app.log(title, ": render()");

        // Delegate rendering to MyGame, which handles the current screen's rendering
        myGame.render();
    }

    @Override
    public void dispose() {
        // Dispose of resources in MyGame
//        Gdx.app.log(title, ": dispose()");

        myGame.dispose();
    }

    @Override
    public void resize(int width, int height) {
        // Delegate resizing to MyGame
//        Gdx.app.log(title, ": resize()");

        myGame.resize(width, height);
    }
}
