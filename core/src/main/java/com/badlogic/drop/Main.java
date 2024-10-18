package com.badlogic.drop;

import com.badlogic.gdx.Game;

public class Main extends Game {
    private MyGame myGame;

    @Override
    public void create() {
        // Initialize the game logic by creating an instance of MyGame
        myGame = new MyGame();

        // Call the create method on MyGame to set up screens
        myGame.create();
    }

    @Override
    public void render() {
        // Delegate rendering to MyGame, which handles the current screen's rendering
        myGame.render();
    }

    @Override
    public void dispose() {
        // Dispose of resources in MyGame
        myGame.dispose();
    }
}
