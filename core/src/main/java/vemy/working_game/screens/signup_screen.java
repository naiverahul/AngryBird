package vemy.working_game.screens;

import com.badlogic.gdx.ScreenAdapter;

import vemy.working_game.my_game;

public class signup_screen extends ScreenAdapter{

    private my_game game;

    public signup_screen(my_game game) {
        this.game = game;
    }
    /*
     * This class will be the screen where the player will sign up for the game.
     * It would have fields for username, password, email, and other necessary information.
     * It will also have a button to login and a button to go back to the game open screen.
     * If sign up is successful, a new profile will be created for the player. The player will be taken to the level select screen.
     */
}
