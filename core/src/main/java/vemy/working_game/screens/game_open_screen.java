package vemy.working_game.screens;

import com.badlogic.gdx.ScreenAdapter;

import vemy.working_game.my_game;

public class game_open_screen extends ScreenAdapter {
    /*
     * This class will be the first screen that the player sees when they open the game.
     * It will have a button that will ask player for login or sign up.
     * It will also have a button that will allow the player to play the game without logging in.
     * In case of playing without logging in, default player profile must be loaded. And the player will be taken to the level select screen
     */

    private my_game game;

    public game_open_screen(my_game game) {
        this.game = game;
    }

    
}
