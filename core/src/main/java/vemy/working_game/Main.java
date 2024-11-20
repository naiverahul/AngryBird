package vemy.working_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import vemy.working_game.managers.asset_manager;

public class Main extends Game {

    private my_game myGame;
    // private asset_manager asger = asset_manager.get_instance();
    // {
    //     asger.load_assets();
    //     asger.finish_loading();
    // }

    @Override
    public void create() {
        // Initialize the game logic by creating an instance of MyGame
        myGame = new my_game();

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