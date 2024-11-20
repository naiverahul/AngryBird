package vemy.working_game.screens;

import com.badlogic.gdx.ScreenAdapter;

import vemy.working_game.my_game;

public class login_screen extends ScreenAdapter{

    private my_game game;

    public login_screen(my_game game) {
        this.game = game;
    }
    /*
     * This class will be the screen where the player will login to the game.
     * It would have fields for username and password.
     * It will also have a button to sign up and a button to go back to the game open screen.
     * If login is successful, the corresponding profile will be loaded. Profile will have the player's progress in the game, setting preferences, etc. The player will be taken to the level select screen.
     * If login is unsuccessful, an error message will be displayed.
     */
}
