package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private AssetManager assetManager;

    @Override
    public void create() {
    MyGame game = new MyGame();
    game.create();
    }
}
